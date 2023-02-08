package com.rivskyinc.studynow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.rivskyinc.studynow.adapters.VideoMainAdapter

class CategoriesActivity : AppCompatActivity() {

    private lateinit var videoArrayList: ArrayList<Video>
    private lateinit var adapter: VideoMainAdapter
    private lateinit var recyclerViewMain: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)
        initViews()
        loadVideosFromFirebase()


    }

    private fun loadVideosFromFirebase() {
        videoArrayList = ArrayList()
        val setupCategory = intent.getStringExtra("Category")

        val category: String? = setupCategory

        val ref = FirebaseDatabase.getInstance().getReference("Videos").orderByChild("category")
            .equalTo(category)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                videoArrayList.clear()
                for (s in snapshot.children) {
                    val modelVideo = s.getValue(Video::class.java)
                    videoArrayList.add(modelVideo!!)
                }

                adapter = VideoMainAdapter(this@CategoriesActivity, videoArrayList)


                recyclerViewMain.layoutManager = LinearLayoutManager(

                    this@CategoriesActivity,
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
                val intent = VideoDetailActivity.NewIntent(this@CategoriesActivity, video)

                startActivity(intent)
            }

        }
    }

    private fun initViews() {
        recyclerViewMain = findViewById(R.id.RecyclerViewCategories)
        // toolBar = findViewById(R.id.toolbar)


    }

}