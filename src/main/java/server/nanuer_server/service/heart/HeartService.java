package server.nanuer_server.service.heart;

import server.nanuer_server.config.BaseException;
import server.nanuer_server.config.BaseResponseStatus;
import server.nanuer_server.config.user.JwtTokenProvider;
import server.nanuer_server.domain.entity.HeartEntity;
import server.nanuer_server.domain.entity.PostEntity;
import server.nanuer_server.domain.entity.UserEntity;
import server.nanuer_server.domain.repository.HeartRepository;
import server.nanuer_server.domain.repository.PostRepository;
import server.nanuer_server.domain.repository.UserRepository;
import server.nanuer_server.dto.heart.AddHeartDto;
import server.nanuer_server.dto.heart.HeartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

// 게시물이 삭제되면 heart도 같이 삭제
@Service
@Transactional
@RequiredArgsConstructor
public class HeartService {
    private final HeartRepository heartRepository;

    private  final PostRepository postRepository;

    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;
    //
    public HeartDto addHeart(HttpServletRequest request, AddHeartDto addheartDto) throws BaseException {
        if(postRepository.findByPostId(addheartDto.getPostId()).getPostStatus() ==0){
            throw new BaseException(BaseResponseStatus.USER_USER_EMPTY_USER);
        }
        String token = request.getHeader("X-AUTH-TOKEN");
        String email  = jwtTokenProvider.getUserPk(token);
        UserEntity userEntity = userRepository.findByEmail(email).get();
        PostEntity postEntity = postRepository.getReferenceById(addheartDto.getPostId());

        addheartDto.setUserEntity(userEntity);
        addheartDto.setPostEntity(postEntity);

        HeartEntity heartEntity = addheartDto.toEntity();

        PostEntity heartPostEntity = heartEntity.getPostEntity();
        heartPostEntity.setHeartCount(heartPostEntity.getHeartCount()+1);
        heartRepository.save(heartEntity);
        return heartEntity.toDto();
    }

    public void deleteHeart(HttpServletRequest request, int postId) throws BaseException {
        String token = request.getHeader("X-AUTH-TOKEN");
        String email = jwtTokenProvider.getUserPk(token);
        List<HeartEntity> heartEntities = heartRepository.findByUserEmail(email);
        for(int i = 0; i<heartEntities.size(); i++){
            if(heartEntities.get(i).getPostEntity().getPostId() == postId){
                if(heartEntities.get(i).getPostEntity().getPostStatus()==0){
                    throw new BaseException(BaseResponseStatus.POST_POST_EMPTY_POST);
                }

                PostEntity postEntity = heartEntities.get(i).getPostEntity();
                postEntity.setHeartCount(postEntity.getHeartCount()-1);

                heartRepository.delete(heartEntities.get(i));
                break;
            }
        }
    }

}
