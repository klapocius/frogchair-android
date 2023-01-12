package com.frogchair.android.feature.catalog.data

import com.frogchair.android.common.network.RestClient
import com.frogchair.android.feature.catalog.model.*
import retrofit2.http.GET
import retrofit2.http.Path
import kotlin.random.Random

typealias RestCatalogResponse = List<Fighter>

object CatalogRepository {

    private val retrofitService: RetrofitService = RestClient.createService(RetrofitService::class.java)

    //    suspend fun getCatalog(pageNumber: Int) = retrofitService.getCatalog(pageNumber)
    suspend fun getCatalog(pageNumber: Int) = buildFighters()


    val lore =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "

    fun randomClass() = Class.values()[Random(System.currentTimeMillis()).nextInt(5)]
    fun randomRarity() = Rarity.values()[Random(System.currentTimeMillis()).nextInt(5)]
    fun randomSign() = Sign.values()[Random(System.currentTimeMillis()).nextInt(5)]
    fun randomTribe() = Tribe.values()[Random(System.currentTimeMillis()).nextInt(3)]
    fun randomID() = when (Random(System.currentTimeMillis()).nextInt(10)) {
        0 -> "117102"
        1 -> "117101"
        2 -> "117100"
        3 -> "117201"
        4 -> "117200"
        5 -> "208802"
        6 -> "118002"
        7 -> "117502"
        8 -> "316902"
        9 -> "115502"
        10 -> "115702"
        else -> "117202"
    }

    fun buildFighters(): MutableList<Fighter> {
        val list = mutableListOf<Fighter>()
        for (i in 0..100) {
            list.add(
                Fighter(
                    randomID(),
                    "Conifenius",
                    randomClass(),
                    randomRarity(),
                    randomTribe(),
                    randomSign(),
                    FighterStats(10, 10, 10, 10, 10),
                    FighterStats(10, 10, 10, 10, 10),
                    lore
                )
            )
        }
        return list
    }

    interface RetrofitService {
        @GET("catalog/{page}/size/60")
        suspend fun getCatalog(@Path("page") pageNumber: Int): RestCatalogResponse
    }

}