package server.nanuer_server.dto.user;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDto {
    private String email;
    private String password;
}