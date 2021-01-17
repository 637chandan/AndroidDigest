package com.chandan.androiddigest

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URI

class MainActivity : AppCompatActivity() {
    private val RESULT_LOAD_IMAGE = 1
    private var imgUri: Uri? = null
    private lateinit var pref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pref = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        requestPermission()
    }

    fun buttonOnClick(view: View) {
        Toast.makeText(this, "Button Clicked", Toast.LENGTH_LONG).show()
        Person.apply {
            this.name = editTextTextPersonName.text.toString()
            this.email = editTextTextEmailAddress.text.toString()
            this.pass = editTextTextPassword.text.toString()
            this.isMale = radioButton7.isChecked
            this.image = imgUri
        }
        Intent(this, SecondActivity::class.java).also {
            startActivity(it)
        }
    }

    fun onAddImageClick(view: View) {
        val intent = Intent().apply {
            this.action = Intent.ACTION_PICK
            this.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }.also {
            startActivityForResult(intent, RESULT_LOAD_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK) {
            val imageUri = data?.data
            imgUri = imageUri
            button2.visibility = View.GONE
            imageView.setImageURI(imageUri)
            imageView.visibility = View.VISIBLE

        }
    }

    private fun hasInternetPermission() = ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.INTERNET
    ) == PackageManager.PERMISSION_GRANTED

    private fun hasReadPermission() = ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.READ_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED

    private fun hasWritePermission() = ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED

    private fun hasLocationPermission() = ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    private fun hasLocationBackPermission() = ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_BACKGROUND_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    private fun requestPermission() {
        var permList = mutableListOf<String>()
        if (!hasInternetPermission()) permList.add(Manifest.permission.INTERNET)
        if (!hasReadPermission()) permList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        if (!hasWritePermission()) permList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (!hasLocationPermission()) permList.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        if (!hasLocationBackPermission()) permList.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        if (!permList.isEmpty()) ActivityCompat.requestPermissions(this, permList.toTypedArray(), 0)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0 && !grantResults.isEmpty()) {
            for (i in grantResults.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) Log.i(
                    "Chandan",
                    "Granted ${permissions[i]}"
                )
                else Log.i("Chandan", "Denied ${permissions[i]}")
            }
        }
    }

    fun createSaveAlertDialog(view: View) {
        AlertDialog.Builder(this)
            .setTitle("Alert Dialog")
            .setMessage("This is Alert Dialog")
            .setPositiveButton("Yes") { dialog: DialogInterface?, which: Int ->
                val edit = pref.edit()
                edit.apply{
                    putString("name", editTextTextPersonName.text.toString())
                    putString("email",editTextTextEmailAddress.text.toString())
                    putString("pass",editTextTextPassword.text.toString())
                    putBoolean("male",radioButton7.isChecked)
                    apply()
                }
                Toast.makeText(this, "Data Saved", Toast.LENGTH_LONG).show()
            }
            .setNegativeButton("No") { dialog: DialogInterface?, which: Int ->
                Toast.makeText(this, "denied", Toast.LENGTH_LONG).show()
            }.create().show()
    }

    fun createSingleChoiceDialog(view: View) {
        val options = arrayOf("First", "Second", "Third")
        AlertDialog.Builder(this)
            .setTitle("Alert Dialog Single Choice")
            .setSingleChoiceItems(options, 0) { dialog, which ->
                Toast.makeText(this, "You selected ${options[which]}", Toast.LENGTH_LONG).show()
            }
            .setPositiveButton("Yes") { dialog: DialogInterface?, which: Int ->
                Toast.makeText(this, "acceted", Toast.LENGTH_LONG).show()
            }
            .setNegativeButton("No") { dialog: DialogInterface?, which: Int ->
                Toast.makeText(this, "denied", Toast.LENGTH_LONG).show()
            }.create().show()

    }

    fun createMultiChoiceDialog(view: View) {
        val options = arrayOf("First", "Second", "Third")
        AlertDialog.Builder(this)
            .setTitle("Alert Dialog Multi Choice").setMultiChoiceItems(options, booleanArrayOf(false,false,false)) {dialog, which, isChecked ->
               if(isChecked) Toast.makeText(this, "You selected ${options[which]}", Toast.LENGTH_LONG).show()
                else  Toast.makeText(this, "You Unselected ${options[which]}", Toast.LENGTH_LONG).show()
            }
            .setPositiveButton("Yes") { dialog: DialogInterface?, which: Int ->
                Toast.makeText(this, "acceted", Toast.LENGTH_LONG).show()
            }
            .setNegativeButton("No") { dialog: DialogInterface?, which: Int ->
                Toast.makeText(this, "denied", Toast.LENGTH_LONG).show()
            }.create().show()
    }

    fun toFragmentActiivty(view: View) {
       Intent(this,FragmentActivity::class.java).also {
           startActivity(it)
       }
    }

    fun loadData(view: View) {
        var name = pref.getString("name", null) as CharSequence
        val email = pref.getString("email", null)
        val pass = pref.getString("pass", null)
        val isMale = pref.getBoolean("male", false)
        editTextTextPersonName.setText(name)
        editTextTextEmailAddress.setText(email)
        editTextTextPassword.setText(pass)
        if(isMale) radioButton7.isChecked = true
        else radioButton7.isChecked = true
    }
}