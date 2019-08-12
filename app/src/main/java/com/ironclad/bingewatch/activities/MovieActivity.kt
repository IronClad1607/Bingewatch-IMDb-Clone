package com.ironclad.bingewatch.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ironclad.bingewatch.R
import com.ironclad.bingewatch.adapters.movies.*
import com.ironclad.bingewatch.movie_modal.*
import com.ironclad.bingewatch.network.RetroClient
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie.*
import kotlinx.android.synthetic.main.content_movie.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.math.roundToLong

class MovieActivity : AppCompatActivity(), CoroutineScope {

    var iSimilar: Int = 2
    var loadingMoreSimilar: Boolean = false
    var lastVisibleItemIdSimilar: Int = 0
    var mSimilar = ArrayList<MovieDetails>()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        val movieID = intent.getIntExtra("movieId", 0)
        launch {
            val movieBody = createDetails(movieID)
            val creditBody = createCredits(movieID)
            val reviewBody = createReview(movieID)
            tvTitle.text = movieBody?.title
            val year = movieBody?.release_date?.substring(0, 4)
            tvYear.text = year
            val genres = if (movieBody?.genres?.size!! >= 2) {
                movieBody.genres[0].name + ", " + movieBody.genres[1].name
            } else {
                movieBody.genres[0].name
            }
            tvGenres.text = genres
            Picasso.get().load("https://image.tmdb.org/t/p/w500${movieBody.backdrop_path}").fit().into(backdropImage)
            tvBudget.text = convertBudget(movieBody.budget)
            tvRuntime.text = convertRuntime(movieBody.runtime)
            tvOverview.text = movieBody.overview
            tvtagLine.text = movieBody.tagline
            tvRelease.text = convertDate(movieBody.release_date)
            val runtime = movieBody.runtime.toString() + " min"
            tvRuntimeDetails.text = runtime
            rvCast.layoutManager = LinearLayoutManager(this@MovieActivity, LinearLayout.HORIZONTAL, false)
            rvCrew.layoutManager = LinearLayoutManager(this@MovieActivity, LinearLayout.HORIZONTAL, false)
            rvGenresInMovies.layoutManager = LinearLayoutManager(this@MovieActivity, LinearLayout.HORIZONTAL, false)
            rvPC.layoutManager = LinearLayoutManager(this@MovieActivity, LinearLayout.HORIZONTAL, false)
            val layoutMangerSimilar = LinearLayoutManager(this@MovieActivity, LinearLayout.HORIZONTAL, false)
            rvSimilar.layoutManager = layoutMangerSimilar
            rvCast.adapter = CastAdapter(creditBody?.cast, this@MovieActivity)
            rvCrew.adapter = CrewAdapter(creditBody?.crew, this@MovieActivity)
            rvPC.adapter = CompanyAdapter(movieBody.production_companies, this@MovieActivity)
            rvGenresInMovies.adapter = GenreAdapter(movieBody.genres, this@MovieActivity)
            if (reviewBody?.results?.size!! > 0) {
                tvAuthor.text = reviewBody.results[0].author
                tvContent.text = reviewBody.results[0].content
            } else {
                tvAuthor.text = "Nil"
                tvContent.text = "Nil"
            }

            createSimilar(1, movieID, 0)

            rvSimilar.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    lastVisibleItemIdSimilar = layoutMangerSimilar.findLastVisibleItemPosition()
                    Log.d(
                        "Similar", """
                        $lastVisibleItemIdSimilar
                        ${mSimilar.size}
                    """.trimIndent()
                    )
                    if (lastVisibleItemIdSimilar == mSimilar.size - 1 && !loadingMoreSimilar) {
                        loadMoreSimilar(iSimilar++, movieID)
                    }
                }
            })
        }

        btnSeell.setOnClickListener {
            val reviewIntent = Intent(this, ReviewActivity::class.java)
            reviewIntent.putExtra("movieReviewID", movieID)
            startActivity(reviewIntent)
        }

    }

    private fun loadMoreSimilar(i: Int, movieID: Int) {
        loadingMoreSimilar = true
        launch {
            Log.d(
                "Similar", """
                $loadingMoreSimilar
                Creating Similar
            """.trimIndent()
            )
            createSimilar(i, movieID, lastVisibleItemIdSimilar)
        }
    }

    private suspend fun createSimilar(page: Int, movieID: Int, last: Int) {
        val similarAPI = RetroClient.movieAPI
        Log.d("Similar", "sending call")
        val response = similarAPI.getSimilar(movieID, page)
        Log.d("Similar", "$response")
        if (response.isSuccessful) {
            val nSimilar: ArrayList<MovieDetails>? = response.body()?.results
            if (loadingMoreSimilar) {
                Log.d("Similar", "sending if")
                mSimilar.addAll(nSimilar!!)
                rvSimilar.scrollToPosition(last)
            } else {
                Log.d("Similar", "sending else")
                mSimilar = nSimilar!!
                rvSimilar.adapter = MovieAdapter(mSimilar, this)
            }
            loadingMoreSimilar = false
        }
    }

    private suspend fun createReview(movieID: Int): Reviews? {
        val reviewAPI = RetroClient.movieAPI
        val response = reviewAPI.getReviews(movieID, 1)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    private suspend fun createDetails(id: Int): MovieAllDetails? {
        val detailsAPI = RetroClient.movieAPI
        val response = detailsAPI.getMovieDetails(id)

        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    private suspend fun createCredits(id: Int): CreditsResponse? {
        val creditsAPI = RetroClient.movieAPI
        val response = creditsAPI.getCreditDetails(id)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    private fun convertRuntime(r: Int?): String {
        val hr = r?.div(60)
        val min = r?.rem(60)
        return "${hr.toString()}hr ${min.toString()}min"
    }

    private fun convertDate(str: String?): String {
        val month = str?.substring(5, 7)
        val year = str?.substring(0, 4)
        val date = str?.substring(8, 10)

        val monStr = when (month) {
            "01" -> "January"
            "02" -> "February"
            "03" -> "March"
            "04" -> "April"
            "05" -> "May"
            "06" -> "June"
            "07" -> "July"
            "08" -> "August"
            "09" -> "September"
            "10" -> "October"
            "11" -> "November"
            else -> "December"
        }
        return "$monStr $date $year"
    }

    private fun convertBudget(b: Int?): String {
        val million = 1000000L
        val billion = 1000000000L
        val trillion = 1000000000000L

        val number = b!!.toDouble().roundToLong()
        if (number in million until billion) {
            val fraction = calculateFraction(number, million)
            return fraction.toString() + "Million"
        } else if (number in billion until trillion) {
            val fraction = calculateFraction(number, billion)
            return fraction.toString() + "Billion"
        }
        return number.toString()
    }

    private fun calculateFraction(number: Long, divisor: Long): Float {
        val truncate = (number * 10L + divisor / 2L) / divisor
        return truncate.toFloat() * 0.10f
    }
}




