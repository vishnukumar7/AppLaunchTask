package com.app.applaunchtask.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.app.applaunchtask.R
import com.app.applaunchtask.database.DatabaseClient
import com.app.applaunchtask.databinding.FragmentUserFormBinding
import com.app.applaunchtask.model.User
import com.app.applaunchtask.model.UserDao

class UserFormFragment : Fragment() {

    lateinit var binding: FragmentUserFormBinding
    lateinit var userDao: UserDao

    var emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_form, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userDao = DatabaseClient.getInstance(requireContext()).userDoa()
        binding.save.setOnClickListener {
            val firstName = binding.firstName.text.toString()
            val lastName = binding.lastName.text.toString()
            val email = binding.email.text.toString()
            when {
                firstName.isEmpty() -> {
                    Toast.makeText(
                        activity,
                        getString(R.string.first_name_not_empty),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                lastName.isEmpty() -> {
                    Toast.makeText(
                        activity,
                        getString(R.string.last_name_not_empty),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                email.isEmpty() -> {
                    Toast.makeText(
                        activity,
                        getString(R.string.email_not_empty),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                !email.matches(emailPattern.toRegex()) -> {
                    Toast.makeText(activity, getString(R.string.valid_email), Toast.LENGTH_SHORT).show()
                }

                else -> {
                    val user = User(firstName, lastName, email)
                    (requireActivity() as HomeActivity).userViewModel.insert(user)
                    (requireActivity() as HomeActivity).onBackPressed()
                }
            }
        }

        binding.cancel.setOnClickListener {
            //requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            (requireActivity() as HomeActivity).onBackPressed()
        }
    }
}