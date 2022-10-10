package com.lx.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lx.myapplication.databinding.FragmentCommunityBinding

class CommunityFragment : Fragment() {
    var _binding: FragmentCommunityBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCommunityBinding.inflate(inflater, container, false)

        binding.button.setOnClickListener {
            activity?.let{
                val intent = Intent(context, CommunityActivity::class.java)
                startActivity(intent)
            }
        }

        return binding.root
    }


}