package com.example.composememoapp.list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
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
import androidx.lifecycle.coroutineScope
import com.example.composememoapp.R
import com.example.composememoapp.ViewModelFactory
import com.example.composememoapp.data.Memo
import com.example.composememoapp.data.source.MemoDataSource
import com.example.composememoapp.data.source.MemoRepository
import com.example.composememoapp.data.source.local.MemoDatabase
import com.example.composememoapp.data.source.local.MemoLocalDataSource
import com.example.composememoapp.ui.theme.ComposeMemoAppTheme
import kotlinx.coroutines.launch

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

            memoList(viewModel)
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
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(70.dp)) {
        var textState by remember { mutableStateOf("") }
        OutlinedTextField(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxHeight(),
            singleLine = true,
            value = textState,
            onValueChange = { textState = it },
            label = { Text("입력창") },
            placeholder = { Text("메모를 추가하세요!") }
        )
        OutlinedButton(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxHeight(),
            onClick = {viewModel.addMemo(Memo(textState, false)) }
        ) {
            Text(text = "Add")
        }
    }

}

@Composable
fun memoList(viewModel : ListViewModel){
    val memoList : List<Memo> by viewModel.memoItems.collectAsState(listOf())
    LazyColumn(){
        item{
            memoList.forEach{
                listItem(it)
            }
        }
    }
}

@Composable()
fun listItem(memo : Memo){
    Card(modifier = Modifier
        .padding(20.dp)
        .fillMaxWidth(),
        elevation = 10.dp,
        shape = RoundedCornerShape(12.dp)
        ) {
        Row(Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
            ) {
            if(memo.lock){
                Icon(painterResource(id = R.drawable.ic_lock), contentDescription = "lock")
            }
            else{
                Icon(painterResource(id = R.drawable.ic_unlock), contentDescription = "unlock")
            }
            Text(
                text = memo.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 10.dp)
            )

        }
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