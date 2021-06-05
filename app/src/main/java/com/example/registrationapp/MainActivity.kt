package com.example.registrationapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.registrationapp.databinding.ActivityMainBinding
import com.example.registrationapp.viewmodel.RegistrationViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel: RegistrationViewModel by lazy {
        ViewModelProvider(this, RegistrationViewModel.Factory(this)).get(RegistrationViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
            this.lifecycleOwner = this@MainActivity
            this.details = viewModel

        }

        viewModel.isActive.observe(this, Observer {
            data -> this.my_button.isEnabled = data
            if(data) {
                this.my_button.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_500))
            } else{
                this.my_button.setBackgroundColor(ContextCompat.getColor(this, R.color.inactive_grey))
            }
        })

        this.my_button.setOnClickListener {
            Toast.makeText(this, R.string.submit_message, Toast.LENGTH_LONG).show()
            finish()
        }

        this.error_cta.setOnClickListener {
            finish()
        }
    }
}