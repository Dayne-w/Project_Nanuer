package server.nanuer_server.service.mypage;

import server.nanuer_server.config.BaseException;
import server.nanuer_server.domain.entity.PostEntity;
import server.nanuer_server.domain.entity.UserEntity;
import server.nanuer_server.domain.repository.HeartRepository;
import server.nanuer_server.domain.repository.PostRepository;
import server.nanuer_server.domain.repository.UserRepository;
import server.nanuer_server.dto.user.UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.nanuer_server.config.BaseResponseStatus;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MyPageService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final HeartRepository heartRepository;


    public List<PostEntity> getMyPosts(String email){
        List<PostEntity> postEntityList = userRepository.findByEmail(email).get().getPostEntities();
//        List<PostDto> postDtoList = postEntityList.stream()
//                .map(PostEntity::toDto)
//                .collect(Collectors.toList());
        return postEntityList;
    }

    // 마이페이지에서 회원이 찜한 게시물들을 볼 수 있는 메서드
    public List<PostEntity> getHeartPosts(String email) {
//        List<HeartDto> heartDtoList = heartRepository
//                .findAll(userRepository.findByEmail(email).get().getUserId())
//                .stream()
//                .map(HeartEntity::toDto)
//                .collect(Collectors.toList());
//
//        List<PostDto> postDtoList = heartDtoList.stream()
//                .map(HeartDto::getPostDto)
//                .collect(Collectors.toList());
//        return postDtoList;

        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        return postRepository.findHeartByUserId(userEntity.get().getUserId());



    }

    public UserInfoDto updateUser(UserInfoDto userInfoDto, String email) throws BaseException {
        UserEntity userEntity = userRepository.findByEmail(email).get();
        if(!userEntity.isPresent()) {
            throw new BaseException(BaseResponseStatus.USERS_EMPTY_USER_EMAIL);
        }
        userEntity = updateUserUsingUserInfoDto(userEntity, userInfoDto);
        userRepository.save(userEntity);
        return userEntity.toDto();
    }

    private UserEntity updateUserUsingUserInfoDto(UserEntity userEntity, UserInfoDto userInfoDto){
        userEntity.setEmail(userInfoDto.getEmail());
        userEntity.setName(userInfoDto.getName());
        userEntity.setPassword(userInfoDto.getPassword());
        userEntity.setNickName(userInfoDto.getNickName());
        userEntity.setPhone(userInfoDto.getPhone());
        userEntity.setBirth(userInfoDto.getBirth());
        userEntity.setProfileImg(userInfoDto.getProfileImg());
        userEntity.setUniversity(userInfoDto.getUniversity());
        userEntity.setUserStatus(userInfoDto.getUserStatus());
        //userEntity.setUserScore(userInfoDto.getUserScore());
        userEntity.setRole(userInfoDto.getRole());
        userEntity.setPostEntities(userInfoDto.toEntity().getPostEntities());
        return userEntity;
    }
}

/*
userEntity.setEmail(userInfoDto.getEmail());
        userEntity.setName(userInfoDto.getName());
        userEntity.setPassword(userInfoDto.getPassword());
        userEntity.setNickName(userInfoDto.getNickName());
        userEntity.setPhone(userInfoDto.getPhone());
        userEntity.setBirth(userInfoDto.getBirth());
        userEntity.setProfileImg(userInfoDto.getProfileImg());
        userEntity.setUniversity(userInfoDto.getUniversity());
        userEntity.setUserStatus(userInfoDto.getUserStatus());
        userEntity.setUserScore(userInfoDto.getUserScore());
        userEntity.setRole(userInfoDto.getRole());
        userEntity.setPostEntities(userInfoDto.toEntity().getPostEntities());*/