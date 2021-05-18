package com.example.lab1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lab1.fragments.FragmentA
import com.example.lab1.fragments.FragmentB

class MainActivity : AppCompatActivity(), Communicator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentA = FragmentA()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragmentA).commit()
    }

    override fun passDataComA(editTextInput: String) {
        val bundle = Bundle()
        bundle.putString("message", editTextInput)

        val transaction = this.supportFragmentManager.beginTransaction()
        val fragmentB = FragmentB()
        fragmentB.arguments = bundle

        transaction.replace(R.id.fragment_container, fragmentB)
        transaction.commit()
    }

    override fun passDataComB(editTextInput: String) {
        val bundle = Bundle()
        bundle.putString("message", editTextInput)

        val transaction = this.supportFragmentManager.beginTransaction()
        val fragmentA = FragmentA()
        fragmentA.arguments = bundle

        transaction.replace(R.id.fragment_container, fragmentA)
        transaction.commit()
    }
}