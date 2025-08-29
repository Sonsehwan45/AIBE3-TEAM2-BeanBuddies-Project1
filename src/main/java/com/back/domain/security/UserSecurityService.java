package com.back.domain.security;

import com.back.domain.entity.SiteUser;
import com.back.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor // final 필드를 포함하는 생성자를 자동으로 만들어줍니다.
@Service // 이 클래스가 스프링의 서비스임을 나타냅니다.
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository; // 사용자 정보를 조회하기 위한 리포지토리

    // 사용자 이름(username)으로 사용자 정보를 조회하는 메서드 (Spring Security가 내부적으로 호출)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // userRepository를 통해 username으로 SiteUser 객체를 조회합니다. 결과는 Optional 타입입니다.
        Optional<SiteUser> _siteUser = this.userRepository.findByUsername(username);
        // 만약 사용자가 존재하지 않으면 예외를 발생시킵니다.
        if (_siteUser.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }
        // Optional에서 실제 SiteUser 객체를 꺼냅니다.
        SiteUser siteUser = _siteUser.get();
        // 사용자의 권한을 담을 리스트를 생성합니다.
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 모든 사용자에게 "ROLE_USER"라는 권한을 부여합니다.
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        // Spring Security가 사용하는 User 객체를 생성하여 반환합니다. (사용자이름, 암호화된 비밀번호, 권한)
        return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
    }
}