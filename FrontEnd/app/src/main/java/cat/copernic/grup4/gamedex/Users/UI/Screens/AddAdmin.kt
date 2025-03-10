package cat.copernic.grup4.gamedex.Users.UI.Screens

import android.content.Context
import android.net.Uri
import android.util.Base64
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cat.copernic.grup4.gamedex.Core.Model.User
import cat.copernic.grup4.gamedex.Core.Model.UserType
import cat.copernic.grup4.gamedex.Core.ui.BottomSection
import cat.copernic.grup4.gamedex.Core.ui.header
import cat.copernic.grup4.gamedex.Core.ui.theme.GameDexTypography
import cat.copernic.grup4.gamedex.R
import cat.copernic.grup4.gamedex.Users.Data.UserRepository
import cat.copernic.grup4.gamedex.Users.Domain.UseCases
import cat.copernic.grup4.gamedex.Users.UI.ViewModel.UserViewModel
import cat.copernic.grup4.gamedex.Users.UI.ViewModel.UserViewModelFactory
import coil.compose.AsyncImage

@Composable
fun AddAdminScreen(navController: NavController, userViewModel: UserViewModel) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telephone by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var profilePicture by remember { mutableStateOf("") }

    val context = LocalContext.current
    val registrationState by userViewModel.registrationSuccess.collectAsState()

    DisposableEffect(Unit) {
        onDispose {
            userViewModel._registrationSuccess.value = null
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background))
            .windowInsetsPadding(WindowInsets.systemBars),
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {

        header(navController, userViewModel)

        // Columna para el resto del contenido
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 100.dp), // Para que ocupe el espacio restante
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(R.string.register_an_admin),
                fontSize = 40.sp,
                style = GameDexTypography.bodyLarge,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),

                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),


                ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    InputField(
                        label = stringResource(id = R.string.username),
                        value = username

                    ) { username = it }
                    InputField(
                        label = stringResource(id = R.string.password),
                        value = password,
                        isPassword = true
                    ) { password = it }
                    InputField(label = stringResource(id = R.string.name), value = name) {
                        name = it
                    }
                    InputField(
                        label = stringResource(id = R.string.surname),
                        value = surname
                    ) { surname = it }
                    InputField(label = stringResource(id = R.string.email), value = email) {
                        email = it
                    }
                    InputField(
                        label = stringResource(id = R.string.telephone),
                        value = telephone,
                        keyboardType = KeyboardType.Number
                    ) { telephone = it }
                    InputField(
                        label = stringResource(id = R.string.birthdate),
                        value = birthDate
                    ) { birthDate = it }

                    Text(
                        text = "Avatar",
                        style = GameDexTypography.bodySmall.copy(fontSize = 16.sp),
                        color = Color.Black
                    )


                    //AVATARSECTION
                    var selectedImageUri by remember {
                        mutableStateOf<Uri?>(null)
                    }

                    val imagePickerLauncher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.PickVisualMedia(),
                        onResult = { uri -> selectedImageUri = uri }
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row() {
                            if (selectedImageUri == null) {
                                Image(
                                    painter = painterResource(id = R.drawable.user),
                                    contentDescription = "Avatar",
                                    modifier = Modifier
                                        .size(120.dp)
                                        .clip(RoundedCornerShape(50))
                                        .clickable {
                                            imagePickerLauncher
                                                .launch(
                                                    PickVisualMediaRequest(
                                                        ActivityResultContracts
                                                            .PickVisualMedia.ImageOnly
                                                    )
                                                )
                                        }
                                )
                            } else {
                                profilePicture =
                                    userViewModel.uriToBase64(context, selectedImageUri!!)
                                        .toString()
                                AsyncImage(
                                    model = selectedImageUri,
                                    contentDescription = "Avatar",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(120.dp)
                                        .clip(CircleShape)
                                )
                            }
                            Icon(
                                Icons.Default.Add,
                                contentDescription = stringResource(R.string.add_avatar),
                                modifier = Modifier
                                    .clickable {
                                        imagePickerLauncher
                                            .launch(
                                                PickVisualMediaRequest(
                                                    ActivityResultContracts
                                                        .PickVisualMedia.ImageOnly
                                                )
                                            )
                                    }
                                    .padding(top = 40.dp)
                                    .background(
                                        colorResource(R.color.header),
                                        shape = RoundedCornerShape(50)
                                    )
                                    .clip(RoundedCornerShape(50))
                                    .size(40.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp)),
                        onClick = {
                            val newUser = User(
                                username = username,
                                password = password,
                                name = name,
                                surname = surname,
                                email = email,
                                telephone = telephone.toIntOrNull() ?: 0, // Convertir telèfon a Int
                                birthDate = birthDate,
                                userType = UserType.ADMIN,
                                state = true,
                                profilePicture = profilePicture
                            )
                            userViewModel.registerUser(newUser)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF69B4)),

                        ) {
                        Text(
                            text = stringResource(id = R.string.confirm),
                            color = Color.White,
                            style = GameDexTypography.bodySmall.copy(fontSize = 16.sp),
                        )
                    }
                }
            }
        }
    }
    LaunchedEffect(registrationState) {
        registrationState?.let { success ->
            if (success) {
                Toast.makeText(context, context.getString(R.string.user_created), Toast.LENGTH_LONG)
                    .show()
                navController.navigate("userList/")
            } else {
                Toast.makeText(
                    context,
                    context.getString(R.string.error_creating_user), Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    BottomSection(navController, userViewModel, 3)
}


@Preview(showBackground = true)
@Composable
fun PreviewAddAdminScreen() {
    val fakeNavController = rememberNavController() // ✅ Crear un NavController fals per la preview
    val useCases = UseCases(UserRepository())
    val userViewModel: UserViewModel = viewModel(factory = UserViewModelFactory(useCases))
    AddAdminScreen(navController = fakeNavController, userViewModel)
}