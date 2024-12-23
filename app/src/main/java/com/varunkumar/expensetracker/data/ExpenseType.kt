package com.varunkumar.expensetracker.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AllInclusive
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.EmojiFoodBeverage
import androidx.compose.material.icons.filled.LocalGroceryStore
import androidx.compose.material.icons.filled.LocalMovies
import androidx.compose.material.icons.filled.Train
import androidx.compose.material.icons.outlined.FoodBank
import androidx.compose.material.icons.outlined.Label
import androidx.compose.material.icons.outlined.LocalGroceryStore
import androidx.compose.material.icons.outlined.LocalMovies
import androidx.compose.material.icons.outlined.Money
import androidx.compose.material.icons.outlined.Train
import androidx.compose.ui.graphics.vector.ImageVector

enum class ExpenseType(
    val icon: ImageVector
) {
    ENTERTAINMENT(Icons.Default.LocalMovies),
    FINANCE(Icons.Default.CurrencyRupee),
    FOOD(Icons.Default.EmojiFoodBeverage),
    TRANSPORT(Icons.Default.Train),
    GROCERIES(Icons.Default.LocalGroceryStore),
    OTHER(Icons.Default.AllInclusive)
}
