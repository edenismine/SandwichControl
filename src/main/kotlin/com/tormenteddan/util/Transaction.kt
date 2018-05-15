package com.tormenteddan.util

/**
 * A transaction as specified by its [type], the place of [origin], its
 * [concept] and the amount of [money][cents] involved.
 *
 * @property type This transaction's type.
 * @property origin A description of the originator of this transaction.
 * @property concept The concept or item involved in this transaction.
 * @property cents The money (in cents) involved in this transaction.
 *
 * @see TransactionType
 *
 * @author daniel.aragon@ciencias.unam.mx
 */
data class Transaction(val type: TransactionType,
                       val origin: String,
                       val concept: String,
                       val cents: Int)
