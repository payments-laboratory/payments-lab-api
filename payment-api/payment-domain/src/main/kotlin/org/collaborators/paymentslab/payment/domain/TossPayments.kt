package org.collaborators.paymentslab.payment.domain

import jakarta.persistence.*
import org.collaborator.paymentlab.common.domain.AbstractAggregateRoot
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@DynamicInsert
@DynamicUpdate
class TossPayments protected constructor(
    @Embedded
    var info: TossPaymentsInfo? = null,
    @Embedded
    var cancelInfo: TossPaymentsCancelInfo? = null,
    @Embedded
    var cardInfo: TossPaymentsCardInfo? = null,
    @Column(nullable = false)
    val status: String,
    @Enumerated(EnumType.STRING)
    private var payMethod: PayMethod,
    @CreationTimestamp
    private val createdAt: LocalDateTime? = null,
    @UpdateTimestamp
    private val modifiedAt: LocalDateTime? = null
): AbstractAggregateRoot() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    var accountId: Long? = null

    constructor(
        info: TossPaymentsInfo,
        cancelInfo: TossPaymentsCancelInfo?,
        cardInfo: TossPaymentsCardInfo?,
        status: String,
        payMethod: PayMethod) : this(info, cancelInfo, cardInfo, status, payMethod, null, null)

    fun completeOf(accountId: Long) {
        this.accountId = accountId
        registerEvent(PaymentCompletedEvent(this))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TossPayments

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }


}