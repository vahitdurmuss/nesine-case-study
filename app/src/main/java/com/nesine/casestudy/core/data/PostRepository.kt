package com.nesine.casestudy.core.data

import com.nesine.casestudy.common.UIResult
import com.nesine.casestudy.core.network.NetworkResult
import com.nesine.casestudy.core.network.RetrofitClientInstance.retrofitInstance
import java.lang.Exception


class PostRepository {

    suspend fun getPosts(): NetworkResult<List<PostModel>?> {

        return try {
            val service: PostService? = retrofitInstance?.create(PostService::class.java)

            val response = service?.getPosts()

            response?.let {
                if (it.isSuccessful) {
                    NetworkResult.Success(response.body())
                } else {
                    NetworkResult.Failure(Error(it.message()))
                }
            } ?: NetworkResult.GeneralFailure(NullPointerException())


        } catch (e: Exception) {
            NetworkResult.GeneralFailure(e)
        }

    }
}