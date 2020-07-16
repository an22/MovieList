package com.an2.core.common.base.data.repository

import com.an2.core.common.base.data.network.BaseResponse
import com.an2.core.common.base.domain.error_handler.ErrorEntity
import com.an2.core.common.base.domain.error_handler.ErrorHandler
import com.an2.core.utils.BResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response

abstract class BaseRepository(
        private val errorHandler: ErrorHandler,
        private val networkDispatcher: CoroutineDispatcher
) {

    suspend fun <Type : Any> request(request: suspend () -> Response<Type>): BResult<Type> {
        return withContext(networkDispatcher) {
            supervisorScope {
                try {
                    val response = request.invoke()
                    if (response.isSuccessful) {
                        BResult.Success(response.body()!!)
                    } else {
                        BResult.Error(errorHandler.toErrorEntity(HttpException(response)))//todo: handle by error code
                    }
                } catch (e: Exception) {
                    BResult.Error(errorHandler.toErrorEntity(e))
                }
            }
        }
    }

    suspend fun <Type : BaseResponse<*>> requestWithErrorCheck(request: suspend () -> Response<Type>): BResult<Type> {
        return withContext(networkDispatcher) {
            supervisorScope {
                try {
                    val response = request.invoke()
                    if (response.isSuccessful) {
                        if (response.body()!!.errText.isBlank()) {
                            BResult.Success(response.body()!!)
                        } else BResult.Error(
                            ErrorEntity.ApiError.MessageError(response.body()!!.errText)
                        )
                    } else {
                        BResult.Error(errorHandler.toErrorEntity(HttpException(response)))//todo: handle by error code
                    }
                } catch (e: Exception) {
                    BResult.Error(errorHandler.toErrorEntity(e))
                }
            }
        }
    }

    suspend fun <T> backgroundWork(block: suspend CoroutineScope.() -> T): T {
        return withContext(networkDispatcher) {
            block.invoke(this)
        }
    }
}