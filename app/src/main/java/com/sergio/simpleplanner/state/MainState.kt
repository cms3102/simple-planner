package com.sergio.simpleplanner.state

import com.sergio.common.base.BaseState

sealed interface MainState : BaseState {
    data object Loading : MainState
    data object Success : MainState
}