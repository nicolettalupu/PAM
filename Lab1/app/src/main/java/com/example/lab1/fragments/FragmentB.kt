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

class FragmentB : Fragment() {

    private lateinit var communicator: Communicator
    var displayMessage: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_b, container, false)

        displayMessage = arguments?.getString("message")
        view.findViewById<EditText>(R.id.messageInput2).setText(displayMessage)

        communicator = activity as Communicator
        view.findViewById<Button>(R.id.sendMessage2).setOnClickListener{
            communicator.passDataComB(view.findViewById<EditText>(R.id.messageInput2).text.toString())
        }

        return view
    }
}