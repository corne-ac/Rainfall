package com.corne.rainfall.ui

import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.ViewModel
import com.corne.rainfall.R.id.mobile_navigation

inline fun <reified T : ViewModel> Fragment.hiltMainNavGraphViewModels() =
    hiltNavGraphViewModels<T>(mobile_navigation)
