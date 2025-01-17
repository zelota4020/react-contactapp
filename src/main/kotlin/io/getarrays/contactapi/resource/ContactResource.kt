package io.getarrays.contactapi.resource

import io.getarrays.contactapi.domain.ContactDto
import io.getarrays.contactapi.service.ContactService
import lombok.RequiredArgsConstructor
import org.apache.tomcat.util.http.fileupload.FileUploadBase.MULTIPART_FORM_DATA
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.http.MediaType.IMAGE_JPEG_VALUE
import org.springframework.http.MediaType.IMAGE_PNG_VALUE
import org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE
import org.springframework.http.MediaType.MULTIPART_MIXED_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.net.URI
import java.nio.file.Files
import java.nio.file.Paths


@RestController
@RequestMapping("/api/v1/contacts")
@RequiredArgsConstructor
class ContactResource(
    private val contactService: ContactService,
    @Value("\${spring.application.photoDirectory}") private val photoDirectory: String
) {

    @PostMapping
    fun create(@RequestBody(required = true) contact: ContactDto): ResponseEntity<ContactDto> {
        //return ResponseEntity.ok().body(contactService.create(contact));
        return ResponseEntity.created(URI.create("/api/v1/contacts/userID")).body(contactService.create(contact))
    }

    @PostMapping(path = ["/{id}"])
    fun update(
        @PathVariable(value = "id", required = true) id: String,
        @RequestBody(required = true) contact: ContactDto
    ): ResponseEntity<ContactDto> {
        return ResponseEntity.ok().body(contactService.update(id, contact));
    }

    @GetMapping
    fun get(
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "10") size: Int
    ): ResponseEntity<Page<ContactDto>> {
        return ResponseEntity.ok().body(contactService.getAll(page, size))
    }

    @GetMapping("/{id}")
    fun get(@PathVariable(value = "id", required = true) id: String): ResponseEntity<ContactDto> {
        return ResponseEntity.ok().body(contactService.get(id))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable(value = "id", required = true) id: String): ResponseEntity<ContactDto> {
        contactService.delete(id)
        return ResponseEntity.ok().build()
    }

    @PostMapping(
        path = ["/photo"],
        consumes = [MULTIPART_FORM_DATA, MULTIPART_FORM_DATA_VALUE, "application/json", MULTIPART_MIXED_VALUE],
        produces = [IMAGE_PNG_VALUE, "application/json"]
    )
    fun uploadPhoto(
        @RequestParam(value = "id", required = true) id: String,
        @RequestParam(value = "file", required = true) file: MultipartFile
    ): ResponseEntity<String> {
        return ResponseEntity.ok().body(contactService.uploadPhoto(id, file))
    }

    @GetMapping(path = ["/image/{filename}"], produces = [IMAGE_PNG_VALUE, IMAGE_JPEG_VALUE])
    @Throws(IOException::class)
    fun getPhoto(@PathVariable("filename", required = true) filename: String): ByteArray {
        return Files.readAllBytes(Paths.get(photoDirectory + "/" + filename))
    }

}
