package server.nanuer_server.utils;

import server.nanuer_server.domain.entity.ChatMessageEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private Utils() {
    }

    public static ChatMessageEntity getObject(final String message) throws Exception {
        return objectMapper.readValue(message, ChatMessageEntity.class);
    }

    public static String getString(final ChatMessageEntity message) throws Exception {
        return objectMapper.writeValueAsString(message);
    }
}