package com.ironclad.bingewatch.adapters.movies

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ironclad.bingewatch.R
import com.ironclad.bingewatch.movie_modal.Review
import kotlinx.android.synthetic.main.cvreviews.view.*

class ReviewAdapter(private val reviews: ArrayList<Review>) : RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val li = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = li.inflate(R.layout.cvreviews, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = reviews.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val review = reviews[position]
        holder.bind(review)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(review: Review) {
            with(view) {
                tvAuthor.text = review.author
                tvContent.text = review.content
            }
        }
    }
}