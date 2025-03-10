package cat.copernic.grup4.gamedex.Core.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cat.copernic.grup4.gamedex.R
import cat.copernic.grup4.gamedex.Users.Data.UserRepository
import cat.copernic.grup4.gamedex.Users.Domain.UseCases
import cat.copernic.grup4.gamedex.Users.UI.ViewModel.UserViewModel
import cat.copernic.grup4.gamedex.Users.UI.ViewModel.UserViewModelFactory
import kotlinx.coroutines.flow.collect

/**
 * Funció composable que defineix la barra de navegació inferior de l'aplicació.
 *
 * @param navController El controlador de navegació.
 * @param userViewModel El ViewModel de l'usuari.
 * @param selectedItem L'ítem seleccionat actualment.
 */
@Composable
fun BottomNavBar(navController: NavController, userViewModel: UserViewModel, selectedItem: Int = 0) {
    NavigationBar (
        containerColor = Color(0xFFCE55F4)
    ) {
        val currentUser by userViewModel.currentUser.collectAsState()
        val icons = listOf(
            R.drawable.apps,
            R.drawable.gamepad,
            R.drawable.users_alt,
            R.drawable.user,
            R.drawable.book_open_cover
        )

        val destinations = listOf(
            "list_category/",
            "listVideogames",
            "userList/",
            "profile/${currentUser?.username}",
            "libraryScreen"
        )
        val descriptions = listOf(
            R.string.category,
            R.string.videogames,
            R.string.users,
            R.string.profile,
            R.string.library
        )
        icons.forEachIndexed { index, iconRes ->
            NavigationBarItem(
                icon = {
                    Image(
                        painter = painterResource(id = iconRes),
                        contentDescription = stringResource(id = descriptions[index]),
                        modifier = Modifier.size(24.dp)
                    )
                },
                selected = selectedItem == index,
                onClick = { if (index < destinations.size) {
                    navController.navigate(destinations[index])
                }},
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    unselectedIconColor = Color.Gray,
                    indicatorColor = Color.White
                ),
                modifier = Modifier.size(40.dp)
            )
        }
    }
}

/**
 * Funció de previsualització per a la barra de navegació inferior.
 */
@Preview(showBackground = true)
@Composable
fun PreviewBottomBar() {
    val fakeNavController = rememberNavController()
    //BottomNavBar(fakeNavController, selectedItem = 0)
}