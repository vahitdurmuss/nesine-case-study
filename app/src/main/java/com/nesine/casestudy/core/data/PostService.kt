package com.nesine.casestudy.core.data

import retrofit2.http.GET

interface PostService {
    @GET("/posts")
    suspend fun getPosts(): retrofit2.Response<List<PostModel>?>
}
