package server.nanuer_server.controller.post;

import server.nanuer_server.config.BaseException;
import server.nanuer_server.config.BaseResponse;
import server.nanuer_server.domain.Progress;
import server.nanuer_server.domain.entity.ChatRoomEntity;
import server.nanuer_server.domain.entity.PostEntity;
import server.nanuer_server.domain.repository.Chat.ChatRoomRepository;
import server.nanuer_server.domain.repository.PostRepository;
import server.nanuer_server.domain.repository.UserRepository;
import server.nanuer_server.service.post.PostService;
import server.nanuer_server.service.user.UserService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import server.nanuer_server.config.BaseResponseStatus;
import server.nanuer_server.dto.post.CreatePostReqDto;
import server.nanuer_server.dto.post.GetPostResDto;
import server.nanuer_server.dto.post.UpdatePostReqDto;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    @Autowired
    private final PostService postService;
    private final UserService userService;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @GetMapping("/test")
    public String testPostController() {
        return "success";
    }

    /* 게시물 리스트 조회 */
    @GetMapping("")
    @JsonIgnore
    public BaseResponse<Map<String, List<PostEntity>>> getPostList(HttpServletRequest request, @RequestParam String query) {
        try {
            int user_id = userService.GetHeaderAndGetUser(request);
            Map<String, List<PostEntity>> response = new HashMap<String, List<PostEntity>>();
            List<PostEntity> posts = postService.getPostList(user_id, query);
            response.put("postList", posts);

            return new BaseResponse<>(response);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /* 게시물 등록 */
    @PostMapping("")
    public BaseResponse<String> createPost(HttpServletRequest request, @RequestBody CreatePostReqDto createPostReqDto) throws BaseException  {
        int user_id = userService.GetHeaderAndGetUser(request);
        createPostReqDto.setUserId(user_id);
        try {
            if (createPostReqDto.getTitle() == null || createPostReqDto.getTitle().length() > 200) {
                return new BaseResponse<>(BaseResponseStatus.POST_POST_INVALID_TITLE);
            }
            if (createPostReqDto.getContent() == null || createPostReqDto.getContent().length() > 1000) {
                return new BaseResponse<>(BaseResponseStatus.POST_POST_INVALID_CONTENT);
            }
            if (createPostReqDto.getCategoryId() < 1 || createPostReqDto.getCategoryId() > 5)
                return new BaseResponse<>(BaseResponseStatus.POST_POST_INVALID_CATEGORY);

            int post_id = postService.createPost(createPostReqDto);
            String result = "post_id = " + post_id + " 게시물 등록 성공";

            //채팅 생성 추가
            ChatRoomEntity chatRoomEntity = ChatRoomEntity.create(true,userRepository.findByUserId(user_id).get(), postRepository.findByPostId(post_id));
            chatRoomRepository.save(chatRoomEntity);

            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /* 게시물 조회 */
    @GetMapping("/{post_id}")
    public BaseResponse<GetPostResDto> getPost(@PathVariable int post_id)  {
        try {
            GetPostResDto post = postService.getPost(post_id);
            return new BaseResponse<>(post);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /* 게시물 수정 */
    @PutMapping("/{post_id}")
    public BaseResponse<String> updatePost(@PathVariable int post_id, @RequestBody UpdatePostReqDto updatePostReqDto) {
        try {
            String result = "post_id = " + postService.updatePost(post_id, updatePostReqDto) + " 수정 완료";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

    }

    /* 게시물 삭제 */
    @PatchMapping("/{post_id}")
    public BaseResponse<String> deletePost(@PathVariable int post_id) {
        try {
            String result = "post_id = " + postService.deletePost(post_id) + " 삭제 완료";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /* 게시물 진행 상태를 '거래 완료' 로 변경 */
    @PatchMapping("/progress")
    public BaseResponse<Progress> updateProgress(@RequestParam int post_id) {
        try {
            return new BaseResponse<>(postService.updateProgress(post_id, 2));
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

}