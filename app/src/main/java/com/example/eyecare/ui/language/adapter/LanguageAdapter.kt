package com.example.eyecare.ui.language.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eyecare.R
import com.example.eyecare.ui.language.LanguageFragment
import com.example.eyecare.ui.language.datamodel.LanguageModel

class LanguageAdapter(
    private val context: LanguageFragment,
    private val activity: Activity,
    private val languages: ArrayList<LanguageModel>,
    private var selectedLang: LanguageModel? = null,
    private val defaultLangCode: String
) : RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder>() {

    init {
        selectedLang = languages.find { it.code == defaultLangCode }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val itemView = LayoutInflater.from(activity).inflate(R.layout.item_language, parent, false)
        return LanguageViewHolder(itemView)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        val language = languages[position]

        holder.nameTextView.text = language.name
        Glide.with(activity).load(language.flag).into(holder.flagImageView)

        if (language == selectedLang) {
            holder.selectorImageView.setBackgroundDrawable(null)
            holder.selectorImageView.setBackgroundResource(R.drawable.ic_language_selected)
            holder.itemView.setBackgroundResource(R.drawable.item_language_bg)
        } else {
            holder.selectorImageView.setBackgroundDrawable(null)
            holder.selectorImageView.setBackgroundResource(R.drawable.ic_unselected_language)
            holder.itemView.setBackgroundResource(R.drawable.item_un_language_bg)
        }

        holder.itemView.setOnClickListener {
            val previousLang = selectedLang
            selectedLang = language

            previousLang?.let { prevLang ->
                val prevIndex = languages.indexOf(prevLang)
                notifyItemChanged(prevIndex)
            }
            notifyItemChanged(holder.position)

            context.languageItemSelected(language, position)
        }
    }

    override fun getItemCount(): Int = languages.size

    inner class LanguageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val selectorImageView: ImageView = itemView.findViewById(R.id.ic_language_selector)
        val nameTextView: TextView = itemView.findViewById(R.id.language_txt)
        val flagImageView: ImageView = itemView.findViewById(R.id.brand_img)
    }

    interface LanguageSelectCallback {
        fun languageItemSelected(language: LanguageModel, position: Int)
    }
}
