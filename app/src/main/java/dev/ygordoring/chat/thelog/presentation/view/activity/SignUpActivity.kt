package dev.ygordoring.chat.thelog.presentation.view.activity

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import dev.ygordoring.chat.thelog.R
import dev.ygordoring.chat.thelog.business.vo.UserVO
import dev.ygordoring.chat.thelog.databinding.ActivitySignUpBinding
import dev.ygordoring.chat.thelog.presentation.viewmodel.SignUpViewModel
import dev.ygordoring.chat.thelog.utils.Util
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpActivity : BaseActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: SignUpViewModel by viewModel()

    private var user: UserVO? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.prepareUi()
        this.prepareListener()
        this.prepareObserver()
    }

    private fun prepareUi() {
        try {
            supportActionBar?.title = getString(R.string.sign_up_title)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

        } catch (e: Exception) {
            Log.e(javaClass.simpleName, e.message, e)
        }
    }

    private fun prepareListener() {
        binding.buttonCreateAccount.setOnClickListener {
            this.checkValidForm()
        }
    }

    private fun prepareObserver() {
        viewModel.isValidForm.observe(this, { valid ->
            if (valid) {
                viewModel.signUp(user!!)
            }
        })
        viewModel.isSignUpSuccess.observe(this, { success ->
            if (success) {
                Util.showToast(this, getString(R.string.sign_up_message_success))

                super.onBackPressed()
            }
        })
        viewModel.errorName.observe(this, { isError ->
            binding.textInputLayoutName.error =
                if (isError) getString(R.string.sign_up_error_name) else null
        })
        viewModel.errorEmail.observe(this, { isError ->
            binding.textInputLayoutEmail.error =
                if (isError) getString(R.string.sign_up_error_email) else null
        })
        viewModel.errorPassword.observe(this, { isError ->
            binding.textInputLayoutPassword.error =
                if (isError) getString(R.string.sign_up_error_password) else null
        })
        viewModel.error.observe(this, { errorMessage ->
            Util.showSnackbar(this, binding.root, errorMessage)
        })
    }

    private fun checkValidForm() {
        Util.hideKeyboard(this, currentFocus)

        user = UserVO(
            binding.editTextName.text.toString(),
            binding.editTextEmail.text.toString(),
            binding.editTextPassword.text.toString()
        )

        viewModel.checkValidFormData(user!!)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}