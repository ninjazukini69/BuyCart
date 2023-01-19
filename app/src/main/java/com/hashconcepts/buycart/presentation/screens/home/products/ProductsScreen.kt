package com.hashconcepts.buycart.presentation.screens.home.products

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hashconcepts.buycart.R
import com.hashconcepts.buycart.data.mapper.toProductInCart
import com.hashconcepts.buycart.data.remote.dto.response.ProductsDto
import com.hashconcepts.buycart.presentation.components.CircularProgress
import com.hashconcepts.buycart.presentation.components.Indicators
import com.hashconcepts.buycart.presentation.screens.destinations.ProductDetailScreenDestination
import com.hashconcepts.buycart.presentation.screens.destinations.WelcomeScreenDestination
import com.hashconcepts.buycart.presentation.screens.home.productDetail.ProductDetailScreenNavArgs
import com.hashconcepts.buycart.ui.theme.*
import com.hashconcepts.buycart.utils.UIEvents
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

/**
 * @created 08/07/2022 - 4:49 PM
 * @project BuyCart
 * @author  guyjeff
 */

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun HomeScreen(

    navigator: DestinationsNavigator,
    productsViewModel: ProductsViewModel = hiltViewModel(),
) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(backgroundColor)
        systemUiController.setNavigationBarColor(Color.White)
    }

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        productsViewModel.eventChannelFlow.collectLatest { uiEvent ->
            when (uiEvent) {
                is UIEvents.ErrorEvent -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = uiEvent.message,
                        duration = SnackbarDuration.Short
                    )
                }
                else -> {
                    //Logout User
                    navigator.popBackStack()
                    navigator.navigate(WelcomeScreenDestination)
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = backgroundColor
    ) {
        Row(){
            Column(
                modifier = Modifier
                    .padding(horizontal = 3.dp)
            ) {
                if (productsViewModel.productsScreenState.isLoading) {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .height(2.dp)
                            .fillMaxWidth(),
                        color = errorColor
                    )
                }

                Text(
                    text = "Discover",
                    color=Color.Red,
                    style = MaterialTheme.typography.h1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 3.dp),

                    textAlign = TextAlign.Left
                )



                Spacer(modifier = Modifier.height(5.dp))

                ProductSection(
                    productState = productsViewModel.productsScreenState,
                    onDeleteProductFromCart = { productDto ->
                        productsViewModel.onEvents(ProductsScreenEvents.DeleteProductFromCart(productDto))
                    },

                    onAddToCartCart = { productDto ->
                        productsViewModel.onEvents(ProductsScreenEvents.AddProductToCart(productDto))
                    },


                    onProductItemClicked = {
                        navigator.navigate(ProductDetailScreenDestination(ProductDetailScreenNavArgs(productId = it)))
                    }
                )
            }
        }

    }
}

