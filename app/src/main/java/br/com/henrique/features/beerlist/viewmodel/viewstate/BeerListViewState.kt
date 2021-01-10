package br.com.henrique.features.beerlist.viewmodel.viewstate

import androidx.lifecycle.LiveData
import br.com.henrique.data.models.BeerItem

data class BeerListViewState(
    val screenState: LiveData<BeerListScreenState>
)

sealed class BeerListScreenState {
    data class ShowBeerList(val beers: List<BeerItem>) : BeerListScreenState()
    data class ShowBeerListNextPage(val beers: List<BeerItem>) : BeerListScreenState()
}