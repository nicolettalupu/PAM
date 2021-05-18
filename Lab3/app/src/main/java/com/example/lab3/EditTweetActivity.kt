package com.example.lab3

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.VolleyError
import com.example.lab3.databinding.ActivityEditTweetBinding
import com.example.lab3.helpers.KeyboardHelper
import com.example.lab3.helpers.ThemeChecker
import com.example.lab3.helpers.getSharedPref
import com.example.lab3.helpers.loadRoundedPhoto
import com.example.lab3.repo.Tweet
import com.example.lab3.repo.VolleyCallback
import org.json.JSONObject

class EditTweetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditTweetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTweetBinding.inflate(layoutInflater)
        ThemeChecker().darkModeChecker(this)
        setContentView(binding.root)

        val id = getSharedPref("id").toString().toInt()
        val token = getSharedPref("access_token").toString()
        // load the user photo
        loadRoundedPhoto(binding.draweeUserPhoto, TwLabAPI(userID = id).data.photo)

        // get intent
        val i = intent
        // add the tweet text to the edittext
        binding.etTweetText.setText(i.getStringExtra("tweet_text"))

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.btnUpdateTweet.setOnClickListener {
            val progressDialog = ProgressDialog(this@EditTweetActivity)
            progressDialog.setMessage("Updating tweet")
            progressDialog.show()
            // lets send out the tweet.
            KeyboardHelper().hideKeyboard(this@EditTweetActivity, binding.btnUpdateTweet)
            val tweetText = binding.etTweetText.text.toString()
            if (tweetText.isNotEmpty()) {
                // we're good to go
                Tweet().edit(
                    this,
                    id,
                    token,
                    i.getIntExtra("tweet_id", 0),
                    tweetText,
                    object : VolleyCallback {
                        override fun onSuccess(result: JSONObject) {
                            if (result.getBoolean("success")) {
                                progressDialog.dismiss()
                                // tweet was sent!
                                Toast.makeText(
                                    this@EditTweetActivity,
                                    "Tweet updated!",
                                    Toast.LENGTH_LONG
                                ).show()
                                finish()
                            } else {
                                progressDialog.dismiss()
                                Toast.makeText(
                                    this@EditTweetActivity,
                                    result.getString("message"),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onError(result: VolleyError) {
                            Toast.makeText(
                                this@EditTweetActivity,
                                result.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    })
            } else {
                Toast.makeText(
                    this@EditTweetActivity,
                    "Type in something to tweet, eh?",
                    Toast.LENGTH_SHORT
                ).show()
            }
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