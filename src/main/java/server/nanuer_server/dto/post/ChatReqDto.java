package server.nanuer_server.dto.post;

import server.nanuer_server.domain.entity.ChatMessageEntity;

public class ChatReqDto {
    private ChatMessageEntity.Type type;
    private String sender;
    private String channelId;
    private Object data;
}
