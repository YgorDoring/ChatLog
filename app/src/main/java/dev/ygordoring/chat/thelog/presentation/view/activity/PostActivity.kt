package dev.ygordoring.chat.thelog.presentation.view.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import dev.ygordoring.chat.thelog.R
import dev.ygordoring.chat.thelog.business.vo.PostVO
import dev.ygordoring.chat.thelog.databinding.ActivityPostBinding
import dev.ygordoring.chat.thelog.presentation.viewmodel.PostViewModel
import dev.ygordoring.chat.thelog.utils.Util
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class PostActivity : BaseActivity() {
    companion object {
        const val EXTRA_POST_OBJ = "EXTRA_POST_OBJ"
    }

    private lateinit var binding: ActivityPostBinding
    private val viewModel: PostViewModel by viewModel()

    private var post: PostVO? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(EXTRA_POST_OBJ)) {
            post = intent.getSerializableExtra(EXTRA_POST_OBJ) as PostVO
        }

        this.prepareUi()
        this.prepareListener()
        this.prepareObserver()
    }

    private fun prepareUi() {
        try {
            if (post != null) {
                supportActionBar?.title = getString(R.string.post_update_title)
            } else {
                supportActionBar?.title = getString(R.string.post_new_title)
            }
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            binding.editTextPost.setText(post?.content)

            this.countChar()

        } catch (e: Exception) {
            Log.e(javaClass.simpleName, e.message, e)
        }
    }

    private fun prepareListener() {
        binding.buttonCreateAccount.setOnClickListener {
            this.checkValidForm()
        }

        binding.editTextPost.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                countChar()
            }
        })
    }

    private fun prepareObserver() {
        viewModel.isValidForm.observe(this, { valid ->
            if (valid) {
                viewModel.save(post, binding.editTextPost.text.toString())
            }
        })

        viewModel.isSaveSuccess.observe(this, { success ->
            if (success) {
                Util.showToast(this, getString(R.string.post_message_success))

                super.onBackPressed()
            }
        })
        viewModel.errorContent.observe(this, { isError ->
            binding.textInputLayoutPost.error =
                if (isError) getString(R.string.post_error_content) else null
        })
        viewModel.error.observe(this, { errorMessage ->
            Util.showSnackbar(this, binding.root, errorMessage)
        })
    }

    private fun checkValidForm() {
        Util.hideKeyboard(this, currentFocus)

        viewModel.checkValidFormData(binding.editTextPost.text.toString())
    }

    private fun countChar() {
        try {
            val count = binding.editTextPost.text.toString().length
            if (count <= 280) {
                binding.textViewCount.text = String.format(Locale.getDefault(), "%d/280", count)
            }
        } catch (e: Exception) {
            Log.e(javaClass.simpleName, e.message, e)
        }
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