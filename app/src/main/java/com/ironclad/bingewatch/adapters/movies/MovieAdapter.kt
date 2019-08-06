package com.ironclad.bingewatch.adapters.movies

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ironclad.bingewatch.R
import com.ironclad.bingewatch.movie_modal.MovieDetails
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cvmovies.view.*

class MovieAdapter(private val popularMovies: ArrayList<MovieDetails>, val context: Context) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val li = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = li.inflate(R.layout.cvmovies, parent, false)
        return ViewHolder(view, context)
    }

    override fun getItemCount() = popularMovies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val popularMovie = popularMovies[position]
        holder.bind(popularMovie)
    }

    class ViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView) {
        fun bind(popularMovies: MovieDetails) {
            with(itemView) {
                tvTitleMovie.text = popularMovies.title
                val year = popularMovies.release_date.substring(0, 4)
                tvYearMovie.text = year
                Picasso.get().load("https://image.tmdb.org/t/p/w500${popularMovies.poster_path}").fit()
                    .into(imagePoster)
            }
        }
    }

}