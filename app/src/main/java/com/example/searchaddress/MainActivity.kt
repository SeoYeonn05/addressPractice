package com.example.searchaddress

import android.Manifest
import android.content.pm.PackageManager
import android.media.Image
import android.os.Bundle
import android.widget.Space
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

@Composable
private fun SearchAddressDialog(
    dialogState: Boolean,
    onDissmissRequest: (dialogState:Boolean) -> Unit
){
    if(dialogState){
        AlertDialog(
            backgroundColor = Color.DarkGray,
            onDismissRequest = {
                onDissmissRequest(dialogState)
            },
            title = null,
            text = null,
            buttons = {
                      DialogUI()

            },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
            shape = RoundedCornerShape(9.dp)
            )
    }
}

@Composable
fun DialogUI(){
    var userInput = remember { mutableStateOf(TextFieldValue())}

    Column {
        Spacer(
            modifier = Modifier
                .height(12.dp)
                .fillMaxWidth()
        )
        Text(
            "찾으시려는 동(읍/면/리)과 번지수or건물명을 정확하게 입력해 주세요.",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize()
                .padding(vertical = 8.dp),
            fontSize = 16.sp,
            lineHeight = 17.sp
        )
        Spacer(
            modifier = Modifier
                .height(12.dp)
                .fillMaxWidth()
        )
        Divider(color = Color.White, thickness = 0.8.dp,
            modifier = Modifier.padding(horizontal = 16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ){
            TextField()
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            ){
                Text("Enter")
            }
        }
    }
}

// sharedPreference 사용 필요
@Composable
fun TextField(){
    var textState by remember { mutableStateOf(TextFieldValue())}
    TextField(
        value = textState,
        onValueChange = {textValue -> textState = textValue},
        trailingIcon = {
            painterResource(id = R.drawable.ic_baseline_search_24)
        }
    )
}

@Composable
fun KotlinDialog(){
    Dialog(onDismissRequest = {}){
        Surface(
            modifier = Modifier
                .width(200.dp)
                .wrapContentHeight(),
            shape = RoundedCornerShape(12.dp),
            color = Color.White
        ) {
            DialogUI()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    var dialogState by remember{
        mutableStateOf(false)}
    SearchAddressTheme {
        SearchAddressDialog(dialogState = dialogState, onDissmissRequest = {
            dialogState = !it
        })
    }
}

//  권한 요청
private val PERMISSION_CODE = 100

private fun requirePermissions(){
    val permissions=arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    )

    val isAllPermissionsGranted = permissions.all { //  permissions의 모든 권한 체크
        ActivityCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }
    if (isAllPermissionsGranted) {    //  모든 권한이 허용되어 있을 경우
        //permissionGranted()
    } else { //  그렇지 않을 경우 권한 요청
        ActivityCompat.requestPermissions(requireActivity(), permissions, PERMISSION_CODE)
    }
}

// 권한 요청 완료시 이 함수를 호출해 권한 요청에 대한 결과를 argument로 받을 수 있음
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

// 권한이 없는 경우 실행
private fun permissionDenied() {
    showToast("위치 권한이 필요합니다")
}