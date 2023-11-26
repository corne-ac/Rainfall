package com.corne.rainfall.ui.help

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.corne.rainfall.databinding.FragmentHelpBinding
import com.corne.rainfall.ui.base.BaseFragment

/**
 * This is a helpFragment class which extends the BaseFragment class.
 * It is responsible for managing the UI and user interactions in the help section of the application.
 */
class helpFragment : BaseFragment<FragmentHelpBinding>() {

    /**
     * This function is responsible for adding content to the view.
     * It sets click listeners on the questions, which toggle the visibility of the corresponding answers.
     */
    override suspend fun addContentToView() {

        binding.q1.setOnClickListener { setVisibility(binding.a1) }
        binding.q2.setOnClickListener { setVisibility(binding.a2) }

    }

    /**
     * This function is responsible for setting the visibility of the TextView passed as a parameter.
     * Initially, it sets all answers to gone. Then, it toggles the visibility of the selected answer.
     * @param a The TextView whose visibility is to be set.
     */
    private fun setVisibility(a: TextView) {
        //Set all answers to gone
        binding.a1.visibility = View.GONE
        binding.a2.visibility = View.GONE
        //Then set selected to visible
        if (a.visibility == View.VISIBLE) {
            a.visibility = View.GONE
        } else {
            a.visibility = View.VISIBLE
        }

    }

    /**
     * This function is responsible for creating the view binding for the fragment.
     * It inflates the layout for the fragment.
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container This is the parent view that the fragment's UI should be attached to.
     * @return Returns the FragmentHelpBinding object.
     */
    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHelpBinding = FragmentHelpBinding.inflate(inflater, container, false)
}