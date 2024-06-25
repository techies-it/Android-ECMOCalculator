package com.ecmocalc.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {

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

    private val _valueBSA = MutableLiveData<Double>()
    val valueBSA: LiveData<Double> get() = _valueBSA

    fun setValueBSA(value: Double) {
        _valueBSA.value = value
    }

    /* Calculation Screen values */

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

    private val _valueHeight = MutableLiveData<String>()
    val valueHeight: LiveData<String> get() = _valueHeight

    fun setValueHeight(value: String) {
        _valueHeight.value = value
    }

    private val _valueWeight = MutableLiveData<String>()
    val valueWeight: LiveData<String> get() = _valueWeight

    fun setValueWeight(value: String) {
        _valueWeight.value = value
    }

    private val _valueWeightBSA = MutableLiveData<String>()
    val valueWeightBSA: LiveData<String> get() = _valueWeightBSA

    fun setValueWeightBSA(value: String) {
        _valueWeightBSA.value = value
    }
}