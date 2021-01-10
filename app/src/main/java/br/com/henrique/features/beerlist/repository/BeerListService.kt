package br.com.henrique.features.beerlist.repository

import br.com.henrique.data.models.BeerItem
import br.com.henrique.data.models.CustomResponse

interface BeerListService {
    suspend fun getBeers(page: Int) : CustomResponse<List<BeerItem>>
}