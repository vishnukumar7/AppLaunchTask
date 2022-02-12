package com.app.applaunchtask.ui

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.app.applaunchtask.R
import com.app.applaunchtask.databinding.FragmentLoginScreenBinding

class LoginScreenFragment : Fragment() {

    var emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

    lateinit var binding: FragmentLoginScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=DataBindingUtil.inflate(inflater, R.layout.fragment_login_screen,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.login.setOnClickListener {
            val userName=binding.userName.text.toString()
            val password=binding.password.text.toString()
            when{
                userName.isEmpty() -> {
                    Toast.makeText(activity, getString(R.string.user_name_empty), Toast.LENGTH_SHORT).show()
                }
                !userName.matches(emailPattern.toRegex()) -> {
                    Toast.makeText(activity, getString(R.string.valid_user_name), Toast.LENGTH_SHORT).show()
                }

                password.isEmpty() -> {
                    Toast.makeText(activity, getString(R.string.password_not_empty), Toast.LENGTH_SHORT).show()
                }

                else -> {
                    if(userName == "testapp@google.com" && password == "Test@123456"){
                        saved(userName,password)
                        (requireActivity() as HomeActivity).switch(HomeFragment())
                    }else{
                        Toast.makeText(activity, getString(R.string.invalid_credential), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun saved(userName: String,password:String){
        val sharedPref=requireActivity().getSharedPreferences("user_pref", MODE_PRIVATE)
        val editor=sharedPref.edit()
        editor.putString("user_name",userName)
        editor.putString("password",password)
        editor.putBoolean("logged",true)
        editor.apply()
    }
}