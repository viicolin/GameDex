package cat.copernic.grup4.gamedex.videogames.ui.screens


import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cat.copernic.grup4.gamedex.Core.ui.header
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.copernic.grup4.gamedex.Core.Model.Videogame
import cat.copernic.grup4.gamedex.Core.ui.BottomSection
import cat.copernic.grup4.gamedex.Core.ui.theme.GameDexTypography
import cat.copernic.grup4.gamedex.R
import cat.copernic.grup4.gamedex.Users.Data.UserRepository
import cat.copernic.grup4.gamedex.Users.Domain.UseCases
import cat.copernic.grup4.gamedex.Users.UI.Screens.InputField
import cat.copernic.grup4.gamedex.Users.UI.ViewModel.UserViewModel
import cat.copernic.grup4.gamedex.Users.UI.ViewModel.UserViewModelFactory
import cat.copernic.grup4.gamedex.videogames.data.VideogameRepository
import cat.copernic.grup4.gamedex.videogames.domain.VideogameUseCase
import cat.copernic.grup4.gamedex.videogames.ui.viewmodel.GameViewModel
import cat.copernic.grup4.gamedex.videogames.ui.viewmodel.GameViewModelFactory
import coil.compose.AsyncImage

@Composable
fun AddGamesScreen(navController : NavController, userViewModel: UserViewModel) {
    val videogameUseCase = VideogameUseCase(VideogameRepository())
    val gameViewModel: GameViewModel = viewModel(factory = GameViewModelFactory(videogameUseCase))

    // TODO Moure variables al ViewModel
    var nameGame by remember { mutableStateOf("") }
    var releaseYear by remember { mutableStateOf("") }
    var ageRecommendation by remember { mutableStateOf("") }
    var developer by remember { mutableStateOf("") }
    var nameCategory by remember { mutableStateOf("") }
    var descriptionGame by remember { mutableStateOf("") }
    var gamePhoto by remember { mutableStateOf("") }

    val context = LocalContext.current
    val createdGameState by gameViewModel.videogameCreated.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(R.color.background))
                .windowInsetsPadding(WindowInsets.systemBars),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            header(navController)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 80.dp)
                    .background(colorResource(R.color.background))
                    .windowInsetsPadding(WindowInsets.systemBars)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stringResource(R.string.add_game),
                    fontSize = 50.sp,
                    color = Color.Black,
                    style = GameDexTypography.bodyLarge
                )
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        InputField(
                            label = stringResource(id = R.string.game_name),
                            value = nameGame
                        ) { nameGame = it }
                        InputField(
                            label = stringResource(id = R.string.release_year),
                            value = releaseYear,
                            keyboardType = KeyboardType.Number
                        ) { releaseYear = it }
                        InputField(
                            label = stringResource(id = R.string.age_recommendation),
                            value = ageRecommendation
                        ) { ageRecommendation = it }
                        InputField(
                            label = stringResource(id = R.string.developer),
                            value = developer
                        ) { developer = it }
                        InputField(
                            label = stringResource(id = R.string.category),
                            value = nameCategory
                        ) { nameCategory = it }
                        InputField(
                            label = stringResource(id = R.string.description),
                            value = descriptionGame
                        ) { descriptionGame = it }

                        Text(
                            text = stringResource(R.string.cover) + ":",
                            color = Color.Black,
                            style = GameDexTypography.bodyLarge,
                            fontSize = 22.sp,
                            modifier = Modifier.padding(end = 80.dp, bottom = 4.dp)
                        )


                        var selectedImageUri by remember {
                            mutableStateOf<Uri?>(null)
                        }

                        val imagePickerLauncher = rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.PickVisualMedia(),
                            onResult = { uri -> selectedImageUri = uri }
                        )

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Row {
                                Spacer(modifier = Modifier.height(4.dp))
                                if (selectedImageUri == null) {
                                    Image(
                                        painter = painterResource(R.drawable.eldenring),
                                        contentDescription = stringResource(R.string.cover),
                                        modifier = Modifier
                                            .size(120.dp)
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
                                    gamePhoto =
                                        gameViewModel.uriToBase64(context, selectedImageUri!!)
                                            .toString()
                                    AsyncImage(
                                        model = selectedImageUri,
                                        contentDescription = stringResource(R.string.cover),
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.size(120.dp)
                                    )
                                }
                                Icon(
                                    Icons.Default.Add,
                                    contentDescription = stringResource(R.string.add_cover),
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

                        Spacer(modifier = Modifier.height(20.dp))
                        Button(
                            onClick = {
                                val newGame = Videogame(
                                    gameId = "",
                                    nameGame = nameGame,
                                    releaseYear = releaseYear,
                                    ageRecommendation = ageRecommendation,
                                    developer = developer,
                                    nameCategory = nameCategory,
                                    descriptionGame = descriptionGame,
                                    gamePhoto = gamePhoto
                                )
                                gameViewModel.createVideogame(newGame)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF69B4)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .clip(RoundedCornerShape(16.dp))
                        ) {
                            Text(
                                text = stringResource(R.string.confirm),
                                color = Color.White,
                                style = GameDexTypography.bodyLarge,
                                fontSize = 22.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }

                }

            }

        }
        BottomSection(navController, userViewModel, 1)

        LaunchedEffect(createdGameState) {
            createdGameState?.let { success ->
                if (success) {
                    Toast.makeText(context, R.string.gameCreated, Toast.LENGTH_LONG).show()
                    navController.navigate("listvideogames")
                } else {
                    Toast.makeText(context, R.string.gameErrorCreate, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}

@Composable
fun InputField(
    label: String,
    value: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    Column {
        Text(
            text = label,
            color = Color.Black,
            style = GameDexTypography.bodyLarge,
            fontSize = 22.sp
        )
        TextField(
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Color.LightGray)
        )
        Spacer(modifier = Modifier.height(6.dp))
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewAddGamesScreen() {
    val fakeNavController = rememberNavController()
    val useCases = UseCases(UserRepository())
    val userViewModel: UserViewModel = viewModel(factory = UserViewModelFactory(useCases))
    AddGamesScreen(navController = fakeNavController, userViewModel)
}