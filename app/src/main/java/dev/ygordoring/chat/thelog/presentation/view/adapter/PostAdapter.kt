package dev.ygordoring.chat.thelog.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.ygordoring.chat.thelog.business.vo.PostVO
import dev.ygordoring.chat.thelog.business.vo.UserVO
import dev.ygordoring.chat.thelog.databinding.RecyclerItemPostBinding
import dev.ygordoring.chat.thelog.utils.OnItemClickListener
import dev.ygordoring.chat.thelog.utils.Util

class PostAdapter(
    private val user: UserVO?,
    private var items: MutableList<PostVO>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PostViewHolder(
            RecyclerItemPostBinding.inflate(LayoutInflater.from(parent.context)),
            mItemClickListener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        (holder as PostViewHolder).bind(user, item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateList(newItems: MutableList<PostVO>) {
        items = newItems
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener) {
        this.mItemClickListener = mItemClickListener
    }
}

class PostViewHolder(
    private val binding: RecyclerItemPostBinding,
    private val listener: OnItemClickListener?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(user: UserVO?, item: PostVO) {
        binding.circleImageViewAvatar.setImageResource(item.user.getAvatar())
        binding.textViewUser.text = item.user.name
        binding.textViewDate.text = Util.dateSqlToBPtBr(item.createdAt)
        binding.textViewContent.text = item.content

        if (user?.id == item.user.id) {
            binding.imageViewEdit.visibility = View.VISIBLE
            binding.imageViewDelete.visibility = View.VISIBLE

            binding.imageViewEdit.setOnClickListener {
                listener?.onItemEditClick(layoutPosition)
            }
            binding.imageViewDelete.setOnClickListener {
                listener?.onItemDeleteClick(layoutPosition)
            }
        } else {
            binding.imageViewEdit.visibility = View.GONE
            binding.imageViewDelete.visibility = View.GONE
        }
    }
}

