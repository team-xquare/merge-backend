package com.example.mergebackend.domain.file.service

import com.amazonaws.AmazonServiceException
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.GetObjectRequest
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.util.IOUtils
import com.example.mergebackend.domain.file.entity.type.FileType
import com.example.mergebackend.domain.file.exception.ImageDeleteFailException
import com.example.mergebackend.domain.file.exception.InvalidFileExtension
import com.example.mergebackend.domain.file.presentation.dto.FileUrlListResponse
import com.example.mergebackend.domain.file.presentation.dto.FileUrlResponse
import com.example.mergebackend.global.env.s3.S3Property
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream

@Service
@Transactional(readOnly = true)
class FileServiceImpl(
        private val s3Client: AmazonS3Client,
        private val s3Property: S3Property,
) : FileService {

    private companion object {
        const val URL_PREFIX = "https://%s.s3.%s.amazonaws.com/%s"
    }

    private fun uploadFile(file: MultipartFile, projectNameEn: String): FileUrlResponse {

        val bytes: ByteArray = IOUtils.toByteArray(file.inputStream)

        val objectMetadata = ObjectMetadata().apply {
            this.contentType = file.contentType
            this.contentLength = bytes.size.toLong()
        }

        var fileName: String = file.originalFilename ?: file.name


        val ext = fileName.split('.').last()

        try {
            FileType.values().first { it.extension == ext }
        } catch (e: NoSuchElementException) {
            throw InvalidFileExtension
        }

        fileName = s3Property.dir + "$projectNameEn/" + fileName

        val putObjectRequest = PutObjectRequest(
                s3Property.bucket,
                fileName,
                ByteArrayInputStream(bytes),
                objectMetadata,
        )

        s3Client.putObject(putObjectRequest)

        return FileUrlResponse(URL_PREFIX.format(
                s3Property.bucket,
                s3Property.region,
                fileName
            )
        )
    }

    override fun upload(file: MultipartFile, projectNameEn: String): FileUrlResponse {

        return uploadFile(file, projectNameEn)
    }

    override fun uploads(file: List<MultipartFile>, projectNameEn: String): FileUrlListResponse {
        return FileUrlListResponse(
            file.map {
                uploadFile(it, projectNameEn)
            }.toMutableList()
        )
    }


    override fun delete(fileName: String, projectNameEn: String) {
        val fileKey = s3Property.dir + "$projectNameEn/" + fileName

        try {
            s3Client.deleteObject(s3Property.bucket, fileKey)
        } catch (e: AmazonServiceException) {
            throw ImageDeleteFailException
        }
    }

//    override fun download(projectNameEn: String, fileName: String): Resource {
//        val fileKey = s3Property.dir + "$projectNameEn/" + fileName
//        val s3Object = s3Client.getObject(GetObjectRequest(s3Property.bucket, fileKey))
//        return InputStreamResource(s3Object.objectContent)
//    }
}