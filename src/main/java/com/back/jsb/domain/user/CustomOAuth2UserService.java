package com.back.jsb.domain.user;

import com.back.jsb.global.security.UserSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest req) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(req);
        String regId = req.getClientRegistration().getRegistrationId(); // "kakao"

        if (!"kakao".equals(regId)) throw new OAuth2AuthenticationException("Unsupported provider: " + regId);

        Map<String, Object> attr = oAuth2User.getAttributes();
        String kakaoId = String.valueOf(attr.get("id"));

        Map<String, Object> kakaoAccount = (Map<String, Object>) attr.get("kakao_account");
        Map<String, Object> profile = kakaoAccount != null ? (Map<String, Object>) kakaoAccount.get("profile") : null;
        String nickname = profile != null ? (String) profile.get("nickname") : null;

        // 1) provider+uid로 탐색
        User user = userRepository
                .findByProviderAndProviderUserId(User.AuthProvider.KAKAO, kakaoId)
                .orElseGet(() -> {
                    String randomRaw = User.newRandomString();
                    String randomHash = passwordEncoder.encode(randomRaw);
                    return userRepository.save(User.fromKakao(kakaoId, nickname, randomHash));
                });

        // 폼 로그인 차단을 원하면 UserDetailsService 쪽에서 provider 검사로 DisabledException 던지기

        return new UserSecurity(user, attr);
    }
}