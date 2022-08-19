package dev.ygordoring.chat.thelog.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.ygordoring.chat.thelog.R
import dev.ygordoring.chat.thelog.business.vo.PostVO
import dev.ygordoring.chat.thelog.databinding.ActivityMainBinding
import dev.ygordoring.chat.thelog.presentation.view.adapter.PostAdapter
import dev.ygordoring.chat.thelog.presentation.viewmodel.MainViewModel
import dev.ygordoring.chat.thelog.utils.OnItemClickListener
import dev.ygordoring.chat.thelog.utils.Util
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()
    lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mAdapter: PostAdapter

    var postList = mutableListOf<PostVO>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getUser()

        this.prepareUi()
        this.prepareListener()
        this.prepareObserver()
    }

    override fun onResume() {
        super.onResume()

        binding.relativeLayoutLoading.visibility = View.VISIBLE
        viewModel.fetchPostList()
    }

    private fun prepareUi() {
        supportActionBar?.title = getString(R.string.home_contributions_title)

        mLayoutManager = LinearLayoutManager(this@MainActivity)
        mAdapter = PostAdapter(viewModel.user, postList)

        binding.recyclerViewPosts.layoutManager = mLayoutManager
        binding.recyclerViewPosts.adapter = mAdapter
        binding.recyclerViewPosts.setHasFixedSize(true)

        binding.recyclerViewPosts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && binding.fabAddPost.isExtended) {
                    binding.fabAddPost.shrink()
                } else if (dy < 0 && !binding.fabAddPost.isExtended) {
                    binding.fabAddPost.extend()
                }
            }
        })
    }

    private fun prepareListener() {
        binding.fabAddPost.setOnClickListener {
            startActivity(Intent(this, PostActivity::class.java))
        }

        binding.recyclerViewPosts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && binding.fabAddPost.isExtended) {
                    binding.fabAddPost.shrink()
                } else if (dy < 0 && !binding.fabAddPost.isExtended) {
                    binding.fabAddPost.extend()
                }
            }
        })

        mAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemEditClick(position: Int) {
                val intent = Intent(this@MainActivity, PostActivity::class.java)
                intent.putExtra(PostActivity.EXTRA_POST_OBJ, postList[position])
                startActivity(intent)
            }

            override fun onItemDeleteClick(position: Int) {
                confirmDelete(postList[position])
            }
        })
    }

    private fun confirmDelete(post: PostVO) {
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setMessage(getString(R.string.post_confirm_delete))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.btn_delete)) { _, _ ->
                viewModel.delete(post.id)
            }
            .setNegativeButton(R.string.btn_cancel) { dialog, _ ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

    private fun prepareObserver() {
        viewModel.isLoading.observe(this, { isLoading ->
            binding.relativeLayoutLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        viewModel.postList.observe(this, { response ->
            postList = response
            mAdapter.updateList(postList)
        })

        viewModel.successDelete.observe(this, { isLogout ->
            viewModel.fetchPostList()
        })

        viewModel.isLogout.observe(this, { isLogout ->
            if (isLogout) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        })

        viewModel.error.observe(this, { message ->
            Util.showSnackbar(this, binding.root, message)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.actions_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                viewModel.logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}