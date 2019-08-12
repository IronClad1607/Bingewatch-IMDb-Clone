package com.ironclad.bingewatch.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ironclad.bingewatch.R
import com.ironclad.bingewatch.adapters.movies.ReviewAdapter
import com.ironclad.bingewatch.movie_modal.Review
import com.ironclad.bingewatch.movie_modal.Reviews
import com.ironclad.bingewatch.network.RetroClient
import kotlinx.android.synthetic.main.activity_review.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ReviewActivity : AppCompatActivity(), CoroutineScope {

    var i: Int = 2
    var loadingMore: Boolean = false
    var lastVisibleItemID: Int = 0
    var mReviews = ArrayList<Review>()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val movieID = intent.getIntExtra("movieReviewID", 0)
        rvReviews.layoutManager = layoutManager
        launch {
            createReviews(movieID, 1, 0)

            rvReviews.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    lastVisibleItemID = layoutManager.findLastVisibleItemPosition()
                    if (lastVisibleItemID == mReviews.size - 1 && !loadingMore) {
                        loadMore(i++, movieID)
                    }
                }
            })

        }
    }

    private fun loadMore(i: Int, movieID: Int) {
        loadingMore = true
        launch {
            createReviews(movieID, i, lastVisibleItemID)
        }
    }

    private suspend fun createReviews(movieID: Int, page: Int, last: Int) {
        val reviewAPI = RetroClient.movieAPI
        val response = reviewAPI.getReviews(movieID, page)
        Log.d("Reviews","${response.body()}")
        if (response.isSuccessful) {
            val nReview: ArrayList<Review>? = response.body()?.results
            Log.d("Reviews", "$nReview")
            if (loadingMore) {
                mReviews.addAll(nReview!!)
                rvReviews.scrollToPosition(last)
            } else {
                mReviews = nReview!!
                rvReviews.adapter = ReviewAdapter(mReviews)
            }
        }
    }
}
