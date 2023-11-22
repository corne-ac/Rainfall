package com.corne.rainfall.ui.help

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.corne.rainfall.R
import com.corne.rainfall.databinding.FragmentHelpBinding
import com.corne.rainfall.databinding.FragmentHomeBinding
import com.corne.rainfall.ui.base.BaseFragment

class helpFragment : BaseFragment<FragmentHelpBinding>() {


    override suspend fun addContentToView() {

        binding.q1.setOnClickListener { setVisibility(binding.a1) }
        binding.q2.setOnClickListener { setVisibility(binding.a2) }

    }

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

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHelpBinding = FragmentHelpBinding.inflate(inflater, container, false)
}