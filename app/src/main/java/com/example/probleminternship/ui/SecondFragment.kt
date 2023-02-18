package com.example.probleminternship.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.probleminternship.databinding.FragmentSecondBinding
import com.example.probleminternship.utils.MSG_TEXT_KEY

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val receiveMsg = binding.receivedValueId
        val str = activity?.intent?.getStringExtra(MSG_TEXT_KEY)
        receiveMsg.text = str
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
