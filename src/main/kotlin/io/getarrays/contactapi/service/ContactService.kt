package io.getarrays.contactapi.service

import io.getarrays.contactapi.domain.ContactDto
import io.getarrays.contactapi.domain.ContactEntity
import io.getarrays.contactapi.repository.ContactRepository
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*
import java.util.stream.Collectors

@Service
@Transactional
class ContactService(
    @Value("\${spring.application.photoDirectory}") private val photoDirectory: String
) {

    private val logger = LoggerFactory.getLogger(ContactService::class.java)

    @Autowired
    private lateinit var contactRepository: ContactRepository;

    fun getAll(page: Int, size: Int): Page<ContactDto> {
        return PageImpl(
            contactRepository
                .findAll(PageRequest.of(page, size, Sort.by("name")))
                .stream()
                .map(this::convertToContractDto)
                .collect(Collectors.toList()),
            PageRequest.of(page, size),
            contactRepository.count()
        )
    }

    fun get(id: String): ContactDto {
        return convertToContractDto(
            contactRepository
                .findById(UUID.fromString(id))
                .orElse(null)
        );
    }

    fun create(contact: ContactDto): ContactDto {
        return convertToContractDto(
            contactRepository.save(
                ContactEntity(
                    contact.id?.let { UUID.fromString(it) },
                    contact.name,
                    contact.email,
                    contact.title,
                    contact.phone,
                    contact.address,
                    contact.status,
                    contact.photoUrl
                )
            )
        )
    }

    fun update(id: String, contact: ContactDto): ContactDto {
        var contactEntity = contactRepository.findById(UUID.fromString(id)).orElse(null)
        contactEntity.name = contact.name
        contactEntity.status = contact.status
        contactEntity.email = contact.email
        contactEntity.phone = contact.phone
        contactEntity.address = contact.address
        contactEntity.title = contact.title
        return convertToContractDto(
            contactRepository.save(contactEntity)
        )
    }

    fun delete(id: String) {
        contactRepository.deleteById(UUID.fromString(id))
    }

    fun uploadPhoto(id: String, file: MultipartFile): String {
        var contactEntity = contactRepository.findById(UUID.fromString(id)).orElse(null)
        var photoUrl = storePhoto(id, file);
        contactEntity.photoUrl = photoUrl?.toString() ?: ""
        contactRepository.save(contactEntity)
        return photoUrl.toString()
    }

    private val fileExtension: (String) -> String? = { fileName ->
        fileName.takeIf { it.contains(".") }
            ?.let { "." + it.substringAfterLast(".") }
    }

    private fun storePhoto(id: String, file: MultipartFile): String? {
        logger.info("Storing photo for contact $id")
        return try {
            val fileStorageLocation = Paths.get(photoDirectory).toAbsolutePath().normalize()
            if (!Files.exists(fileStorageLocation)) {
                Files.createDirectory(fileStorageLocation)
            }
            val extension = fileExtension(file.originalFilename ?: "no-filename") ?: "png"
            Files.copy(
                file.inputStream,
                fileStorageLocation.resolve("$id$extension"),
                StandardCopyOption.REPLACE_EXISTING
            )
            ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/contacts/image/")
                .path(id)
                .path(extension)
                .toUriString()
        } catch (e: Exception) {
            logger.error("Failed to store photo", e)
            null
        }
    }

    private fun convertToContractDto(contactEntity: ContactEntity?): ContactDto {
        return ContactDto(
            contactEntity!!.id.toString(),
            contactEntity.name,
            contactEntity.email,
            contactEntity.title,
            contactEntity.phone,
            contactEntity.address,
            contactEntity.status,
            contactEntity.photoUrl
        )
    }
}
