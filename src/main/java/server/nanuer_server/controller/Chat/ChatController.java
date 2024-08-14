package server.nanuer_server.controller.Chat;

import server.nanuer_server.config.BaseException;
import server.nanuer_server.config.BaseResponse;
import server.nanuer_server.config.User.JwtTokenProvider;
import server.nanuer_server.domain.entity.ChatMessageEntity;
import server.nanuer_server.domain.entity.ChatRoomEntity;
import server.nanuer_server.domain.entity.UserEntity;
import server.nanuer_server.domain.repository.Chat.ChatRoomRepository;
import server.nanuer_server.domain.repository.UserRepository;
import server.nanuer_server.dto.Chat.GetChatUserDto;
import server.nanuer_server.service.User.UserService;
import server.nanuer_server.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final UserService userService;
    private final ChatService chatService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

    /*
        /sub/channel/12345      - 구독(channelId:12345)
        /pub/send        - 메시지 발행
    */

    //방번호(roomId), 유저Id(sender)
    @GetMapping("/getInfo")
    public BaseResponse<GetChatUserDto> GetChatUser(HttpServletRequest request, @RequestParam int post_id) throws BaseException {
        int userId = userService.GetHeaderAndGetUser(request);
        UserEntity userEntity = userRepository.findByUserId(userId).get();
        int roomNumber = chatRoomRepository.findAllByPostId(post_id).get(0).getRoomNumber();
        ChatRoomEntity room = chatService.createRoom(request, post_id);
        GetChatUserDto getChatUserDto = GetChatUserDto.builder()
                .userId(userId)
                .roomNumber(roomNumber)
                .nickName(userEntity.getNickName())
                .profileImg(userEntity.getProfileImg())
                .build();
        return new BaseResponse<>(getChatUserDto);
    }

    @MessageMapping("/send") //채팅방에서 메세지 보내기 버튼
    public ChatMessageEntity message(ChatMessageEntity message) {
        //String userEmail = jwtTokenProvider.getUserPk(token);
        //String nickName = userRepository.findByEmail(userEmail).get().getNickName();

        UserEntity userEntity = userRepository.findByUserId(message.getSender()).get();
        message.setNickName(userEntity.getNickName());
        message.setProfileImg(userEntity.getProfileImg());
        System.out.println("ChatController : message() => " + message.getData());
        chatService.sendChatMessage(message);
        return message;
    }

    //jwt 읽어서 isWriter(글작성자인지, 아닌지) return
    /*
    @GetMapping("/isWriter")
    public BaseResponse<Boolean> IsWriter(){

        Boolean result = ;
        return new BaseResponse<>(result)
    }
*/

    /*
    @PostMapping("/join")
    @ResponseBody
    public BaseResponse<Integer> createRoom(HttpServletRequest request, @RequestParam int postId) throws BaseException {

        ChatRoomEntity room = chatService.createRoom(request, postId);
        return new BaseResponse<>(room.getRoomNumber());
    }*/




}