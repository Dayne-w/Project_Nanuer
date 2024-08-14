package server.nanuer_server.controller.User;
import server.nanuer_server.config.BaseException;
import server.nanuer_server.config.BaseResponse;
import server.nanuer_server.dto.User.GetUserInfoRes;
import server.nanuer_server.dto.User.UserInfoDto;
import server.nanuer_server.service.User.UserService;
import server.nanuer_server.config.User.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


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

    //삭제
    @DeleteMapping("")
    public BaseResponse<String> delete(HttpServletRequest request) {
        String token = request.getHeader("X-AUTH-TOKEN");
        String email = jwtTokenProvider.getUserPk(token);
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
    public BaseResponse<String> UserStatus(HttpServletRequest request) {
        String token = request.getHeader("X-AUTH-TOKEN");
        String email = jwtTokenProvider.getUserPk(token);
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
    public BaseResponse<GetUserInfoRes> GetUser(HttpServletRequest request) {
        String token = request.getHeader("X-AUTH-TOKEN");
        String email = jwtTokenProvider.getUserPk(token);
        try{
            GetUserInfoRes userInfoDto = userService.GetUser2(email);

            return new BaseResponse<>(userInfoDto);

        }
        catch (BaseException exception) {
            System.out.println("error");
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //jwt 토근 헤더에서 가져와 사용자 이메일 조회
    @ResponseBody
    @GetMapping("/userInfo")
    public BaseResponse<Integer> getUserInfoByJwt(HttpServletRequest request) throws BaseException {
        int userId = userService.GetHeaderAndGetUser(request);
        return new BaseResponse<>(userId);
    }

    @ResponseBody
    @GetMapping("/AuthTest")
    public BaseResponse<Boolean> getUserAuth(HttpServletRequest request) throws BaseException {
        Boolean result = userService.UserAuth(request);
        return new BaseResponse<>(result);
    }


}