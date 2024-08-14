package server.nanuer_server.dto.User;

import server.nanuer_server.domain.entity.UserEntity;
import server.nanuer_server.domain.entity.UserRole;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JoinUserDto {

    //private String id;
    private String password;
    private String name;
    private String email;
    private String nickName;
    private String phone;
    private String birth;
    private String profileImg;
    private String university;
    private String userStatus;
    private int  userScore;
    private UserRole role;
    /* DTO -> Entity */
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
                .userStatus("active")
                .userScore(0)
                .role(UserRole.ROLE_USER)
                .build();
        return userEntity;
    }


}
