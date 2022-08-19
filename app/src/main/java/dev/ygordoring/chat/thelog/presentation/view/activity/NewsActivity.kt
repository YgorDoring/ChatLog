package dev.ygordoring.chat.thelog.presentation.view.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import dev.ygordoring.chat.thelog.R
import dev.ygordoring.chat.thelog.business.vo.NewsVO
import dev.ygordoring.chat.thelog.databinding.ActivityNewsBinding
import dev.ygordoring.chat.thelog.presentation.view.adapter.NewsAdapter
import dev.ygordoring.chat.thelog.presentation.viewmodel.NewsViewModel
import dev.ygordoring.chat.thelog.utils.Util
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsActivity : BaseActivity() {
    private lateinit var binding: ActivityNewsBinding
    private val viewModel: NewsViewModel by viewModel()
    lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mAdapter: NewsAdapter

    var newsList = mutableListOf<NewsVO>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.prepareUi()
        this.prepareObserver()

        viewModel.fetchNewsList()
    }

    private fun prepareUi() {
        supportActionBar?.title = getString(R.string.news_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mLayoutManager = LinearLayoutManager(this@NewsActivity)
        mAdapter = NewsAdapter(newsList)

        binding.recyclerViewNews.layoutManager = mLayoutManager
        binding.recyclerViewNews.adapter = mAdapter
        binding.recyclerViewNews.setHasFixedSize(true)
    }

    private fun prepareObserver() {
        viewModel.newsList.observe(this, { response ->
            newsList.addAll(response)
            mAdapter.notifyDataSetChanged()

            binding.relativeLayoutLoading.visibility = View.GONE
        })

        viewModel.error.observe(this, { message ->
            binding.relativeLayoutLoading.visibility = View.GONE

            Util.showSnackbar(this, binding.root, message)
        })
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