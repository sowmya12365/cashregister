package com.example.cashregister.navigation

object Routes {
    const val HOME = "home"
    const val MANAGER = "manager"
    const val HISTORY = "history"
    const val HISTORY_DETAIL = "history_detail/{index}"
    const val RESTOCK = "restock"

    fun historyDetail(index: Int) = "history_detail/$index"
}