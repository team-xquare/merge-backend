package com.example.mergebackend.domain.file.presentation

import com.example.mergebackend.domain.file.service.FileService
import com.example.mergebackend.domain.project.presentation.dto.response.ProjectListResponse
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/file")
class FileController (
        private val fileService: FileService
){
    @PostMapping("/upload")
    fun uploadMultipleFile(
            @RequestPart("file", required = true) file: MultipartFile,
            @RequestParam("projectNameEn", required = true) projectNameEn: String
    ) = fileService.upload(file, projectNameEn)

    @PostMapping("/upload/files")
    fun uploadMultipleFiles(
            @RequestPart("files", required = true) files: List<MultipartFile>,
            @RequestParam("projectNameEn", required = true) projectNameEn: String
    ) = fileService.uploads(files, projectNameEn)

    @DeleteMapping("/delete/{fileName}")
    fun deleteFile(
            @PathVariable("fileName") fileNames: String,
            @RequestParam("projectNameEn", required = true) projectNameEn: String
    ) = fileService.delete(fileNames, projectNameEn)

}