package com.example.lab3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.VolleyError
import com.example.lab3.databinding.ActivityLoginBinding
import com.example.lab3.helpers.KeyboardHelper
import com.example.lab3.helpers.setSharedPref
import com.example.lab3.repo.Auth
import com.example.lab3.repo.VolleyCallback
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvSignUp.setOnClickListener {
            startActivity(Intent(this@LoginActivity,CreateAccountActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            KeyboardHelper().hideKeyboard(this,binding.btnLogin)
            binding.overlay.visibility = View.VISIBLE

            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            Auth().login(this,username = username,password = password, callback = object:
                VolleyCallback {
                override fun onSuccess(result: JSONObject){
                    // we got something
                    if(result.getBoolean("success")){
                        // we got what we needed!
                        // store the id, name, username and access_token in savedprefs
                        setSharedPref("id",result.getLong("id").toString())
                            setSharedPref("name",result.getString("name"))
                        setSharedPref("username",result.getString("username"))
                        setSharedPref("access_token",result.getString("access_token"))

                        // we're good to go home.
                        val i = Intent(this@LoginActivity,HomeActivity::class.java)
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(i)
                        finish()
                    }else{
                        // display the message from the response
                        Toast.makeText(this@LoginActivity,result.getString("message"),
                            Toast.LENGTH_SHORT).show()
                        binding.overlay.visibility = View.GONE
                    }
                }
                override fun onError(result: VolleyError) {
                    // probably a network error
                    Toast.makeText(this@LoginActivity,"Couldn't log in. Try again",
                        Toast.LENGTH_SHORT).show()
                    Log.e("res",result.message.toString())
                    binding.overlay.visibility = View.GONE
                }
            })
        }
    }
}