package com.maxim.simpleschedule.main.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.maxim.simpleschedule.R
import com.maxim.simpleschedule.core.presentation.ProvideViewModel
import com.maxim.simpleschedule.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ProvideViewModel {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun <T : ViewModel> viewModel(clasz: Class<out ViewModel>): T {
        return (application as ProvideViewModel).viewModel(clasz)
    }
}