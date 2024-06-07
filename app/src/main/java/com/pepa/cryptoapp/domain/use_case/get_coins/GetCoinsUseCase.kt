package com.pepa.cryptoapp.domain.use_case.get_coins

import com.pepa.cryptoapp.common.Resource
import com.pepa.cryptoapp.domain.model.Coin
import com.pepa.cryptoapp.domain.model.toCoin
import com.pepa.cryptoapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(): Flow<Resource<List<Coin>>> = flow {
        try {
            emit(Resource.Loading())
            val coins = repository.getCoins().map {
                it.toCoin()
            }
            emit(Resource.Success(coins))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error("Connection issue"))
        }
    }
}