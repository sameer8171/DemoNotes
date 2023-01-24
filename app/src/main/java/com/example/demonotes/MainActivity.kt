package com.example.demonotes


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.demonotes.databinding.ActivityMainBinding
import com.example.demonotes.model.UserRequest
import com.example.demonotes.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val authViewModel by viewModels<AuthViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.btnRegister.setOnClickListener {
//            authViewModel.registerUser(UserRequest("shyam@gmail.com", "4321", "shyam"))
//            Toast.makeText(this, "Hello World", Toast.LENGTH_LONG).show()
//        }
//        binding.btnLogin.setOnClickListener {
//            authViewModel.loginUser(UserRequest("shyam@gmail.com", "4321", "shyam"))
//            Toast.makeText(this, "Hello World", Toast.LENGTH_LONG).show()
//        }
    }
}