package com.example.githubexplorer.views.ui

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.githubexplorer.databinding.TagChipBinding

class TagChip(context: Context, text: String) : LinearLayout(context) {
    var binding: TagChipBinding

    init {
        binding = TagChipBinding.inflate(LayoutInflater.from(context))
        binding.chipText.text = text
        addView(binding.root)

        val params = binding.root.layoutParams as LayoutParams
        params.rightMargin = 12
        params.topMargin = 12
    }
}