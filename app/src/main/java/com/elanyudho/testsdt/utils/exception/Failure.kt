package com.elanyudho.testsdt.utils.exception

import com.elanyudho.testsdt.utils.vo.RequestResults

data class  Failure(val requestResults: RequestResults, val throwable: Throwable, val code:String="")