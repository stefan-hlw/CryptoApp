package com.pepa.cryptoapp.domain.use_case.get_coin

import com.pepa.cryptoapp.common.Resource
import com.pepa.cryptoapp.data.remote.dto.toCoinDetail
import com.pepa.cryptoapp.domain.model.CoinDetail
import com.pepa.cryptoapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(coinId: String): Flow<Resource<CoinDetail>> = flow {
        emit(Resource.Loading())
        val result = runCatching {
            val coin = repository.getCoinById(coinId).toCoinDetail()
            Resource.Success(coin)
        }.getOrElse { throwable ->
            when (throwable) {
                is HttpException -> Resource.Error(throwable.localizedMessage ?: "Unexpected error occurred")
                is IOException -> Resource.Error("Connection issue")
                else -> Resource.Error("An unknown error occurred")
            }
        }
        emit(result)
    }
}