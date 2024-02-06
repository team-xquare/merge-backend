package com.example.mergebackend.global.common.annotation

import java.lang.annotation.ElementType
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

@Constraint(validatedBy = [NotEmptyEnumValidator::class])
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class NotEmptyEnum(
    val message: String = "Enum value must not be empty",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

class NotEmptyEnumValidator : ConstraintValidator<NotEmptyEnum, Enum<*>> {
    override fun isValid(value: Enum<*>?, context: ConstraintValidatorContext?): Boolean {
        return value?.name?.isNotBlank() == true
    }
}