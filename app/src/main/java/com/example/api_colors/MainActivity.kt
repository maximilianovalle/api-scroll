package com.example.api_colors

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {
    private lateinit var colorRecyclerView: RecyclerView
    private val colorList = mutableListOf<Color>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        colorRecyclerView = findViewById(R.id.color_list)
        colorRecyclerView.layoutManager = LinearLayoutManager(this)
        colorRecyclerView.adapter = ColorAdapter(colorList)

        fetchColors()
    }

    private fun fetchColors() {
        val client = AsyncHttpClient()
        val url = "https://www.colourlovers.com/api/colors/new?numResults=20&format=json"

        client[url, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                val colorArray = json.jsonArray
                for (i in 0 until colorArray.length()) {
                    val colorObject = colorArray.getJSONObject(i)
                    val title = colorObject.getString("title")
                    val hex = colorObject.getString("hex")
                    val dateCreated = colorObject.getString("dateCreated")

                    val color = Color(title, hex, dateCreated)
                    colorList.add(color)
                }

                runOnUiThread {
                    colorRecyclerView.adapter?.notifyDataSetChanged()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Colors Error", errorResponse)
            }
        }]
    }
}