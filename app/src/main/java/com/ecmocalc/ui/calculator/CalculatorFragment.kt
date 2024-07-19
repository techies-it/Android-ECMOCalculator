package com.ecmocalc.ui.calculator

import android.annotation.SuppressLint
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.ecmocalc.databinding.FragmentCalculatorBinding
import com.ecmocalc.models.StaticValues
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
import com.ecmocalc.utils.Calculations.Companion.calOxygenConsumption
import com.ecmocalc.utils.Calculations.Companion.calOxygenContentArterial
import com.ecmocalc.utils.Calculations.Companion.calOxygenContentVenous
import com.ecmocalc.utils.Calculations.Companion.calOxygenDelivery
import com.ecmocalc.utils.Calculations.Companion.calOxygenIndex
import com.ecmocalc.utils.Calculations.Companion.calPaO2ByFiO2Ratio
import com.ecmocalc.utils.Calculations.Companion.calPoundsToKilograms
import com.ecmocalc.utils.Calculations.Companion.calPulmonaryVascularResistance
import com.ecmocalc.utils.Calculations.Companion.calSweepGas
import com.ecmocalc.utils.Calculations.Companion.calSystemicVascularResistance
import com.ecmocalc.utils.Calculations.Companion.calWeightBasedBodySurfaceArea
import com.ecmocalc.utils.Helper


