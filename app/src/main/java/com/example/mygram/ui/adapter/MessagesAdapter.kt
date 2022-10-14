package com.example.mygram.ui.adapter

/*
class MessagesAdapter(): ListAdapter<Message, MessagesAdapter.MessageViewHolder>(DiffCallback) {
    class MessageViewHolder(private var binding: MessageItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(message: Message){
            binding.messageText.text = message.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            MessageItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind()
    }

    companion object DiffCallback = object : DiffUtil.ItemCallback<Message>(){
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return  oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.id == newItem.id
        }

    }



}*/