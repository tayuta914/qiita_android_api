package com.example.qiita_android_api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.qiita_android_api.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val listApi by lazy { getApi() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            fetchItems()
        }
    }



    private fun fetchItems() {

        listApi.items().enqueue(object : Callback<List<Item>> {
            override fun onFailure(call: Call<List<Item>>?, t: Throwable?) {
                Log.d("fetchItems", "response fail")
                Log.d("fetchItems", "throwable :$t")
            }

            override fun onResponse(call: Call<List<Item>>?, response: Response<List<Item>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d("fetchItems", "response success")
                        var items = mutableListOf<String>()

                        for (item in it) {
                            items.add(item.title)
                        }

                        val adapter = ArrayAdapter<String>(this@MainActivity, android.R.layout.simple_list_item_1, items)
                        val list: ListView = findViewById(R.id.list_item);
                        list.adapter  = adapter
                    }
                }
                Log.d("fetchItems", "response code:" + response.code())
                Log.d("fetchItems", "response errorBody:" + response.errorBody())
            }
        })
    }
}