package br.edu.utfpr.sqlite_hello_world

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Toast
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
        val newRegister = ContentValues()
        newRegister.put("display_name", binding.editTextName.text.toString())
        newRegister.put("phone", binding.editTextPhone.text.toString())

        database.insert("registers", null, newRegister)

        Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
    }

    private fun onClickEdit() {
        val register = ContentValues()
        register.put("display_name", binding.editTextName.text.toString())
        register.put("phone", binding.editTextPhone.text.toString())

        database.update(
            "registers",
            register,
            "_id=${binding.editTextId.text}",
            null
        )

        Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
    }

    private fun onClickRemove() {
        database.delete("registers", "_id=${binding.editTextId.text}", null)

        Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
    }

    private fun onClickSearch() {
        val register = database.query(
            "registers",
            null,
            "_id=${binding.editTextId.text}",
            null,
            null,
            null,
            null
        )

        if (register.moveToNext()) {
            binding.editTextName.setText(register.getString(Companion.NAME_COLUMN_INDEX))
            binding.editTextPhone.setText(register.getString(Companion.PHONE_COLUMN_INDEX))
        } else {
            Toast.makeText(this, "Register not found", Toast.LENGTH_LONG).show()
        }
    }

    private fun onClickList() {
        val registers = database.query(
            "registers",
            null,
            null,
            null,
            null,
            null,
            null
        )
        val registersString = StringBuilder()

        while(registers.moveToNext()) {
            registersString.append(registers.getInt(Companion.ID_COLUMN_INDEX))
            registersString.append("-")
            registersString.append(registers.getInt(Companion.NAME_COLUMN_INDEX))
            registersString.append("-")
            registersString.append(registers.getInt(Companion.PHONE_COLUMN_INDEX))
            registersString.append("\n")
        }

        Toast.makeText(this, registersString.toString(), Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val ID_COLUMN_INDEX = 0
        private const val NAME_COLUMN_INDEX = 1
        private const val PHONE_COLUMN_INDEX = 2
    }
}