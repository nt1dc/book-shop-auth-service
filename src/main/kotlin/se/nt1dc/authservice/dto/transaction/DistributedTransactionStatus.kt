package se.nt1dc.authservice.dto.transaction

enum class DistributedTransactionStatus {
    NEW, CONFIRMED, ROLLBACK, TO_ROLLBACK
}