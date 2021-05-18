package com.example.lab3

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.VolleyError
import com.example.lab3.databinding.ActivityComposeTweetBinding
import com.example.lab3.helpers.KeyboardHelper
import com.example.lab3.helpers.ThemeChecker
import com.example.lab3.helpers.getSharedPref
import com.example.lab3.helpers.loadRoundedPhoto
import com.example.lab3.repo.Tweet
import com.example.lab3.repo.VolleyCallback
import org.json.JSONObject

class ComposeTweetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityComposeTweetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComposeTweetBinding.inflate(layoutInflater)
        ThemeChecker().darkModeChecker(this)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val id = getSharedPref("id").toString().toInt()
        val token = getSharedPref("access_token").toString()
        // load the user photo
        loadRoundedPhoto(binding.draweeUserPhoto,TwLabAPI(userID = id).data.photo)

        // request focus on our edittext
        binding.etTweetText.requestFocus()

        binding.btnTweet.setOnClickListener {
            // progress dialog
            val progressDialog = ProgressDialog(this@ComposeTweetActivity)
            progressDialog.setMessage("Sending out tweet")
            progressDialog.show()

            // lets send out the tweet.
            KeyboardHelper().hideKeyboard(this@ComposeTweetActivity,binding.btnTweet)
            val tweetText = binding.etTweetText.text.toString()
            if(tweetText.isNotEmpty()){
                // we're good to go
                Tweet().new(this,id,token,tweetText,object: VolleyCallback {
                    override fun onSuccess(result: JSONObject) {
                        if(result.getBoolean("success")){
                            // tweet was sent!
                            progressDialog.dismiss()
                            Toast.makeText(this@ComposeTweetActivity,"Tweet sent!", Toast.LENGTH_LONG).show()
                            finish()
                        }else{
                            progressDialog.dismiss()
                            Toast.makeText(this@ComposeTweetActivity,result.getString("message"),
                                Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onError(result: VolleyError) {
                        Toast.makeText(this@ComposeTweetActivity,result.message, Toast.LENGTH_SHORT).show()
                    }

                })
            }else{
                Toast.makeText(this@ComposeTweetActivity,"Type in something to tweet, eh?", Toast.LENGTH_SHORT).show()
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