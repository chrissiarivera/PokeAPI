package jalanechrissia.rivera.com.pokeapi

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private var txtMessage: TextView? = null
    private var mRecyclerView: RecyclerView? = null
    private val pokemon = ArrayList<Pokemon>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViews()

        mRecyclerView!!.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        mRecyclerView!!.addItemDecoration(DividerItemDecoration(recyclerView_main.context, LinearLayoutManager.VERTICAL))

        fetchJson()
    }

    private fun findViews() {
        txtMessage = findViewById<TextView>(R.id.txtMessage)
        mRecyclerView = findViewById<RecyclerView>(R.id.recyclerView_main)
    }

    private fun fetchJson() {
        for(i in 1..20) {
            doAsync {
                val result = "https://pokeapi.co/api/v2/pokemon/$i"
                val pokeClient = OkHttpClient()
                val pokeRequest = Request.Builder().url(result).build()
                pokeClient.newCall(pokeRequest).enqueue(object : Callback {
                    override fun onResponse(call: Call?, response: Response?) {
                        val pokeBody = response?.body()?.string()
                        val pokeGson = GsonBuilder().create()
                        val pokeFeed = pokeGson.fromJson(pokeBody, Pokemon::class.java)

                        uiThread {
                            pokemon.add(Pokemon(pokeFeed.name, pokeFeed.sprite))
                            val adapter = PokeAdapter(pokemon)
                            mRecyclerView!!.adapter = adapter

                            if(pokemon.size!=0) {
                                txtMessage!!.text = "You have "+ pokemon.size.toString() + " Pokemons"

                            }
                            if(pokemon.size == 20) {
                                progressBar.visibility = View.GONE
                            }
                        }
                    }
                    override fun onFailure(call: Call?, e: IOException?) {
                        println("Failed to execute object request")
                    }
                })
            }
        }
    }
}



