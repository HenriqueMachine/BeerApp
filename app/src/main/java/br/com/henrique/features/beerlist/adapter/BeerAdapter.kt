package br.com.henrique.features.beerlist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.henrique.data.models.BeerItem
import br.com.henrique.helper.hide
import br.com.henrique.helper.show
import br.com.henrique.testpag.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_beer_list.view.*


class BeerAdapter(private val list: List<BeerItem>) :
    RecyclerView.Adapter<BeerAdapter.BaseBeerViewHolder<*>>() {

    companion object {
        const val DEFAULT_BEER_ELEMENT = 0
    }

    lateinit var onClickBeer: (BeerItem) -> Unit

    override fun getItemViewType(position: Int): Int = DEFAULT_BEER_ELEMENT

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBeerViewHolder<*> {
        return when (viewType) {
            DEFAULT_BEER_ELEMENT -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_beer_list, parent, false)
                DefaultBeer(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: BaseBeerViewHolder<*>, position: Int) {
        val element = list[position]
        when (holder) {
            is DefaultBeer -> holder.bindBeer(element)
        }
    }

    override fun getItemCount(): Int = list.size

    abstract inner class BaseBeerViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bindBeer(beer: T)
    }

    private inner class DefaultBeer(itemView: View) : BaseBeerViewHolder<BeerItem>(itemView) {
        override fun bindBeer(beer: BeerItem) {
            with(itemView) {
                Glide
                    .with(context)
                    .load(beer.image_url)
                    .centerCrop()
                    .into(imageViewImageBeer).also {
                        progressImageBeer.hide()
                        imageViewImageBeer.show()
                    }

                cardViewBeer.setOnClickListener {
                    if (::onClickBeer.isInitialized) {
                        onClickBeer.invoke(beer)
                    }
                }

                textViewNameBeer.text = beer.name.also {
                    progressNameBeer.hide()
                    textViewNameBeer.show()
                }

                textViewAlcoholAverageItem.also {
                    it.text = "${beer.abv}%"
                    bindColorAverage(beer.abv)
                }
            }
        }

        private fun View.bindColorAverage(average: Double) {
            if (average <= 5.0) viewShapeAverageAlcohol.background.setTint(
                ContextCompat.getColor(context, R.color.greenAverageColor)
            )
            if (average > 5.0 && average <= 10.0) viewShapeAverageAlcohol.background.setTint(
                ContextCompat.getColor(context, R.color.orangeAverageColor)
            )
            if (average > 10.0) viewShapeAverageAlcohol.background.setTint(
                ContextCompat.getColor(context, R.color.redAverageColor)
            )
        }
    }
}