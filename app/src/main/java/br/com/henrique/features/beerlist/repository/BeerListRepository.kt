package br.com.henrique.features.beerlist.repository

import br.com.henrique.data.client.BeerListAPI
import br.com.henrique.data.models.BeerItem
import br.com.henrique.data.models.CustomResponse

class BeerListRepository(private val api: BeerListAPI) : BeerListService {

    override suspend fun getBeers(page: Int): CustomResponse<List<BeerItem>> {
        return try {
            val result = api.getBeersPerPage(page)
            CustomResponse.Success(result)
        } catch (e : Exception){
            CustomResponse.Error(e)
        }
    }
}