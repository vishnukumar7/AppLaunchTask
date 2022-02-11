package com.app.applaunchtask.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.app.applaunchtask.MyApplication
import com.app.applaunchtask.R
import com.app.applaunchtask.UserViewModel
import com.app.applaunchtask.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
private val TAG="HomeActivity"
    lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_home)
        switch(HomeFragment())
        binding.add.setOnClickListener { switch(UserFormFragment()) }
    }

    val userViewModel: UserViewModel by viewModels<UserViewModel> {
        UserViewModel.UserViewModelFactory((application as MyApplication).appRepository)
    }

    override fun onBackPressed() {
       if(supportFragmentManager.backStackEntryCount==1){
            finishAffinity()
       }else
           super.onBackPressed()

    }

    fun switch(fragment: Fragment){
        setFragment(fragment)
        val backStateName=fragment.javaClass.name

       val transaction=supportFragmentManager.beginTransaction()
        if(!supportFragmentManager.popBackStackImmediate(backStateName,0)){
            transaction.replace(R.id.container,fragment)
            transaction.addToBackStack(backStateName)
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            transaction.commit()
        }
    }

   fun setFragment(fragment: Fragment){
        when(fragment){
            is HomeFragment -> {
                binding.add.visibility=View.VISIBLE
            }

            is UserFormFragment -> {
                binding.add.visibility=View.GONE
            }
        }
    }




}