package com.weather.feature.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.weather.core.model.DailyForecast
import com.weather.feature.forecast.databinding.FragmentForecastBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForecastFragment : Fragment() {

    private var _binding: FragmentForecastBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ForecastViewModel by viewModels()

    private lateinit var adapter: ForecastAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForecastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getForecast()

        collectUiState()
        setupRecyclerView()
        setupSwipeToRefresh()
        setupRetry()
    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is ForecastUiState.Success -> populateDailyForecast(it.forecast)
                        ForecastUiState.Loading -> showProgressBar()
                        is ForecastUiState.Error -> showRetry()
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = ForecastAdapter()
        binding.forecastList.adapter = adapter
        val itemDecoration = VerticalSpaceItemDecoration(40)
        binding.forecastList.addItemDecoration(itemDecoration)
    }

    private fun populateDailyForecast(dailyForecast: List<DailyForecast>) {
        binding.forecastList.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
        binding.btnRetry.visibility = View.GONE
        binding.swipeRefreshLayout.isRefreshing = false
        adapter.submitList(dailyForecast)
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnRetry.visibility = View.GONE
    }

    private fun showRetry() {
        binding.btnRetry.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
        binding.forecastList.visibility = View.GONE
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun setupSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getForecast()
        }
    }

    private fun setupRetry() {
        binding.btnRetry.setOnClickListener {
            viewModel.getForecast()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}