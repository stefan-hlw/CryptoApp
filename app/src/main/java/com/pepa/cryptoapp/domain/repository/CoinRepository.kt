package com.pepa.cryptoapp.domain.repository

import com.pepa.cryptoapp.data.remote.dto.CoinDetailDto
import com.pepa.cryptoapp.data.remote.dto.CoinDto

interface CoinRepository {
    suspend fun getCoins(): List<CoinDto>
    suspend fun getCoinById(coinId: String): CoinDetailDto
}