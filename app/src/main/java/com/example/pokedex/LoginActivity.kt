package com.example.pokedex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.pokedex.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var bind : ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Para Binding con elementos del Layout
        bind = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bind.root)

        // Inicializa objetos:
        auth = Firebase.auth
        setLoginRegister() //sigue en la siguiente sección.
    }

    private fun setLoginRegister(){
        bind.registerbtn.setOnClickListener {
            if (bind.correo.text.isNotEmpty() && bind.password.text.isNotEmpty()){
                // utiliza la clase de FirebaseAuth:
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(

                    bind.correo.text.toString(), //usuario y password
                    bind.password.text.toString()

                ).addOnCompleteListener{
                    if(it.isSuccessful){
                        usuarioCreado() //Viene más adelante la función
                        bind.correo.text.clear() //Limpiar las cajas de texto
                        bind.password.text.clear()
                    }
                }.addOnFailureListener{
                    // en caso de error
                    Toast.makeText(this,it.toString(), Toast.LENGTH_LONG).show()

                }
            }
        }
        bind.loginbtn.setOnClickListener {
            // Valida que correo y password no esten vacíos, incluye:
            if(bind.correo.text.isNotEmpty() && bind.password.text.isNotBlank()){
                //Para ingresar cambia al método de signInWithEmailAndPassword
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    bind.correo.text.toString(),
                    bind.password.text.toString()
                ).addOnCompleteListener{
                    if(it.isSuccessful){
                        Toast.makeText(this,"Bienvenido Maestro Pokemon!", Toast.LENGTH_LONG).show()
                        //Crea un intento y entra a MainActivity.

                        val intento = Intent(this, MainActivity::class.java)
                        startActivity(intento)
                        finish()

                    }else{
                        Toast.makeText(this,"Error en los datos!", Toast.LENGTH_LONG).show()
                    }
                }
            }
            else{
                Toast.makeText(this,"Llena los campos de correo y/o contraseña!", Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun usuarioCreado(){
        val builder = AlertDialog.Builder(this)
        with(builder){
            setTitle("usuario pokedex")
            setMessage("Usuario creado con éxito!")
            setPositiveButton("Ok",null)
            show()
        }
    }

}