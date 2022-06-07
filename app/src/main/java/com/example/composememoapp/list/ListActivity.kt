package com.example.composememoapp.list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composememoapp.R
import com.example.composememoapp.data.Memo
import com.example.composememoapp.ui.theme.ComposeMemoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeMemoAppTheme {
                ListComposable()
            }
        }
    }
}


@Composable()
fun ListComposable(){
    Surface(modifier = Modifier.background(Color.White)){
        
        title()
        
       // memoTextField()
        
       // memoList()
    }
}

@Composable
fun title(){
    Row(modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ){
        Icon(painter = painterResource(id = R.drawable.ic_checkbox), contentDescription = "Check Box")
        Text()
    }
}


@Composable()
fun listItem(memo : Memo){
    Row(){

    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeMemoAppTheme {
        ListComposable()
    }
}