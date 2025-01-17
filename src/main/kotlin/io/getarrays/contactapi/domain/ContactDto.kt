package io.getarrays.contactapi.domain

import com.fasterxml.jackson.annotation.JsonInclude
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor

@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
data class ContactDto(
    val id: String?,
    val name: String,
    val email: String?,
    val title: String?,
    val phone: String?,
    val address: String?,
    val status: String?,
    val photoUrl: String?
)
