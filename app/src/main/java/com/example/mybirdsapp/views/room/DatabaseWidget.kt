package com.example.mybirdsapp.views.room

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mybirdsapp.models.room.HomeState
import com.example.mybirdsapp.viewModels.HomeViewModel

@Composable
fun DatabaseWidget(
    homeViewModel: HomeViewModel,
    state: HomeState,
    onNextButtonClicked: (Int) -> Unit,
    onCancelButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier
        .fillMaxSize()
        .padding(top = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,) {
        Text(
            modifier = Modifier.padding(top = 15.dp),
            text = "Mis Productos",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
        TextField(
            modifier = Modifier.padding(top = 15.dp),
            value = state.productName,
            onValueChange = { homeViewModel.changeName(it) },
            placeholder = { Text(text = "Nombre del producto") }
        )
        TextField(
            modifier = Modifier.padding(top = 15.dp),
            value = state.productPrice,
            onValueChange = { homeViewModel.changePrice(it) },
            placeholder = { Text(text = "Precio") }
        )
        Button(onClick = { homeViewModel.createProduct() }) {
            Text(text = "Agregar Producto")
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth().padding(top = 20.dp)
        ) {
            items(state.products) {
                ProductItem(product = it, modifier = Modifier.fillMaxWidth(), onEdit = {
                    homeViewModel.editProduct(it)
                }, onDelete = {
                    homeViewModel.deleteProduct(it)
                })
            }
        }
    }
}
