package com.example.lab3.repo

import android.content.Context
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.lab3.TwLabAPI
import org.json.JSONObject

class Auth {
    fun signup(c: Context, name: String, username: String, password: String, callback: VolleyCallback){
        // Form fields and values
        val params = HashMap<String,String>()
        params["name"] = name
        params["username"] = username
        params["password"] = password
        val formValues = JSONObject(params as Map<String, String>)

        // Volley post request with parameters
        val request = JsonObjectRequest(
            Request.Method.POST,TwLabAPI().auth.signup,formValues,
            Response.Listener { res ->
                callback.onSuccess(res)
            }, Response.ErrorListener{ error ->
                // Error in request
                callback.onError(error)
            })

        // Volley request policy, only one time request to avoid duplicate transaction
        request.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            // 0 means no retry
            0, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES = 2
            1f // DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        // Add the volley post request to the request queue
        Volley.newRequestQueue(c).add(request)
    }

    fun login(c: Context, username: String, password: String, callback: VolleyCallback){
        // Form fields and values
        val params = HashMap<String,String>()
        params["username"] = username
        params["password"] = password
        val formValues = JSONObject(params as Map<String, String>)

        // Volley post request with parameters
        val request = JsonObjectRequest(
            Request.Method.POST,TwLabAPI().auth.login,formValues,
            Response.Listener { res ->
                callback.onSuccess(res)
            }, Response.ErrorListener{ error ->
                // Error in request
                callback.onError(error)
            })

        // Volley request policy, only one time request to avoid duplicate transaction
        request.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            // 0 means no retry
            0, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES = 2
            1f // DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        // Add the volley post request to the request queue
        Volley.newRequestQueue(c).add(request)
    }
}