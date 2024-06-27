package com.ecmocalc.ui.calculator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.ecmocalc.R
import com.ecmocalc.databinding.FragmentCalculatorBinding
import com.ecmocalc.ui.SharedViewModel
import com.ecmocalc.utils.Calculations.Companion.calBodySurfaceArea
import com.ecmocalc.utils.Calculations.Companion.calCardiacIndex
import com.ecmocalc.utils.Calculations.Companion.calCardiacIndexCalculator
import com.ecmocalc.utils.Calculations.Companion.calCardiacOutput
import com.ecmocalc.utils.Calculations.Companion.calCentimetersToInches
import com.ecmocalc.utils.Calculations.Companion.calDilutionalHCT
import com.ecmocalc.utils.Calculations.Companion.calEstimatedRedCellMass
import com.ecmocalc.utils.Calculations.Companion.calHeparinLoadingDose
import com.ecmocalc.utils.Calculations.Companion.calInchesToCentimeters
import com.ecmocalc.utils.Calculations.Companion.calKilogramsToPounds
import com.ecmocalc.utils.Calculations.Companion.calOxygenIndex
import com.ecmocalc.utils.Calculations.Companion.calPaO2ByFiO2Ratio
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
        uiOxygenIndex()
        uiPaO2ByFiO2Ratio()
        uiHeparinLoadingDose()
        uiCardiacIndexCalculator()
        uiEstimatedRedCellMass()
        uiDilutionalHematocrit()
        uiCardiacOutput()
        uiCardiacIndex()

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
            sharedViewModel.setValueHeightForBSA(value.toString())
        }
        binding.etWeight.addTextChangedListener { value ->
            sharedViewModel.setValueWeightForBSA(value.toString())
        }

        sharedViewModel.valueHeightForBSA.observe(viewLifecycleOwner, Observer { value ->
            calculateBSA()
        })

        sharedViewModel.valueWeightForBSA.observe(viewLifecycleOwner, Observer { value ->
            calculateBSA()
        })
    }

    private fun calculateBSA() {
        val height =
            sharedViewModel.valueHeightForBSA.value?.let { Helper.convertStringToDouble(it) }
        val weight =
            sharedViewModel.valueWeightForBSA.value?.let { Helper.convertStringToDouble(it) }
        if (height != null && weight != null) {
            binding.tvResultBSA.text = calBodySurfaceArea(weight, height)
        } else {
            binding.tvResultBSA.text = "--m²"
        }
    }

    private fun uiWeightBaseBodySurfaceArea() {
        binding.etWeightBSA.addTextChangedListener { value ->
            if (value.isNullOrEmpty()) {
                binding.tvResultWeightBaseBodySurfaceArea.text = "--m²"
            } else {
                sharedViewModel.setValueWeightBSAByWeight(value.toString())
            }
        }

        sharedViewModel.valueWeightBSAByWeight.observe(viewLifecycleOwner, Observer { value ->
            binding.tvResultWeightBaseBodySurfaceArea.text = Helper.convertStringToDouble(value)
                ?.let { calWeightBasedBodySurfaceArea(it) }
        })
    }

    private fun uiOxygenIndex() {
        binding.etMap.addTextChangedListener { value ->
            sharedViewModel.setValueMAPForOI(value.toString())
        }
        binding.etFio2.addTextChangedListener { value ->
            sharedViewModel.setValueFiO2ForOI(value.toString())
        }
        binding.etPao2.addTextChangedListener { value ->
            sharedViewModel.setValuePaO2ForOI(value.toString())
        }

        sharedViewModel.valueMAPForOI.observe(viewLifecycleOwner, Observer { value ->
            calculateOxygenIndex()
        })

        sharedViewModel.valueFiO2ForOI.observe(viewLifecycleOwner, Observer { value ->
            calculateOxygenIndex()
        })

        sharedViewModel.valuePaO2ForOI.observe(viewLifecycleOwner, Observer { value ->
            calculateOxygenIndex()
        })
    }

    private fun calculateOxygenIndex() {
        val vMAP = sharedViewModel.valueMAPForOI.value?.let { Helper.convertStringToDouble(it) }
        val vFiO2 = sharedViewModel.valueFiO2ForOI.value?.let { Helper.convertStringToDouble(it) }
        val vPaO2 = sharedViewModel.valuePaO2ForOI.value?.let { Helper.convertStringToDouble(it) }
        if (vMAP != null && vFiO2 != null && vPaO2 != null) {
            binding.tvResultOxygenIndex.text = calOxygenIndex(vFiO2, vMAP, vPaO2)
        } else {
            binding.tvResultOxygenIndex.text = "---"
        }
    }

    private fun uiPaO2ByFiO2Ratio() {
        binding.etFiO2Ratio.addTextChangedListener { value ->
            sharedViewModel.setValueFiO2ForRatio(value.toString())
        }
        binding.etPaO2Ratio.addTextChangedListener { value ->
            sharedViewModel.setValuePaO2ForRatio(value.toString())
        }

        sharedViewModel.valueFiO2ForRatio.observe(viewLifecycleOwner, Observer { value ->
            calculatePaO2ByFiO2Ratio()
        })

        sharedViewModel.valuePaO2ForRatio.observe(viewLifecycleOwner, Observer { value ->
            calculatePaO2ByFiO2Ratio()
        })
    }

    private fun calculatePaO2ByFiO2Ratio() {
        val vPaO2 = sharedViewModel.valuePaO2ForRatio.value?.let { Helper.convertStringToDouble(it) }
        val vFiO2 = sharedViewModel.valueFiO2ForRatio.value?.let { Helper.convertStringToDouble(it) }
        if (vFiO2 != null && vPaO2 != null) {
            binding.tvResultPaO2FiO2Ratio.text = calPaO2ByFiO2Ratio(vPaO2, vFiO2)
        } else {
            binding.tvResultPaO2FiO2Ratio.text = "---"
        }
    }

    private fun uiHeparinLoadingDose() {
        binding.etWeightHeparinLoadingDose.addTextChangedListener { value ->
            if (value.isNullOrEmpty()) {
                binding.tvResultHeparinLoadingDose.text =
                    getString(R.string.default_weight_heparin_loading_dose)
            } else {
                sharedViewModel.setValueWeightForHeparinLoadingDose(value.toString())
            }
        }

        sharedViewModel.valueWeightForHeparinLoadingDose.observe(
            viewLifecycleOwner,
            Observer { value ->
                binding.tvResultHeparinLoadingDose.text = Helper.convertStringToDouble(value)
                    ?.let { calHeparinLoadingDose(it) }
            })
    }

    private fun uiCardiacIndexCalculator() {
        binding.etBsaCardiacIndexCalculator.addTextChangedListener { value ->
            if (value.isNullOrEmpty()) {
                binding.tvResultCardiacIndexCalculator.text =
                    getString(R.string.default_cardiac_index_calculator)
            } else {
                sharedViewModel.setValueBSAForCardiacIndexCalculator(value.toString())
            }
        }

        sharedViewModel.valueBSAForCardiacIndexCalculator.observe(
            viewLifecycleOwner,
            Observer { value ->
                binding.tvResultCardiacIndexCalculator.text = Helper.convertStringToDouble(value)
                    ?.let { calCardiacIndexCalculator(it) }
            })
    }

    private fun uiEstimatedRedCellMass() {
        binding.etWeightEstimatedRedCellMass.addTextChangedListener { value ->
            sharedViewModel.setValueWeightForEstimatedRedCellMass(value.toString())
        }
        binding.etHematocritEstimatedRedCellMass.addTextChangedListener { value ->
            sharedViewModel.setValueHematocritForEstimatedRedCellMass(value.toString())
        }

        sharedViewModel.valueWeightForEstimatedRedCellMass.observe(
            viewLifecycleOwner,
            Observer { value ->
                calculateEstimatedRedCellMass()
            })

        sharedViewModel.valueHematocritForEstimatedRedCellMass.observe(
            viewLifecycleOwner,
            Observer { value ->
                calculateEstimatedRedCellMass()
            })
    }

    private fun calculateEstimatedRedCellMass() {
        val vWeight = sharedViewModel.valueWeightForEstimatedRedCellMass.value?.let {
            Helper.convertStringToDouble(it)
        }
        val vHematocrit = sharedViewModel.valueHematocritForEstimatedRedCellMass.value?.let {
            Helper.convertStringToDouble(it)
        }
        if (vWeight != null && vHematocrit != null) {
            binding.tvResultEstimatedRedCellMass.text =
                calEstimatedRedCellMass(vWeight, vHematocrit)
        } else {
            binding.tvResultEstimatedRedCellMass.text = "--- mL"
        }
    }

    private fun uiDilutionalHematocrit() {
        binding.etWeightDilutionalHematocrit.addTextChangedListener { value ->
            sharedViewModel.setValueWeightForDilutionalHematocrit(value.toString())
        }
        binding.etHctDilutionalHematocrit.addTextChangedListener { value ->
            sharedViewModel.setValueHCTForDilutionalHematocrit(value.toString())
        }
        binding.etEclsCircuitVolDilutionalHematocrit.addTextChangedListener { value ->
            sharedViewModel.setValueECLSCircuitVolForDilutionalHematocrit(value.toString())
        }

        sharedViewModel.valueWeightForDilutionalHematocrit.observe(
            viewLifecycleOwner,
            Observer { value ->
                calculateDilutionalHematocrit()
            })

        sharedViewModel.valueHCTForDilutionalHematocrit.observe(
            viewLifecycleOwner,
            Observer { value ->
                calculateDilutionalHematocrit()
            })

        sharedViewModel.valueECLSCircuitVolForDilutionalHematocrit.observe(
            viewLifecycleOwner,
            Observer { value ->
                calculateDilutionalHematocrit()
            })
    }

    private fun calculateDilutionalHematocrit() {
        val vWeight = sharedViewModel.valueWeightForDilutionalHematocrit.value?.let {
            Helper.convertStringToDouble(it)
        }
        val vHCT = sharedViewModel.valueHCTForDilutionalHematocrit.value?.let {
            Helper.convertStringToDouble(it)
        }
        val vECLSCircuitVol =
            sharedViewModel.valueECLSCircuitVolForDilutionalHematocrit.value?.let {
                Helper.convertStringToDouble(it)
            }
        if (vWeight != null && vHCT != null && vECLSCircuitVol != null) {
            binding.tvResultDilutionalHematocritTil.text =
                calDilutionalHCT(vWeight, vHCT, vECLSCircuitVol)
        } else {
            binding.tvResultDilutionalHematocritTil.text = "--- %"
        }
    }

    private fun uiCardiacOutput() {
        binding.etHrCardiacOutput.addTextChangedListener { value ->
            sharedViewModel.setValueHRForCardiacOutput(value.toString())
        }
        binding.etStrokeVolCardiacOutput.addTextChangedListener { value ->
            sharedViewModel.setValueStrokeVolForCardiacOutput(value.toString())
        }

        sharedViewModel.valueHRForCardiacOutput.observe(viewLifecycleOwner, Observer { value ->
            calculateCardiacOutput()
        })

        sharedViewModel.valueStrokeVolForCardiacOutput.observe(viewLifecycleOwner, Observer { value ->
            calculateCardiacOutput()
        })
    }

    private fun calculateCardiacOutput() {
        val vHR = sharedViewModel.valueHRForCardiacOutput.value?.let { Helper.convertStringToDouble(it) }
        val vStrokeVol = sharedViewModel.valueStrokeVolForCardiacOutput.value?.let { Helper.convertStringToDouble(it) }
        if (vHR != null && vStrokeVol != null) {
            binding.tvResultCardiacOutput.text = calCardiacOutput(vHR, vStrokeVol)
        } else {
            binding.tvResultCardiacOutput.text = "0.00 L/min"
        }
    }

    private fun uiCardiacIndex() {
        binding.etCoCardiacIndex.addTextChangedListener { value ->
            sharedViewModel.setValueCOForCardiacIndex(value.toString())
        }
        binding.etBsaCardiacIndex.addTextChangedListener { value ->
            sharedViewModel.setValueBSAForCardiacIndex(value.toString())
        }

        sharedViewModel.valueCOForCardiacIndex.observe(viewLifecycleOwner, Observer { value ->
            calculateCardiacIndex()
        })

        sharedViewModel.valueBSAForCardiacIndex.observe(viewLifecycleOwner, Observer { value ->
            calculateCardiacIndex()
        })
    }

    private fun calculateCardiacIndex() {
        val vCO = sharedViewModel.valueCOForCardiacIndex.value?.let { Helper.convertStringToDouble(it) }
        val vBSA = sharedViewModel.valueBSAForCardiacIndex.value?.let { Helper.convertStringToDouble(it) }
        if (vCO != null && vBSA != null) {
            binding.tvResultCardiacIndex.text = calCardiacIndex(vCO, vBSA)
        } else {
            binding.tvResultCardiacIndex.text = "--- L/min/m²"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}