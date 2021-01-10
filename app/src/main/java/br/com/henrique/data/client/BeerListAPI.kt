package br.com.henrique.data.client

import br.com.henrique.data.models.BeerItem
import retrofit2.http.GET
import retrofit2.http.Query

interface BeerListAPI {

    @GET("beers")
    suspend fun getBeersPerPage(
        @Query("page") page: Int,
        @Query ("per_page") items: Int = 80
    ) : List<BeerItem>

}