package com.kstransfer.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.kstransfer.R

class TermsAndConditionsFragment : Fragment() {

    companion object {
        fun newInstance() = TermsAndConditionsFragment()
    }

    private lateinit var viewModel: TermsAndConditionsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_terms_and_conditions, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TermsAndConditionsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
