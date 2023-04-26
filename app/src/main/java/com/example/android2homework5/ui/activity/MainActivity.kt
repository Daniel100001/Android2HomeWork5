package com.example.android2homework5.ui.activity

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import com.example.android2homework5.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val permissionMedia = "image/*"
    private val pickImage = 100

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()){
        binding.imageView.setImageURI(it)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpPermission()
    }

    private fun setUpPermission() {
        binding.galleryButton.setOnClickListener {
            when {
                checkSelfPermission(READ_EXTERNAL_STORAGE) == PERMISSION_GRANTED -> {
                    getContent.launch(permissionMedia)
                }
                shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE) -> {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)

                }
                else -> {
                    requestPermissions(
                        arrayOf(READ_EXTERNAL_STORAGE),
                        pickImage
                    )
                }
            }
        }
    }

}

