package loc.example.storedemoapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.google.gson.Gson
import loc.example.storedemoapp.R
import loc.example.storedemoapp.data.FakeData
import loc.example.storedemoapp.model.HomeUiState
import loc.example.storedemoapp.model.Product
import loc.example.storedemoapp.ui.theme.StoreDemoAppTheme

@Composable
fun HomeScreen(uiState: HomeUiState, modifier: Modifier = Modifier) {
    StoreDemoAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = modifier,
            color = MaterialTheme.colorScheme.background
        ) {
            if (uiState.isLoading) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(size = 64.dp))
                }
            } else {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    Spacer(modifier = Modifier.height(8.dp))
                    DeliveryCard()
                    Spacer(modifier = Modifier.height(8.dp))
                    RecommendedDeals(items = uiState.recommendedDeals)
                }
            }
        }
    }
}

@Composable
fun DeliveryCard(modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 32.dp)) {
            Text(
                text = "Snacks, pizza, beer, and more in just a tap",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Column {
                    Text(text = "Estimated delivery in")
                    Text(text = "15-20 min", modifier = Modifier.padding(top = 8.dp))
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.padding(top = 32.dp, bottom = 8.dp)
                    ) {
                        Text(text = "Order Delivery")
                    }
                }
                Image(
                    painter = painterResource(id = R.drawable.food_pizza_and_soda),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun RecommendedDeals(items: List<Product>, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(
            "Recommended Deals",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)
        }
    }
    RecommendedDealsCarousel(items = items)
}

@Composable
fun RecommendedDealsCarousel(
    items: List<Product>,
    modifier: Modifier = Modifier
) {
    LazyRow(modifier = modifier.padding(top = 8.dp)) {
        items(items = items, key = { it.id }) {
            RecommendedDeal(it)
        }
    }
}

@Composable
fun RecommendedDeal(item: Product, modifier: Modifier = Modifier) {
    OutlinedCard(
        modifier = modifier
            .padding(end = 16.dp)
            .size(width = 300.dp, height = 180.dp)
    ) {
        Row(modifier = Modifier.padding(all = 16.dp)) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        painter = painterResource(id = R.drawable.savings),
                        contentDescription = null
                    )
                    Text(
                        text = stringResource(id = R.string.savings),
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
                Row(modifier = Modifier.padding(top = 8.dp)) {
                    Column(
                        modifier = Modifier
                            .weight(weight = 2f)
                            .fillMaxSize()
                    ) {
                        Column(modifier = Modifier.heightIn(min = 96.dp)) {
                            Text(
                                text = item.title,
                                lineHeight = 16.sp,
                                style = MaterialTheme.typography.titleSmall,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 4
                            )
                            Text(
                                text = "When you buy one of these",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        Text(
                            text = "Offer expires 01/09/24",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Image(
                        painter = rememberAsyncImagePainter(model = item.image),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        alignment = Alignment.Center,
                        modifier = Modifier
                            .weight(weight = 1f)
                            .padding(start = 8.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    StoreDemoAppTheme {
        val ctx = LocalContext.current
        val gson = Gson()
        val uiState = HomeUiState(
            isLoading = false,
            recommendedDeals = FakeData.getRecommendedDeals(ctx, gson)
        )
        HomeScreen(uiState = uiState)
    }
}

@Preview(showBackground = true)
@Composable
fun DeliveryCardPreview() {
    DeliveryCard()
}

@Preview(showBackground = true)
@Composable
fun RecommendedDealsPreview() {
    StoreDemoAppTheme {
        RecommendedDeals(items = FakeData.recommendedDeals)
    }
}
