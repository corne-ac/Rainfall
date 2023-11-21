package com.corne.rainfall.ui

import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.ViewModel
import com.corne.rainfall.R.id.mobile_navigation

/**
 * Extension function for Fragment class to get ViewModel instances associated with the main navigation graph.
 *
 * This function uses the Hilt library's hiltNavGraphViewModels function to get ViewModel instances
 * that are scoped to the navigation graph with the id 'mobile_navigation'.
 *
 * @return ViewModel instance of the specified type T.
 */
inline fun <reified T : ViewModel> Fragment.hiltMainNavGraphViewModels() =
    hiltNavGraphViewModels<T>(mobile_navigation)