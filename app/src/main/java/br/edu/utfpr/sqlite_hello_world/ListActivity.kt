package br.edu.utfpr.sqlite_hello_world

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.SimpleCursorAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.utfpr.sqlite_hello_world.database.DatabaseHandler
import br.edu.utfpr.sqlite_hello_world.databinding.ActivityListBinding

class ListActivity : AppCompatActivity() {
    private lateinit var binding : ActivityListBinding
    private lateinit var database : DatabaseHandler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = DatabaseHandler(this)

        val registers = database.cursorList()
        val adapter = SimpleCursorAdapter(
            this,
            android.R.layout.simple_list_item_2,
            registers,
            arrayOf("display_name", "phone"),
            intArrayOf(android.R.id.text1, android.R.id.text2),
            0
        )

        binding.mainListView.adapter = adapter
    }
}