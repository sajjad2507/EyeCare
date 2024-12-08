package com.example.eyecare.ui.filterdashboard

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.eyecare.R
import com.example.eyecare.databinding.ItemTempLayoutBinding
import com.example.eyecare.ui.utils.Utils.setSingleClickListener
import com.example.eyecare.ui.utils.constants.Constants
import com.example.eyecare.ui.utils.preferences.EasyPrefs

class FilterDashboardAdapter(private val onItemClick: (Int,ItemTempLayoutBinding) -> Unit) :
    ListAdapter<Int, FilterDashboardAdapter.FilterViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val binding = ItemTempLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FilterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item, position)
        }
    }
    inner class FilterViewHolder(private val binding: ItemTempLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Int, position: Int) {
            binding.apply {
                icon.setImageResource(item)
                root.setSingleClickListener{
                    onItemClick.invoke(position,binding)
                }
            }
            prefObservers(binding, position)
        }
    }

    fun prefObservers(binding: ItemTempLayoutBinding, position: Int) {
        val colorTemperateLiveData = EasyPrefs.colorTemperatureLiveData()
        val colorTemperateObserver = Observer<String> { value ->
            setupTemperatureLayout(value, binding, position)
        }
        colorTemperateLiveData.observeForever(colorTemperateObserver)
    }

    // Setup Temperature Layout
    private fun setupTemperatureLayout(
        temp: String,
        binding: ItemTempLayoutBinding,
        position: Int
    ) {
        binding.apply {
            when (temp) {
                Constants.EYE_CARE_VALUE -> {
                    when (position) {
                        0 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        1 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        2 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        3 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        4 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        5 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        6 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        7 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }
                    }
                }

                Constants.MOON_LIGHT_VALUE -> {
                    when (position) {
                        0 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        1 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        2 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        3 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        4 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        5 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        6 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        7 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }
                    }
                }

                Constants.SUN_RISE_LIGHT_VALUE -> {
                    when (position) {
                        0 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        1 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        2 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        3 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        4 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        5 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        6 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        7 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }
                    }
                }

                Constants.SUN_DOWN_LIGHT_VALUE -> {
                    when (position) {
                        0 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        1 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        2 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        3 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        4 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        5 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        6 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        7 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }
                    }
                }

                Constants.CANDLE_LIGHT_VALUE -> {
                    when (position) {
                        0 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        1 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        2 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        3 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        4 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        5 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        6 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        7 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }
                    }
                }

                Constants.TREES_LIGHT_VALUE -> {
                    when (position) {
                        0 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        1 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        2 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        3 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        4 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        5 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        6 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        7 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }
                    }
                }

                Constants.NORMAL_LIGHT_VALUE -> {
                    when (position) {
                        0 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        1 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        2 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        3 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        4 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        5 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        6 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        7 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }
                    }
                }

                Constants.NORMAL_FIRE_VALUE -> {
                    when (position) {
                        0 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        1 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        2 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        3 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        4 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        5 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        6 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }

                        7 -> {
                            circularView.setBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.blue
                                )
                            )
                            icon.setColorFilter(
                                ContextCompat.getColor(
                                    root.context,
                                    R.color.white
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                        }
                    }
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Int>() {
            override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
                return oldItem == newItem
            }
        }
    }
}
