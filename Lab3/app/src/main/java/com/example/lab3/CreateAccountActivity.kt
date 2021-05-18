package com.example.lab3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.VolleyError
import com.example.lab3.databinding.ActivityCreateAccountBinding
import com.example.lab3.helpers.KeyboardHelper
import com.example.lab3.helpers.setSharedPref
import com.example.lab3.repo.Auth
import com.example.lab3.repo.VolleyCallback
import org.json.JSONObject

class CreateAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.btnCreate.setOnClickListener {
            // hide the keyboard & show the overlay to block controls
            KeyboardHelper().hideKeyboard(this, binding.btnCreate)
            binding.overlay.visibility = View.VISIBLE
            Toast.makeText(this, "Creating your account", Toast.LENGTH_SHORT).show()
            // try adn create the account.
            val name = binding.etName.text.toString()
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            Auth().signup(
                this,
                name = name,
                username = username,
                password = password,
                callback = object :
                    VolleyCallback {
                    override fun onSuccess(result: JSONObject) {
                        // we got something
                        if (result.getBoolean("success")) {
                            // we got what we needed!
                            // store the id, name, username and access_token in savedprefs
                            setSharedPref("id", result.getLong("id").toString())
                            setSharedPref("name", result.getString("name"))
                            setSharedPref("username", result.getString("username"))
                            setSharedPref("access_token", result.getString("access_token"))

                            // we're good to go home.
                            val i = Intent(this@CreateAccountActivity, HomeActivity::class.java)
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            startActivity(i)
                            finish()
                        } else {
                            // display the message from the response
                            Toast.makeText(
                                this@CreateAccountActivity,
                                result.getString("message"),
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.overlay.visibility = View.GONE
                        }
                    }

                    override fun onError(result: VolleyError) {
                        // probably a network error
                        Toast.makeText(
                            this@CreateAccountActivity,
                            "Couldn't create your account. Try again",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.e("res", result.message.toString())
                        binding.overlay.visibility = View.GONE
                    }
                })
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        return
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}