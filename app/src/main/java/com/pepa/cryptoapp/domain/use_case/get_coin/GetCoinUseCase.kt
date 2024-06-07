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
        try {
            emit(Resource.Loading())
            val coin = repository.getCoinById(coinId).toCoinDetail()
            emit(Resource.Success(coin))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error("Connection issue"))
        }
    }
}