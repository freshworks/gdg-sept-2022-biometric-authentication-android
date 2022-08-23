package com.main.biometric.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.view.isVisible
import com.main.biometric.interfaces.BiometricAuthListener
import com.main.biometric.BiometricUtils
import com.main.biometric.FreshWorksApplication
import com.main.biometric.databinding.ActivityAppLockBinding

class AppLockActivity : AppCompatActivity(), BiometricAuthListener {

    companion object {
        fun start(
            context: Context,
        ) {
            val starter: Intent =
                Intent(context, AppLockActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            context.startActivity(starter)
        }
    }

    private lateinit var binding: ActivityAppLockBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FreshWorksApplication[this].getAppComponent().inject(this)
        binding = ActivityAppLockBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            tryAgain.isVisible = false
            lockDescription.isVisible = false
        }
        showPrompt()
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }

    override fun onBiometricAuthenticationSuccess(result: BiometricPrompt.AuthenticationResult) {
        finish()
    }

    override fun onBiometricAuthenticationError(errorCode: Int, errString: CharSequence) {
        binding.apply {
            tryAgain.apply {
                isVisible = true
                setOnClickListener {
                    showPrompt()
                }
            }
            lockDescription.isVisible = true
        }
    }

    private fun showPrompt() {
        BiometricUtils().showBiometricPrompt(
            activity = this,
            listener = this
        )
    }
}