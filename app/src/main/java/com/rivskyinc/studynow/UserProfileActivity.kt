package com.rivskyinc.studynow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserProfileActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var userImage : ImageView
    private lateinit var userName : TextView
    private lateinit var buttonLogOut : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        initViews()

        auth = Firebase.auth
        setupUserImage()
        userName.text = auth.currentUser?.displayName


        buttonLogOut.setOnClickListener {
            auth.signOut()
            finishAffinity()
        }
    }

    private fun initViews() {
        userImage = findViewById(R.id.imageViewProfileUser)
        userName = findViewById(R.id.UserName)
        buttonLogOut = findViewById(R.id.buttonLogOut)

    }

    private fun setupUserImage() {

        Glide.with(this).load(auth.currentUser?.photoUrl).into(userImage)


    }
}