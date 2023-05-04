package com.example.android2homework5.ui.activity

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import com.example.android2homework5.databinding.ActivityMainBinding
import com.example.android2homework5.extensions.hasPermissionCheckAndRequest

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var uri: Uri? = null

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { isGranted ->
        for (permission in isGranted) {
            when {
                permission.value -> fileChooserContract.launch("image/*")
                !shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE) -> {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpPermission()
    }

    private fun setUpPermission() {
        binding.galleryButton.setOnClickListener {
            if (hasPermissionCheckAndRequest(
                    requestPermissionLauncher,
                    arrayOf(READ_EXTERNAL_STORAGE)
                )
            ) {
                fileChooserContract.launch("image/*")
            }
        }
    }
    private val fileChooserContract =
        registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri ->
            if (imageUri != null) {
                binding.imageView.setImageURI(imageUri)
                uri = imageUri
            }
        }
}


