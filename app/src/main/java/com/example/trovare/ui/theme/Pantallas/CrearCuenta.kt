package com.example.trovare.ui.theme.Pantallas

import android.content.Intent
import android.content.Intent.getIntent
import android.util.Log
import android.util.Patterns
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
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
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import com.example.trovare.Pantalla
import com.example.trovare.R
import com.example.trovare.ui.theme.Data.Usuario
import com.example.trovare.ui.theme.Recursos.BarraSuperior
import com.example.trovare.ui.theme.Trv1
import com.example.trovare.ui.theme.Trv6
import com.example.trovare.ui.theme.Trv8
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearCuenta(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val auth = FirebaseAuth.getInstance()

    val firestore = Firebase.firestore

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var textoNombre by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("", TextRange(0, 7)))
    }
    var textoApellido by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("", TextRange(0, 7)))
    }
    var textoCorreo by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("", TextRange(0, 7)))
    }
    var textoPassword by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("", TextRange(0, 7)))
    }

    var passwordOculta by rememberSaveable { mutableStateOf(true) }
    var aceptarTyC by rememberSaveable { mutableStateOf(false) }
    var cuentaCreada by remember { mutableStateOf(false) }

    val keyboardOptionsTexto: KeyboardOptions =
        KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done)
    val keyboardOptionsCorreo: KeyboardOptions =
        KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email, imeAction = ImeAction.Done)
    val keyboardOptionsPassword: KeyboardOptions = KeyboardOptions.Default.copy(
        keyboardType = KeyboardType.Password,
        imeAction = ImeAction.Done
    )

    var isError by rememberSaveable { mutableStateOf(false) }
    val charLimit = 8

    fun validate(text: String) {
        Log.i("Erro tamaño",text.length.toString())
        isError = text.length < charLimit
    }
    Scaffold(
        topBar = {
            BarraSuperior(navController = navController)
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
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
                        text = "CREAR CUENTA",
                        style = MaterialTheme.typography.displayMedium
                    )
                    Icon(
                        modifier = modifier
                            .align(Alignment.CenterHorizontally)
                            .size(100.dp),
                        imageVector = Icons.Rounded.Person,
                        contentDescription = "",
                        tint = Color.White
                    )
                    //Nombre----------------------------------------------------------------------------
                    OutlinedTextField(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(start = 25.dp, end = 25.dp, bottom = 15.dp),
                        value = textoNombre,
                        onValueChange = { textoNombre = it },
                        label = {
                            Text(
                                text = "Nombre(s)",
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
                        keyboardOptions = keyboardOptionsTexto,
                    )
                    //Apellido--------------------------------------------------------------------------
                    OutlinedTextField(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(start = 25.dp, end = 25.dp, bottom = 15.dp),
                        value = textoApellido,
                        onValueChange = { textoApellido = it },
                        label = {
                            Text(
                                text = "Apellido(s)",
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
                        keyboardOptions = keyboardOptionsTexto,
                    )
                    //Correo----------------------------------------------------------------------------
                    OutlinedTextField(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(start = 25.dp, end = 25.dp, bottom = 15.dp),
                        value = textoCorreo,
                        onValueChange = { textoCorreo = it },
                        label = {
                            Text(
                                text = "Correo",
                                style = MaterialTheme.typography.labelSmall
                            )
                        },
                        placeholder = {
                            Text(
                                text = "Ejemplo@mail.com",
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
                        keyboardOptions = keyboardOptionsCorreo,
                    )
                    //Contrasena------------------------------------------------------------------------
                    OutlinedTextField(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(start = 25.dp, end = 25.dp, bottom = 15.dp),
                        value = textoPassword,
                        onValueChange = { textoPassword = it
                            validate(textoPassword.text)},
                        supportingText = {
                            isError = isError
                            if (isError) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "Al menos $charLimit carácteres",
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        },
                        trailingIcon = {
                            IconButton(onClick = { passwordOculta = !passwordOculta }) {
                                val visibilityIcon =
                                    if (passwordOculta) Icons.Rounded.Visibility else Icons.Rounded.VisibilityOff
                                val description =
                                    if (passwordOculta) "Mostrar contraseña" else "Ocultar contraseña"
                                Icon(
                                    imageVector = visibilityIcon,
                                    contentDescription = description,
                                    tint = Color.White
                                )
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
                    //Aceptar terminos y condiciones----------------------------------------------------
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            modifier = modifier.padding(start = 25.dp),
                            selected = aceptarTyC,
                            colors = RadioButtonDefaults.colors(selectedColor = Trv6),
                            onClick = { aceptarTyC = !aceptarTyC }
                        )
                        Text(
                            modifier = modifier.padding(end = 25.dp),
                            text = "Aceptar Términos y Condiciones",
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Justify,
                            textDecoration = TextDecoration.Underline,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = modifier.fillMaxHeight(0.7f))
                    //Boton registro--------------------------------------------------------------------
                    TextButton(
                        enabled = aceptarTyC,
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(start = 25.dp, end = 25.dp, bottom = 10.dp),
                        onClick = {
                            //Iniciar-------------------------------------------------------------------
                            if(textoNombre.text.isBlank() || textoApellido.text.isBlank() || textoCorreo.text.isBlank() || textoPassword.text.isBlank()){
                                Log.i("error", "campos imcompletos")
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "Campos obligatorios no completados",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            } else if(!Patterns.EMAIL_ADDRESS.matcher(textoCorreo.text).matches()){
                                Log.i("error correo", textoCorreo.text)
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "El correo no tiene una estructura valida",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            } else if(textoPassword.text.length < charLimit) {
                                Log.i("error contraseña", textoPassword.text.length.toString())
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "Contraseña débil",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            } else {
                                auth.createUserWithEmailAndPassword(
                                    textoCorreo.text,
                                    textoPassword.text
                                ).addOnCompleteListener { task ->

                                    if (task.isSuccessful) {
                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                message = "Cuenta creada exitosamente, se necesita verificación de correo",
                                                duration = SnackbarDuration.Short
                                            )
                                            cuentaCreada = true
                                        }
                                        //Guardar datos firebase
                                        saveUserData(
                                            textoNombre.text,
                                            textoCorreo.text,
                                            firestore
                                        )
                                        val user = FirebaseAuth.getInstance().currentUser
                                        user?.sendEmailVerification()
                                    } else {
                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                message = "El correo ingresado ya está asociado a otra cuenta",
                                                duration = SnackbarDuration.Short
                                            )
                                        }
                                    }
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Trv6,
                            contentColor = Color.White
                        ),
                    ) {
                        Text(text = "Registrarme")
                    }
                    LaunchedEffect(cuentaCreada) {
                        if (cuentaCreada) {
                            delay(1000)
                            navController.navigate(Pantalla.InicioDeSesion.ruta) {
                                popUpTo(navController.graph.id) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

//Guardar datos del usuario en firestore
private fun saveUserData(
    textoNombre: String,
    textoCorreo: String,
    firestore: FirebaseFirestore
) {
    // Crear un objeto para representar la información del usuario
    val userData = Usuario(textoNombre, R.drawable.perfil, "2023", "", "México", null)
    Log.i("cuenta", "Punto")
    firestore.collection("Usuario").document(textoCorreo).set(userData).addOnSuccessListener {
        Log.i("cuenta", "Datos guardados")
    }.addOnFailureListener {
        Log.i("cuenta", "Datos no guardados")
    }
}