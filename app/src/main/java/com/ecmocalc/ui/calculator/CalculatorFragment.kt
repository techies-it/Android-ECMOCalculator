package com.ecmocalc.ui.calculator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.ecmocalc.R
import com.ecmocalc.databinding.FragmentCalculatorBinding
import com.ecmocalc.ui.SharedViewModel
import com.ecmocalc.utils.Calculations.Companion.calBodySurfaceArea
import com.ecmocalc.utils.Calculations.Companion.calCentimetersToInches
import com.ecmocalc.utils.Calculations.Companion.calInchesToCentimeters
import com.ecmocalc.utils.Calculations.Companion.calKilogramsToPounds
import com.ecmocalc.utils.Calculations.Companion.calPoundsToKilograms
import com.ecmocalc.utils.Calculations.Companion.calWeightBasedBodySurfaceArea
import com.ecmocalc.utils.Helper

class CalculatorFragment : Fragment() {

    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)

        uiPoundsToKilograms()
        uiKilogramsToPounds()
        uiInchesToCentimeters()
        uiCentimetersToInches()
        uiBodyToSurfaceArea()
        uiWeightBaseBodySurfaceArea()

        return binding.root
    }

    private fun uiPoundsToKilograms() {
        binding.etLbs.addTextChangedListener { value ->
            if (value.isNullOrEmpty()) {
                binding.tvResultKilograms.text = "--Kg"
            } else {
                sharedViewModel.setValueLbs(value.toString())
            }
        }

        sharedViewModel.valueLbs.observe(viewLifecycleOwner, Observer { value ->
            binding.tvResultKilograms.text = Helper.convertStringToDouble(value)
                ?.let { calPoundsToKilograms(it) }
        })
    }

    private fun uiKilogramsToPounds() {
        binding.etKg.addTextChangedListener { value ->
            if (value.isNullOrEmpty()) {
                binding.tvResultPounds.text = "--Lbs"
            } else {
                sharedViewModel.setValueKg(value.toString())
            }
        }

        sharedViewModel.valueKg.observe(viewLifecycleOwner, Observer { value ->
            binding.tvResultPounds.text = Helper.convertStringToDouble(value)
                ?.let { calKilogramsToPounds(it) }
        })
    }

    private fun uiInchesToCentimeters() {
        binding.etInches.addTextChangedListener { value ->
            if (value.isNullOrEmpty()) {
                binding.tvResultCentimeters.text = "--cm"
            } else {
                sharedViewModel.setValueInches(value.toString())
            }
        }

        sharedViewModel.valueInches.observe(viewLifecycleOwner, Observer { value ->
            binding.tvResultCentimeters.text = Helper.convertStringToDouble(value)
                ?.let { calInchesToCentimeters(it) }
        })
    }

    private fun uiCentimetersToInches() {
        binding.etCentimeters.addTextChangedListener { value ->
            if (value.isNullOrEmpty()) {
                binding.tvResultInches.text = "--in"
            } else {
                sharedViewModel.setValueCentimeters(value.toString())
            }
        }

        sharedViewModel.valueCentimeters.observe(viewLifecycleOwner, Observer { value ->
            binding.tvResultInches.text = Helper.convertStringToDouble(value)
                ?.let { calCentimetersToInches(it) }
        })
    }

    private fun uiBodyToSurfaceArea() {
        binding.etHeight.addTextChangedListener { value ->
            sharedViewModel.setValueHeight(value.toString())
        }
        binding.etWeight.addTextChangedListener { value ->
            sharedViewModel.setValueWeight(value.toString())
        }

        sharedViewModel.valueHeight.observe(viewLifecycleOwner, Observer { value ->
            calculateBSA()
        })

        sharedViewModel.valueWeight.observe(viewLifecycleOwner, Observer { value ->
            calculateBSA()
        })
    }

    private fun calculateBSA() {
        val height = sharedViewModel.valueHeight.value?.let { Helper.convertStringToDouble(it) }
        val weight = sharedViewModel.valueWeight.value?.let { Helper.convertStringToDouble(it) }
        if (height != null && weight != null) {
            binding.tvResultBSA.text = calBodySurfaceArea(weight, height)
        }else{
            binding.tvResultBSA.text = "--m²"
        }
    }

    private fun uiWeightBaseBodySurfaceArea() {
        binding.etWeightBSA.addTextChangedListener { value ->
            if (value.isNullOrEmpty()) {
                binding.tvResultWeightBaseBodySurfaceArea.text = "--m²"
            } else {
                sharedViewModel.setValueWeightBSA(value.toString())
            }
        }

        sharedViewModel.valueWeightBSA.observe(viewLifecycleOwner, Observer { value ->
            binding.tvResultWeightBaseBodySurfaceArea.text = Helper.convertStringToDouble(value)
                ?.let { calWeightBasedBodySurfaceArea(it) }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}