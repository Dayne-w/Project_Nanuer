package com.example.nanuer_server.controller.User;

import com.example.nanuer_server.config.BaseException;
import com.example.nanuer_server.config.BaseResponse;
import com.example.nanuer_server.domain.entity.User;
import com.example.nanuer_server.domain.entity.UserRole;
import com.example.nanuer_server.dto.User.JoinUserDto;

import com.example.nanuer_server.dto.User.UserInfoDto;
import com.example.nanuer_server.service.User.UserService;
import com.example.nanuer_server.config.User.JwtTokenProvider;
import com.example.nanuer_server.dto.User.LoginUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;


@Log4j2
@RequestMapping("/user")
@RequiredArgsConstructor
@RestController
public class UserController {


    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("")
    public String hello() {
        return "hello";
    }


    @ResponseBody
    @PostMapping("/join")
    public BaseResponse<User> join(@RequestBody JoinUserDto userDto) throws BaseException {
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordzEncoder();
        //UserEntity userEntity = UserEntity.createUser(userDto);

        try {
            User userEntity = userService.signup(userDto);
            return new BaseResponse<>(userEntity);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //로그인

    @PostMapping("/login")
    @ResponseBody
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


    //삭제
    @DeleteMapping("")
    public BaseResponse<String> delete(@RequestParam String email) {
        try{
            userService.delete(email);
            String result = email + " 해당 유저가 삭제되었습니다.";
            return new BaseResponse<>(result);

        }
        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //유저 상태관리
    @PatchMapping("/status")
    public BaseResponse<String> UserStatus(@RequestParam String email) {
        try{
            userService.UserStatus(email);
            UserInfoDto userInfoDto  = userService.GetUser(email);
            String status = userInfoDto.getUserStatus();
            String result = email + " 해당 유저가 " + status + " 되었습니다.";
            return new BaseResponse<>(result);

        }
        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //유저 정보 조회
    @GetMapping("/info")
    public BaseResponse<UserInfoDto> GetUser(@RequestParam String email) {
        try{
            UserInfoDto userInfoDto = userService.GetUser(email);

            return new BaseResponse<>(userInfoDto);

        }
        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }



}