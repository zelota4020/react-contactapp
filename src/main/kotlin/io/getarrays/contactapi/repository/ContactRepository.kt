package io.getarrays.contactapi.repository

import io.getarrays.contactapi.domain.ContactEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ContactRepository: JpaRepository<ContactEntity, UUID>
