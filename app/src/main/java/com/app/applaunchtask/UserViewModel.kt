package com.app.applaunchtask

import androidx.lifecycle.*
import com.app.applaunchtask.model.User
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class UserViewModel(private val appRepository: AppRepository) : ViewModel() {

    val userList: LiveData<MutableList<User>> = appRepository.userList.asLiveData()

    fun insert(user: User)= viewModelScope.launch {
        appRepository.insert(user)
    }

    fun delete(user: User)= viewModelScope.launch {
        appRepository.delete(user)
    }

    class UserViewModelFactory(private val appRepository: AppRepository): ViewModelProvider.Factory{

        /**
         * Creates a new instance of the given `Class`.
         *
         * @param modelClass a `Class` whose instance is requested
         * @return a newly created ViewModel
         */
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(UserViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return UserViewModel(appRepository) as T
            }
            throw IllegalArgumentException("Unknown VieModel Class")
        }
    }
}