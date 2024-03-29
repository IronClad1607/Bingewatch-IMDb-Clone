package com.ironclad.bingewatch.adapters.movies

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.ironclad.bingewatch.R
import com.ironclad.bingewatch.activities.PeopleActivity
import com.ironclad.bingewatch.movie_modal.Cast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cvcastcrew.view.*

class CastAdapter(private val cast: ArrayList<Cast>?, val context: Context) :
    RecyclerView.Adapter<CastAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val li = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = li.inflate(R.layout.cvcastcrew, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = cast?.size!!

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val castSingle = cast?.get(position)
        holder.bind(castSingle!!)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(cast: Cast) {
            with(itemView) {
                tvName.text = cast.name
                tvCharacter.text = cast.character
                Picasso.get().load("https://image.tmdb.org/t/p/w500${cast.profile_path}").fit().into(imagePoster)
                setOnClickListener {
                    val peopleIntent = Intent(context, PeopleActivity::class.java)
                    peopleIntent.putExtra("peopleId", cast.id)
                    startActivity(context, peopleIntent, null)
                }
            }
        }
    }
}