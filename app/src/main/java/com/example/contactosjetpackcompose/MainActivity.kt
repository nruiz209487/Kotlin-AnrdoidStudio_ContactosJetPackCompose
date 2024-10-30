package com.example.contactosjetpackcompose

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.activivdadlistacontactos.Contacto
import com.example.contactosjetpackcompose.ui.theme.ContactosJetPackComposeTheme
import androidx.compose.material3.Text as Text1

/**
 * MAIN
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContactosJetPackComposeTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "item_list") {
                    composable("item_list") {
                        ItemListScreen()
                    }
                }
            }
        }
    }
}

/**
 * SE ENCARGA DE ALMECENAR EN UNA LISTA LOS ITEMS DE CLASE CONTACTO
 */
@Composable
fun ItemListScreen() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        ItemList(
            //LISTA DE CONTACTOS
            itemContacto = listOf(
                Contacto("Juan Pérez", "+34 654 321 987", 0),
                Contacto("María Gómez", "+34 612 345 678", 1),
                Contacto("Carlos Rodríguez", "+34 675 890 123", 0),
                Contacto("Ana Martínez", "+34 689 432 156", 1),
                Contacto("Luis Fernández", "+34 623 567 234", 0),
                Contacto("Elena Sánchez", "+34 698 765 432", 1),
                Contacto("Miguel López", "+34 671 234 567", 0),
                Contacto("Sofía Ramírez", "+34 689 876 543", 1),
                Contacto("Pedro Castillo", "+34 622 987 654", 0),
                Contacto("Laura Jiménez", "+34 611 234 890", 1)
            ),
            modifier = Modifier.padding(innerPadding),

        )
    }
}

/**
 * SE ENCARGA DE RECCORER LA LISTA CONTATCOS Y DE LAMAR A LA FUNCION ContactoView
 */
@Composable
fun ItemList(itemContacto: List<Contacto>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        items(itemContacto) { contacto ->
            ContactoView(contacto = contacto)
        }
    }
}

/**
 * VISTA DE LOS CONTATCOS RECIBE COMO PARAMETRO UN COMTACTATO
 */
@Composable
fun ContactoView(contacto: Contacto, ) {
    val context = LocalContext.current
    //VARIABLE  QUE INDICA SI EL BOTON A SIDO PULADO O NO
    val mostrarContactoCompleto = remember { mutableStateOf(false) }

    Card(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            val image: Painter = if (contacto.image == 1) {
                painterResource(id = R.drawable.mujer)
            } else {
                painterResource(id = R.drawable.hombre)
            }
            //IMAGEN
            Image(
                painter = image,
                contentDescription = "Foto contacto",
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))
//NOMBRE Y NUMERO
            Text1(
                //si el boton esta a true muestra los datos si sta false llama a la funcion que muestra el nombre recortado
                text = if (mostrarContactoCompleto.value) "${contacto.nombre} - ${contacto.tfno}" else obtenerIniciales(contacto.nombre),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:${contacto.tfno}")
                        }
                        context.startActivity(intent)
                    }
            )

            Spacer(modifier = Modifier.height(8.dp))
//BOTON
            Button(
                onClick = {
                    mostrarContactoCompleto.value = !mostrarContactoCompleto.value
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(Color.LightGray)
            ) {
                Text1(
                    text = if (mostrarContactoCompleto.value) "Ocultar" else "Mostrar Contacto",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


/**
 * Funciom que coje las iniciales del nombre recibe como parametro el parametro nombre de contatco
 */
fun obtenerIniciales(nombre: String): String {
    return nombre
        .split(" ")
        .mapNotNull { it.firstOrNull()?.uppercaseChar() }
        .joinToString(".")
}