@Composable
fun ProductSection(

    productState: ProductsScreenState,
    onAddToCartCart: (ProductsDto) -> Unit,
    onDeleteProductFromCart: (ProductsDto) -> Unit,
    onProductItemClicked: (Int) -> Unit,


) {
    val productInCart = productState.productInCart

    LazyColumn(


        content = {
            items(productState.products) { productsDto ->

                Column(
                    modifier = Modifier
                        .padding(horizontal = 5.dp, vertical = 2.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(color = Color.hsv(0F, 0F, 0.1F))


                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(color = Color.hsv(0F, 0F, 0.15F))


                        ,

                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                modifier = Modifier
                                    .size(50.dp)
                                ,
                                painter = painterResource(id = R.drawable.ic_nyk),
                                contentDescription = "Profile Pic",
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Company name",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    color = Color.White
                                )
                                Text(
                                    text = "Product description",
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                            }
                        }
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_more),
                                contentDescription = "More Options",
                                tint = Color.White
                            )
                        }
                    }

                    AsyncImage(
                        model = productsDto.image,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = R.drawable.placeholder_image),
                        modifier = Modifier
                            .size(300.dp)
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                var hmm by remember { mutableStateOf(true) }
                                val fll = if (hmm) R.drawable.ic_like_outline else R.drawable.ic_like_outline_f
                                IconButton(onClick = { hmm = !hmm }) {
                                    Icon(
                                        painter = painterResource(fll),
                                        contentDescription = "Like Icon",
                                        tint = Color.White,
                                        modifier = Modifier.size(40.dp)

                                    )
                                }
                                Text(
                                    text = "856",
                                    color = Color.Gray,
                                    fontSize = 12.sp


                                    )
                            }
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                var ghp by remember { mutableStateOf(true)}
                                val ghq = if (ghp) R.drawable.ic_comment else R.drawable.ic_comment_f
                                IconButton(onClick = { ghp = !ghp }) {
                                    Icon(
                                        painter = painterResource(ghq),
                                        contentDescription = "Comment Icon",
                                        tint = Color.White,
                                        modifier = Modifier.size(40.dp)
                                    )
                                }
                                Text(
                                    text = "485",
                                    fontSize = 12.sp,
                                    color = Color.Gray

                                )
                            }
                            Column(

                                modifier = Modifier.padding(bottom = 30.dp, top = 10.dp)
                            ) {
                                var jhp by remember { mutableStateOf(true)}
                                val jhq = if (jhp) R.drawable.shopping_cart_fill0_wght400_grad0_opsz48 else R.drawable.ic_cart
                                IconButton(onClick = { jhp = !jhp }) {
                                    Icon(                                        painter = painterResource(jhq),
                                        contentDescription = "Cart Icon",
                                        tint = Color.White,
                                        modifier = Modifier
                                            .padding(top = 28.dp, end = 5.dp)
                                            .size(40.dp)
                                    )
                                }
                                Text(
                                    text="267",
                                    fontSize=12.1.sp,
                                    color = Color.Gray,
                                    modifier = Modifier
                                        .padding( start = 12.75.dp,top = 5.dp)

                                )
                            }

                            }
                        }

                    }
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column() {

                            Spacer(modifier = Modifier.height(5.dp))

                        }
                        Column(modifier = Modifier.fillMaxWidth()) {

                        }

                    }
                }

        })
}

@Composable
fun CategorySection(
    productState: ProductsScreenState,
    onCategorySelected: (String, Int) -> Unit,
) {
    val categories = productState.categories
    val categoryIndex = productState.selectedCategoryIndex

    if (categories.isNotEmpty()) {
        LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp), content = {
            items(categories) { category ->
                Text(
                    text = category,
                    style = MaterialTheme.typography.h2,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (categoryIndex == categories.indexOf(category)) primaryColor else Color.White)
                        .padding(horizontal = 15.dp, vertical = 10.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            onCategorySelected(category, categories.indexOf(category))
                        }
                )
            }
        }
        )

    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OfferSection(offers: List<String>) {
    val pagerState = rememberPagerState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        HorizontalPager(
            count = offers.size,
            state = pagerState,
            itemSpacing = 10.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            val currentOffer = offers[pagerState.currentPage]
            AsyncImage(
                model = currentOffer,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.placeholder_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .height(170.dp)
            )
        }

        Spacer(modifier = Modifier.height(7.dp))

        Indicators(size = offers.size, index = pagerState.currentPage)
    }

    LaunchedEffect(key1 = pagerState.currentPage) {
        try {
            delay(3000L)
            val page = if (pagerState.currentPage < pagerState.pageCount - 1) {
                pagerState.currentPage + 1
            } else 0
            pagerState.scrollToPage(page)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

@Composable
fun SearchFilterSection(
    productState: ProductsScreenState,
    onShowCategories: () -> Unit
) {
    var searchText by remember { mutableStateOf("") }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            placeholder = {
                Text(
                    text = "Search Product",
                    style = MaterialTheme.typography.body1,
                    color = disableColor
                )
            },
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = primaryColor,
                textColor = secondaryColor,
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .background(Color.White, shape = RoundedCornerShape(10.dp))
                .weight(1f),
            maxLines = 1,
            singleLine = true,
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(onClick = { onShowCategories() }) {
            Icon(
                modifier = Modifier
                    .size(50.dp)
                    .clip(shape = RoundedCornerShape(size = 8.dp))
                    .background(if (productState.filterSelected) primaryColor else Color.White)
                    .padding(5.dp),
                painter = painterResource(id = R.drawable.ic_filter),
                contentDescription = null,
                tint = secondaryColor
            )
        }
    }
}


