package com.ecmocalc.ui.cannula

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.ecmocalc.R
import com.ecmocalc.databinding.FragmentCannulaBinding
import com.ecmocalc.models.StaticValues
import com.ecmocalc.ui.SharedViewModel
import com.ecmocalc.utils.Calculations
import com.ecmocalc.utils.Calculations.Companion.calBodySurfaceArea
import com.ecmocalc.utils.Calculations.Companion.calCardiacOutputWithCIAndBSA
import com.ecmocalc.utils.Calculations.Companion.calTargetBloodFlowForAdultEntry
import com.ecmocalc.utils.Calculations.Companion.calTargetBloodFlowForPediatricEntry
import com.ecmocalc.utils.Helper.Companion.convertStringToDouble
import com.google.android.material.button.MaterialButton

class CannulaFragment : Fragment(), TargetCIListAdapter.SetTargetCIValue {

    private var _binding: FragmentCannulaBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var targetBloodFlowListAdapter: TargetBloodFlowListAdapter? = null
    private var valuesDialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCannulaBinding.inflate(inflater, container, false)

        binding.layoutTargetCi.setOnClickListener {
            showTargetCIListDialog()
        }

        sharedViewModel.editTextCI.observe(viewLifecycleOwner) { value ->
            binding.etTargetCI.text = value
            targetBloodFlowListAdapter?.updateSelection(value)
        }

        sharedViewModel.editTextWeight.observe(viewLifecycleOwner) { value ->
            val formattedValue = convertStringToDouble(value)
            when {
                formattedValue != null && formattedValue > 10.0 -> {
                    binding.tvTitle.text = getString(R.string.adult_entry)
                    binding.layoutHeight.visibility = View.VISIBLE
                    binding.layoutTargetCi.visibility = View.VISIBLE
                    binding.tvBSA.visibility = View.VISIBLE
                    binding.layoutTargetBloodFlow.visibility = View.GONE
                    calculateBSA()
                }

                formattedValue != null && formattedValue > 0.0 && formattedValue <= 10.0 -> {
                    binding.tvTitle.text = getString(R.string.pediatric_entry)
                    binding.layoutHeight.visibility = View.GONE
                    binding.layoutTargetCi.visibility = View.VISIBLE
                    binding.tvBSA.visibility = View.GONE
                    sharedViewModel.setEditTextCI(null)
                    binding.etheight.setText(null)
                    generateTargetBloodFlowList()
                }

                value.isNullOrEmpty() || formattedValue == 0.0 -> {
                    binding.tvTitle.text = getString(R.string.enter_weight)
                    binding.layoutHeight.visibility = View.INVISIBLE
                    binding.layoutTargetCi.visibility = View.INVISIBLE
                    binding.layoutTargetBloodFlow.visibility = View.GONE
                    binding.layoutVANeck.visibility = View.GONE
                    binding.layoutVAGroin.visibility = View.GONE
                    binding.layoutVVDL.visibility = View.GONE
                    binding.tvBSA.visibility = View.GONE
                    sharedViewModel.setEditTextCI(null)
                    binding.layoutVANeck.visibility = View.GONE
                    binding.layoutVAGroin.visibility = View.GONE
                    binding.layoutVVDL.visibility = View.GONE
                }
            }
        }

        binding.etweight.addTextChangedListener { value ->
            sharedViewModel.setEditTextWeight(value.toString())
        }

        sharedViewModel.editTextHeight.observe(viewLifecycleOwner, Observer { value ->
            calculateBSA()
        })

        binding.etheight.addTextChangedListener { value ->
            sharedViewModel.setEditTextHeight(value.toString())
        }

