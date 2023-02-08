package com.rivskyinc.studynow

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.rivskyinc.studynow.adapters.VideoMainAdapter


class MainActivity : AppCompatActivity() {

    private lateinit var videoArrayList: ArrayList<Video>
    private lateinit var adapter: VideoMainAdapter
    private lateinit var recyclerViewMain: RecyclerView
    private lateinit var userImage: ImageView
    private lateinit var auth: FirebaseAuth
    private lateinit var buttonProgramming: Button
    private lateinit var buttonLanguages: Button
    private lateinit var buttonHistory: Button
    private lateinit var buttonPersonalDev : Button
    private lateinit var buttonMusic : Button


    @SuppressLint("PrivateResource")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        loadVideosFromFirebase()
        // toolBar.title = "V i n c i"
        // this.setSupportActionBar(toolbar)

        auth = Firebase.auth

        setupUserImage()
        val anim = AnimationUtils.loadAnimation(
            this,
            com.google.android.material.R.anim.mtrl_bottom_sheet_slide_in
        )


        buttonLanguages.setOnClickListener {
            it.startAnimation(anim)
            val language = "65"

            val intent = Intent(this, CategoriesActivity::class.java)
            intent.putExtra("Category", language)
            startActivity(intent)
        }

        buttonProgramming.setOnClickListener {
            it.startAnimation(anim)
            val programming = "22"

            val intent = Intent(this, CategoriesActivity::class.java)
            intent.putExtra("Category", programming)
            startActivity(intent)
        }

        buttonHistory.setOnClickListener {
            it.startAnimation(anim)
            val history = "78"

            val intent = Intent(this, CategoriesActivity::class.java)
            intent.putExtra("Category", history)
            startActivity(intent)
        }
        buttonPersonalDev.setOnClickListener {
            it.startAnimation(anim)
            val history = "04"

            val intent = Intent(this, CategoriesActivity::class.java)
            intent.putExtra("Category", history)
            startActivity(intent)
        }
        buttonMusic.setOnClickListener {
            it.startAnimation(anim)
            val history = "09"

            val intent = Intent(this, CategoriesActivity::class.java)
            intent.putExtra("Category", history)
            startActivity(intent)
        }

        userImage.setOnClickListener {
            it.startAnimation(anim)
            startActivity(Intent(this, UserProfileActivity::class.java))

        }
    }

    private fun setupUserImage() {

        Glide.with(this).load(auth.currentUser?.photoUrl).into(userImage)


    }

    private fun initViews() {
        recyclerViewMain = findViewById(R.id.recyclerViewMain)
        userImage = findViewById(R.id.imageUser)

        buttonProgramming = findViewById(R.id.buttonProgramming)
        buttonLanguages = findViewById(R.id.buttonLanguages)
        buttonHistory = findViewById(R.id.buttonHistory)
        buttonPersonalDev = findViewById(R.id.buttonPersonalDevelopment)
        buttonMusic = findViewById(R.id.buttonMusic)
    }


    private fun loadVideosFromFirebase() {
        videoArrayList = ArrayList()
        val ref = FirebaseDatabase.getInstance().getReference("Videos")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                videoArrayList.clear()
                for (s in snapshot.children) {
                    val modelVideo = s.getValue(Video::class.java)
                    videoArrayList.add(modelVideo!!)
                }
                adapter = VideoMainAdapter(this@MainActivity, videoArrayList)
                recyclerViewMain.layoutManager = LinearLayoutManager(

                    this@MainActivity,
                    LinearLayoutManager.VERTICAL, false
                )


                recyclerViewMain.adapter = adapter

                startDetailActivity()

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun startDetailActivity() {
        adapter.onVideoClickListenerObject = object : VideoMainAdapter.OnVideoClickListener {
            override fun onVideoClick(video: Video) {
                val intent = VideoDetailActivity.NewIntent(this@MainActivity, video)

                startActivity(intent)
            }

        }
    }


    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        this.finishAffinity()

    }

}