package br.com.henrique.features.beerdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.henrique.data.models.BeerItem
import br.com.henrique.features.beerlist.ui.ListBeerFragment
import br.com.henrique.testpag.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_beer_detail.*

class BeerDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_beer_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val beer = arguments?.getSerializable(ListBeerFragment.BEER_IDENTIFIER) as BeerItem
        bindBeerDetail(beer)
        bindClicks()

    }

    private fun bindBeerDetail(beer: BeerItem) {
        bindImageBeer(beer)
        textViewNameBeerDetail.text = beer.name
        textViewTagLineBeerDetailContent.text = beer.tagline
        textViewAlcoholBeerDetailContent.text = "${beer.abv}% ABV"
        textViewBitternessScaleBeerDetailContent.text = "${beer.ibu} IBU"
        textViewDescriptionBeerContent.text = beer.description
    }

    private fun bindImageBeer(beer: BeerItem) {
        Glide
            .with(requireContext())
            .load(beer.image_url)
            .centerCrop()
            .into(imageViewImageBeerDetail)
    }
    private fun bindClicks(){
        imageViewArrowBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}