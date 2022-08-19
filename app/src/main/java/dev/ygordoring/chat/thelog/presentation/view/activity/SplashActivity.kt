package dev.ygordoring.chat.thelog.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import dev.ygordoring.chat.thelog.databinding.ActivitySplashBinding
import dev.ygordoring.chat.thelog.presentation.viewmodel.SplashViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val viewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.prepareObserver()

        viewModel.checkAuthentication()
    }

    private fun prepareObserver() {
        viewModel.isAuthenticated.observe(this, { isAuthenticated ->
            if (isAuthenticated) {
                this.goToHome()
            } else {
                this.goToLogin()
            }
        })

        viewModel.error.observe(this, {
            this.goToLogin()
        })
    }

    private fun goToLogin() {
        startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
        finish()
    }

    private fun goToHome() {
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        finish()
    }
}