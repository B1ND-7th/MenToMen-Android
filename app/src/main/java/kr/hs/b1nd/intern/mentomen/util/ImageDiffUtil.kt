package kr.hs.b1nd.intern.mentomen.util

import android.net.Uri
import androidx.recyclerview.widget.DiffUtil

object ImageDiffUtil : DiffUtil.ItemCallback<Uri?>() {
    override fun areItemsTheSame(oldItem: Uri, newItem: Uri): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: Uri, newItem: Uri): Boolean = oldItem == newItem
}