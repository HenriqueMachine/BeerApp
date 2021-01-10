package br.com.henrique.di

import br.com.henrique.data.client.BeerListAPI
import br.com.henrique.data.client.RetrofitClient
import br.com.henrique.features.beerlist.repository.BeerListRepository
import br.com.henrique.features.beerlist.repository.BeerListService
import br.com.henrique.features.beerlist.viewmodel.BeerListViewModel
import br.com.henrique.testpag.BuildConfig
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {
    single {
        RetrofitClient().createWebService<BeerListAPI>(
            okHttpClient = RetrofitClient().createHttpClient(),
            baseUrl = BuildConfig.BASE_PUNK_URL
        )
    }
    factory<BeerListService> { BeerListRepository(api = get()) }
    viewModel { BeerListViewModel(repository = get()) }
}