package br.edu.utfpr.sqlite_hello_world

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.SimpleCursorAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.utfpr.sqlite_hello_world.adapter.CustomAdapter
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
        val adapter = CustomAdapter(
            this,
            registers,
        )

        binding.mainListView.adapter = adapter
    }
}