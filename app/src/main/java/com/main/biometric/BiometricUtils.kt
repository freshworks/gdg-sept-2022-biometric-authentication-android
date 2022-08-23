package com.main.biometric

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.main.biometric.interfaces.BiometricAuthListener
import timber.log.Timber

class BiometricUtils {
    private fun initBiometricPrompt(
        activity: AppCompatActivity,
        listener: BiometricAuthListener
    ): BiometricPrompt {
        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                listener.onBiometricAuthenticationError(errorCode, errString.toString())
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Timber.tag("BioMetricAuthentication")
                    .e("Authentication failed for an unknown reason")
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                listener.onBiometricAuthenticationSuccess(result)
            }
        }
        return BiometricPrompt(activity, ContextCompat.getMainExecutor(activity), callback)
    }

    fun showBiometricPrompt(
        @StringRes
        title: Int = R.string.unlock_fresh_works,
        showSubtitle: Boolean = true,
        activity: AppCompatActivity,
        listener: BiometricAuthListener,
        allowDeviceCredential: Boolean = true
    ) {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .apply {
                setTitle(activity.getString(title))
                setConfirmationRequired(false)
                if (allowDeviceCredential) {
                    setDeviceCredentialAllowed(allowDeviceCredential)
                } else {
                    setNegativeButtonText(activity.getString(R.string.cancel))
                }
                if (showSubtitle) {
                    setSubtitle((activity.getString(R.string.confirm_your_screen_lock)))
                }
            }.build()
        val biometricPrompt: BiometricPrompt = initBiometricPrompt(activity, listener)
        biometricPrompt.authenticate(promptInfo)
    }
}