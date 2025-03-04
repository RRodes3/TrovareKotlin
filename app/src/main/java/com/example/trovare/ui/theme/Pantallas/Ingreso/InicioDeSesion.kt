package com.example.trovare.ui.theme.Pantallas.Ingreso

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Mail
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.trovare.ui.theme.Navegacion.Pantalla
import com.example.trovare.R
import com.example.trovare.ui.theme.Recursos.BarraSuperior
import com.example.trovare.ui.theme.Trv1
import com.example.trovare.ui.theme.Trv6
import com.example.trovare.ui.theme.Trv8
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import android.util.Log
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import android.util.Patterns
import com.google.firebase.firestore.FirebaseFirestore
import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.ui.platform.LocalContext
import com.example.trovare.ViewModel.TrovareViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InicioDeSesion(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: TrovareViewModel
){
    // Declaramos variables
    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()

    var textoCorreo by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("", TextRange(0, 7)))
    }
    var textoPasswrod by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("", TextRange(0, 7)))
    }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var passwordOculta by rememberSaveable { mutableStateOf(true) }

    val keyboardOptionsCorreo: KeyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email, imeAction = ImeAction.Done)
    val keyboardOptionsPassword: KeyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done)

    val context = LocalContext.current

    Scaffold(
        topBar = {
            BarraSuperior(navController = navController)
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState)
        },
    ) {
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(it),
            color = Trv1
        ) {
            Card(
                modifier = modifier
                    .padding(25.dp),
                colors = CardDefaults.cardColors(Trv8)
            ) {
                Column {
                    Text(
                        modifier = modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        text = "INICIAR SESIÓN",
                        style = MaterialTheme.typography.displayMedium
                    )
                    Spacer(modifier = modifier.fillMaxHeight(0.03f))
                    //Logo Trovare----------------------------------------------------------------------
                    Image(
                        modifier = modifier
                            .fillMaxHeight(0.2f)
                            .fillMaxSize(),
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = ""
                    )
                    Spacer(modifier = modifier.height(15.dp))

                    //Correo----------------------------------------------------------------------------
                    OutlinedTextField(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(start = 25.dp, end = 25.dp, bottom = 15.dp),
                        value = textoCorreo,
                        onValueChange = { textoCorreo = it },
                        leadingIcon = {Icon(imageVector = Icons.Rounded.Mail, contentDescription = "")},
                        label = {
                            Text(
                                text = "Correo",
                                style = MaterialTheme.typography.labelSmall
                            )
                        },
                        textStyle = MaterialTheme.typography.labelSmall,
                        placeholder = {
                            Text(
                                text = "Ejemplo@mail.com",
                                style = MaterialTheme.typography.labelSmall
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.White,
                            focusedLabelColor = Color.White,
                            unfocusedLabelColor = Color.White,
                            containerColor = Trv8,
                            cursorColor = Color.White,
                            focusedIndicatorColor = Color.White,
                            unfocusedIndicatorColor = Color.White
                        ),
                        singleLine = true,
                        keyboardOptions = keyboardOptionsCorreo,
                    )
                    //Contraseña------------------------------------------------------------------------
                    OutlinedTextField(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(start = 25.dp, end = 25.dp, bottom = 15.dp),
                        value = textoPasswrod,
                        onValueChange = { textoPasswrod = it },
                        trailingIcon = {
                            IconButton(onClick = { passwordOculta = !passwordOculta }) {
                                val visibilityIcon = if (passwordOculta) Icons.Rounded.Visibility else Icons.Rounded.VisibilityOff
                                // Please provide localized description for accessibility services
                                val description = if (passwordOculta) "Mostrar contraseña" else "Ocultar contraseña"
                                Icon(imageVector = visibilityIcon, contentDescription = description)
                            }
                        },
                        visualTransformation = if (passwordOculta) PasswordVisualTransformation() else VisualTransformation.None,
                        label = {
                            Text(
                                text = "Contraseña",
                                style = MaterialTheme.typography.labelSmall
                            )
                        },
                        textStyle = MaterialTheme.typography.labelSmall,
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.White,
                            focusedLabelColor = Color.White,
                            unfocusedLabelColor = Color.White,
                            containerColor = Trv8,
                            cursorColor = Color.White,
                            focusedIndicatorColor = Color.White,
                            unfocusedIndicatorColor = Color.White
                        ),
                        singleLine = true,
                        keyboardOptions = keyboardOptionsPassword,
                    )
                    //Recuperar contrasena----------------------------------------------------------
                    Text(
                        modifier = modifier
                            .padding(horizontal = 25.dp)
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(Pantalla.RecuperarContrasena.ruta)
                            },
                        text = "Recuperar contraseña",
                        textAlign = TextAlign.Right,
                        style = MaterialTheme.typography.bodySmall,
                        textDecoration = TextDecoration.Underline,
                        color = Color.White
                    )
                    Spacer(modifier = modifier.fillMaxHeight(0.65f))
                    //
                    Text(
                        text = "¿No tienes cuenta? Regístrate",
                        modifier = modifier
                            .padding(horizontal = 25.dp, vertical = 10.dp)
                            .fillMaxWidth()
                            .clickable {
                                //navegar a Crear cuenta
                                navController.navigate(Pantalla.Registro.ruta)
                            },
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodySmall,
                        textDecoration = TextDecoration.Underline,
                        color = Color.White
                    )
                    TextButton(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(start = 25.dp, end = 25.dp, bottom = 10.dp),
                        onClick = {

                            if(textoCorreo.text.isBlank() || textoPasswrod.text.isBlank()){
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "Campos obligatorios no completados",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }else if(!Patterns.EMAIL_ADDRESS.matcher(textoCorreo.text).matches()){
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "El correo no tiene una estructura válida",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }else if (!isNetworkAvailable(context)) {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "Error de conexión",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            } else {
                                try{
                                    // Verificamos si la cuenta es de administrador
                                    db.collection("Administrador").whereEqualTo("correo", textoCorreo.text)
                                        .whereEqualTo("password", textoPasswrod.text)
                                        .get()
                                        .addOnSuccessListener { querySnapshot ->
                                            if(!querySnapshot.isEmpty){

                                                Log.d("Trovare","Ingreso usuario administrador")
                                                navController.navigate(Pantalla.Administrador.ruta){
                                                    popUpTo(navController.graph.id){
                                                        inclusive = true
                                                    }
                                                }
                                            }else{
                                                // Verificamos si la cuenta es de usuario
                                                val user = FirebaseAuth.getInstance().currentUser

                                                auth.signInWithEmailAndPassword(textoCorreo.text, textoPasswrod.text)
                                                    .addOnCompleteListener{ task ->

                                                        if(task.isSuccessful){
                                                            if (user?.isEmailVerified == false){
                                                                scope.launch {
                                                                    snackbarHostState.showSnackbar(
                                                                        message = "Tu cuenta aún no está verificada, abre el link que enviamos a tu correo electrónico.",
                                                                        duration = SnackbarDuration.Short
                                                                    )
                                                                }
                                                            }else{
                                                                scope.launch {
                                                                    snackbarHostState.showSnackbar(
                                                                        message = "Bienvenido...",
                                                                        duration = SnackbarDuration.Short
                                                                    )
                                                                    }
                                                                    navController.navigate(Pantalla.NavegacionSecundaria.ruta){
                                                                        popUpTo(navController.graph.id){
                                                                            inclusive = true
                                                                        }
                                                                }
                                                                viewModel.obtenerDato()
                                                            }
                                                        }else{
                                                            scope.launch {
                                                                snackbarHostState.showSnackbar(
                                                                    message = "Correo o contraseña incorrectos",
                                                                    duration = SnackbarDuration.Short
                                                                )
                                                            }
                                                        }
                                                    }
                                            }
                                        }
                                        .addOnFailureListener{
                                            Log.d("Trovare","Error al conectar con la base")
                                        }
                                }catch(ex:Exception){
                                    Log.d("Login", "Error en la conexión de la base de datos")
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Trv6,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "Ingresar")
                    }
                }
            }
        }
    }
}

@SuppressLint("ServiceCast")
private fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val network = connectivityManager.activeNetwork ?: return false
    val capabilities =
        connectivityManager.getNetworkCapabilities(network) ?: return false

    return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
}