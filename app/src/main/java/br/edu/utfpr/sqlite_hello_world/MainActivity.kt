package br.edu.utfpr.sqlite_hello_world

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.utfpr.sqlite_hello_world.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var database: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButtonListeners()

        database = SQLiteDatabase.openOrCreateDatabase(
            this.getDatabasePath("dbfile.sqlite"),
            null
        )

        database.execSQL("CREATE TABLE IF NOT EXISTS registers (_id INTEGER PRIMARY KEY AUTOINCREMENT, display_name TEXT, phone TEXT)")
    }

    private fun setButtonListeners() {
        binding.buttonSave.setOnClickListener {
            onClickSave()
        }

        binding.buttonEdit.setOnClickListener {
            onClickEdit()
        }

        binding.buttonDelete.setOnClickListener {
            onClickRemove()
        }

        binding.buttonSearch.setOnClickListener {
            onClickSearch()
        }

        binding.buttonList.setOnClickListener {
            onClickList()
        }
    }

    private fun onClickSave() {
    }

    private fun onClickEdit() {
    }

    private fun onClickRemove() {
    }

    private fun onClickSearch() {
    }

    private fun onClickList() {
    }
}