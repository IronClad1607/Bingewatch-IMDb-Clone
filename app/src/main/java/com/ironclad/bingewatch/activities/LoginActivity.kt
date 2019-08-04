package com.ironclad.bingewatch.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.edit
import com.ironclad.bingewatch.R
import com.ironclad.bingewatch.auth.AuthRequestToken
import com.ironclad.bingewatch.auth.SessionResponseBody
import com.ironclad.bingewatch.network.getGuestSession
import com.ironclad.bingewatch.network.getTokenRequest
import com.ironclad.bingewatch.network.postAuthTokenRequest
import com.ironclad.bingewatch.network.postSession
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LoginActivity : AppCompatActivity(), CoroutineScope {
    private val supervisor = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + supervisor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val pref = getPreferences(Context.MODE_PRIVATE)

        val retainLoginInIntent = Intent(this, HomeActivity::class.java)
        if (pref.contains("username") && pref.contains("password")) {
            startActivity(retainLoginInIntent)
        }


        btnGuest.setOnClickListener {
            launch {
                val session = getGuestSession()
                Log.i("Guest", "$session")

            }
        }

        btnLogin.setOnClickListener {
            launch {
                val token = getTokenRequest()

                val authRequestBody =
                    AuthRequestToken(
                        etEmail.text.toString(),
                        etPassword.text.toString(),
                        token?.request_token
                    )

                val authResponse = postAuthTokenRequest(authRequestBody)


                val requestToken = authResponse?.request_token //Authorized RequestToken
                val sessionResponseBody = SessionResponseBody(requestToken)

                val sessionResponse = postSession(sessionResponseBody)

                Log.d("Session", "$sessionResponse")
                if (sessionResponse != null) {
                    pref.edit {
                        putString("username", etEmail.text.toString())
                        putString("password", etPassword.text.toString())
                        putString("sessionId", sessionResponse!!.session_id)
                    }
                    btnLogin.doResult(true)
                    val logInIntent = Intent(this@LoginActivity, HomeActivity::class.java)
                    logInIntent.putExtra("session_id", sessionResponse!!.session_id)
                    startActivity(logInIntent)
                } else {
                    Toast.makeText(this@LoginActivity, "Invalid Username or Password!", Toast.LENGTH_LONG).show()
                }
            }
        }


        btnSignUp.setOnClickListener {
            val signUpIntent = Intent()
            signUpIntent.action = Intent.ACTION_VIEW
            signUpIntent.data = Uri.parse("https://www.themoviedb.org/account/signup")
            startActivity(signUpIntent)
            btnSignUp.reset()

            Log.d("Signup", "$signUpIntent")
        }
    }
}
