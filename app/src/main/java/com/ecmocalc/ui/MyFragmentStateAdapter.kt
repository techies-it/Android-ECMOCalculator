package com.ecmocalc.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ecmocalc.ui.calculator.CalculatorFragment
import com.ecmocalc.ui.cannula.CannulaFragment
import com.ecmocalc.ui.support.SupportFragment

class MyFragmentStateAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 3 // Number of fragments
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CalculatorFragment()
            1 -> CannulaFragment()
            2 -> SupportFragment()
            else -> throw IllegalStateException("Invalid position")
        }
    }
}