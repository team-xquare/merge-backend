package com.example.mergebackend.domain.file.service

import com.example.mergebackend.domain.file.presentation.dto.FileUrlListResponse
import com.example.mergebackend.domain.file.presentation.dto.FileUrlResponse
import org.springframework.web.multipart.MultipartFile

interface FileService {

    fun upload(file: MultipartFile, projectNameEn: String): FileUrlResponse

    fun uploads(file: List<MultipartFile>, projectNameEn: String): FileUrlListResponse

    fun delete(fileName: String, projectNameEn: String)
}