package br.com.henrique.features.beerlist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.henrique.data.models.BeerItem
import br.com.henrique.data.models.CustomResponse
import br.com.henrique.features.beerlist.repository.BeerListService
import br.com.henrique.features.beerlist.viewmodel.viewstate.BeerListScreenState
import br.com.henrique.features.beerlist.viewmodel.viewstate.BeerListViewState
import br.com.henrique.helper.setState
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class BeerListViewModel(private val repository: BeerListService) : ViewModel(), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job
    private var page = 1
    private var listBeer : ArrayList<BeerItem> = arrayListOf()
    val viewState = BeerListViewState(screenState = MutableLiveData())

    fun getBeersFirstPage() {
        launch {
            val result = withContext(Dispatchers.IO) {
                repository.getBeers(1)
            }

            when (result) {
                is CustomResponse.Success -> {
                    listBeer.clear()
                    listBeer.addAll(result.data)
                    viewState.screenState.setState(BeerListScreenState.ShowBeerList(listBeer))
                }
                is CustomResponse.Error -> {
                    //TODO: Retry
                }
            }
        }
    }

    fun nextPage(){
        page++
        launch {
            val result = withContext(Dispatchers.IO) {
                repository.getBeers(page)
            }

            when (result) {
                is CustomResponse.Success -> {
                    listBeer.addAll(result.data)
                    viewState.screenState.setState(BeerListScreenState.ShowBeerListNextPage(listBeer))
                }
                is CustomResponse.Error -> {
                    //TODO: Retry
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}