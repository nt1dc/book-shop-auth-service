package se.nt1dc.authservice.dto.transaction

data class DistributedTransaction(var id: String? = null,
                                  var status: DistributedTransactionStatus,
                                  val participants: MutableList<DistributedTransactionParticipant> = mutableListOf())