package br.edu.utfpr.sqlite_hello_world.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.edu.utfpr.sqlite_hello_world.entity.Register

class DatabaseHandler (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_NAME (_id INTEGER PRIMARY KEY AUTOINCREMENT, display_name TEXT, phone TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "dbfile.sqlite"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "registers"
        public const val ID_COLUMN_INDEX = 0
        public const val NAME_COLUMN_INDEX = 1
        public const val PHONE_COLUMN_INDEX = 2
    }

    fun insert(register: Register) {
        val database = this.writableDatabase
        val newRegister = ContentValues()

        newRegister.put("display_name",register.name)
        newRegister.put("phone", register.phone)

        database.insert(TABLE_NAME, null, newRegister)
    }

    fun update(register: Register) {
        val database = this.writableDatabase
        val updatedRegister = ContentValues()

        updatedRegister.put("display_name",register.name)
        updatedRegister.put("phone", register.phone)

        database.update(
            TABLE_NAME,
            updatedRegister,
            "_id=${register._id}",
            null
        )
    }

    fun delete(id: Int) {
        val database = this.writableDatabase

        database.delete(TABLE_NAME, "_id=${id}", null)
    }

    fun find(id: Int): Register? {
        val database = this.writableDatabase
        val register = database.query(
            "registers",
            null,
            "_id=${id}",
            null,
            null,
            null,
            null
        )

        return if (register.moveToNext()) {
            Register(
                _id = id,
                name = register.getString(NAME_COLUMN_INDEX),
                phone = register.getString(PHONE_COLUMN_INDEX)
            )
        } else {
            null
        }
    }

    fun list(): MutableList<Register> {
        val database = this.writableDatabase
        val rawRegisters = database.query(
            "registers",
            null,
            null,
            null,
            null,
            null,
            null
        )
        val registers = mutableListOf<Register>()

        while(rawRegisters.moveToNext()) {
            registers.add(Register(
                _id = rawRegisters.getInt(ID_COLUMN_INDEX),
                name =rawRegisters.getString(NAME_COLUMN_INDEX),
                phone = rawRegisters.getString(PHONE_COLUMN_INDEX)
            ))
        }

        return registers
    }

    fun cursorList(): Cursor {
        val database = this.writableDatabase
        val registers = database.query(
            "registers",
            null,
            null,
            null,
            null,
            null,
            null
        )

        return registers
    }
}