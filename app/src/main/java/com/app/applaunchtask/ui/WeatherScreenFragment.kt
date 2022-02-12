package com.app.applaunchtask.ui

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.applaunchtask.R
import com.app.applaunchtask.adapter.HourAdapter
import com.app.applaunchtask.databinding.FragmentWeatherScreenBinding

class WeatherScreenFragment : Fragment() {
private val TAG="WeatherScreenFragment"
    lateinit var binding: FragmentWeatherScreenBinding
    lateinit var progressDialog: ProgressDialog
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=DataBindingUtil.inflate(inflater, R.layout.fragment_weather_screen,container,false)
        return binding.root
    }

    fun convertFahToCel(tempF: Double): String = ((tempF - 32) * 5 / 9).toString()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDialog = ProgressDialog(context, R.style.AppCompatAlertDialogStyle)
        progressDialog.setMessage("Please Wait..")
        progressDialog.setCancelable(false)
        progressDialog.show()
        binding.recyclerViewHour.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewHour.setHasFixedSize(false)

        (requireActivity() as HomeActivity).userViewModel.getWeatherData()
        (requireActivity() as HomeActivity).userViewModel.weatherResponse.observe(viewLifecycleOwner) { response ->
            response?.let {
                progressDialog.dismiss()
                binding.temperature.text =
                    "%.1f ${Html.fromHtml("&#xb0;")}C".format(convertFahToCel(response.current.temp).toDouble())
                binding.weatherType.text = response.current.weather[0].main
                binding.humidity.text = response.current.humidity.toString()
                binding.windSpeed.text = response.current.windSpeed.toString()
                val adapter = HourAdapter(response.hourly)
                binding.recyclerViewHour.adapter = adapter
            }
        }

        binding.back.setOnClickListener { (requireActivity() as HomeActivity).onBackPressed() }

        binding.logout.setOnClickListener {

            val sharedPref =
                requireActivity().getSharedPreferences("user_pref", AppCompatActivity.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.clear()
            editor.apply()

            (requireActivity() as HomeActivity).userViewModel.deleteAll()
            (requireActivity() as HomeActivity).switch(LoginScreenFragment())
        }
    }




}