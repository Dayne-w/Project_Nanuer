package server.nanuer_server.service.user;

import server.nanuer_server.config.BaseException;

import server.nanuer_server.config.user.JwtTokenProvider;
import server.nanuer_server.domain.entity.PostEntity;
import server.nanuer_server.domain.entity.UserEntity;
import server.nanuer_server.domain.repository.UserRepository;
import server.nanuer_server.dto.user.GetUserInfoRes;
import server.nanuer_server.dto.user.JoinUserDto;
import server.nanuer_server.dto.user.LoginUserDto;
import server.nanuer_server.dto.user.UserInfoDto;
import server.nanuer_server.service.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.nanuer_server.config.BaseResponseStatus;


import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PostService postService;

    //회원가입

    public UserEntity signup(JoinUserDto userDto) throws BaseException {
        String email = userDto.getEmail();
        if (userRepository.findByEmail(userDto.getEmail()).orElse(null) != null) {
            throw new BaseException(BaseResponseStatus.POST_USERS_EXISTS_EMAIL);
            //이메일 중복 validation
        }
        else if(userRepository.findByPhone(userDto.getPhone()).orElse(null) != null){
            throw new BaseException(BaseResponseStatus.POST_USERS_EXISTS_PHONE);
            //휴대폰 중복 validation
        }
        try{
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            return userRepository.save(userDto.toEntity());
        }
        catch (Exception exception) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    //로그인
    public UserInfoDto login(LoginUserDto loginUserDto) throws BaseException {
        UserEntity userEntity = userRepository.findByEmail(loginUserDto.getEmail()).orElseThrow(
                () -> new BaseException(BaseResponseStatus.FAILED_TO_LOGIN)
        );
        if (!passwordEncoder.matches(loginUserDto.getPassword(), userEntity.getPassword())) {
            throw new BaseException(BaseResponseStatus.FAILED_TO_LOGIN);
        }
        UserInfoDto userInfoDto = userEntity.toDto();
        return userInfoDto;
    }

    //삭제
    public void delete(String email) throws BaseException {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        if(!userEntity.isPresent()) {
            throw new BaseException(BaseResponseStatus.USERS_EMPTY_USER_EMAIL);
        }
        for(PostEntity postEntity : userEntity.get().getPostEntities()){
            postService.deletePost(postEntity.getPostId());
        }
        userRepository.delete(userEntity.get());
    }

    //비활성화, 활성화
    public void UserStatus(String email) throws BaseException {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        if(userEntity.isEmpty()) {
            throw new BaseException(BaseResponseStatus.USERS_EMPTY_USER_EMAIL);
        }
        else{
            if(Objects.equals(userEntity.get().getUserStatus(), "active")) {
                userEntity.get().status("inactive");
            }
            else if(Objects.equals(userEntity.get().getUserStatus(), "inactive")){
                userEntity.get().status("active");
            }
        }
    }

    //유저 정보 가져오기
    public UserInfoDto GetUser(String email) throws BaseException {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        UserInfoDto userInfoDto = userEntity.get().toDto();
        if(!userEntity.isPresent()) {
            throw new BaseException(BaseResponseStatus.USERS_EMPTY_USER_EMAIL);
        }
        return userInfoDto;
    }

    //유저 정보 return 값 특정해주셔서 만든 service 함수 입니다!!
    public GetUserInfoRes GetUser2(String email) throws BaseException {
        UserEntity userEntity = userRepository.findByEmail(email).get();
        GetUserInfoRes userInfoDto = new GetUserInfoRes();
        userInfoDto.setBirth(userEntity.getBirth());
        userInfoDto.setEmail(userEntity.getEmail());
        userInfoDto.setName(userEntity.getName());
        userInfoDto.setPhone(userEntity.getPhone());
        userInfoDto.setUniversity(userEntity.getUniversity());
        userInfoDto.setProfileImg(userEntity.getProfileImg());
        userInfoDto.setNickName(userEntity.getNickName());
        userInfoDto.setPassword(userEntity.getPassword());
        if(!userEntity.isPresent()) {
            throw new BaseException(BaseResponseStatus.USERS_EMPTY_USER_EMAIL);
        }
        return userInfoDto;
    }

    public UserInfoDto GetUserByPhone(String phone) throws BaseException {
        Optional<UserEntity> userEntity = userRepository.findByPhone(phone);
        UserInfoDto userInfoDto = userEntity.get().toDto();
        if(!userEntity.isPresent()) {
            throw new BaseException(BaseResponseStatus.USERS_EMPTY_USER_EMAIL);
        }
        return userInfoDto;
    }

    //비밀번호 재설정
    // 재설정 페이지 전에 받은 폰번호로 회원 찾기
    public void ModifyPw(String phone, String password) throws BaseException {
        Optional<UserEntity> userEntity = userRepository.findByPhone(phone);
        if(userEntity.isEmpty()) {
            throw new BaseException(BaseResponseStatus.USERS_EMPTY_USER_EMAIL);
        }
        else{
            userEntity.get().upDatePw(" ");
            userEntity.get().upDatePw(passwordEncoder.encode(password));
        }

    }

    public int GetHeaderAndGetUser(HttpServletRequest request) throws BaseException {
        String token = request.getHeader("X-AUTH-TOKEN");
        String userEmail = jwtTokenProvider.getUserPk(token);
        Optional<UserEntity> userEntity = userRepository.findByEmail(userEmail);
        int userId = userEntity.get().getUserId();
        return userId;
    }

    public Boolean UserAuth(HttpServletRequest request)throws BaseException{
        String token = request.getHeader("X-AUTH-TOKEN");
        if (jwtTokenProvider.getUserPk(token) != null){
            return true;
        }
        else return false;
    }

}
