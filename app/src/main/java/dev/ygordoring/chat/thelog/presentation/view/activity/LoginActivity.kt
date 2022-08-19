package dev.ygordoring.chat.thelog.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import dev.ygordoring.chat.thelog.R
import dev.ygordoring.chat.thelog.databinding.ActivityLoginBinding
import dev.ygordoring.chat.thelog.presentation.viewmodel.LoginViewModel
import dev.ygordoring.chat.thelog.utils.Util
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModel()

    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.prepareListener()
        this.prepareObserver()
    }

    private fun prepareListener() {
        binding.buttonLogin.setOnClickListener {
            this.checkValidForm()
        }
        binding.textViewBtnSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun prepareObserver() {
        viewModel.isValidForm.observe(this, { valid ->
            if (valid) {
                viewModel.login(email, password)
            }
        })
        viewModel.loginResult.observe(this, { success ->
            if (success) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()

            } else {
                Util.showSnackbar(this, binding.root, getString(R.string.login_error_message))
            }
        })
        viewModel.errorEmail.observe(this, { isError ->
            binding.textInputLayoutEmail.error =
                if (isError) getString(R.string.login_error_email) else null
        })
        viewModel.errorPassword.observe(this, { isError ->
            binding.textInputLayoutPassword.error =
                if (isError) getString(R.string.login_error_password) else null
        })
        viewModel.errorPassword.observe(this, { isError ->
            binding.textInputLayoutPassword.error =
                if (isError) getString(R.string.login_error_password) else null
        })
        viewModel.error.observe(this, { errorMessage ->
            Util.showSnackbar(this, binding.root, errorMessage)
        })
    }

    private fun checkValidForm() {
        Util.hideKeyboard(this, currentFocus)

        email = binding.editTextEmail.text.toString()
        password = binding.editTextPassword.text.toString()

        viewModel.checkValidFormData(email, password)
    }
}