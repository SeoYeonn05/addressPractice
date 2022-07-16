package com.example.searchaddress

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.searchaddress.ui.theme.SearchAddressTheme
import retrofit2.Call
import retrofit2.Response
import java.lang.NullPointerException

class SearchAddressDialog : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchAddressTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                }
            }
        }
    }
}

@Composable
fun SearchAddressDialog(
    dialogState: Boolean,
    onDissmissRequest: (dialogState:Boolean) -> Unit
){
    if(dialogState){
        AlertDialog(
            backgroundColor = Color.White,
            onDismissRequest = {
                onDissmissRequest(dialogState)
            },
            title = null,
            text ={
                Text(
                "찾으시려는 동(읍/면/리)과 번지수or건물명을 정확하게 입력해 주세요.",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(),
                fontSize = 16.sp,
                lineHeight = 17.sp
            )},
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
    // var userInput = remember { mutableStateOf(TextFieldValue()) }

    Column {
        Spacer(
            modifier = Modifier
                .height(12.dp)
                .fillMaxWidth()
        )
        Divider(color = Color.DarkGray, thickness = 0.8.dp,
            modifier = Modifier.padding(horizontal = 16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ){
            TextField()
        }
        Spacer(
            modifier = Modifier
                .height(12.dp)
                .fillMaxWidth()
        )
    }
}

// sharedPreference 사용 필요
@Composable
fun TextField(){
    var textState by remember { mutableStateOf(TextFieldValue()) }
    TextField(
        modifier = Modifier.padding(20.dp),
        value = textState,
        onValueChange = {textValue -> textState = textValue},
        trailingIcon = {
            painterResource(id = R.drawable.ic_baseline_search_24)
        }
    )
    val address = textState.toString()
    val call = APiObject.retrofitService.getAddress(
        searchKeyword = address
    )

    call.enqueue(object: retrofit2.Callback<AddressDetail> {
        override fun onResponse(call: Call<AddressDetail>, response: Response<AddressDetail>) {
            if(response.isSuccessful){
                try {
                    val addressDetail: List<AddressDetail.HtReturnValue.Result.Zipcode> = response.body()!!.htReturnValue.result.zipcodes
                    Log.d("성공", "주소출력")

                    setListView(addressDetail)
                } catch (e: NullPointerException) {
                    Log.d("실패", e.message.toString())
                }
            }
            else{
                Log.d("실패", "에러")
            }
        }
        override fun onFailure(call: Call<AddressDetail>, t: Throwable){
            Log.d("실패", t.message.toString())
        }
    })
    Log.d("확인", "주소 검색 작동")
}

private fun setListView(addressDetail: List<AddressDetail.HtReturnValue.Result.Zipcode>){
    val placeAdapter = PlaceAdapter(requireContext(), addressDetail)

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    Surface() {
        var dialogState by remember{
            mutableStateOf(true)}

        SearchAddressDialog(dialogState = dialogState, onDissmissRequest = {
            dialogState = !it
        })
    }
}