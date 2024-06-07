package com.pepa.cryptoapp.domain.use_case.get_coins

import com.pepa.cryptoapp.common.Resource
import com.pepa.cryptoapp.domain.model.Coin
import com.pepa.cryptoapp.domain.model.toCoin
import com.pepa.cryptoapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(): Flow<Resource<List<Coin>>> = flow {
        emit(Resource.Loading())
        val result = runCatching {
            val coins = repository.getCoins().map {
                it.toCoin()
            }
            Resource.Success(coins)
        }.getOrElse { throwable ->
            when(throwable) {
                is HttpException -> Resource.Error(throwable.localizedMessage ?: "Unexpected error occurred")
                is java.io.IOException -> Resource.Error("Connection issue")
                else -> Resource.Error("An unknown error occurred")
            }
        }
        emit(result)
    }
}