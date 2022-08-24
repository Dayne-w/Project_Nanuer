package com.example.nanuer_server.controller;

import com.example.nanuer_server.config.BaseException;
import com.example.nanuer_server.config.BaseResponse;
import com.example.nanuer_server.config.User.JwtTokenProvider;
import com.example.nanuer_server.domain.entity.UserEntity;
import com.example.nanuer_server.domain.entity.UserRole;
import com.example.nanuer_server.dto.User.JoinUserDto;
import com.example.nanuer_server.dto.User.LoginUserDto;
import com.example.nanuer_server.dto.User.UserInfoDto;
import com.example.nanuer_server.service.User.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@RequiredArgsConstructor
@RestController
public class PermitController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @ResponseBody
    @PostMapping("/join")
    public BaseResponse<UserEntity> join(@RequestBody JoinUserDto joinUserDto) {
        try {
            UserEntity userEntity = userService.signup(joinUserDto);
            return new BaseResponse<>(userEntity);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //로그인
    @ResponseBody
    @PostMapping("/login")
    public BaseResponse<String> login(@RequestBody LoginUserDto loginUserDto) {
        try {

            UserInfoDto userInfoDto = userService.login(loginUserDto);
            String Email = userInfoDto.getEmail();
            UserRole role = userInfoDto.getRole();
            String result = jwtTokenProvider.createToken(Email, role);
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //아이디 찾기
    @GetMapping("/getEmail")
    public BaseResponse<String> GetUserEmail(@RequestParam String phone){
        try{
            UserInfoDto userInfoDto = userService.GetUserByPhone(phone);
            String result =  userInfoDto.getEmail();
            return  new BaseResponse<>(result);

        }
        catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //비밀번호 재설정
    @PatchMapping("/updatePw")
    public BaseResponse<String> ModifyPw(@RequestParam String phone, String password){
        try {
            userService.ModifyPw(phone,password);
            UserInfoDto userInfoDto  = userService.GetUserByPhone(phone);
            String result = "새 비밀번호 : " + userInfoDto.getPassword();
            return new BaseResponse<>(result);
        } catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


}
