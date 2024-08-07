package br.edu.utfpr.sqlite_hello_world

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.utfpr.sqlite_hello_world.database.DatabaseHandler
import br.edu.utfpr.sqlite_hello_world.databinding.ActivityMainBinding
import br.edu.utfpr.sqlite_hello_world.entity.Register

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var databaseHandler: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButtonListeners()

        databaseHandler = DatabaseHandler(this)
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
        databaseHandler.insert(Register(
            name =  binding.editTextName.text.toString(),
            phone = binding.editTextPhone.text.toString(),
            _id = 0
        ))

        Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
    }

    private fun onClickEdit() {
        databaseHandler.update(Register(
            name =  binding.editTextName.text.toString(),
            phone = binding.editTextPhone.text.toString(),
            _id = binding.editTextId.text.toString().toInt()
        ))

        Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
    }

    private fun onClickRemove() {
        databaseHandler.delete(binding.editTextId.text.toString().toInt())

        Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
    }

    private fun onClickSearch() {
        val register = databaseHandler.find(binding.editTextId.text.toString().toInt())

        if (register != null) {
            binding.editTextName.setText(register.name)
            binding.editTextPhone.setText(register.phone)
        } else {
            Toast.makeText(this, "Register not found", Toast.LENGTH_LONG).show()
        }
    }

    private fun onClickList() {
        val registers = databaseHandler.list()
        val registersString = StringBuilder()

        registers.forEach {
            registersString.append(it._id)
            registersString.append("-")
            registersString.append(it.name)
            registersString.append("-")
            registersString.append(it.phone)
            registersString.append("\n")
        }

        Toast.makeText(this, registersString.toString(), Toast.LENGTH_LONG).show()
    }
}