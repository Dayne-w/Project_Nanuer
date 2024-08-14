package server.nanuer_server.service.User;

import server.nanuer_server.config.User.CustomUserDetails;
import server.nanuer_server.domain.entity.UserEntity;
import server.nanuer_server.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    //로그인할 때 들어온 username으로 DB에서 정보 찾기
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당 사용자가 존재하지 않습니다. : " + email));

        //UserDetailsImpl에서 정의한 생성자
        return new CustomUserDetails(userEntity);
    }
}