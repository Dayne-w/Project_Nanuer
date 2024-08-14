package server.nanuer_server.service.post;

import server.nanuer_server.config.BaseException;
import server.nanuer_server.domain.Progress;
import com.example.nanuer_server.domain.entity.*;
import com.example.nanuer_server.domain.repository.*;
import com.example.nanuer_server.dto.Post.*;
import server.nanuer_server.service.heart.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.nanuer_server.config.BaseResponseStatus;
import server.nanuer_server.domain.entity.CategoryEntity;
import server.nanuer_server.domain.entity.HeartEntity;
import server.nanuer_server.domain.entity.PostEntity;
import server.nanuer_server.domain.entity.UserEntity;
import server.nanuer_server.domain.repository.CategoryRepository;
import server.nanuer_server.domain.repository.HeartRepository;
import server.nanuer_server.domain.repository.PostRepository;
import server.nanuer_server.domain.repository.UserRepository;
import server.nanuer_server.dto.Post.CreatePostReqDto;
import server.nanuer_server.dto.Post.GetPostResDto;
import server.nanuer_server.dto.Post.UpdatePostReqDto;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final HeartRepository heartRepository;
    private final HeartService heartService;

    public List<PostEntity> getAllPosts() throws BaseException {
        return postRepository.findAllOrderByPostIdDesc();
    }

    public List<PostEntity> getPostList(int user_id, String query) throws BaseException {
        return postRepository.findAll(user_id, query);
    }

    public int createPost(CreatePostReqDto createPostReqDto) throws BaseException {
        UserEntity userEntity = userRepository.getReferenceById(createPostReqDto.getUserId());
        CategoryEntity categoryEntity = categoryRepository.getReferenceById(createPostReqDto.getCategoryId());

        createPostReqDto.setUserEntity(userEntity);
        createPostReqDto.setCategoryEntity(categoryEntity);

        return postRepository.save(createPostReqDto.toEntity()).getPostId();

    }


    @Transactional
    public GetPostResDto getPost(int post_id) throws BaseException {
        PostEntity postEntity = postRepository.findById(post_id)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.POST_POST_EMPTY_POST));

        postEntity.increaseView();

        return new GetPostResDto(postEntity);
    }

    @Transactional
    public int updatePost(int post_id, UpdatePostReqDto updatePostReqDto) throws BaseException {
        PostEntity postEntity = postRepository.findById(post_id)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.POST_POST_EMPTY_POST));

        postEntity.update(updatePostReqDto);

        return post_id;
    }

    @Transactional
    public int deletePost(int post_id) throws BaseException {
        PostEntity postEntity = postRepository.findById(post_id)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.POST_POST_EMPTY_POST));

        for (HeartEntity heartEntity : heartRepository.findAllByPostId(postEntity.getPostId())) {
            heartRepository.delete(heartEntity);
        }

        postEntity.delete();
        return post_id;
    }

    public Progress updateProgress(int post_id, int progressId) throws BaseException {
        PostEntity postEntity = postRepository.findById(post_id)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.POST_POST_EMPTY_POST));

        if (progressId == 1) {
            postEntity.setProgress(Progress.Confirm);
            postRepository.save(postEntity);
            return Progress.Confirm;
        }
        else if (progressId == 2) {
            postEntity.setProgress(Progress.End);
            postRepository.save(postEntity);
            return Progress.End;
        }

        return Progress.Recruit;
    }
}