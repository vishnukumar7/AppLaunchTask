package com.app.applaunchtask.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.applaunchtask.R
import com.app.applaunchtask.databinding.ActivityLoginScreenBinding

class LoginScreenActivity : AppCompatActivity() {

    var emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

    lateinit var binding: ActivityLoginScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_login_screen)

        binding.login.setOnClickListener {
            val userName=binding.userName.text.toString()
            val password=binding.password.text.toString()
            when{
                userName.isEmpty() -> {
                    Toast.makeText(this@LoginScreenActivity, getString(R.string.user_name_empty), Toast.LENGTH_SHORT).show()
                }
                !userName.matches(emailPattern.toRegex()) -> {
                    Toast.makeText(this@LoginScreenActivity, getString(R.string.valid_user_name), Toast.LENGTH_SHORT).show()
                }

                password.isEmpty() -> {
                    Toast.makeText(this@LoginScreenActivity, getString(R.string.password_not_empty), Toast.LENGTH_SHORT).show()
                }

                else -> {
                    if(userName == "testapp@google.com" && password == "Test@123456"){
                        saved(userName,password)
                        startActivity(Intent(this@LoginScreenActivity, HomeActivity::class.java))
                    }else{
                        Toast.makeText(this@LoginScreenActivity, getString(R.string.invalid_credential), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun saved(userName: String,password:String){
        val sharedPref=getSharedPreferences("user_pref", MODE_PRIVATE)
        val editor=sharedPref.edit()
        editor.putString("user_name",userName)
        editor.putString("password",password)
        editor.putBoolean("logged",true)
        editor.apply()
    }
}