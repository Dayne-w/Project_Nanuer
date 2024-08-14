package server.nanuer_server.controller.heart;

import server.nanuer_server.config.BaseException;
import server.nanuer_server.config.BaseResponse;
import server.nanuer_server.dto.heart.AddHeartDto;
import server.nanuer_server.dto.heart.HeartDto;
import server.nanuer_server.service.heart.HeartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/heart")
@RequiredArgsConstructor
public class HeartController {
    private final HeartService heartService;

    // 내가 찜한 게시물 조회는 마이페이지 서비스에서 구현함.

    //AddHeartDto 요청할 때 "userId"와 "postId"만 json으로 넘기면 됨
    @PostMapping("/add")
    public BaseResponse<HeartDto> addHeart(HttpServletRequest request, @RequestBody AddHeartDto addheartDto){
        try {
            HeartDto addedHeartDto = heartService.addHeart(request, addheartDto);
            return new BaseResponse<>(addedHeartDto);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @DeleteMapping("/delete/{post_id}")
    public BaseResponse<String> deleteHeart(HttpServletRequest request, @PathVariable(name = "post_id") int postId){
        try {
            heartService.deleteHeart(request, postId);
            String message = "찜이 해제되었습니다.";
            return new BaseResponse<>(message);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
