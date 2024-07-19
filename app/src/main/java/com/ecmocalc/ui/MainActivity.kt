package com.ecmocalc.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ecmocalc.R
import com.ecmocalc.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // TODO: Launch splash screen activity before this to match the design.
        if (Build.VERSION.SDK_INT >= 31){
            installSplashScreen() //init new splash screen api for Android 12+
           // val splashScreen = installSplashScreen() //init new splash screen api for Android 12+
            //splashScreen.setKeepOnScreenCondition { true }
        } else{
            setTheme(R.style.Theme_ECMOCalc) //else use old approach
        }
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()

        setContentView(binding.root)

        window.navigationBarColor = ContextCompat.getColor(this, R.color.white)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        // Retain the first, second, and third fragments in memory
        binding.viewPager.offscreenPageLimit = 2 // To fix Focus issue with EditTexts in Fragments when using ViewPager2
        binding.viewPager.isUserInputEnabled = false // Disable swipe gesture
        binding.viewPager.adapter = MyFragmentStateAdapter(this)
        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.calculator -> {
                    binding.viewPager.setCurrentItem(0, true)
                }
                R.id.cannula -> {
                    binding.viewPager.setCurrentItem(1, true)
                }
                R.id.support -> {
                    binding.viewPager.setCurrentItem(2, true)
                }
            }
            true
        }

    }
}

// https://stackoverflow.com/questions/68711324/android-how-to-set-a-drawable-as-a-windowsplashscreenbackground-parameter-in-th/72464371#72464371
// https://github.com/ionic-team/capacitor-assets/issues/495
// https://developer.android.com/develop/ui/views/launch/splash-screen
// https://github.com/android/views-widgets-samples/issues/107