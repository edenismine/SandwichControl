package com.tormenteddan.util

/**
 * Transaction types.
 *
 * @see Transaction
 *
 * @author daniel.aragon@ciencias.unam.mx
 */
enum class TransactionType {
    /** A transaction that costs the client money. E.g. purchases */
    SPENT,
    /** A transaction that earned the client money. E.g. sales. */
    EARNED
}