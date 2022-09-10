package com.elanyudho.core.abstract

abstract class UseCase <in Params, out Results>{
    abstract suspend fun run (params: Params) :Results
    object None
}