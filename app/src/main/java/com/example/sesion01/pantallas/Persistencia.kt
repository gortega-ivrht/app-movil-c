package com.example.sesion01.pantallas

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.sesion01.CursoresListaActivity
import com.example.sesion01.viewmodel.UserViewModel


@Composable
fun Persistencia (userViewModel: UserViewModel){
    val contex = LocalContext.current
    var name by remember { mutableStateOf("USUARIO") }
    val userList by userViewModel.userList.collectAsState()

    LaunchedEffect(Unit) {
        userViewModel.loadUsers()
    }

    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        BasicTextField(
            value = name,
            onValueChange = {name = it},
            modifier = Modifier.padding(16.dp)
        )

        Button(onClick = {
            if (name.isNotBlank()){
                userViewModel.insertUser(name)
                name=""
            }
        }) {
            Text("Agregar Usuario")
        }

        Spacer(modifier = Modifier.height(16.dp))

        /* LazyColumn {
            items(userList){
                user -> Text(user.name)
            }
        }*/

        //sesion6
        OutlinedButton(onClick = {
            Log.d("Persistencia","Click en el botón lista")
            val intent = Intent(
                contex,
                CursoresListaActivity::class.java
            )
            contex.startActivity(intent)
        }) {
            Text(text = "Ver Listar", style = MaterialTheme.typography.bodyLarge)
        }
    }
}