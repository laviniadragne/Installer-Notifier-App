package com.example.probleminternship.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.probleminternship.SecondActivity
import com.example.probleminternship.databinding.FragmentMainBinding
import com.example.probleminternship.utils.MSG_TEXT_KEY

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    // Button for send text
    binding.apply {
        sendButtonId.setOnClickListener {
            val context = requireContext()
            val str = binding.sendTextId.text.toString()

            // Create an intent with a destination of SecondActivity
            val intent = Intent(context, SecondActivity::class.java)

            intent.putExtra(MSG_TEXT_KEY, str)

            // Start an activity using the data and destination from the Intent
            context.startActivity(intent)
        }
    }

    // Button for list apps
    binding.listButtonId.setOnClickListener {
        // Create an action from MainFragment to AppListFragment
        val action = MainFragmentDirections.actionMainFragmentToAppListFragment()

        // Navigate using that action
        view.findNavController().navigate(action)
    }
}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}