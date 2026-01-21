package ru.nikzarch.witcherboard.mongo.service

interface MarketService {

    fun buyItem(witcherId: Long, itemId: String)

    fun sellItem(witcherId: Long, itemId: String)
    fun getBalanceByUserId(userId: Long) : Long
}
