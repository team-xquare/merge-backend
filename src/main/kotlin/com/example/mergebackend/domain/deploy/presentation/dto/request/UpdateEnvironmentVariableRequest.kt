package com.example.mergebackend.domain.deploy.presentation.dto.request
import com.example.mergebackend.domain.deploy.entity.type.EnvType
import com.example.mergebackend.domain.deploy.entity.type.ServiceType
import com.example.mergebackend.global.common.annotation.NotEmptyEnum
import java.util.UUID
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class UpdateEnvironmentVariableRequest(
    @field:NotEmptyEnum(message = "envType은 빈 문자열이 될 수 없습니다.")
    @field:NotNull(message = "envType은 null이 될 수 없습니다.")
    val envType: EnvType?,

    @field:NotEmptyEnum(message = "envType은 빈 문자열이 될 수 없습니다.")
    @field:NotNull(message = "serviceType은 null이 될 수 없습니다.")
    val serviceType: ServiceType?,

    @field:NotNull(message = "variableList는 null이 될 수 없습니다.")
    val variableList: Map<String, String>?,

    val projectId: UUID
)
