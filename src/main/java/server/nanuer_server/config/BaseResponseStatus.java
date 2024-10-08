package server.nanuer_server.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {

    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),

    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),

    // users
    USERS_EMPTY_USER_EMAIL(false, 2010, "유저 이메일 값을 확인해주세요."),
    USER_USER_EMPTY_USER(false, 2011, "존재하지 않는 유저입니다."),

    // [POST] /post
    POST_USERS_EMPTY_EMAIL(false, 2015, "아이디를 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2016, "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(false,2017,"중복된 이메일입니다."),
    POST_POST_INVALID_TITLE(false, 2018, "게시물의 제목을 확인해주세요."),
    POST_POST_INVALID_CONTENT(false, 2019, "게시물의 내용을 확인해주세요."),
    POST_POST_EMPTY_POST(false,2020, "존재하지 않는 게시물입니다."),
    POST_POST_INVALID_CATEGORY(false, 2021, "게시물의 카테고리를 확인해주세요."),

    // [POST] /users
    POST_USERS_EMPTY_ID(false, 2015, "아이디를 입력해주세요."),
    POST_USERS_INVALID_ID(false, 2016, "이메일 형식을 확인해주세요."),
    //POST_USERS_EXISTS_ID(false,2017,"중복된 이메일입니다."),
    POST_USERS_EXISTS_PHONE(false, 2021, "중복된 휴대폰 번호입니다."),

    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false,3014,"없는 아이디거나 비밀번호가 틀렸습니다."),



    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    //[PATCH] /users/{userIdx}
    MODIFY_FAIL_USERNAME(false,4014,"유저네임 수정 실패"),
    DELETE_FAIL_USERNAME(false,4015,"유저 삭제 실패")
    ;




    //SAME_ID(HttpStatus.BAD_REQUEST, "동일한 아이디가 존재합니다."),
    //NO_USER(HttpStatus.BAD_REQUEST, "없는 사용자입니다."),
    //NO_LOGIN(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다"),
    //NO_ADMIN(HttpStatus.FORBIDDEN, "권한이 없는 사용자입니다");


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}