        return binding.root
    }


    private fun calculateBSA() {
        val formattedValueWeight = sharedViewModel.editTextWeight.value?.toString()
            ?.let { it1 -> convertStringToDouble(it1) }

        val formattedValueHeight = sharedViewModel.editTextHeight.value?.toString()
            ?.let { it1 -> convertStringToDouble(it1) }

        if (formattedValueHeight != null && formattedValueWeight != null) {
            val finalBSA = "BSA ${calBodySurfaceArea(formattedValueWeight, formattedValueHeight)}"
            sharedViewModel.setValueBSA(
                finalBSA.removeSuffix(" m²").removePrefix("BSA ").toDouble()
            )
            binding.tvBSA.text = finalBSA
            generateTargetBloodFlowList()
        } else {
            binding.tvBSA.text = getString(R.string.bsa_m)
            sharedViewModel.setValueBSA(0.0)
            binding.layoutTargetBloodFlow.visibility = View.GONE
            sharedViewModel.setEditTextCI(null)
            binding.layoutVANeck.visibility = View.GONE
            binding.layoutVAGroin.visibility = View.GONE
            binding.layoutVVDL.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.editTextCI.observe(viewLifecycleOwner) { value ->
            binding.etTargetCI.text = value
            targetBloodFlowListAdapter?.updateSelection(value)
        }
    }


    private fun generateVANeckList(listVANeck: ArrayList<StaticValues>) {
        binding.layoutVANeck.visibility = View.VISIBLE

        val staticValuesListAdapterVANeck = StaticValuesListAdapter(listVANeck)
        binding.rvVANeck?.adapter = staticValuesListAdapterVANeck
    }

    private fun generateVAGroinList(listVAGroin: ArrayList<StaticValues>) {
        binding.layoutVAGroin.visibility = View.VISIBLE

        val staticValuesListAdapterVAGroin = StaticValuesListAdapter(listVAGroin)
        binding.rvVAGroin?.adapter = staticValuesListAdapterVAGroin
    }

    private fun generateVVDLList(listVVDL: ArrayList<StaticValues>) {
        binding.layoutVVDL.visibility = View.VISIBLE

        val staticValuesListAdapterVVDL = StaticValuesListAdapter(listVVDL)
        binding.rvVVDL?.adapter = staticValuesListAdapterVVDL
    }

    private fun generateTargetBloodFlowList() {
        binding.layoutTargetBloodFlow?.visibility = View.VISIBLE
        val targetBloodFlow: ArrayList<StaticValues> = ArrayList<StaticValues>()
        targetBloodFlow.clear()
        if (binding.tvTitle.text == getString(R.string.pediatric_entry)) {
            binding.etTargetCI.text = null
            binding.tvTitleTargetBloodFlow.text = "0-10Kg Target Blood Flow"
            targetBloodFlow.add(StaticValues("100 ml/kg : ${
                convertStringToDouble(binding.etweight.text.toString())?.let {
                    calTargetBloodFlowForPediatricEntry(
                        it, 100.0
                    )
                }
            }", "100"))
            targetBloodFlow.add(StaticValues("150 ml/kg : ${
                convertStringToDouble(binding.etweight.text.toString())?.let {
                    calTargetBloodFlowForPediatricEntry(
                        it, 150.0
                    )
                }
            }", "150"))
            targetBloodFlow.add(StaticValues("175 ml/kg : ${
                convertStringToDouble(binding.etweight.text.toString())?.let {
                    calTargetBloodFlowForPediatricEntry(
                        it, 175.0
                    )
                }
            }", "175"))
            targetBloodFlow.add(StaticValues("200 ml/kg : ${
                convertStringToDouble(binding.etweight.text.toString())?.let {
                    calTargetBloodFlowForPediatricEntry(
                        it, 200.0
                    )
                }
            }", "200"))
            targetBloodFlow.add(StaticValues("250 ml/kg : ${
                convertStringToDouble(binding.etweight.text.toString())?.let {
                    calTargetBloodFlowForPediatricEntry(
                        it, 250.0
                    )
                }
            }", "250"))
        } else {
            binding.tvTitleTargetBloodFlow.text = "10Kg and Greater Target Blood Flow"
            if (sharedViewModel.valueBSA.value != null
                && sharedViewModel.valueBSA.value != 0.0
                && sharedViewModel.editTextWeight.value != null
                && sharedViewModel.editTextWeight.value != "0.0"
            ) {

                val CO05 = calCardiacOutputWithCIAndBSA(
                    0.5, sharedViewModel.valueBSA.value!!
                )
                val TBF05 = calTargetBloodFlowForAdultEntry(
                    CO05.removeSuffix(" L/min").toDouble(),
                    sharedViewModel.editTextWeight.value?.toDouble()!!
                )
                targetBloodFlow.add(StaticValues("0.5 C.I. = $CO05 = $TBF05", "0.5"))

                val CO10 = calCardiacOutputWithCIAndBSA(
                    1.0, sharedViewModel.valueBSA.value!!
                )
                val TBF10 = calTargetBloodFlowForAdultEntry(
                    CO10.removeSuffix(" L/min").toDouble(),
                    sharedViewModel.editTextWeight.value?.toDouble()!!
                )
                targetBloodFlow.add(StaticValues("1.0 C.I. = $CO10 = $TBF10", "1.0"))

                val CO12 = calCardiacOutputWithCIAndBSA(
                    1.2, sharedViewModel.valueBSA.value!!
                )
                val TBF12 = calTargetBloodFlowForAdultEntry(
                    CO12.removeSuffix(" L/min").toDouble(),
                    sharedViewModel.editTextWeight.value?.toDouble()!!
                )
                targetBloodFlow.add(StaticValues("1.2 C.I. = $CO12 = $TBF12", "1.2"))

                val CO14 = calCardiacOutputWithCIAndBSA(
                    1.4, sharedViewModel.valueBSA.value!!
                )
                val TBF14 = calTargetBloodFlowForAdultEntry(
                    CO14.removeSuffix(" L/min").toDouble(),
                    sharedViewModel.editTextWeight.value?.toDouble()!!
                )
                targetBloodFlow.add(StaticValues("1.4 C.I. = $CO14 = $TBF14", "1.4"))

                val CO16 = calCardiacOutputWithCIAndBSA(
                    1.6, sharedViewModel.valueBSA.value!!
                )
                val TBF16 = calTargetBloodFlowForAdultEntry(
                    CO16.removeSuffix(" L/min").toDouble(),
                    sharedViewModel.editTextWeight.value?.toDouble()!!
                )
                targetBloodFlow.add(StaticValues("1.6 C.I. = $CO16 = $TBF16", "1.6"))

                val CO18 = calCardiacOutputWithCIAndBSA(
                    1.8, sharedViewModel.valueBSA.value!!
                )
                val TBF18 = calTargetBloodFlowForAdultEntry(
                    CO18.removeSuffix(" L/min").toDouble(),
                    sharedViewModel.editTextWeight.value?.toDouble()!!
                )
                targetBloodFlow.add(StaticValues("1.8 C.I. = $CO18 = $TBF18", "1.8"))

                val CO20 = calCardiacOutputWithCIAndBSA(
                    2.0, sharedViewModel.valueBSA.value!!
                )
                val TBF20 = calTargetBloodFlowForAdultEntry(
                    CO20.removeSuffix(" L/min").toDouble(),
                    sharedViewModel.editTextWeight.value?.toDouble()!!
                )
                targetBloodFlow.add(StaticValues("2.0 C.I. = $CO20 = $TBF20", "2.0"))

                val CO22 = calCardiacOutputWithCIAndBSA(
                    2.2, sharedViewModel.valueBSA.value!!
                )
                val TBF22 = calTargetBloodFlowForAdultEntry(
                    CO22.removeSuffix(" L/min").toDouble(),
                    sharedViewModel.editTextWeight.value?.toDouble()!!
                )
                targetBloodFlow.add(StaticValues("2.2 C.I. = $CO22 = $TBF22", "2.2"))

                val CO24 = calCardiacOutputWithCIAndBSA(
                    2.4, sharedViewModel.valueBSA.value!!
                )
                val TBF24 = calTargetBloodFlowForAdultEntry(
                    CO24.removeSuffix(" L/min").toDouble(),
                    sharedViewModel.editTextWeight.value?.toDouble()!!
                )
                targetBloodFlow.add(StaticValues("2.4 C.I. = $CO24 = $TBF24", "2.4"))

                val CO26 = calCardiacOutputWithCIAndBSA(
                    2.6, sharedViewModel.valueBSA.value!!
                )
                val TBF26 = calTargetBloodFlowForAdultEntry(
                    CO26.removeSuffix(" L/min").toDouble(),
                    sharedViewModel.editTextWeight.value?.toDouble()!!
                )
                targetBloodFlow.add(StaticValues("2.6 C.I. = $CO26 = $TBF26", "2.6"))

                val CO28 = calCardiacOutputWithCIAndBSA(
                    2.8, sharedViewModel.valueBSA.value!!
                )
                val TBF28 = calTargetBloodFlowForAdultEntry(
                    CO28.removeSuffix(" L/min").toDouble(),
                    sharedViewModel.editTextWeight.value?.toDouble()!!
                )
                targetBloodFlow.add(StaticValues("2.8 C.I. = $CO28 = $TBF28", "2.8"))

                val CO30 = calCardiacOutputWithCIAndBSA(
                    3.0, sharedViewModel.valueBSA.value!!
                )
                val TBF30 = calTargetBloodFlowForAdultEntry(
                    CO30.removeSuffix(" L/min").toDouble(),
                    sharedViewModel.editTextWeight.value?.toDouble()!!
                )
                targetBloodFlow.add(StaticValues("3.0 C.I. = $CO30 = $TBF30", "3.0"))
            }
        }

        targetBloodFlowListAdapter = TargetBloodFlowListAdapter(targetBloodFlow)
        binding.rvTargetBloodFlow?.visibility = View.VISIBLE
        binding.rvTargetBloodFlow?.adapter = targetBloodFlowListAdapter
    }

    private fun showTargetCIListDialog() {
        valuesDialog = Dialog(requireContext())
        valuesDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        valuesDialog?.setCancelable(true)
        valuesDialog?.setContentView(R.layout.target_c_i_dialog)
        valuesDialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val title: TextView = valuesDialog?.findViewById(R.id.tv_title)!!
        val btCancel: MaterialButton = valuesDialog?.findViewById(R.id.bt_cancel)!!
        val listValues: RecyclerView = valuesDialog?.findViewById(R.id.list_values)!!

        btCancel.setOnClickListener {
            valuesDialog?.dismiss()
        }

        title.text = "Target C.I."
        val targetCIArrayList: ArrayList<StaticValues> = ArrayList<StaticValues>()
        targetCIArrayList.clear()

        if (binding.tvTitle.text == getString(R.string.pediatric_entry)) {
            targetCIArrayList.add(StaticValues("100"))
            targetCIArrayList.add(StaticValues("150"))
            targetCIArrayList.add(StaticValues("175"))
            targetCIArrayList.add(StaticValues("200"))
            targetCIArrayList.add(StaticValues("250"))
        } else {
            targetCIArrayList.add(StaticValues("0.5"))
            targetCIArrayList.add(StaticValues("1.0"))
            targetCIArrayList.add(StaticValues("1.2"))
            targetCIArrayList.add(StaticValues("1.4"))
            targetCIArrayList.add(StaticValues("1.6"))
            targetCIArrayList.add(StaticValues("1.8"))
            targetCIArrayList.add(StaticValues("2.0"))
            targetCIArrayList.add(StaticValues("2.2"))
            targetCIArrayList.add(StaticValues("2.4"))
            targetCIArrayList.add(StaticValues("2.6"))
            targetCIArrayList.add(StaticValues("2.8"))
            targetCIArrayList.add(StaticValues("3.0"))
        }

        val targetCIListAdapter = TargetCIListAdapter(targetCIArrayList, this@CannulaFragment)
        listValues.adapter = targetCIListAdapter

        valuesDialog?.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onValueClick(value: StaticValues?, position: Int) {
        value?.name?.let { sharedViewModel.setEditTextCI(it) }
        if (binding.tvTitle.text == getString(R.string.pediatric_entry)) {
            val mTargetBloodFlow = convertStringToDouble(binding.etweight.text.toString())?.let {
                calTargetBloodFlowForPediatricEntry(
                    it, sharedViewModel.editTextCI.value?.toDouble()!!
                )
            }
            if (mTargetBloodFlow?.removeSuffix(" L/min")?.toDouble() != null) {
                sharedViewModel.setValueTargetBloodFlow(
                    mTargetBloodFlow?.removeSuffix(" L/min")?.toDouble()
                )
            } else {
                sharedViewModel.setValueTargetBloodFlow(null)
            }

            sharedViewModel.valueTargetBloodFlow.value?.let { Calculations.getVANeckForPed(it) }
                ?.let { generateVANeckList(it) }
            sharedViewModel.valueTargetBloodFlow.value?.let { Calculations.getVAGroinForPed(it) }
                ?.let { generateVAGroinList(it) }
            sharedViewModel.valueTargetBloodFlow.value?.let { Calculations.getVVDLForPed(it) }
                ?.let { generateVVDLList(it) }

        } else {
            val mTargetBloodFlow = calCardiacOutputWithCIAndBSA(
                sharedViewModel.editTextCI.value?.toDouble()!!, sharedViewModel.valueBSA.value!!
            )

            if (mTargetBloodFlow?.removeSuffix(" L/min")?.toDouble() != null) {
                sharedViewModel.setValueTargetBloodFlow(
                    mTargetBloodFlow?.removeSuffix(" L/min")?.toDouble()
                )
            } else {
                sharedViewModel.setValueTargetBloodFlow(null)
            }

            sharedViewModel.valueTargetBloodFlow.value?.let { Calculations.getVANeckForAud(it) }
                ?.let { generateVANeckList(it) }
            sharedViewModel.valueTargetBloodFlow.value?.let { Calculations.getVAGroinForAud(it) }
                ?.let { generateVAGroinList(it) }
            sharedViewModel.valueTargetBloodFlow.value?.let { Calculations.getVVDLForAud(it) }
                ?.let { generateVVDLList(it) }
        }
        targetBloodFlowListAdapter?.updateSelection(value?.value)
        valuesDialog?.dismiss()
    }
}