package com.example.searchaddress

import android.Manifest
import android.content.pm.PackageManager
import android.media.Image
import android.os.Bundle
import android.view.View
import android.widget.Space
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.SemanticsProperties.Text
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.searchaddress.ui.theme.SearchAddressTheme
import androidx.compose.material.Text
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.searchaddress.SearchAddressDialog

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchAddressTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting()
                }
            }
        }
    }

}

fun onDialogBtnClicked(view: View){

}

@Composable
fun Greeting() {
    var dialogState by remember{
        mutableStateOf(false)}
    Button(
        modifier = Modifier.padding(20.dp),
        onClick = { dialogState = true }) {
        Text(text = "Button")
    }
    SearchAddressDialog(dialogState = dialogState, onDissmissRequest = {
        dialogState = !it
    })
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Greeting()
}
/*
//  ?????? ??????
private val PERMISSION_CODE = 100

private fun requirePermissions(){
    val permissions=arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    )

    val isAllPermissionsGranted = permissions.all { //  permissions??? ?????? ?????? ??????
        ActivityCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }
    if (isAllPermissionsGranted) {    //  ?????? ????????? ???????????? ?????? ??????
        //permissionGranted()
    } else { //  ????????? ?????? ?????? ?????? ??????
        ActivityCompat.requestPermissions(requireActivity(), permissions, PERMISSION_CODE)
    }
}

// ?????? ?????? ????????? ??? ????????? ????????? ?????? ????????? ?????? ????????? argument??? ?????? ??? ??????
override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    if(requestCode == PERMISSION_CODE){
        if(grantResults.isNotEmpty()){
            for(grant in grantResults){
                if(grant == PackageManager.PERMISSION_GRANTED) {
                    /*no-op*/
                }else{
                    permissionDenied()
                    requirePermissions()
                }
            }
        }
    }
}

// ????????? ?????? ?????? ??????
private fun permissionDenied() {
    showToast("?????? ????????? ???????????????")

}*/