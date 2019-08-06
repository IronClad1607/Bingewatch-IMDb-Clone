package com.ironclad.bingewatch.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.ironclad.bingewatch.R
import com.ironclad.bingewatch.adapters.movies.MovieAdapter
import com.ironclad.bingewatch.movie_modal.MovieDetails
import com.ironclad.bingewatch.network.RetroClient
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MovieFragment : Fragment(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    var iPopular: Int = 2
    var iCinema: Int = 2
    var iUpcoming: Int = 2
    var iTopRated: Int = 2
    var loadingMorePopular: Boolean = false
    var loadingMoreCinema: Boolean = false
    var loadingMoreUpcoming: Boolean = false
    var loadingMoreTopRated: Boolean = false
    var lastVisibleItemIdPopular: Int = 0
    var lastVisibleItemIdCinema: Int = 0
    var lastVisibleItemIdUpcoming: Int = 0
    var lastVisibleItemIdTopRated: Int = 0
    var mMoviesPopular = ArrayList<MovieDetails>()
    var mMoviesCinema = ArrayList<MovieDetails>()
    var mMoviesUpcoming = ArrayList<MovieDetails>()
    var mMoviesTopRated = ArrayList<MovieDetails>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        launch {
            val lMPopular = LinearLayoutManager(requireContext(), LinearLayout.HORIZONTAL, false)
            val lMCinema = LinearLayoutManager(requireContext(), LinearLayout.HORIZONTAL, false)
            val lMUpcoming = LinearLayoutManager(requireContext(), LinearLayout.HORIZONTAL, false)
            val lMTopRated = LinearLayoutManager(requireContext(), LinearLayout.HORIZONTAL, false)

            rvPopularMovies.layoutManager = lMPopular
            rvCinemas.layoutManager = lMCinema
            rvComingSoon.layoutManager = lMUpcoming
            rvTopRated.layoutManager = lMTopRated

            createPopular(1, 0)
            createCinema(1, 0)
            createUpcoming(1, 0)
            createTopRated(1, 0)

            rvPopularMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    lastVisibleItemIdPopular = lMPopular.findLastVisibleItemPosition()
                    if (lastVisibleItemIdPopular == mMoviesPopular.size - 1 && !loadingMorePopular) {
                        loadMorePopular(iPopular++)
                    }
                }
            })

            rvCinemas.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    lastVisibleItemIdCinema = lMCinema.findLastVisibleItemPosition()
                    if (lastVisibleItemIdCinema == mMoviesCinema.size - 1 && !loadingMoreCinema) {
                        loadMoreCinema(iCinema++)
                    }
                }
            })

            rvComingSoon.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    lastVisibleItemIdUpcoming = lMUpcoming.findLastVisibleItemPosition()
                    if (lastVisibleItemIdUpcoming == mMoviesUpcoming.size - 1 && !loadingMoreUpcoming) {
                        loadMoreUpComing(iUpcoming++)
                    }
                }
            })

            rvTopRated.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    lastVisibleItemIdTopRated = lMTopRated.findLastVisibleItemPosition()
                    if (lastVisibleItemIdTopRated == mMoviesTopRated.size - 1 && !loadingMoreTopRated) {
                        loadMoreTopRated(iTopRated++)
                    }
                }
            })

        }
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    private fun loadMoreTopRated(i: Int) {
        loadingMoreTopRated = true
        launch {
            createTopRated(i, lastVisibleItemIdTopRated)
        }
    }

    private suspend fun createTopRated(page: Int, last: Int) {
        val topRatedAPI = RetroClient.movieAPI
        val response = topRatedAPI.getTopRated(page)
        if (response.isSuccessful) {
            val nMovies: ArrayList<MovieDetails>? = response.body()?.results
            if (loadingMoreTopRated) {
                mMoviesTopRated.addAll(nMovies!!)
                rvTopRated.scrollToPosition(last)
            } else {
                mMoviesTopRated = nMovies!!
                rvTopRated.adapter = MovieAdapter(mMoviesTopRated, requireContext()).apply {
                    notifyDataSetChanged()
                }
            }
            loadingMoreTopRated = false
        }
    }

    private fun loadMoreUpComing(i: Int) {
        loadingMoreUpcoming = true
        launch {
            createUpcoming(i, lastVisibleItemIdUpcoming)
        }
    }

    private suspend fun createUpcoming(page: Int, last: Int) {
        val upcomingAPI = RetroClient.movieAPI
        val response = upcomingAPI.getUpcoming(page)
        if (response.isSuccessful) {
            val nMovies: ArrayList<MovieDetails>? = response.body()?.results
            if (loadingMoreUpcoming) {
                mMoviesUpcoming.addAll(nMovies!!)
                rvComingSoon.scrollToPosition(last)
            } else {
                mMoviesUpcoming = nMovies!!
                rvComingSoon.adapter = MovieAdapter(mMoviesUpcoming, requireContext()).apply {
                    notifyDataSetChanged()
                }
            }
            loadingMoreUpcoming = false
        }
    }

    private fun loadMoreCinema(i: Int) {
        loadingMoreCinema = true
        launch {
            createCinema(i, lastVisibleItemIdCinema)
        }
    }

    private suspend fun createCinema(page: Int, last: Int) {
        val cinemaAPI = RetroClient.movieAPI
        val response = cinemaAPI.getInCinema(page)

        if (response.isSuccessful) {
            val nMovies: ArrayList<MovieDetails>? = response.body()?.results
            if (loadingMoreCinema) {
                mMoviesCinema.addAll(nMovies!!)
                rvCinemas.scrollToPosition(last)
            } else {
                mMoviesCinema = nMovies!!
                rvCinemas.adapter = MovieAdapter(mMoviesCinema, requireContext()).apply {
                    notifyDataSetChanged()
                }
            }
            loadingMoreCinema = false
        }
    }

    private fun loadMorePopular(i: Int) {
        loadingMorePopular = true
        launch {
            createPopular(i, lastVisibleItemIdPopular)
        }
    }

    private suspend fun createPopular(page: Int, last: Int) {
        val popularAPI = RetroClient.movieAPI
        val response = popularAPI.getPopularMovies(page)

        Log.d("Movies", "${response.body()?.results}")
        if (response.isSuccessful) {
            val nMovies: ArrayList<MovieDetails>? = response.body()?.results
            if (loadingMorePopular) {
                mMoviesPopular.addAll(nMovies!!)
                rvPopularMovies.scrollToPosition(last)
            } else {
                mMoviesPopular = nMovies!!
                rvPopularMovies.adapter = MovieAdapter(mMoviesPopular, requireContext()).apply {
                    notifyDataSetChanged()
                }
            }
            loadingMorePopular = false
        }
    }


}
