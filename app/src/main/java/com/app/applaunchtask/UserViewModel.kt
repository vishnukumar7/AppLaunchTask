package com.app.applaunchtask

import android.util.Log
import androidx.lifecycle.*
import com.app.applaunchtask.model.User
import com.app.applaunchtask.model.WeatherResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.IllegalArgumentException

class UserViewModel(private val appRepository: AppRepository) : ViewModel() {
private val TAG="UserViewModel"
    val userList: LiveData<MutableList<User>> = appRepository.userList.asLiveData()
val weatherResponse: MutableLiveData<WeatherResponse> = MutableLiveData()
    fun insert(user: User)= viewModelScope.launch {
        appRepository.insert(user)
    }

    fun delete(user: User)= viewModelScope.launch {
        appRepository.delete(user)
    }

    fun getWeatherData(){
        CoroutineScope(Dispatchers.IO).launch {
            val response=appRepository.getCurrentWeather()
            withContext(Dispatchers.Main){
                if(response.isSuccessful){
                    weatherResponse.value=response.body()
                }else{
                    Log.d(TAG, "getWeatherData: error "+response.errorBody().toString())
                }
            }
        }
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