class CalculatorFragment : Fragment() {

    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        setupUI(binding.mainLayoutCalculator)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        hideKeyboard()
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
        uiSystemicVascularResistance()
        uiPulmonaryVascularResistance()
        uiOxygenContentArterial()
        uiOxygenDelivery()
        uiOxygenContentVenous()
        uiOxygenConsumption()
        uiSweepGas()
    }

    private fun uiPoundsToKilograms() {
        binding.etLbs.addTextChangedListener { value ->
            sharedViewModel.setValueLbs(value.toString())
        }

        sharedViewModel.valueLbs.observe(viewLifecycleOwner, Observer { value ->
            binding.tvResultKilograms.text = if (value.isNullOrEmpty()) {
                "--Kg"
            } else {
                Helper.convertStringToDouble(value)
                    ?.let { calPoundsToKilograms(it) }
            }
        })
    }

    private fun uiKilogramsToPounds() {
        binding.etKg.addTextChangedListener { value ->
            sharedViewModel.setValueKg(value.toString())
        }

        sharedViewModel.valueKg.observe(viewLifecycleOwner, Observer { value ->
            binding.tvResultPounds.text = if (value.isNullOrEmpty()) {
                "--Lbs"
            } else {
                Helper.convertStringToDouble(value)
                    ?.let { calKilogramsToPounds(it) }
            }
        })
    }

    private fun uiInchesToCentimeters() {
        binding.etInches.addTextChangedListener { value ->
            sharedViewModel.setValueInches(value.toString())
        }

        sharedViewModel.valueInches.observe(viewLifecycleOwner, Observer { value ->
            binding.tvResultCentimeters.text = if (value.isNullOrEmpty()) {
                "--cm"
            } else {
                Helper.convertStringToDouble(value)
                    ?.let { calInchesToCentimeters(it) }
            }
        })
    }

    private fun uiCentimetersToInches() {
        binding.etCentimeters.addTextChangedListener { value ->
            sharedViewModel.setValueCentimeters(value.toString())
        }

        sharedViewModel.valueCentimeters.observe(viewLifecycleOwner, Observer { value ->
            binding.tvResultInches.text = if (value.isNullOrEmpty()) {
                "--in"
            } else {
                Helper.convertStringToDouble(value)
                    ?.let { calCentimetersToInches(it) }
            }
            scrollAction(1)
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
        scrollAction(2)
    }

    private fun uiWeightBaseBodySurfaceArea() {
        binding.etWeightBSA.addTextChangedListener { value ->
            sharedViewModel.setValueWeightBSAByWeight(value.toString())
        }

        sharedViewModel.valueWeightBSAByWeight.observe(viewLifecycleOwner, Observer { value ->
            binding.tvResultWeightBaseBodySurfaceArea.text = if (value.isNullOrEmpty()) {
                "--m²"
            } else {
                Helper.convertStringToDouble(value)
                    ?.let { calWeightBasedBodySurfaceArea(it) }
            }
            scrollAction(3)
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
        scrollAction(4)
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
        val vPaO2 =
            sharedViewModel.valuePaO2ForRatio.value?.let { Helper.convertStringToDouble(it) }
        val vFiO2 =
            sharedViewModel.valueFiO2ForRatio.value?.let { Helper.convertStringToDouble(it) }
        if (vFiO2 != null && vPaO2 != null) {
            binding.tvResultPaO2FiO2Ratio.text = calPaO2ByFiO2Ratio(vPaO2, vFiO2)
        } else {
            binding.tvResultPaO2FiO2Ratio.text = "---"
        }
        scrollAction(5)
    }

    private fun uiHeparinLoadingDose() {
        binding.etWeightHeparinLoadingDose.addTextChangedListener { value ->
            sharedViewModel.setValueWeightForHeparinLoadingDose(value.toString())
        }

        sharedViewModel.valueWeightForHeparinLoadingDose.observe(
            viewLifecycleOwner,
            Observer { value ->
                var resultHeparinLoadingDoseList: ArrayList<StaticValues> =
                    ArrayList<StaticValues>()
                resultHeparinLoadingDoseList.clear()
                if (value.isNullOrEmpty()) {
                    resultHeparinLoadingDoseList.add(StaticValues("25u/Kg = 0 units"))
                    resultHeparinLoadingDoseList.add(StaticValues("50u/Kg = 0 units"))
                    resultHeparinLoadingDoseList.add(StaticValues("75u/Kg = 0 units"))
                    resultHeparinLoadingDoseList.add(StaticValues("100u/Kg = 0 units"))
                    resultHeparinLoadingDoseList.add(StaticValues("200u/Kg = 0 units"))
                    resultHeparinLoadingDoseList.add(StaticValues("300u/Kg = 0 units"))
                } else {
                    resultHeparinLoadingDoseList = Helper.convertStringToDouble(value)
                        ?.let { calHeparinLoadingDose(it) }!!
                }

                val resultsListAdapter = ResultsListAdapter(resultHeparinLoadingDoseList)
                binding.rvResultHeparinLoadingDose.visibility = View.VISIBLE
                binding.rvResultHeparinLoadingDose.adapter = resultsListAdapter
                scrollAction(17, isList = true)
            })
    }

    private fun uiCardiacIndexCalculator() {
        binding.etBsaCardiacIndexCalculator.addTextChangedListener { value ->
            sharedViewModel.setValueBSAForCardiacIndexCalculator(value.toString())
        }

        sharedViewModel.valueBSAForCardiacIndexCalculator.observe(
            viewLifecycleOwner,
            Observer { value ->
                var resultCardiacIndexCalculatorList: ArrayList<StaticValues> =
                    ArrayList<StaticValues>()
                resultCardiacIndexCalculatorList.clear()
                if (value.isNullOrEmpty()) {
                    resultCardiacIndexCalculatorList.add(StaticValues("CI 1.0 = 0.00 L/min"))
                    resultCardiacIndexCalculatorList.add(StaticValues("CI 1.5 = 0.00 L/min"))
                    resultCardiacIndexCalculatorList.add(StaticValues("CI 1.8 = 0.00 L/min"))
                    resultCardiacIndexCalculatorList.add(StaticValues("CI 2.0 = 0.00 L/min"))
                    resultCardiacIndexCalculatorList.add(StaticValues("CI 2.2 = 0.00 L/min"))
                    resultCardiacIndexCalculatorList.add(StaticValues("CI 2.4 = 0.00 L/min"))
                    resultCardiacIndexCalculatorList.add(StaticValues("CI 2.6 = 0.00 L/min"))
                    resultCardiacIndexCalculatorList.add(StaticValues("CI 2.8 = 0.00 L/min"))
                    resultCardiacIndexCalculatorList.add(StaticValues("CI 3.0 = 0.00 L/min"))
                } else {
                    resultCardiacIndexCalculatorList = Helper.convertStringToDouble(value)
                        ?.let { calCardiacIndexCalculator(it) }!!
                }

                val resultsListAdapter = ResultsListAdapter(resultCardiacIndexCalculatorList)
                binding.rvResultCardiacIndexCalculator.visibility = View.VISIBLE
                binding.rvResultCardiacIndexCalculator.adapter = resultsListAdapter
                scrollAction(18, isList = true)
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
        scrollAction(6)
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
        scrollAction(7)
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

        sharedViewModel.valueStrokeVolForCardiacOutput.observe(
            viewLifecycleOwner,
            Observer { value ->
                calculateCardiacOutput()
            })
    }

    private fun calculateCardiacOutput() {
        val vHR =
            sharedViewModel.valueHRForCardiacOutput.value?.let { Helper.convertStringToDouble(it) }
        val vStrokeVol = sharedViewModel.valueStrokeVolForCardiacOutput.value?.let {
            Helper.convertStringToDouble(it)
        }
        if (vHR != null && vStrokeVol != null) {
            binding.tvResultCardiacOutput.text = calCardiacOutput(vHR, vStrokeVol)
        } else {
            binding.tvResultCardiacOutput.text = "0.00 L/min"
        }
        scrollAction(8)
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
        val vCO =
            sharedViewModel.valueCOForCardiacIndex.value?.let { Helper.convertStringToDouble(it) }
        val vBSA =
            sharedViewModel.valueBSAForCardiacIndex.value?.let { Helper.convertStringToDouble(it) }
        if (vCO != null && vBSA != null) {
            binding.tvResultCardiacIndex.text = calCardiacIndex(vCO, vBSA)
        } else {
            binding.tvResultCardiacIndex.text = "--- L/min/m²"
        }
        scrollAction(9)
    }

    private fun uiSystemicVascularResistance() {
        binding.etMapSystemicVascularResistance.addTextChangedListener { value ->
            sharedViewModel.setValueMAPForSystemicVascularResistance(value.toString())
        }
        binding.etCvpSystemicVascularResistance.addTextChangedListener { value ->
            sharedViewModel.setValueCVPForSystemicVascularResistance(value.toString())
        }
        binding.etCoSystemicVascularResistance.addTextChangedListener { value ->
            sharedViewModel.setValueCOForSystemicVascularResistance(value.toString())
        }

        sharedViewModel.valueMAPForSystemicVascularResistance.observe(
            viewLifecycleOwner,
            Observer { value ->
                calculateSystemicVascularResistance()
            })

        sharedViewModel.valueCVPForSystemicVascularResistance.observe(
            viewLifecycleOwner,
            Observer { value ->
                calculateSystemicVascularResistance()
            })

        sharedViewModel.valueCOForSystemicVascularResistance.observe(
            viewLifecycleOwner,
            Observer { value ->
                calculateSystemicVascularResistance()
            })
    }

    private fun calculateSystemicVascularResistance() {
        val vMAP = sharedViewModel.valueMAPForSystemicVascularResistance.value?.let {
            Helper.convertStringToDouble(it)
        }
        val vCVP = sharedViewModel.valueCVPForSystemicVascularResistance.value?.let {
            Helper.convertStringToDouble(it)
        }
        val vCO = sharedViewModel.valueCOForSystemicVascularResistance.value?.let {
            Helper.convertStringToDouble(it)
        }
        if (vMAP != null && vCVP != null && vCO != null) {
            binding.tvResultSystemicVascularResistance.text =
                calSystemicVascularResistance(vMAP, vCVP, vCO)
        } else {
            binding.tvResultSystemicVascularResistance.text = "--- Dynes-sec/cm⁵"
        }
        scrollAction(10)
    }

    private fun uiPulmonaryVascularResistance() {
        binding.etMpapPulmonaryVascularResistance.addTextChangedListener { value ->
            sharedViewModel.setValueMPAPForPulmonaryVascularResistance(value.toString())
        }
        binding.etPcwpPulmonaryVascularResistance.addTextChangedListener { value ->
            sharedViewModel.setValuePCWPForPulmonaryVascularResistance(value.toString())
        }
        binding.etCoPulmonaryVascularResistance.addTextChangedListener { value ->
            sharedViewModel.setValueCOForPulmonaryVascularResistance(value.toString())
        }

        sharedViewModel.valueMPAPForPulmonaryVascularResistance.observe(
            viewLifecycleOwner,
            Observer { value ->
                calculatePulmonaryVascularResistance()
            })

        sharedViewModel.valuePCWPForPulmonaryVascularResistance.observe(
            viewLifecycleOwner,
            Observer { value ->
                calculatePulmonaryVascularResistance()
            })

        sharedViewModel.valueCOForPulmonaryVascularResistance.observe(
            viewLifecycleOwner,
            Observer { value ->
                calculatePulmonaryVascularResistance()
            })
    }

    private fun calculatePulmonaryVascularResistance() {
        val vMPAP = sharedViewModel.valueMPAPForPulmonaryVascularResistance.value?.let {
            Helper.convertStringToDouble(it)
        }
        val vPCWP = sharedViewModel.valuePCWPForPulmonaryVascularResistance.value?.let {
            Helper.convertStringToDouble(it)
        }
        val vCO = sharedViewModel.valueCOForPulmonaryVascularResistance.value?.let {
            Helper.convertStringToDouble(it)
        }
        if (vMPAP != null && vPCWP != null && vCO != null) {
            binding.tvResultPulmonaryVascularResistance.text =
                calPulmonaryVascularResistance(vMPAP, vPCWP, vCO)
        } else {
            binding.tvResultPulmonaryVascularResistance.text = "--- Dynes-sec/cm⁵"
        }
        scrollAction(11)
    }

    private fun uiOxygenContentArterial() {
        binding.etHgbOxygenContentArterial.addTextChangedListener { value ->
            sharedViewModel.setValueHgbForOxygenContentArterial(value.toString())
        }
        binding.etSao2OxygenContentArterial.addTextChangedListener { value ->
            sharedViewModel.setValueSaO2ForOxygenContentArterial(value.toString())
        }
        binding.etPao2OxygenContentArterial.addTextChangedListener { value ->
            sharedViewModel.setValuePaO2ForOxygenContentArterial(value.toString())
        }

        sharedViewModel.valueHgbForOxygenContentArterial.observe(
            viewLifecycleOwner,
            Observer { value ->
                calculateOxygenContentArterial()
            })

        sharedViewModel.valueSaO2ForOxygenContentArterial.observe(
            viewLifecycleOwner,
            Observer { value ->
                calculateOxygenContentArterial()
            })

        sharedViewModel.valuePaO2ForOxygenContentArterial.observe(
            viewLifecycleOwner,
            Observer { value ->
                calculateOxygenContentArterial()
            })
    }

    private fun calculateOxygenContentArterial() {
        val vHgb = sharedViewModel.valueHgbForOxygenContentArterial.value?.let {
            Helper.convertStringToDouble(it)
        }
        val vSaO2 = sharedViewModel.valueSaO2ForOxygenContentArterial.value?.let {
            Helper.convertStringToDouble(it)
        }
        val vPaO2 = sharedViewModel.valuePaO2ForOxygenContentArterial.value?.let {
            Helper.convertStringToDouble(it)
        }
        if (vHgb != null && vSaO2 != null && vPaO2 != null) {
            binding.tvResultOxygenContentArterial.text =
                calOxygenContentArterial(vHgb, vSaO2, vPaO2)
        } else {
            binding.tvResultOxygenContentArterial.text = "--- mL/dL"
        }
        scrollAction(12)
    }

    private fun uiOxygenDelivery() {
        binding.etCoOxygenDelivery.addTextChangedListener { value ->
            sharedViewModel.setValueCOForOxygenDelivery(value.toString())
        }
        binding.etCao2OxygenDelivery.addTextChangedListener { value ->
            sharedViewModel.setValueCaO2ForOxygenDelivery(value.toString())
        }

        sharedViewModel.valueCOForOxygenDelivery.observe(viewLifecycleOwner, Observer { value ->
            calculateOxygenDelivery()
        })

        sharedViewModel.valueCaO2ForOxygenDelivery.observe(viewLifecycleOwner, Observer { value ->
            calculateOxygenDelivery()
        })
    }

    private fun calculateOxygenDelivery() {
        val vCO =
            sharedViewModel.valueCOForOxygenDelivery.value?.let { Helper.convertStringToDouble(it) }
        val vCaO2 =
            sharedViewModel.valueCaO2ForOxygenDelivery.value?.let { Helper.convertStringToDouble(it) }
        if (vCO != null && vCaO2 != null) {
            binding.tvResultOxygenDelivery.text = calOxygenDelivery(vCO, vCaO2)
        } else {
            binding.tvResultOxygenDelivery.text = "--- mL/min"
        }
        scrollAction(13)
    }

    private fun uiOxygenContentVenous() {
        binding.etHgbOxygenContentVenous.addTextChangedListener { value ->
            sharedViewModel.setValueHgbForOxygenContentVenous(value.toString())
        }
        binding.etSvo2OxygenContentVenous.addTextChangedListener { value ->
            sharedViewModel.setValueSvO2ForOxygenContentVenous(value.toString())
        }
        binding.etPvo2OxygenContentVenous.addTextChangedListener { value ->
            sharedViewModel.setValuePvO2ForOxygenContentVenous(value.toString())
        }

        sharedViewModel.valueHgbForOxygenContentVenous.observe(
            viewLifecycleOwner,
            Observer { value ->
                calculateOxygenContentVenous()
            })

        sharedViewModel.valueSvO2ForOxygenContentVenous.observe(
            viewLifecycleOwner,
            Observer { value ->
                calculateOxygenContentVenous()
            })

        sharedViewModel.valuePvO2ForOxygenContentVenous.observe(
            viewLifecycleOwner,
            Observer { value ->
                calculateOxygenContentVenous()
            })
    }

    private fun calculateOxygenContentVenous() {
        val vHgb = sharedViewModel.valueHgbForOxygenContentVenous.value?.let {
            Helper.convertStringToDouble(it)
        }
        val vSvO2 = sharedViewModel.valueSvO2ForOxygenContentVenous.value?.let {
            Helper.convertStringToDouble(it)
        }
        val vPvO2 = sharedViewModel.valuePvO2ForOxygenContentVenous.value?.let {
            Helper.convertStringToDouble(it)
        }
        if (vHgb != null && vSvO2 != null && vPvO2 != null) {
            binding.tvResultOxygenContentVenous.text = calOxygenContentVenous(vHgb, vSvO2, vPvO2)
        } else {
            binding.tvResultOxygenContentVenous.text = "--- mL/dL"
        }
        scrollAction(14)
    }

    private fun uiOxygenConsumption() {
        binding.etCoOxygenConsumption.addTextChangedListener { value ->
            sharedViewModel.setValueCOForOxygenConsumption(value.toString())
        }
        binding.etCao2Cvo2OxygenConsumption.addTextChangedListener { value ->
            sharedViewModel.setValueCaO2CvO2ForOxygenConsumption(value.toString())
        }

        sharedViewModel.valueCOForOxygenConsumption.observe(viewLifecycleOwner, Observer { value ->
            calculateOxygenConsumption()
        })

        sharedViewModel.valueCaO2CvO2ForOxygenConsumption.observe(
            viewLifecycleOwner,
            Observer { value ->
                calculateOxygenConsumption()
            })
    }

    private fun calculateOxygenConsumption() {
        val vCO =
            sharedViewModel.valueCOForOxygenConsumption.value?.let { Helper.convertStringToDouble(it) }
        val vCaO2CvO2 = sharedViewModel.valueCaO2CvO2ForOxygenConsumption.value?.let {
            Helper.convertStringToDouble(it)
        }
        if (vCO != null && vCaO2CvO2 != null) {
            binding.tvResultOxygenConsumption.text = calOxygenConsumption(vCO, vCaO2CvO2)
        } else {
            binding.tvResultOxygenConsumption.text = "--- mL/min"
        }
        scrollAction(15)
    }

    private fun uiSweepGas() {
        binding.etCurrentPaco2SweepGas.addTextChangedListener { value ->
            sharedViewModel.setValueCurrentPaCO2ForSweepGas(value.toString())
        }
        binding.etCurrentSweepFlowSweepGas.addTextChangedListener { value ->
            sharedViewModel.setValueCurrentSweepFlowForSweepGas(value.toString())
        }
        binding.etDesiredPaco2SweepGas.addTextChangedListener { value ->
            sharedViewModel.setValueDesiredPaCO2ForSweepGas(value.toString())
        }

        sharedViewModel.valueCurrentPaCO2ForSweepGas.observe(viewLifecycleOwner, Observer { value ->
            calculateSweepGas()
        })

        sharedViewModel.valueCurrentSweepFlowForSweepGas.observe(
            viewLifecycleOwner,
            Observer { value ->
                calculateSweepGas()
            })

        sharedViewModel.valueDesiredPaCO2ForSweepGas.observe(viewLifecycleOwner, Observer { value ->
            calculateSweepGas()
        })
    }

    private fun calculateSweepGas() {
        val vCurrentPaCO2 = sharedViewModel.valueCurrentPaCO2ForSweepGas.value?.let {
            Helper.convertStringToDouble(it)
        }
        val vCurrentSweepFlow = sharedViewModel.valueCurrentSweepFlowForSweepGas.value?.let {
            Helper.convertStringToDouble(it)
        }
        val vDesiredPaCO2 = sharedViewModel.valueDesiredPaCO2ForSweepGas.value?.let {
            Helper.convertStringToDouble(it)
        }
        if (vCurrentPaCO2 != null && vCurrentSweepFlow != null && vDesiredPaCO2 != null) {
            binding.tvResultSweepGas.text =
                calSweepGas(vCurrentPaCO2, vCurrentSweepFlow, vDesiredPaCO2)
        } else {
            binding.tvResultSweepGas.text = "--- L/min"
        }
        scrollAction(16)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun scrollAction(position: Int, isList:Boolean = false) {
        if (sharedViewModel.scrollPosition.value != position) {
            sharedViewModel.setScrollPosition(position)
            // Calculate new scroll position
            var newScrollY = -1
            if (isList){
                newScrollY = (binding.scrollView.scrollY + 600).coerceAtLeast(0)
            }else{
                newScrollY = (binding.scrollView.scrollY + 90).coerceAtLeast(0)
            }
            // Scroll smoothly to a specific position
            binding.scrollView.post {
                binding.scrollView.smoothScrollTo(0, newScrollY)
            }
        }
    }

    @SuppressLint("", "ClickableViewAccessibility")
    private fun setupUI(view: View) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (view !is EditText) {
            view.setOnTouchListener { v, event ->
                hideSoftKeyboard()
                false
            }
        }

        // If a layout container, iterate over children and seed recursion.
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                setupUI(innerView)
            }
        }
    }

    private fun hideSoftKeyboard() {
        val inputMethodManager =
            activity?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard()
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }
}