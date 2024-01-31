package com.example.mergebackend.global.config.error.data

import org.springframework.http.HttpStatus

enum class ErrorCode(
        val status: HttpStatus,
        val message: String,
) {

    //400
    PASSWORD_NOT_MATCHED(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    SCHOOL_GCN_NOT_EXIST(HttpStatus.BAD_REQUEST, "존재하지 않는 학번입니다."),

    // 401
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),

    //403
    PERMISSION_DENIED(HttpStatus.FORBIDDEN, "권한이 거부되었습니다."),

    // 404
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),

    // 409
    ALREADY_SIGN_UP(HttpStatus.CONFLICT, "이미 가입한 계정입니다."),

    // 500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 에러"),
    S3_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "AWS S3가 정상 작동하지 않습니다.")
}