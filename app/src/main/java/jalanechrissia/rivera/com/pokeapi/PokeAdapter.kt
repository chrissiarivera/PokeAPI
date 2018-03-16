package jalanechrissia.rivera.com.pokeapi

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.pokemon_row.view.*

/**
 * Created by Jalane Chrissia on 08/03/2018.
 */
class PokeAdapter(private val pokemonList: ArrayList<Pokemon>) : RecyclerView.Adapter<CustomViewHolder>(){

    override fun onBindViewHolder(holder: CustomViewHolder?, position: Int) {
        val pokePos: Pokemon = pokemonList[position]
        holder!!.view.txtPokemonName.text = pokePos.name
        val pokeImage = holder.view.imgPokemon
        Picasso.with(holder.view.context).load(pokePos.sprites.front_default).into(pokeImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.pokemon_row, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }
}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view) {

}