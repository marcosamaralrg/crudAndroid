package com.example.myappsql

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.DataInput

class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: MyDatabaseHelper
    private lateinit var resultTextView: TextView
    private lateinit var nameInput: EditText
    private lateinit var emailInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbHelper = MyDatabaseHelper(this)

        nameInput = findViewById(R.id.editName)
        emailInput = findViewById(R.id.editEmail)
        val addButton = findViewById<Button>(R.id.btnAdd)
        val showButton = findViewById<Button>(R.id.btnShow)
        val clearButton = findViewById<Button>(R.id.btnClear)
        resultTextView = findViewById(R.id.txtResults)
        val idInput = findViewById<EditText>(R.id.editId)
        val updateButton = findViewById<Button>(R.id.btnUpdate)
        val deleteButton = findViewById<Button>(R.id.btnDelete)
        val btnSegundaTela = findViewById<Button>(R.id.btn_segundaTela)

        addButton.setOnClickListener(){
            val name = nameInput.text.toString()
            val email = emailInput.text.toString()
            dbHelper.insertUser(name,email)
            Toast.makeText(this, "Usuário inserido", Toast.LENGTH_SHORT).show()
            nameInput.text.clear()
            emailInput.text.clear()
        }

        showButton.setOnClickListener(){
            val users = dbHelper.getAllUsers()
            val result = users.joinToString("\n") {"${it.id}: ${it.name} - ${it.email}"}
            resultTextView.text = result
        }

        clearButton.setOnClickListener(){
            dbHelper.deleteAllusers()
            Toast.makeText(this, "Todos os usuários foram apagados", Toast.LENGTH_SHORT).show()
        }

        updateButton.setOnClickListener {
            val idText = idInput.text.toString()
            val name = nameInput.text.toString()
            val email = emailInput.text.toString()
            if (idText.isNotEmpty()) {
                val id = idText.toInt()
                dbHelper.updateUser(User(id, name, email))
                Toast.makeText(this, "Usuário atualizado", Toast.LENGTH_SHORT).show()
                idInput.text.clear()
                nameInput.text.clear()
                emailInput.text.clear()
            } else {
                Toast.makeText(this, "Informe o ID para atualizar", Toast.LENGTH_SHORT).show()
            }
        }

        deleteButton.setOnClickListener {
            val idText = idInput.text.toString()
            if (idText.isNotEmpty()) {
                val id = idText.toInt()
                dbHelper.deleteUserById(id)
                Toast.makeText(this, "Usuário apagado", Toast.LENGTH_SHORT).show()
                idInput.text.clear()
            } else {
                Toast.makeText(this, "Informe o ID para apagar", Toast.LENGTH_SHORT).show()
            }
        }

        btnSegundaTela.setOnClickListener(){
            val segundaTela = Intent(this, SegundaTela::class.java)
            startActivity(segundaTela)
        }
    }
}