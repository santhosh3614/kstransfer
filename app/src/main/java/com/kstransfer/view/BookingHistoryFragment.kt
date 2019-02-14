package com.kstransfer.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.kstransfer.R

class BookingHistoryFragment : Fragment() {

    companion object {
        fun newInstance() = BookingHistoryFragment()
    }

    private lateinit var viewModel: BookingHistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_booking_history, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BookingHistoryViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
