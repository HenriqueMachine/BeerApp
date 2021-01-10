package br.com.henrique.features.beerlist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.henrique.data.models.BeerItem
import br.com.henrique.features.beerlist.adapter.BeerAdapter
import br.com.henrique.features.beerlist.viewmodel.BeerListViewModel
import br.com.henrique.features.beerlist.viewmodel.viewstate.BeerListScreenState
import br.com.henrique.helper.listenState
import br.com.henrique.helper.onStateChanged
import br.com.henrique.testpag.R
import kotlinx.android.synthetic.main.fragment_list_beer.*
import org.koin.android.viewmodel.ext.android.viewModel


class ListBeerFragment : Fragment() {

    companion object {
        private const val SPAN_COUNT = 2
        const val BEER_IDENTIFIER = "beer_selected_from_list"
    }

    private val beerListViewModel: BeerListViewModel by viewModel()
    private var beersAdapter: BeerAdapter? = null
    private var listBeers: ArrayList<BeerItem> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_beer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        beerListViewModel.getBeersFirstPage()
        bindViewState()
    }

    private fun bindViewState() {
        listenState(beerListViewModel.viewState.screenState) { state ->
            when (state) {
                is BeerListScreenState.ShowBeerList -> bindAdapter(state.beers)
                is BeerListScreenState.ShowBeerListNextPage -> bindNextPageBeers(state.beers)
            }
        }
    }

    private fun bindAdapter(list: List<BeerItem>) {
        listBeers.addAll(list)
        beersAdapter = BeerAdapter(listBeers)
        val gridLayoutManager = GridLayoutManager(context, SPAN_COUNT)
        recyclerViewListBeer.also {
            it.layoutManager = gridLayoutManager
            it.adapter = beersAdapter
            it.onStateChanged { recyclerView, state ->
                if (!recyclerView.canScrollVertically(1) && state == RecyclerView.SCROLL_STATE_IDLE) {
                    beerListViewModel.nextPage()
                }
            }
            bindClicks()
        }
    }

    private fun bindNextPageBeers(list: List<BeerItem>) {
        listBeers.addAll(list)
        beersAdapter?.notifyDataSetChanged()
    }

    private fun bindClicks() {
        beersAdapter?.onClickBeer = {
            findNavController().navigate(
                R.id.action_BeerListFragment_to_BeerDetailFragment,
                bundleOf(Pair(BEER_IDENTIFIER, it))
            )
        }
    }
}