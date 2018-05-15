package com.tormenteddan.stores

import com.tormenteddan.util.Article
import com.tormenteddan.util.Transaction
import com.tormenteddan.util.TransactionType
import javafx.beans.property.*
import tornadofx.*
import java.util.*

/**
 * A ingredientsList store supervisor that watches over and collects information
 * from the stores it controls.
 *
 * @property globalBalance The supervisor's current balance, calculated by
 * adding the supervisor's stores' balance.
 * @property balanceMap Maps each of the supervised stores to their balance.
 * @property shoppingList Maps each of the supervised stores to a list of
 * their missing items.
 * @property ledger Using this collection, the supervisor keeps track of all
 * the transactions that take place inside his/her network.
 */
open class SandwichStoreSupervisor : Observer {
    val totalBalanceProperty = SimpleIntegerProperty(0)
    var totalBalance by totalBalanceProperty

    val storesProperty = SimpleSetProperty<SandwichStore>(
            hashSetOf<SandwichStore>().observable()
    )
    val stores by storesProperty

    val balanceMapProperty = SimpleMapProperty<String, Int>(
            hashMapOf<String, Int>().observable())
    var balanceMap by balanceMapProperty

    val shoppingListProperty = SimpleListProperty<Article>(
            arrayListOf<Article>().observable())
    var shoppingList by shoppingListProperty

    val ledgerProperty = SimpleListProperty<Transaction>(
            arrayListOf<Transaction>().observable())
    var ledger by ledgerProperty


    /**
     * This method is called whenever the observed object is changed. An
     * application calls an [Observable] object's
     * [notifyObservers][Observable.notifyObservers] method to have all the
     * object's observers notified of the change.
     *
     * @param o the observable object.
     * @param arg argument passed to the
     * [notifyObservers][Observable.notifyObservers] method.
     */
    override fun update(o: Observable?, arg: Any?) {
        if (o is SandwichStore) {
            if (arg is Transaction) ledger.add(arg)
            balanceMap[o.address] = o.balance
            totalBalance = balanceMap.values.sum()
            stores.add(o)
            shoppingList.addAll(o.missingItems)
            shoppingList = shoppingList
                    .distinct()
                    .filter { it.missing > 0 }
                    .observable()
        }
    }

    /**
     * This function iterates over all the stores that have reported to the
     * supervisor and have [missing articles][SandwichStore.missingItems];
     * it[replenishes][SandwichStore.replenish] their inventories, creates a
     * [transaction][Transaction] and makes each store update its supervisors
     * accordingly.
     */
    fun buyMissingItems() {
        for (store in stores) {
            store.missingItems.forEach {
                val missing = it.missing
                if (store.replenish(it, it.missing)) {
                    val transaction = Transaction(TransactionType.SPENT,
                            store.address, it.description,
                            (missing * it.cost))
                    store.update(store, transaction)
                }
            }
        }
    }

    fun replenish(article: Article) : Boolean {
        val store = stores.find { s -> s.address == article.origin}
        val missing = article.missing
        val success = store?.replenish(article, article.missing) ?: false
        if (success) {
            val transaction = Transaction(TransactionType.SPENT,
                    store!!.address, article.description,
                    (missing * article.cost))
            store.update(store, transaction)
        }
        return success
    }
}

