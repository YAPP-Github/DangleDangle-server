package yapp.be.storage.jpa.model

import jakarta.persistence.*

@Entity
@Table(name = "shelter")
class Shelter(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column
    val identifier: String,
    @Column
    val name: String,
    @Column
    val description: String,
    @Column
    val address: Address,
    @Column
    val phone: String,
    @Column
    val notice: String,
    @Column
    val parking: Boolean = false,
    @Column
    val account: String,
    @Column
    val bankName: String,
    @Column
    val donationUrl: String,
    @Column
    val image: String,
    @Column
    val email: String,
)
