package br.edu.utfpr.sqlite_hello_world.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import br.edu.utfpr.sqlite_hello_world.MainActivity
import br.edu.utfpr.sqlite_hello_world.R
import br.edu.utfpr.sqlite_hello_world.database.DatabaseHandler
import br.edu.utfpr.sqlite_hello_world.entity.Register

class CustomAdapter(val context : Context, val cursor: Cursor) : BaseAdapter() {
    override fun getCount(): Int {
        return cursor.count
    }

    override fun getItem(position: Int): Register {
        cursor.moveToPosition(position)
        val register = Register(
            cursor.getInt(DatabaseHandler.ID_COLUMN_INDEX),
            cursor.getString(DatabaseHandler.NAME_COLUMN_INDEX),
            cursor.getString(DatabaseHandler.PHONE_COLUMN_INDEX),
        )

        return register
    }

    override fun getItemId(position: Int): Long {
        cursor.moveToPosition(position)
        return cursor.getLong(0)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.element_list, null)

        val textViewNameElementList = view.findViewById<TextView>(R.id.textViewNameElementList)
        val textViewPhoneElementList = view.findViewById<TextView>(R.id.textViewPhoneElementList)
        val editButtonElementList = view.findViewById<ImageButton>(R.id.editButtonElementList)

        cursor.moveToPosition(position)

        textViewNameElementList.text = cursor.getString(DatabaseHandler.NAME_COLUMN_INDEX)
        textViewPhoneElementList.text = cursor.getString(DatabaseHandler.PHONE_COLUMN_INDEX)

        editButtonElementList.setOnClickListener {
            cursor.moveToPosition(position)

            val intent = Intent(context, MainActivity::class.java)

            intent.putExtra("id",  cursor.getInt(DatabaseHandler.ID_COLUMN_INDEX))
            intent.putExtra("name", cursor.getString(DatabaseHandler.NAME_COLUMN_INDEX))
            intent.putExtra("phone", cursor.getString(DatabaseHandler.PHONE_COLUMN_INDEX))

            context.startActivity(intent)
        }

        return view
    }
}