package com.example.demonotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.demonotes.databinding.FragmentLoginBinding
import com.example.demonotes.model.UserRequest
import com.example.demonotes.utils.NetworkResult
import com.example.demonotes.utils.TokenManager
import com.example.demonotes.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val authViewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
        if (tokenManager.getToken() != null){
            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            val inputValidation = validateUserInput()
            if (inputValidation.first){
                authViewModel.loginUser(getUserRequest())
            }else{
                binding.tvError.text = inputValidation.second
            }

        }
        binding.btnRegister.setOnClickListener {
            findNavController().popBackStack()
        }
        observer()
    }

    fun observer() {
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.loader.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    it.data?.let { it1 -> tokenManager.saveToken(it1.token) }
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                }
                is NetworkResult.Error -> {
                    binding.tvError.text = it.message
                }
                is NetworkResult.Loading -> {
                    binding.loader.isVisible = true
                }
            }
        })
    }

    private fun getUserRequest(): UserRequest {
        val userName = binding.etUserName.text.toString()
        val password = binding.etPassword.text.toString()
        return UserRequest(email = userName, password = password, username = "" )
    }

    private fun validateUserInput(): Pair<Boolean, String> {
        getUserRequest()
        return authViewModel.validateCredentials(
            username = getUserRequest().username,
            email = getUserRequest().email,
            password = getUserRequest().password, true
        )
    }

}