package br.edu.utfpr.sqlite_hello_world

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

        if (intent.getIntExtra("id", 0) != 0) {
            binding.editTextId.setText(intent.getIntExtra("id", 0).toString())
            binding.editTextPhone.setText(intent.getStringExtra("phone"))
            binding.editTextName.setText(intent.getStringExtra("name"))
        } else {
            binding.buttonDelete.visibility = View.GONE
            binding.buttonSearch.visibility = View.GONE
        }

        databaseHandler = DatabaseHandler(this)
    }

    private fun setButtonListeners() {
        binding.buttonEdit.setOnClickListener {
            onClickEdit()
        }

        binding.buttonDelete.setOnClickListener {
            onClickRemove()
        }

        binding.buttonSearch.setOnClickListener {
            onClickSearch()
        }
    }

    private fun onClickEdit() {
        if (binding.editTextId.text.isEmpty()) {
            databaseHandler.insert(Register(
                name =  binding.editTextName.text.toString(),
                phone = binding.editTextPhone.text.toString(),
                _id = 0
            ))

            Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
        } else {
            databaseHandler.update(Register(
                name =  binding.editTextName.text.toString(),
                phone = binding.editTextPhone.text.toString(),
                _id = binding.editTextId.text.toString().toInt()
            ))

            Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
        }

        finish()
    }

    private fun onClickRemove() {
        databaseHandler.delete(binding.editTextId.text.toString().toInt())

        Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()

        finish()
    }

    private fun onClickSearch() {
        val builder = AlertDialog.Builder(this)

        val editCodeSearch = EditText(this)

        builder.setTitle("Type the ID")
        builder.setView(editCodeSearch)
        builder.setCancelable(false)
        builder.setNegativeButton("Close", null)
        builder.setPositiveButton("Search", DialogInterface.OnClickListener {
            dialog, i ->
                val register = databaseHandler.find(editCodeSearch.text.toString().toInt())

                if (register != null) {
                    binding.editTextId.setText(editCodeSearch.text.toString())
                    binding.editTextName.setText(register.name)
                    binding.editTextPhone.setText(register.phone)
                } else {
                    Toast.makeText(this, "Register not found", Toast.LENGTH_LONG).show()
                }
        })

        builder.show()


    }

    private fun onClickList() {
//        val registers = databaseHandler.list()
//        val registersString = StringBuilder()
//
//        registers.forEach {
//            registersString.append(it._id)
//            registersString.append("-")
//            registersString.append(it.name)
//            registersString.append("-")
//            registersString.append(it.phone)
//            registersString.append("\n")
//        }
//
//        Toast.makeText(this, registersString.toString(), Toast.LENGTH_LONG).show()
        val intent = Intent(this, ListActivity::class.java)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        println("onStart()")
    }

    override fun onResume() {
        super.onResume()
        println("onResume()")
    }

    override fun onPause() {
        super.onPause()
        println("onPause()")
    }

    override fun onStop() {
        super.onStop()
        println("onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("onDestroy()")
    }

    override fun onRestart() {
        super.onRestart()
        println("onRestart()")
    }
}