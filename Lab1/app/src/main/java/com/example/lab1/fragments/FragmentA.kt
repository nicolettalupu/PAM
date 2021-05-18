package com.example.lab1.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.lab1.Communicator
import com.example.lab1.R

class FragmentA : Fragment() {

    private lateinit var communicator: Communicator
    var displayMessage: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_a, container, false)

        displayMessage = arguments?.getString("message")
        view.findViewById<EditText>(R.id.messageInput1).setText(displayMessage)

        communicator = activity as Communicator
        view.findViewById<Button>(R.id.sendMessage1).setOnClickListener {
            communicator.passDataComA(view.findViewById<EditText>(R.id.messageInput1).text.toString())
        }

        return view
    }
}