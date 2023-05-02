package org.collaborators.paymentslab.account.domain

import jakarta.persistence.*
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "ACCOUNTS")
class Account {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
    var accountKey: String? = null
    var email: String? = null
    var password: String? = null
    var username: String? = null
    var emailCheckToken: String? = null
    var emailCheckTokenGeneratedAt: LocalDateTime? = null
    var emailVerified: Boolean = false
    var joinedAt: LocalDateTime? = null
    @UpdateTimestamp
    val lastModifiedAt: LocalDateTime? = null
    val withdraw: Boolean = false
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    lateinit var roles: MutableSet<Role>

    protected constructor()
    private constructor(email: String, password: String, username: String) {
        accountKey = ""
        this.email = email
        this.password = password
        this.username = username
        this.roles = hashSetOf(Role.USER)
    }

    companion object {
        fun register(email: String, encodedPassword: String, username: String): Account {
            val account = Account(email, encodedPassword, username)
            account.generateEmailCheckToken()
            return account
        }
    }

    private fun generateEmailCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString()
        this.emailCheckTokenGeneratedAt = LocalDateTime.now()
    }

    fun completeRegister() {
        this.emailVerified = true
        this.joinedAt = LocalDateTime.now()
    }

    fun isValidToken(token: String): Boolean {
        return this.emailCheckToken.equals(token)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Account

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}