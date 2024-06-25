package com.ecmocalc.ui

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.ecmocalc.R
import com.ecmocalc.databinding.ActivityMainBinding
import com.ecmocalc.ui.calculator.CalculatorFragment
import com.ecmocalc.ui.cannula.CannulaFragment
import com.ecmocalc.ui.support.SupportFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val calculatorFragment = CalculatorFragment()
    private val cannulaFragment = CannulaFragment()
    private val supportFragment = SupportFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        // TODO: Launch splash screen activity before this to match the design.
        if (Build.VERSION.SDK_INT >= 31){
            installSplashScreen() //init new splash screen api for Android 12+
        } else{
            setTheme(R.style.Theme_ECMOCalc) //else use old approach
        }
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()

        setContentView(binding.root)
        //splashScreen.setKeepOnScreenCondition { false }
        window.navigationBarColor = ContextCompat.getColor(this, R.color.white)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        replaceFragment(calculatorFragment)

        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.calculator -> {
                    replaceFragment(calculatorFragment)
                }
                R.id.cannula -> {
                    replaceFragment(cannulaFragment)
                }
                R.id.support -> {
                    replaceFragment(supportFragment)
                }
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }


}

// https://stackoverflow.com/questions/68711324/android-how-to-set-a-drawable-as-a-windowsplashscreenbackground-parameter-in-th/72464371#72464371
// https://github.com/ionic-team/capacitor-assets/issues/495
// https://developer.android.com/develop/ui/views/launch/splash-screen