package com.example.mergebackend.global.config.error.data

import org.springframework.http.HttpStatus

enum class ErrorCode(
        val status: HttpStatus,
        val message: String,
) {

    //400
    PASSWORD_NOT_MATCHED(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    IMAGE_UPLOAD_FAILED(HttpStatus.BAD_REQUEST, "이미지 업로드에 실패하였습니다."),
    WRONG_IMAGE(HttpStatus.BAD_REQUEST, "잘못된 이미지 형식입니다."),
    INVALID_FILE_EXTENSION(HttpStatus.BAD_REQUEST, "지원하지 않는 파일 확장자입니다."),
    IMAGE_DELETE_FAIL(HttpStatus.BAD_REQUEST, "이미지를 삭제하는데 실패했습니다."),

    //401
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),

    //403
    PERMISSION_DENIED(HttpStatus.FORBIDDEN, "권한이 거부되었습니다."),

    // 404
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "프로젝트를 찾을 수 없습니다."),
    ENVIRONMENT_VARIABLE_NOT_FOUND(HttpStatus.NOT_FOUND, "환경변수를 찾을 수 없습니다."),

    // 409
    ALREADY_SIGN_UP(HttpStatus.CONFLICT, "이미 가입한 계정입니다."),
    ALREADY_EXISTS_ENVIRONMENT_VARIABLE(HttpStatus.CONFLICT, "이미 환경변수가 존재합니다,"),
    ALREADY_EXIST(HttpStatus.BAD_REQUEST, "이미 존재하는 프로젝트 이름입니다."),

    // 500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 에러"),
    S3_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "AWS S3가 정상 작동하지 않습니다."),
    VAULT_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Vault 서버 에러"),
    KUBERNETES_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Kubernetes 서버 에러")
}