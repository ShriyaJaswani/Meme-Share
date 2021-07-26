package com.project.myapplication

import android.app.DownloadManager
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.provider.Settings.Secure.getString
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
//    var image : ImageView? = null
    var currentUrl : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        loadMeme()
    }

    private fun loadMeme() {
        progress.visibility = View.VISIBLE
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"

        val stringRequest = JsonObjectRequest(Request.Method.GET, url, null,
                { response ->
                    currentUrl = response.getString("url")
                    Glide.with(this).load(currentUrl).listener(object : RequestListener<Drawable>{
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                                progress.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                                progress.visibility = View.GONE
                            return false
                        }

                    }).into(memeImage)
                    Log.d("Success", "messege")

                },
                {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                    Log.d("searching the error", "messege")
                })
        queue.add(stringRequest)
    }


    fun shareButton(view: View) {
        var intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Checkout this meme I got from reddit $currentUrl")
        val chooser = Intent.createChooser(intent, "Send this using...")
        startActivity(intent)
    }
    fun loadNext(view: View) {
        loadMeme()
    }
}