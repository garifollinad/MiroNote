package com.example.mironote.ui.info

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.example.mironote.R
import com.example.mironote.di.Injectable
import com.example.mironote.ui.main.MainFragment
import com.example.mironote.ui.menu.MenuActivity
import com.example.mironote.utils.Constants
import com.example.mironote.utils.PrefUtils
import com.example.mironote.utils.Screen
import javax.inject.Inject

class InfoFragment : Fragment(), Injectable {

    private lateinit var codeScanner: CodeScanner

    companion object {
        fun newInstance(data: Bundle? = null): InfoFragment =
            InfoFragment().apply {
                arguments = data
            }
    }

    @Inject
    lateinit var prefUtils: PrefUtils

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        val scannerView = view.findViewById<CodeScannerView>(R.id.scanner_view)
        val activity = requireActivity()
        codeScanner = CodeScanner(activity, scannerView)
        askPermission()
        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    private fun openCamera() {
        codeScanner.decodeCallback = DecodeCallback {
            activity?.runOnUiThread {
                if (it.text.isNotEmpty()) {
                    prefUtils.saveData(PrefUtils.TOKEN, it.text)
                    (requireActivity() as MenuActivity).navigateTo(
                        fragment = MainFragment.newInstance(),
                        tag = Screen.MAIN_PAGE.name,
                        addToStack = true
                    )
                }
            }
        }
    }

    private fun askPermission() {
        val cameraGranted = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        if (cameraGranted) {
            openCamera()
        } else {
            requestPermissions(
                arrayOf(
                    Manifest.permission.CAMERA
                ),
                Constants.CAMERA_PERMISSION_REQUEST
            )
        }
    }

    private fun bindViews(view: View) = with(view) {

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.CAMERA_PERMISSION_REQUEST) {
            if (grantResults.isNotEmpty()) {
                val cameraGranted = ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED

                if (cameraGranted) {
                    openCamera()
                }
            }
            return
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

}