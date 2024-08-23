package server.nanuer_server.service.chat;

import server.nanuer_server.config.BaseException;
import server.nanuer_server.domain.entity.ChatMessageEntity;
import server.nanuer_server.domain.entity.ChatRoomEntity;
import server.nanuer_server.domain.entity.PostEntity;
import server.nanuer_server.domain.entity.UserEntity;
import server.nanuer_server.domain.repository.chat.ChatRoomRepository;
import server.nanuer_server.domain.repository.PostRepository;
import server.nanuer_server.domain.repository.UserRepository;
import server.nanuer_server.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {

    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final UserService userService;
    private final ChatRoomRepository chatRoomRepository;

    public ChatRoomEntity createRoom(HttpServletRequest request, int postId) throws BaseException {
        int userId = userService.GetHeaderAndGetUser(request);
        UserEntity userEntity = userRepository.findByUserId(userId).get();
        PostEntity postEntity = postRepository.findByPostId(postId);
        if(chatRoomRepository.findByUserEntityAndIsWriterAndPostEntity(userEntity, false, postEntity).isEmpty() &&
                chatRoomRepository.findByUserEntityAndIsWriterAndPostEntity(userEntity, true, postEntity).isEmpty() ){
            ChatRoomEntity chatRoomEntity = ChatRoomEntity.create(false,userEntity, postEntity);
            chatRoomRepository.save(chatRoomEntity);
            return chatRoomEntity;
        }
        else return chatRoomRepository.findAllByPostId(postId).get(0);
    }

    public void sendChatMessage(ChatMessageEntity chatMessage) {
        simpMessageSendingOperations.convertAndSend("/sub/channel/" + chatMessage.getRoomId(), chatMessage);
    }

}
