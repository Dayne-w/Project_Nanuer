package server.nanuer_server.controller.user;

import server.nanuer_server.config.BaseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import server.nanuer_server.config.BaseResponseStatus;

@RestController
public class TokenExceptionController {
    @GetMapping("/exception/entrypoint")
    public void entryPoint() throws BaseException {
        throw new BaseException(BaseResponseStatus.INVALID_USER_JWT);
    }

    @GetMapping("/exception/access")
    public void denied() throws BaseException {
        throw new BaseException(BaseResponseStatus.INVALID_USER_JWT);
    }
}