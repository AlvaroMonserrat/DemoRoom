package com.rrat.demoroom.view.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.rrat.demoroom.R
import com.rrat.demoroom.WordsApplication
import com.rrat.demoroom.data.entities.ClientCrops
import com.rrat.demoroom.data.entities.Word
import com.rrat.demoroom.view.adapter.WordListAdapter
import com.rrat.demoroom.viewmodel.WordViewModel
import com.rrat.demoroom.viewmodel.WordViewModelFactory

class MainActivity : AppCompatActivity() {

    private val newWordActivityRequestCode = 1
    private val wordViewModel: WordViewModel by viewModels{
        WordViewModelFactory((application as WordsApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = WordListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        wordViewModel.allWords.observe(this){
            words-> words.let { adapter.submitList(it) }
        }

        wordViewModel.allClientCrops.observe(this){
            clientCrops-> clientCrops.let{
                Log.i("Salida", it[0].toString())
          }
       }


        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener{
            val intent = Intent(this@MainActivity, NewWordActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK)
        {
            data?.getStringExtra(NewWordActivity.EXTRA_REPLY)?.let{
                reply -> val word = Word(word = reply)
                wordViewModel.insertWord(word = word)

            }
            wordViewModel.insertClientCrops(ClientCrops("dada", "ddd"))
        }else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}