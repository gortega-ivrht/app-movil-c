package com.example.sesion01.pantallas

import android.graphics.Paint.Align
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sesion01.data.model.User
import com.example.sesion01.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaCursores(viewModel: UserViewModel){

    var nameFilter by remember { mutableStateOf("") }
    val users by  viewModel.userList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, top = 32.dp, end = 16.dp, bottom = 16.dp)
    ) {
        // campo de entrada para el filtro de nombre
        TextField(
            value = nameFilter,
            onValueChange = {nameFilter = it},
            label = { Text("Filtra por nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Botón para filtrar
        Button(
            onClick = { viewModel.filterUsersByName(nameFilter)},
            modifier = Modifier.align(Alignment.End)

        ) {
            Text("Buscar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier =  Modifier.fillMaxSize()) {
            items(users){
                user -> UserRow(user,
                                onDelete = {id -> viewModel.deleteUser(id)},
                                onUpdate = {id, name -> viewModel.updateUser(id,name)})
            }
        }


    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserRow(user: User,
            onDelete: (Long) -> Unit ,
            onUpdate: (Long,String) -> Unit) {

    var isEditing by remember { mutableStateOf(false) }
    var updateName by remember { mutableStateOf(user.name) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, Color.Gray)
    ) {
       Text(text = user.id.toString(),
           modifier = Modifier.weight(1f),
       fontSize = 10.sp)

        if(isEditing){
            TextField(
                value = updateName,
                onValueChange = { updateName = it },
                modifier = Modifier.weight(2f),
                textStyle = LocalTextStyle.current.copy(fontSize = 10.sp)
            )

            Button(
                onClick = { onUpdate(user.id,updateName)
                            isEditing = false // Salir del modo edición
                            },
                modifier = Modifier.padding(end = 4.dp),
                contentPadding = PaddingValues(1.dp)
            ) {
               Text("Guardar", fontSize = 10.sp)
            }

            Button(
                onClick = {
                        updateName = user.name
                        isEditing= false
                },
                modifier = Modifier.padding(start = 4.dp),
                contentPadding = PaddingValues(1.dp)
            ) {
                Text("Cancelar", fontSize = 10.sp)
            }

        }else{
            Text(text= user.name, modifier = Modifier.weight(2f))

            Button(
                onClick = {isEditing = true},
                modifier = Modifier.padding(start = 4.dp),
                contentPadding = PaddingValues(1.dp)
            ) {
                Text("Editar", fontSize = 10.sp)
            }
        }

        // sesion7
        Button(
            onClick = { onDelete(user.id) },
            modifier = Modifier.padding(start = 4.dp),
            contentPadding = PaddingValues(1.dp)
        ) {
            Text("Eliminar", fontSize = 10.sp)
        }

    }
}
