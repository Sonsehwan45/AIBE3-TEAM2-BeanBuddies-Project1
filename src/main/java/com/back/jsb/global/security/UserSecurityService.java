package com.back.jsb.global.security;

import com.back.jsb.domain.user.User;
import com.back.jsb.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 스프링 시큐리티가 로그인 시 DB에서 사용자 정보를 가져올 때 호출되는 서비스
 * username으로 DB 조회 -> User 객체 가져오는 역할
 * 있다면 User객체를 PrincipalDetails로 감싸서 반환
 */
@Service
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        return new UserSecurity(user);
    }
}
