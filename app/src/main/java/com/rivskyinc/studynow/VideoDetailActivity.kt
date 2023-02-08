package com.rivskyinc.studynow

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.rivskyinc.studynow.adapters.CommentsAdapter

class VideoDetailActivity : AppCompatActivity() {

    private lateinit var titleDetail: TextView
    private lateinit var youtubePlayerView: YouTubePlayerView
    private lateinit var auth: FirebaseAuth
    private lateinit var editTextSendComment: EditText
    private lateinit var buttonSend: Button
    private lateinit var adapter: CommentsAdapter
    private lateinit var recyclerViewComments: RecyclerView
    private lateinit var commentArrayList: ArrayList<Comment>
    private lateinit var ratingBar: RatingBar
    private lateinit var ratingPoint : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_detail)
        auth = Firebase.auth
        val database = Firebase.database

        initViews()

        val videoTitle: String? = intent.getStringExtra("video.title")

        val videoID: String? = intent.getStringExtra("video.url")

        titleDetail.text = videoTitle
        //  val uiController: PlayerUIController = youTubePlayerView.getPlayerUIController()
        ratingBar.rating = .0f
        ratingBar.stepSize = .5f

        ratingBar.setOnRatingBarChangeListener{ ratingBar, rating, fromUser ->
           
            ratingPoint.text = rating.toString()
            val ref = database.getReference("Videos")
            ref.child(videoID.toString()).child("rating").setValue("$rating")
        }


            database.getReference("Videos").child(videoID.toString()).child("rating").
            get().addOnSuccessListener {

                ratingBar.rating = (it.value).toString().toFloat()

            }.addOnFailureListener{
                Log.d("firebase", "Error getting data", it )
            }



        buttonSend.setOnClickListener {

            val ref = database.getReference("Videos")
            ref.child(videoID.toString()).child("comments").child(ref.push().key ?: "newPath")
                .setValue(
                    Comment(
                        editTextSendComment.text.toString(),
                        auth.currentUser?.uid,
                        auth.currentUser?.photoUrl.toString(),
                        auth.currentUser?.displayName,
                        videoID
                    )
                )
            editTextSendComment.editableText.clear()
        }
        loadComments()

        actionBar?.hide()

        youtubePlayerView.enterFullScreen()
        youtubePlayerView.toggleFullScreen()

        lifecycle.addObserver(youtubePlayerView)

        // on below line we are adding listener
        // for  youtube player view.

        youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {

                // loading the selected video
                // into the YouTube Player

                youTubePlayer.loadVideo(videoID!!, 0f)

            }

        })

    }

    private fun loadComments() {

        val videoID: String? = intent.getStringExtra("video.url")
        commentArrayList = ArrayList()

        FirebaseDatabase.getInstance().getReference("Videos").ref.child(videoID.toString())
            .child("comments")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    commentArrayList.clear()
                    for (s in snapshot.children) {
                        val comment = s.getValue(Comment::class.java)
                        comment?.let { commentArrayList.add(it) }
                    }
                    adapter = CommentsAdapter(this@VideoDetailActivity, commentArrayList)

                    recyclerViewComments.layoutManager = LinearLayoutManager(

                        this@VideoDetailActivity,
                        LinearLayoutManager.VERTICAL, false
                    )

                    recyclerViewComments.adapter = adapter
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@VideoDetailActivity, "Error : Database", Toast.LENGTH_LONG).show()
                }

            })
    }

    private fun initViews() {
        titleDetail = findViewById(R.id.titleDetailView)

        //urlVideo = findViewById(R.id.videoView)
        editTextSendComment = findViewById(R.id.editTextComment)
        youtubePlayerView = findViewById(R.id.youTubePlayerView)
        buttonSend = findViewById(R.id.buttonSend)
        recyclerViewComments = findViewById(R.id.recyclerViewComments)
        ratingBar = findViewById(R.id.ratingBar)
        ratingPoint = findViewById(R.id.ratingPoint)

    }

    companion object {

        fun NewIntent(context: Context, video: Video): Intent {

            val intent = Intent(context, VideoDetailActivity::class.java)
            intent.putExtra("video.url", video.url)
            intent.putExtra("video.title", video.title)
            return intent
        }
    }

}
