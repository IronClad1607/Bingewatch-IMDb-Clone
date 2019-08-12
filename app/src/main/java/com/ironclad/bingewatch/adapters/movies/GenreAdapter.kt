package com.ironclad.bingewatch.adapters.movies

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ironclad.bingewatch.R
import com.ironclad.bingewatch.movie_modal.Genres
import kotlinx.android.synthetic.main.cvgenremovie.view.*

class GenreAdapter(private val genres: ArrayList<Genres>?, val context: Context) :
    RecyclerView.Adapter<GenreAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val li = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = li.inflate(R.layout.cvgenremovie, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = genres?.size!!

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val genre = genres?.get(position)
        holder.bind(genre!!)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(genres: Genres) {
            with(view) {
                gen.text = genres.name
            }
        }
    }
}