package server.nanuer_server.dto.user;


import server.nanuer_server.domain.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserInfoRes {
    //유저 정보 return 값 이렇게만 반환해달라고 요청하셔서 만든 dto입니다!!
    private String password;
    private String name;
    private String email;
    private String nickName;
    private String phone;
    private String birth;
    private String profileImg;
    private String university;



    public UserEntity toEntity() {
        UserEntity userEntity = UserEntity.builder()
                .password(password)
                .name(name)
                .nickName(nickName)
                .email(email)
                .phone(phone)
                .birth(birth)
                .profileImg(profileImg)
                .university(university)

                .build();
        return userEntity;
    }

}
