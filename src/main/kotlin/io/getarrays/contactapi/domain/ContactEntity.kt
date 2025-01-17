package io.getarrays.contactapi.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import org.hibernate.annotations.UuidGenerator
import java.util.*


@Entity
@Table(name = "contacts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class ContactEntity(
    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    var id: UUID? = null,
    @Column(nullable = false)
    var name: String,
    @Column
    var email: String?,
    @Column
    var title: String?,
    @Column
    var phone: String?,
    @Column
    var address: String?,
    @Column
    var status: String?,
    @Column
    var photoUrl: String?
)
