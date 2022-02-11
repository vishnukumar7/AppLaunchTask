package com.app.applaunchtask.ui

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.app.applaunchtask.R
import com.app.applaunchtask.databinding.FragmentWeatherScreenBinding
import com.app.applaunchtask.databinding.WeatherListBinding
import com.app.applaunchtask.model.HourlyItem
import java.text.SimpleDateFormat
import java.util.*

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
        progressDialog=ProgressDialog(context,R.style.AppCompatAlertDialogStyle)
        progressDialog.setMessage("Please Wait..")
        progressDialog.setCancelable(false)
        progressDialog.show()
        (requireActivity() as HomeActivity).userViewModel.getWeatherData()
        (requireActivity() as HomeActivity).userViewModel.weatherResponse.observe(viewLifecycleOwner) { response ->
            response?.let {
                progressDialog.dismiss()
              binding.temperature.text=String.format("%.1f C",convertFahToCel(response.current.temp))
                binding.weatherType.text=response.current.weather[0].main
                binding.humidity.text=response.current.humidity.toString()
                binding.windSpeed.text =response.current.windSpeed.toString()
            }
        }
    }


    class HourAdapter(private var hourList: List<HourlyItem>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val itemBinding = DataBindingUtil.inflate<WeatherListBinding>(
                LayoutInflater.from(parent.context),
                R.layout.weather_list,
                parent,
                false
            )
            return HourViewHolder(itemBinding)
        }


        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (holder is HourViewHolder) {
                holder.itemBinding.temp.text = convertFahToCel((hourList[position].temp) as Double)
                holder.itemBinding.time.text =
                    getDateCurrentTimeZone(hourList[position].dt.toLong())
            }
        }


        fun getDateCurrentTimeZone(timestamp: Long): String? {
            try {
                val calendar: Calendar = Calendar.getInstance()
                val tz: TimeZone = TimeZone.getDefault()
                calendar.timeInMillis = timestamp * 1000
                calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.timeInMillis))
                val sdf = SimpleDateFormat("HH:mm", Locale.ENGLISH)
                val currenTimeZone: Date = calendar.time as Date
                return sdf.format(currenTimeZone)
            } catch (e: Exception) {
            }
            return ""
        }


        fun convertFahToCel(tempF: Double?): String = tempF?.let { (tempF - 32) * 5 / 9 }.toString()



        override fun getItemCount(): Int {
            return hourList.size
        }

        class HourViewHolder(view: WeatherListBinding) : RecyclerView.ViewHolder(view.root) {
            val itemBinding = view
        }

    }
}