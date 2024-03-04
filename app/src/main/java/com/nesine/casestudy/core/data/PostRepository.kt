package com.nesine.casestudy.core.data

import com.nesine.casestudy.common.UIResult
import com.nesine.casestudy.core.network.RetrofitClientInstance.retrofitInstance
import java.lang.Exception


class PostRepository {

    suspend fun getPosts(): UIResult<List<PostModel>?> {

        return try {
            val service: PostService? = retrofitInstance?.create(PostService::class.java)

            val response = service?.getPosts()

            response?.let {
                if (it.isSuccessful) {
                    UIResult.Success(response.body())
                } else {
                    UIResult.Failure(Error(it.message()))
                }
            } ?: UIResult.GeneralFailure(NullPointerException())


        } catch (e: Exception) {
            UIResult.GeneralFailure(e)
        }

    }
}