package com.pepa.cryptoapp.data.repository

import com.pepa.cryptoapp.data.remote.CoinPaprikaApi
import com.pepa.cryptoapp.data.remote.dto.CoinDetailDto
import com.pepa.cryptoapp.data.remote.dto.CoinDto
import com.pepa.cryptoapp.domain.repository.CoinRepository
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val api: CoinPaprikaApi
): CoinRepository {
    override suspend fun getCoins(): List<CoinDto> {
        return api.getCoins()
    }

    override suspend fun getCoinById(coinId: String): CoinDetailDto {
        return api.getCoinById(coinId)
    }
}