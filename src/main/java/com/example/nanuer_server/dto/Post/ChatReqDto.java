package com.example.nanuer_server.dto.Post;

import com.example.nanuer_server.domain.entity.ChatMessageEntity;

public class ChatReqDto {
    private ChatMessageEntity.Type type;
    private String sender;
    private String channelId;
    private Object data;
}
