package com.ironclad.bingewatch.adapters.movies

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ironclad.bingewatch.R
import com.ironclad.bingewatch.movie_modal.Crew
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cvcastcrew.view.*

class CrewAdapter(private val crew: ArrayList<Crew>?, val context: Context) :
    RecyclerView.Adapter<CrewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val li = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = li.inflate(R.layout.cvcastcrew, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = crew?.size!!

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val crewSingle = crew?.get(position)
        holder.bind(crewSingle!!)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(crew: Crew) {
            with(view) {
                tvName.text = crew.name
                if (crew.department == "Writing") {
                    tvCharacter.text = "${crew.department} / ${crew.job}"
                } else {
                    tvCharacter.text = crew.job
                }
                Picasso.get().load("https://image.tmdb.org/t/p/w500${crew.profile_path}").fit().into(imagePoster)
            }

        }

    }
}