package loc.example.storedemoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import loc.example.storedemoapp.ui.screen.DiscoverScreen
import loc.example.storedemoapp.ui.screen.FuelScreen
import loc.example.storedemoapp.ui.screen.HomeScreen
import loc.example.storedemoapp.ui.screen.OrderScreen
import loc.example.storedemoapp.ui.screen.RewardScreen
import loc.example.storedemoapp.ui.theme.StoreDemoAppTheme
import loc.example.storedemoapp.viewmodel.HomeViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StoreDemoAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DemoStoreApp()
                }
            }
        }
    }
}

@Composable
fun DemoStoreApp(
    viewModel: HomeViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val uiState by viewModel.uiState.collectAsState()
    val currBackStack by navController.currentBackStackEntryAsState()
    val prevBackStack = navController.previousBackStackEntry
    val ctx = LocalContext.current
    val isHomeScreen = currBackStack?.destination?.route == StoreScreen.HOME.name
    val actions: @Composable RowScope.() -> Unit = if (isHomeScreen) {
        {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = stringResource(id = R.string.location)
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = stringResource(id = R.string.account)
                )
            }
        }
    } else {
        {}
    }
    val navTitle = if (isHomeScreen) {
        ctx.getString(R.string.greeting, uiState.username)
    } else {
        currBackStack?.destination?.route
    } ?: ctx.getString(StoreScreen.HOME.title)

    Scaffold(
        topBar = {
            StoreTopBar(
                title = navTitle,
                canNavigateUp = prevBackStack != null,
                onNavClick = { navController.navigateUp() },
                actions = actions
            )
        },
        bottomBar = {
            BottomAppBar {
                NavigationBarItem(
                    selected = true,
                    onClick = { navController.navigate(route = StoreScreen.HOME.name) },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.home),
                            contentDescription = stringResource(id = R.string.home)
                        )
                    },
                    label = {
                        Text(text = stringResource(id = R.string.home))
                    })
                NavigationBarItem(
                    selected = true,
                    onClick = { navController.navigate(route = StoreScreen.ORDER.name) },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.shopping_bag),
                            contentDescription = stringResource(id = R.string.order)
                        )
                    },
                    label = {
                        Text(text = stringResource(id = R.string.order))
                    })
                NavigationBarItem(
                    selected = true,
                    onClick = { navController.navigate(route = StoreScreen.REWARD.name) },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.reward),
                            contentDescription = stringResource(id = R.string.reward)
                        )
                    },
                    label = {
                        Text(text = stringResource(id = R.string.reward))
                    })
                NavigationBarItem(
                    selected = true,
                    onClick = { navController.navigate(route = StoreScreen.FUEL.name) },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.fuel),
                            contentDescription = stringResource(id = R.string.fuel)
                        )
                    },
                    label = {
                        Text(text = stringResource(id = R.string.fuel))
                    })
                NavigationBarItem(
                    selected = true,
                    onClick = { navController.navigate(route = StoreScreen.DISCOVER.name) },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.discover),
                            contentDescription = stringResource(id = R.string.discover)
                        )
                    },
                    label = {
                        Text(text = stringResource(id = R.string.discover))
                    })
            }
        },
    ) {
        NavHost(
            navController = navController,
            startDestination = StoreScreen.HOME.name,
            modifier = Modifier.padding(paddingValues = it)
        ) {
            composable(route = StoreScreen.HOME.name) {
                HomeScreen(products = uiState.recommendedDeals, isLoading = uiState.isLoading)
            }
            composable(route = StoreScreen.ORDER.name) {
                OrderScreen()
            }
            composable(route = StoreScreen.REWARD.name) {
                RewardScreen()
            }
            composable(route = StoreScreen.FUEL.name) {
                FuelScreen()
            }
            composable(route = StoreScreen.DISCOVER.name) {
                DiscoverScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreTopBar(
    title: String,
    canNavigateUp: Boolean,
    onNavClick: () -> Unit,
    actions: @Composable() (RowScope.() -> Unit)
) {
    TopAppBar(title = {
//        Heading(username = username)
        Text(text = title)
    }, navigationIcon = {
        if (canNavigateUp) {
            IconButton(onClick = onNavClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back_button)
                )
            }
        }
    }, actions = actions)
}

//@Composable
//fun Heading(username: String, modifier: Modifier = Modifier) {
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        modifier = modifier
//    ) {
//        Text(
//            text = stringResource(R.string.greeting, username),
//            style = MaterialTheme.typography.headlineMedium,
//            modifier = Modifier.weight(1f)
//        )
//        IconButton(onClick = { /*TODO*/ }) {
//            Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
//        }
//        IconButton(onClick = { /*TODO*/ }) {
//            Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null)
//        }
//    }
//}

enum class StoreScreen(val title: Int) {
    HOME(title = R.string.home),
    ORDER(title = R.string.order),
    REWARD(title = R.string.reward),
    FUEL(title = R.string.fuel),
    DISCOVER(title = R.string.discover)
}

//@Preview
//@Composable
//fun HeadingPreview() {
//    val username = "Jane"
//    Heading(username = username)
//}