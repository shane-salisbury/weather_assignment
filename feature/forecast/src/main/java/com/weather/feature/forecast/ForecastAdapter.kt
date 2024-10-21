package com.weather.feature.forecast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.weather.core.model.DailyForecast
import com.weather.feature.forecast.databinding.ListItemForecastBinding

class ForecastAdapter :
    ListAdapter<DailyForecast, RecyclerView.ViewHolder>(DailyForecastDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DailyForecastViewHolder(
            ListItemForecastBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val dailyForecast = getItem(position)
        (holder as DailyForecastViewHolder).bind(dailyForecast)
    }

    class DailyForecastViewHolder(
        private val binding: ListItemForecastBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DailyForecast) {
            binding.apply {
                dailyForecast = item
                executePendingBindings()
            }
        }
    }
}

private class DailyForecastDiffCallback : DiffUtil.ItemCallback<DailyForecast>() {

    override fun areItemsTheSame(oldItem: DailyForecast, newItem: DailyForecast): Boolean {
        return oldItem.date == newItem.date
    }

    override fun areContentsTheSame(oldItem: DailyForecast, newItem: DailyForecast): Boolean {
        return oldItem == newItem
    }
}
