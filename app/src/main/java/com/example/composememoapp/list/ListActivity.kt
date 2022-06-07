package com.example.composememoapp.list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composememoapp.R
import com.example.composememoapp.ViewModelFactory
import com.example.composememoapp.data.Memo
import com.example.composememoapp.data.source.MemoDataSource
import com.example.composememoapp.data.source.MemoRepository
import com.example.composememoapp.data.source.local.MemoDatabase
import com.example.composememoapp.data.source.local.MemoLocalDataSource
import com.example.composememoapp.ui.theme.ComposeMemoAppTheme

class ListActivity : ComponentActivity() {

    private val database by lazy {
        MemoDatabase.getDatabase(this)
    }
    private val repository by lazy {
        MemoRepository(MemoLocalDataSource(database.memoDao()))
    }

    private val viewModel: ListViewModel by viewModels {
        ViewModelFactory(repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeMemoAppTheme {
                ListComposable(viewModel)
            }
        }
    }
}


@Composable()
fun ListComposable(viewModel : ListViewModel){
    Surface(modifier = Modifier.background(Color.White)){
        Column {
            titleField()

            memoTextField(viewModel)

            memoList()
        }
    }
}

@Composable
fun titleField(){
    Row(modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ){
        Icon(painter = painterResource(id = R.drawable.ic_checkbox), contentDescription = "Check Box",modifier = Modifier.size(50.dp))
        Text(
            text = stringResource(id = R.string.title_text),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier= Modifier
                .padding(horizontal = 10.dp)
                .padding(top = 10.dp)
            )
    }
}

@Composable
fun memoTextField(viewModel : ListViewModel){
    Row(modifier = Modifier.fillMaxWidth().height(70.dp)) {
        var textState by remember { mutableStateOf("") }
        val clicked = {
            viewModel.addMemo(Memo(textState, false))
            textState = ""
        }
        OutlinedTextField(
            modifier = Modifier.padding(horizontal = 10.dp).fillMaxHeight(),
            singleLine = true,
            value = textState,
            onValueChange = { textState = it },
            label = { Text("입력창") },
            placeholder = { Text("메모를 추가하세요!") }
        )
        OutlinedButton(
            modifier = Modifier.padding(10.dp).fillMaxHeight(),
            onClick = { clicked }
        ) {
            Text(text = "Add")
        }
    }

}

@Composable
fun memoList(){
    LazyColumn(){

    }
}

@Composable()
fun listItem(memo : Memo){
    Row(){
        Text(
            text = memo.title,
            overflow = TextOverflow.Ellipsis,

            )
    }
}


/*
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeMemoAppTheme {
        ListComposable(viewModel)
    }
}

 */