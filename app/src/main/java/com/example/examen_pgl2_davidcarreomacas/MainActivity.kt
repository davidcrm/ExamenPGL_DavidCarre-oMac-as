package com.example.examen_pgl2_davidcarreomacas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.examen_pgl2_davidcarreomacas.ui.theme.Examen_PGL2_DavidCarreñoMacíasTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //Variable para nombre del alumno
        val nombre = "David"
        // Variable para avatar
        val avatar = "avatar3.png"



        setContent {
            Examen_PGL2_DavidCarreñoMacíasTheme {
                Scaffold(
                        topBar = {
                            //Crea una topBar y un título para la app
                            CenterAlignedTopAppBar(
                                title = { Text("Conversación $nombre")},
                                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                )
                                )
                        },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)){
                        Column {
                            Row {
                                Ficha(nombre = nombre)
                            }
                            MainButton(text = "Elegir nuevo color") {
                            }
                            Conversacion(msg = Mensajes.conversationSample)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MensajeCompleto(msg: Message){
    Row (modifier = Modifier.padding(all= 8.dp)){
        Image(
            painter = painterResource(id = R.drawable.profesor),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, color = MaterialTheme.colorScheme.primary, CircleShape)
        )
        EspacioHorizontal(alto = 8)
        //variable que recuerda si el mensaje está expandido o no
        var isExpanded by remember { mutableStateOf(false) }
        //variable para cambiar de color el mensaje según su estado
        val surfaceColor by animateColorAsState(
            if (isExpanded) Color.Magenta else Color.LightGray
        )
        Column (modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = msg.author,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall
            )
            //Añade espacio vertical entre los mensajes
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                color = surfaceColor,
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)){
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    // Si está expandido muestra el contenido y si no, mustra la primera línea
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun Ficha(nombre: String){
    Row {
        Box(modifier = Modifier
            .border(2.dp, Color.Gray, shape = RectangleShape)
            .padding(all = 5.dp)
            .shadow(2.dp))
        {
            Row (modifier = Modifier.fillMaxWidth()){
                Image(painter = painterResource(
                    id = R.drawable.avatar3),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, color = MaterialTheme.colorScheme.primary, CircleShape)
                )
                Column {
                    Text(text = "Alumno: $nombre",
                        modifier = Modifier
                    )
                    EspacioHorizontal(alto = 10)
                    Text(text = "Soy un alumno",
                        modifier = Modifier
                    )
                }
            }
        }
    }
}

@Composable
fun MainButton(
    text: String,
    onClick: () -> Unit
) {
    var colorBoton by remember { mutableStateOf(Color.Magenta)}

    val listaColores= listOf(Color.Green, Color.Blue, Color.Red, Color.Cyan)


    OutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.primary,
            containerColor = colorBoton
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
    ) {
        Text(text = text)
    }
}
fun generarColor(lista: List<Color>): List<Color> {
    return lista.shuffled().take(1)
}

@Composable
fun EspacioHorizontal(alto: Int){
    Spacer(modifier = Modifier.height(alto.dp))
}

@Composable
fun Conversacion(msg: List<Message>){
    LazyColumn {
        items(msg){ message ->
            MensajeCompleto(msg = message)
        }
    }
}