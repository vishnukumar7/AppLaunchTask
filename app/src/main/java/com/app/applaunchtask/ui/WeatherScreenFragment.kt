package com.app.applaunchtask.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.app.applaunchtask.ApiInterface
import com.app.applaunchtask.R
import com.app.applaunchtask.RetrofitHelper
import com.app.applaunchtask.databinding.FragmentWeatherScreenBinding
import com.google.gson.Gson
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WeatherScreenFragment : Fragment() {
private val TAG="WeatherScreenFragment"
    lateinit var binding: FragmentWeatherScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=DataBindingUtil.inflate(inflater, R.layout.fragment_weather_screen,container,false)
        return binding.root
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val weatherApi=RetrofitHelper.getInstance().create(ApiInterface::class.java)

        GlobalScope.launch {
                val result=weatherApi.getWeather()
            Log.d(TAG, "onViewCreated: "+Gson().toJson(result.body()))
        }
    }
}