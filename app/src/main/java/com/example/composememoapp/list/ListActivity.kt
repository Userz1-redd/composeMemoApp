package com.example.composememoapp.list

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
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
        val context = LocalContext.current
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
            onClick = {
                viewModel.addMemo(Memo(textState, false))
                textState = ""
                Toast.makeText(context,"메모를 추가했습니다",Toast.LENGTH_LONG)
            }
        ) {
            Text(text = "Add")
        }
    }

}

@Composable
fun memoList(viewModel : ListViewModel){
    val memoList : List<Memo> by viewModel.memoItems.observeAsState(listOf())
    LazyColumn(){
        item{
            memoList.forEach{
                listItem(viewModel,it)
            }
        }
    }
}

@Composable()
fun listItem(viewModel : ListViewModel,memo : Memo){
    var dialogState by remember{mutableStateOf(false)}
    Card(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .clickable(onClick = {dialogState = true}),
        elevation = 10.dp,
        shape = RoundedCornerShape(12.dp),
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
                text = if(memo.lock) stringResource(id = R.string.lock_text) else memo.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 10.dp)
            )

        }
    }
    var dismissDialog = {
        dialogState = !dialogState
    }
    if(dialogState){
        Dialog(onDismissRequest = dismissDialog) {
            Surface(modifier = Modifier.width(200.dp)
                .wrapContentHeight(),
                shape = RoundedCornerShape(12.dp),
                color = Color.White
            ) {
                memoDialogContent(dismissDialog,viewModel,memo = memo)
            }
        }
    }
}

@Composable()
fun memoDialog(onDismissDialog : () -> Unit,
               viewModel: ListViewModel,
               memo : Memo){
    Dialog(onDismissRequest = onDismissDialog) {
        Surface(modifier = Modifier.width(200.dp)
            .wrapContentHeight(),
            shape = RoundedCornerShape(12.dp),
            color = Color.White
            ) {
            memoDialogContent(onDismissDialog,viewModel,memo = memo)
        }
    }
}

@Composable()
fun memoDialogContent(onDismissDialog: () -> Unit,viewModel : ListViewModel,memo : Memo){
    Column(Modifier.padding(12.dp)){
        var textState by remember{mutableStateOf(memo.title)}
        var lockState by remember{mutableStateOf(memo.lock)}
        var passwordState by remember{mutableStateOf("")}
        OutlinedTextField(modifier = Modifier
            .padding(horizontal = 10.dp),
            singleLine = true,
            value = textState,
            onValueChange = { textState = it },
            label = { Text("현재 메모") })
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(12.dp))
        Row() {
            Checkbox(checked = lockState, onCheckedChange = { lockState = it })
            Text(text ="Lock", modifier = Modifier.padding(top = 10.dp))
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(12.dp))
        OutlinedTextField(
            modifier = Modifier
                .padding(horizontal = 10.dp),
            singleLine = true,
            value = passwordState,
            onValueChange = { passwordState= it },
            label = { Text("Password") },
            placeholder = { Text("Password") }
        )
        OutlinedButton(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            onClick = {
                memo.title = textState
                memo.lock = lockState
                viewModel.modifyMemo(memo)
                onDismissDialog
            }
        ) {
            Text(text = "Modify")
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