package com.elanyudho.testsdt.ui.secondpart

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import com.elanyudho.testsdt.R
import com.elanyudho.testsdt.databinding.ActivitySecondPartBinding
import com.elanyudho.testsdt.ui.secondpart.adapter.JokesAdapter
import com.elanyudho.testsdt.utils.extension.gone
import com.elanyudho.testsdt.utils.extension.visible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SecondPartActivity : AppCompatActivity(), Observer<SecondPartViewModel.JokesUiState> {

    @Inject
    lateinit var mViewModel: SecondPartViewModel

    private lateinit var binding: ActivitySecondPartBinding

    private val jokesAdapter: JokesAdapter by lazy { JokesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondPartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mViewModel.uiState.observe(this, this)
        mViewModel.doLoadJokesList()

        setJokesAction()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.hint_search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.length == 0) {
                    mViewModel.searchJokes("all")
                } else {
                    mViewModel.searchJokes(newText)
                }
                return true
            }
        })
        return true
    }

    override fun onChanged(state: SecondPartViewModel.JokesUiState?) {
        when(state) {

            is SecondPartViewModel.JokesUiState.SuccessLoadJokesList -> {
                jokesAdapter.submitList(state.body)
            }
            is SecondPartViewModel.JokesUiState.Loading -> {
                with(binding) {
                    if (state.isLoading) {
                        progressBar.visible()
                        rvJokes.gone()
                    } else {
                        progressBar.gone()
                        rvJokes.visible()
                    }
                }
            }
            is SecondPartViewModel.JokesUiState.FailedLoad -> {
                //Toast.makeText(this, state.failure, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setJokesAction() {

        with(binding.rvJokes) {
            adapter = jokesAdapter
            setHasFixedSize(true)
        }

        jokesAdapter.setOnClickData {
            Toast.makeText(this, "the id of the item is ${it.id}", Toast.LENGTH_SHORT).show()
        }
    }

}