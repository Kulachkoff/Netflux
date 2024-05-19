package com.kravchenko.netflux.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kravchenko.netflux.ui.theme.Grey
import com.kravchenko.netflux.ui.theme.PrimaryBlack
import com.kravchenko.netflux.ui.theme.PrimaryGreen
import com.kravchenko.netflux.ui.theme.inter

@Composable
fun CustomBottomAppBar(
    pages: List<Routes>,
    selectedPage: String,
    onPageClicked: (page: String) -> Unit
) {
    NavigationBar(
        windowInsets = WindowInsets.navigationBars
    ) {
        Row(
            modifier = Modifier
                .background(PrimaryBlack)
                .padding(horizontal = 30.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            pages.forEach { page ->
                NavigationBarItem(
                    selected = selectedPage == page.route,
                    onClick = {
                        onPageClicked(page.route)
                    },
                    icon = {
                        Icon(
                            imageVector = if (selectedPage == page.route) page.selectedIcon!! else page.icon!!,
                            tint = if (selectedPage == page.route) PrimaryGreen else Grey,
                            contentDescription = page.iconDescription,
                        )
                    },
                    label = {
                        Text(
                            text = page.name!!,
                            fontFamily = inter,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(indicatorColor = PrimaryBlack),
                    alwaysShowLabel = true
                )
            }
        }
    }
}