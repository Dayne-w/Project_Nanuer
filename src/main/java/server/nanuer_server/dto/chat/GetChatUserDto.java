package server.nanuer_server.dto.chat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetChatUserDto {

    private int userId;

    private String nickName;

    private String profileImg;

    private int roomNumber;


}
