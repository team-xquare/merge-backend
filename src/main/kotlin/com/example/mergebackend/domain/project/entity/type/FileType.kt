package com.example.mergebackend.domain.project.entity.type

enum class FileType(
        val extension: String
) {
    PNG("png"),
    JPG("jpg"),
    JPEG("jpeg"),
    GIF("gif"),
    PDF("pdf"),
    TIFF("tiff"),
    PSD("psd"),
    BMP("bmp")
}