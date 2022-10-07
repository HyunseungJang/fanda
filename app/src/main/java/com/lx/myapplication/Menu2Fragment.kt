package com.lx.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lx.myapplication.databinding.FragmentMenu2Binding

class Menu2Fragment : Fragment() {
    var _binding: FragmentMenu2Binding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMenu2Binding.inflate(inflater, container, false)

        return binding.root
    }
}