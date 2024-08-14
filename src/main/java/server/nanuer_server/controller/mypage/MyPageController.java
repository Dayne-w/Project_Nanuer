package server.nanuer_server.controller.mypage;

import server.nanuer_server.config.BaseException;
import server.nanuer_server.config.BaseResponse;
import server.nanuer_server.config.User.JwtTokenProvider;
import server.nanuer_server.domain.entity.PostEntity;
import server.nanuer_server.dto.User.UserInfoDto;
import server.nanuer_server.service.User.UserService;
import server.nanuer_server.service.mypage.MyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {
    private final MyPageService myPageService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;


    @GetMapping("/my-posts")
    public BaseResponse<Map<String, List<PostEntity>>> getMyPosts(HttpServletRequest request){
        String token = request.getHeader("X-AUTH-TOKEN");
        String email = jwtTokenProvider.getUserPk(token);

        Map<String, List<PostEntity>> response = new HashMap<>();
        response.put("postList", myPageService.getMyPosts(email));
        //List<PostDto> postDtoList = myPageService.getMyPosts(email);
        return new BaseResponse<>(response);
    }

    @GetMapping("/heart-posts")
    public BaseResponse<Map<String, List<PostEntity>>> getHeartPosts(HttpServletRequest request){
        String token = request.getHeader("X-AUTH-TOKEN");
        String email = jwtTokenProvider.getUserPk(token);

        Map<String, List<PostEntity>> response = new HashMap<>();
        response.put("postList", myPageService.getHeartPosts(email));
        //List<PostDto> postDtoList = myPageService.getHeartPosts(email);
        return new BaseResponse<>(response);
    }

    @PatchMapping("/update-user")
    public BaseResponse<UserInfoDto> updatedUser(HttpServletRequest request, @RequestBody UserInfoDto userInfoDto){
        String token = request.getHeader("X-AUTH-TOKEN");
        String email = jwtTokenProvider.getUserPk(token);
        try {
            var updatedUser = myPageService.updateUser(userInfoDto, email);
            return new BaseResponse<>(updatedUser);
        } catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

}
