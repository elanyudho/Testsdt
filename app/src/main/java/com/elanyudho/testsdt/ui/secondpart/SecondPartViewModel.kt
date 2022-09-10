package com.elanyudho.testsdt.ui.secondpart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elanyudho.testsdt.domain.model.Jokes
import com.elanyudho.testsdt.domain.usecase.JokesListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SecondPartViewModel @Inject constructor(
    private val getJokesListUseCase: JokesListUseCase
) : ViewModel(){

    sealed class JokesUiState {
        data class SuccessLoadJokesList(val body: List<Jokes>) : JokesUiState()
        data class FailedLoad(val failure: String) : JokesUiState()
        data class Loading(val isLoading: Boolean) : JokesUiState()
    }

    private val _uiState: MutableLiveData<JokesUiState> = MutableLiveData()

    val uiState: LiveData<JokesUiState>
        get() = _uiState

    fun doLoadJokesList() {
        _uiState.value = JokesUiState.Loading(true)
        viewModelScope.launch(Dispatchers.IO) {

            getJokesListUseCase.run("all")
                .catch {
                    withContext(Dispatchers.Main) {
                        _uiState.value = JokesUiState.Loading(false)
                        _uiState.value = JokesUiState.FailedLoad(it.message.toString())
                    }
                }
                .collect { listData ->
                    withContext(Dispatchers.Main) {
                        _uiState.value = JokesUiState.Loading(false)
                        _uiState.value = JokesUiState.SuccessLoadJokesList(listData)
                    }
                }
        }
    }

    fun searchJokes(query: String) {
        _uiState.value = JokesUiState.Loading(true)
        viewModelScope.launch(Dispatchers.IO) {

            getJokesListUseCase.run(query)
                .catch {
                    withContext(Dispatchers.Main) {
                        _uiState.value = JokesUiState.Loading(false)
                        _uiState.value = JokesUiState.FailedLoad(it.message.toString())
                    }
                }
                .collect { listData ->
                    withContext(Dispatchers.Main) {
                        _uiState.value = JokesUiState.Loading(false)
                        _uiState.value = JokesUiState.SuccessLoadJokesList(listData)
                    }
                }
        }
    }
}
