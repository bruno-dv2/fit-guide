package com.example.fitguide

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fitguide.databinding.ActivityHomeProfessorBinding

class HomeProfessor : AppCompatActivity() {

    private lateinit var binding: ActivityHomeProfessorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeProfessorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up click listener for "Deslogar" button
        binding.textViewDeslogar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Close the HomeProfessor activity
        }

        // Set up click listeners for the other buttons
       binding.buttonGerenciarExercicios.setOnClickListener {
            val intent = Intent(this, GerenciarExercicios::class.java)
           startActivity(intent)
       }
//
//        binding.buttonGerenciarUsuarios.setOnClickListener {
//            val intent = Intent(this, GerenciarUsuariosActivity::class.java)
//            startActivity(intent)
//        }
//
//        binding.buttonGerenciarFichas.setOnClickListener {
//            val intent = Intent(this, GerenciarFichasActivity::class.java)
//            startActivity(intent)
//        }
//
        binding.buttonGerenciarEquipamentos.setOnClickListener {
            val intent = Intent(this, GerenciarEquipamentos::class.java)
            startActivity(intent)
<<<<<<< HEAD
       }

       binding.buttonMeuPerfil.setOnClickListener {
            val intent = Intent(this, MeuPerfil::class.java)
            startActivity(intent)
        }
=======
        }

//        binding.buttonMeuPerfil.setOnClickListener {
//            val intent = Intent(this, MeuPerfilActivity::class.java)
//            startActivity(intent)
//        }
>>>>>>> 47db03ea02c08ce83237e76ba8b59508e6942a8e
    }
}