package loc.example.storedemoapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import dagger.hilt.android.AndroidEntryPoint
import loc.example.storedemoapp.model.HomeUiState
import loc.example.storedemoapp.ui.screen.HomeScreen
import loc.example.storedemoapp.viewmodel.HomeViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DemoStoreApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DemoStoreApp(
    viewModel: HomeViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Heading(uiState = uiState)
            })
        },
        bottomBar = {
            BottomAppBar {
                NavigationBarItem(
                    selected = true,
                    onClick = { navController.navigate(route = StoreScreen.HOME.name) },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.home),
                            contentDescription = stringResource(
                                id = R.string.home
                            )
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
                            contentDescription = stringResource(
                                id = R.string.order
                            )
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
                            contentDescription = stringResource(
                                id = R.string.reward
                            )
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
                            contentDescription = stringResource(
                                id = R.string.fuel
                            )
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
                            contentDescription = stringResource(
                                id = R.string.discover
                            )
                        )
                    },
                    label = {
                        Text(text = stringResource(id = R.string.discover))
                    })
            }
        },
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        val graph =
            navController.createGraph(startDestination = StoreScreen.HOME.name, route = null) {
                composable(route = StoreScreen.HOME.name) {
                    HomeScreen(uiState = uiState)
                }
                composable(route = StoreScreen.ORDER.name) {
                    Log.d(TAG, "DemoStoreApp: order bottom nav item clicked..")
                }
                composable(route = StoreScreen.REWARD.name) {
                    Log.d(TAG, "DemoStoreApp: reward bottom nav item clicked..")
                }
                composable(route = StoreScreen.FUEL.name) {
                    Log.d(TAG, "DemoStoreApp: fuel bottom nav item clicked..")
                }
                composable(route = StoreScreen.DISCOVER.name) {
                    Log.d(TAG, "DemoStoreApp: discover bottom nav item clicked..")
                }
            }
        NavHost(
            navController = navController,
            graph = graph
        )
        HomeScreen(uiState = uiState, modifier = Modifier.padding(paddingValues = it))
    }
}

@Composable
fun Heading(uiState: HomeUiState, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.greeting, uiState.username),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null)
        }
    }
}

enum class StoreScreen {
    HOME,
    ORDER,
    REWARD,
    FUEL,
    DISCOVER
}

@Preview
@Composable
fun HeadingPreview() {
    Heading(HomeUiState())
}