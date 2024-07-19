package com.ecmocalc.ui.support

import android.Manifest
import android.app.AlertDialog
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ecmocalc.databinding.FragmentSupportBinding
import com.ecmocalc.ui.WebViewActivity
import com.ecmocalc.utils.Constants
import com.ecmocalc.utils.Helper.Companion.getNavigationMode

class SupportFragment : Fragment() {

    private var _binding: FragmentSupportBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSupportBinding.inflate(inflater, container, false)

        if (getNavigationMode(requireContext())) {
            binding.spacerView.visibility = View.GONE
        } else {
            binding.spacerView.visibility = View.VISIBLE
        }

        binding.btPhone.setOnClickListener {
            checkCallingPermission()
        }

        binding.btWebsite.setOnClickListener {
            startActivity(Intent(requireContext(), WebViewActivity::class.java).apply {
                putExtra(Constants.KEY_WEB_PAGE, "https://innovativeecmo.com/")
            })
        }

        binding.btEducation.setOnClickListener {
            startActivity(Intent(requireContext(), WebViewActivity::class.java).apply {
                putExtra(Constants.KEY_WEB_PAGE, "https://innovativeecmo.com/ecmo-education/")
            })
        }

        binding.btContact.setOnClickListener {
            startActivity(Intent(requireContext(), WebViewActivity::class.java).apply {
                putExtra(Constants.KEY_WEB_PAGE, "https://innovativeecmo.com/contact/")
            })
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        hideKeyboard()
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard()
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    private fun checkCallingPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the location directly
                makePhoneCall()
            }

            shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE) -> {
                // Explain to the user why you need the permission
                showDialog()
            }

            else -> {
                // Request the permission
                requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
            }
        }
    }

    private fun makePhoneCall() {
        val phoneNumber = "18008743266" // Replace with the phone number you want to dial
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:$phoneNumber")
        startActivity(callIntent)
    }

    /**
     *  Alert dialog to show reason
     *  for asking location permission
     */
    private fun showDialog() {
        AlertDialog.Builder(activity)
            .setMessage("App require calling permission to make call.")
            .setCancelable(false)
            .setPositiveButton(
                "Ok"
            ) { dialog, id -> //put your code that needed to be executed when okay is clicked
                dialog.cancel()
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
            .setNegativeButton(
                "Cancel"
            ) { dialog, id ->
                dialog.cancel()
                //activity?.finish()
            }

            .create()
            .show()
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            makePhoneCall()
        } else {
            // Permission is denied
            //activity?.finish()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}