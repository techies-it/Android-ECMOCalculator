package com.ecmocalc.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {

    private val _textViewTitle = MutableLiveData<String>()
    val textViewTitle: LiveData<String> get() = _textViewTitle

    fun setTextViewTitle(value: String) {
        _textViewTitle.value = value
    }

    private val _editTextWeight = MutableLiveData<String>()
    val editTextWeight: LiveData<String> get() = _editTextWeight

    fun setEditTextWeight(value: String) {
        _editTextWeight.value = value
    }

    private val _editTextHeight = MutableLiveData<String>()
    val editTextHeight: LiveData<String> get() = _editTextHeight

    fun setEditTextHeight(value: String) {
        _editTextHeight.value = value
    }

    private val _editTextCI = MutableLiveData<String?>()
    val editTextCI: LiveData<String?> get() = _editTextCI

    fun setEditTextCI(value: String?) {
        _editTextCI.value = value
    }

    private val _editTextCIForPed = MutableLiveData<String?>()
    val editTextCIForPed: LiveData<String?> get() = _editTextCIForPed

    fun setEditTextCIForPed(value: String?) {
        _editTextCIForPed.value = value
    }

    private val _editTextCIForAud = MutableLiveData<String?>()
    val editTextCIForAud: LiveData<String?> get() = _editTextCIForAud

    fun setEditTextCIForAud(value: String?) {
        _editTextCIForAud.value = value
    }

    private val _valueBSA = MutableLiveData<Double?>()
    val valueBSA: LiveData<Double?> get() = _valueBSA

    fun setValueBSA(value: Double?) {
        _valueBSA.value = value
    }

    private val _valueTargetBloodFlow = MutableLiveData<Double?>()
    val valueTargetBloodFlow: LiveData<Double?> get() = _valueTargetBloodFlow

    fun setValueTargetBloodFlow(value: Double?) {
        _valueTargetBloodFlow.value = value
    }

    /* Calculation Screen values */

    private val _scrollPosition = MutableLiveData<Int>()
    val scrollPosition: LiveData<Int> get() = _scrollPosition

    fun setScrollPosition(value: Int) {
        _scrollPosition.value = value
    }

    private val _valueLbs = MutableLiveData<String>()
    val valueLbs: LiveData<String> get() = _valueLbs

    fun setValueLbs(value: String) {
        _valueLbs.value = value
    }

    private val _valueKg = MutableLiveData<String>()
    val valueKg: LiveData<String> get() = _valueKg

    fun setValueKg(value: String) {
        _valueKg.value = value
    }

    private val _valueInches = MutableLiveData<String>()
    val valueInches: LiveData<String> get() = _valueInches

    fun setValueInches(value: String) {
        _valueInches.value = value
    }

    private val _valueCentimeters = MutableLiveData<String>()
    val valueCentimeters: LiveData<String> get() = _valueCentimeters

    fun setValueCentimeters(value: String) {
        _valueCentimeters.value = value
    }

    /* Body Surface Area */

    private val _valueHeightForBSA = MutableLiveData<String>()
    val valueHeightForBSA: LiveData<String> get() = _valueHeightForBSA

    fun setValueHeightForBSA(value: String) {
        _valueHeightForBSA.value = value
    }

    private val _valueWeightForBSA = MutableLiveData<String>()
    val valueWeightForBSA: LiveData<String> get() = _valueWeightForBSA

    fun setValueWeightForBSA(value: String) {
        _valueWeightForBSA.value = value
    }

    /* Weight Base Body Surface Area */

    private val _valueWeightBSAByWeight = MutableLiveData<String>()
    val valueWeightBSAByWeight: LiveData<String> get() = _valueWeightBSAByWeight

    fun setValueWeightBSAByWeight(value: String) {
        _valueWeightBSAByWeight.value = value
    }

    /* For Oxygen Index*/

    private val _valueMAPForOI = MutableLiveData<String>()
    val valueMAPForOI: LiveData<String> get() = _valueMAPForOI

    fun setValueMAPForOI(value: String) {
        _valueMAPForOI.value = value
    }

    private val _valueFiO2ForOI = MutableLiveData<String>()
    val valueFiO2ForOI: LiveData<String> get() = _valueFiO2ForOI

    fun setValueFiO2ForOI(value: String) {
        _valueFiO2ForOI.value = value
    }

    private val _valuePaO2ForOI = MutableLiveData<String>()
    val valuePaO2ForOI: LiveData<String> get() = _valuePaO2ForOI

    fun setValuePaO2ForOI(value: String) {
        _valuePaO2ForOI.value = value
    }

    /* For FiO2/PaO2 Ratio */

    private val _valueFiO2ForRatio = MutableLiveData<String>()
    val valueFiO2ForRatio: LiveData<String> get() = _valueFiO2ForRatio

    fun setValueFiO2ForRatio(value: String) {
        _valueFiO2ForRatio.value = value
    }

    private val _valuePaO2ForRatio = MutableLiveData<String>()
    val valuePaO2ForRatio: LiveData<String> get() = _valuePaO2ForRatio

    fun setValuePaO2ForRatio(value: String) {
        _valuePaO2ForRatio.value = value
    }

    /* For Heparin Loading Dose */

    private val _valueWeightForHeparinLoadingDose = MutableLiveData<String>()
    val valueWeightForHeparinLoadingDose: LiveData<String> get() = _valueWeightForHeparinLoadingDose

    fun setValueWeightForHeparinLoadingDose(value: String) {
        _valueWeightForHeparinLoadingDose.value = value
    }

    /* For Cardiac Index Calculator */

    private val _valueBSAForCardiacIndexCalculator = MutableLiveData<String>()
    val valueBSAForCardiacIndexCalculator: LiveData<String> get() = _valueBSAForCardiacIndexCalculator

    fun setValueBSAForCardiacIndexCalculator(value: String) {
        _valueBSAForCardiacIndexCalculator.value = value
    }

    /* For Estimated Red Cell Mass */

    private val _valueWeightForEstimatedRedCellMass = MutableLiveData<String>()
    val valueWeightForEstimatedRedCellMass: LiveData<String> get() = _valueWeightForEstimatedRedCellMass

    fun setValueWeightForEstimatedRedCellMass(value: String) {
        _valueWeightForEstimatedRedCellMass.value = value
    }

    private val _valueHematocritForEstimatedRedCellMass = MutableLiveData<String>()
    val valueHematocritForEstimatedRedCellMass: LiveData<String> get() = _valueHematocritForEstimatedRedCellMass

    fun setValueHematocritForEstimatedRedCellMass(value: String) {
        _valueHematocritForEstimatedRedCellMass.value = value
    }

    /* For Dilutional Hematocrit */

    private val _valueWeightForDilutionalHematocrit = MutableLiveData<String>()
    val valueWeightForDilutionalHematocrit: LiveData<String> get() = _valueWeightForDilutionalHematocrit

    fun setValueWeightForDilutionalHematocrit(value: String) {
        _valueWeightForDilutionalHematocrit.value = value
    }

    private val _valueHCTForDilutionalHematocrit = MutableLiveData<String>()
    val valueHCTForDilutionalHematocrit: LiveData<String> get() = _valueHCTForDilutionalHematocrit

    fun setValueHCTForDilutionalHematocrit(value: String) {
        _valueHCTForDilutionalHematocrit.value = value
    }

    private val _valueECLSCircuitVolForDilutionalHematocrit = MutableLiveData<String>()
    val valueECLSCircuitVolForDilutionalHematocrit: LiveData<String> get() = _valueECLSCircuitVolForDilutionalHematocrit

    fun setValueECLSCircuitVolForDilutionalHematocrit(value: String) {
        _valueECLSCircuitVolForDilutionalHematocrit.value = value
    }

    /* For Cardiac Output */

    private val _valueHRForCardiacOutput = MutableLiveData<String>()
    val valueHRForCardiacOutput: LiveData<String> get() = _valueHRForCardiacOutput

    fun setValueHRForCardiacOutput(value: String) {
        _valueHRForCardiacOutput.value = value
    }

    private val _valueStrokeVolForCardiacOutput = MutableLiveData<String>()
    val valueStrokeVolForCardiacOutput: LiveData<String> get() = _valueStrokeVolForCardiacOutput

    fun setValueStrokeVolForCardiacOutput(value: String) {
        _valueStrokeVolForCardiacOutput.value = value
    }

    /* For Cardiac Index */

    private val _valueCOForCardiacIndex = MutableLiveData<String>()
    val valueCOForCardiacIndex: LiveData<String> get() = _valueCOForCardiacIndex

    fun setValueCOForCardiacIndex(value: String) {
        _valueCOForCardiacIndex.value = value
    }

    private val _valueBSAForCardiacIndex = MutableLiveData<String>()
    val valueBSAForCardiacIndex: LiveData<String> get() = _valueBSAForCardiacIndex

    fun setValueBSAForCardiacIndex(value: String) {
        _valueBSAForCardiacIndex.value = value
    }

    /* For Systemic Vascular Resistance */

    private val _valueMAPForSystemicVascularResistance = MutableLiveData<String>()
    val valueMAPForSystemicVascularResistance: LiveData<String> get() = _valueMAPForSystemicVascularResistance

    fun setValueMAPForSystemicVascularResistance(value: String) {
        _valueMAPForSystemicVascularResistance.value = value
    }

    private val _valueCVPForSystemicVascularResistance = MutableLiveData<String>()
    val valueCVPForSystemicVascularResistance: LiveData<String> get() = _valueCVPForSystemicVascularResistance

    fun setValueCVPForSystemicVascularResistance(value: String) {
        _valueCVPForSystemicVascularResistance.value = value
    }

    private val _valueCOForSystemicVascularResistance = MutableLiveData<String>()
    val valueCOForSystemicVascularResistance: LiveData<String> get() = _valueCOForSystemicVascularResistance

    fun setValueCOForSystemicVascularResistance(value: String) {
        _valueCOForSystemicVascularResistance.value = value
    }

    /* For Pulmonary Vascular Resistance */

    private val _valueMPAPForPulmonaryVascularResistance = MutableLiveData<String>()
    val valueMPAPForPulmonaryVascularResistance: LiveData<String> get() = _valueMPAPForPulmonaryVascularResistance

    fun setValueMPAPForPulmonaryVascularResistance(value: String) {
        _valueMPAPForPulmonaryVascularResistance.value = value
    }

    private val _valuePCWPForPulmonaryVascularResistance = MutableLiveData<String>()
    val valuePCWPForPulmonaryVascularResistance: LiveData<String> get() = _valuePCWPForPulmonaryVascularResistance

    fun setValuePCWPForPulmonaryVascularResistance(value: String) {
        _valuePCWPForPulmonaryVascularResistance.value = value
    }

    private val _valueCOForPulmonaryVascularResistance = MutableLiveData<String>()
    val valueCOForPulmonaryVascularResistance: LiveData<String> get() = _valueCOForPulmonaryVascularResistance

    fun setValueCOForPulmonaryVascularResistance(value: String) {
        _valueCOForPulmonaryVascularResistance.value = value
    }

    /* For Oxygen Content (CaO₂) - Arterial */

    private val _valueHgbForOxygenContentArterial = MutableLiveData<String>()
    val valueHgbForOxygenContentArterial: LiveData<String> get() = _valueHgbForOxygenContentArterial

    fun setValueHgbForOxygenContentArterial(value: String) {
        _valueHgbForOxygenContentArterial.value = value
    }

    private val _valueSaO2ForOxygenContentArterial = MutableLiveData<String>()
    val valueSaO2ForOxygenContentArterial: LiveData<String> get() = _valueSaO2ForOxygenContentArterial

    fun setValueSaO2ForOxygenContentArterial(value: String) {
        _valueSaO2ForOxygenContentArterial.value = value
    }

    private val _valuePaO2ForOxygenContentArterial = MutableLiveData<String>()
    val valuePaO2ForOxygenContentArterial: LiveData<String> get() = _valuePaO2ForOxygenContentArterial

    fun setValuePaO2ForOxygenContentArterial(value: String) {
        _valuePaO2ForOxygenContentArterial.value = value
    }

    /* For Oxygen Delivery (DO₂) */

    private val _valueCOForOxygenDelivery = MutableLiveData<String>()
    val valueCOForOxygenDelivery: LiveData<String> get() = _valueCOForOxygenDelivery

    fun setValueCOForOxygenDelivery(value: String) {
        _valueCOForOxygenDelivery.value = value
    }

    private val _valueCaO2ForOxygenDelivery = MutableLiveData<String>()
    val valueCaO2ForOxygenDelivery: LiveData<String> get() = _valueCaO2ForOxygenDelivery

    fun setValueCaO2ForOxygenDelivery(value: String) {
        _valueCaO2ForOxygenDelivery.value = value
    }

    /* For Oxygen Content (CvO₂) - Venous */

    private val _valueHgbForOxygenContentVenous = MutableLiveData<String>()
    val valueHgbForOxygenContentVenous: LiveData<String> get() = _valueHgbForOxygenContentVenous

    fun setValueHgbForOxygenContentVenous(value: String) {
        _valueHgbForOxygenContentVenous.value = value
    }

    private val _valueSvO2ForOxygenContentVenous = MutableLiveData<String>()
    val valueSvO2ForOxygenContentVenous: LiveData<String> get() = _valueSvO2ForOxygenContentVenous

    fun setValueSvO2ForOxygenContentVenous(value: String) {
        _valueSvO2ForOxygenContentVenous.value = value
    }

    private val _valuePvO2ForOxygenContentVenous = MutableLiveData<String>()
    val valuePvO2ForOxygenContentVenous: LiveData<String> get() = _valuePvO2ForOxygenContentVenous

    fun setValuePvO2ForOxygenContentVenous(value: String) {
        _valuePvO2ForOxygenContentVenous.value = value
    }

    /* For Oxygen Consumption (VO₂) */

    private val _valueCOForOxygenConsumption = MutableLiveData<String>()
    val valueCOForOxygenConsumption: LiveData<String> get() = _valueCOForOxygenConsumption

    fun setValueCOForOxygenConsumption(value: String) {
        _valueCOForOxygenConsumption.value = value
    }

    private val _valueCaO2CvO2ForOxygenConsumption = MutableLiveData<String>()
    val valueCaO2CvO2ForOxygenConsumption: LiveData<String> get() = _valueCaO2CvO2ForOxygenConsumption

    fun setValueCaO2CvO2ForOxygenConsumption(value: String) {
        _valueCaO2CvO2ForOxygenConsumption.value = value
    }

    /* For Sweep Gas */

    private val _valueCurrentPaCO2ForSweepGas = MutableLiveData<String>()
    val valueCurrentPaCO2ForSweepGas: LiveData<String> get() = _valueCurrentPaCO2ForSweepGas

    fun setValueCurrentPaCO2ForSweepGas(value: String) {
        _valueCurrentPaCO2ForSweepGas.value = value
    }

    private val _valueCurrentSweepFlowForSweepGas = MutableLiveData<String>()
    val valueCurrentSweepFlowForSweepGas: LiveData<String> get() = _valueCurrentSweepFlowForSweepGas

    fun setValueCurrentSweepFlowForSweepGas(value: String) {
        _valueCurrentSweepFlowForSweepGas.value = value
    }

    private val _valueDesiredPaCO2ForSweepGas = MutableLiveData<String>()
    val valueDesiredPaCO2ForSweepGas: LiveData<String> get() = _valueDesiredPaCO2ForSweepGas

    fun setValueDesiredPaCO2ForSweepGas(value: String) {
        _valueDesiredPaCO2ForSweepGas.value = value
    }
}