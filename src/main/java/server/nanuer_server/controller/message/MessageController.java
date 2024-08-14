package server.nanuer_server.controller.message;

import server.nanuer_server.config.BaseResponse;
import server.nanuer_server.service.message.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@Log4j2
@RequestMapping("/message")
@RequiredArgsConstructor
@RestController
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/send")
    @ResponseBody
    public BaseResponse<String> sendSMS(String phone) {
        Random rand  = new Random();
        String numStr = "";
        for(int i=0; i<5; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr += ran;
        }
        System.out.println("수신자 번호 : " + phone);
        System.out.println("인증번호 : " + numStr);
        messageService.sendSMS(phone,numStr);
        return new BaseResponse<>(numStr);
    }
}
