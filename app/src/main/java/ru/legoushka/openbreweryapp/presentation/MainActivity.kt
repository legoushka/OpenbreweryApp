package ru.legoushka.openbreweryapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import ru.legoushka.openbreweryapp.presentation.navigation.BreweryNavGraph
import ru.legoushka.openbreweryapp.presentation.theme.DefaultTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		WindowCompat.setDecorFitsSystemWindows(window, false)
		setContent {
			DefaultTheme {
				BreweryNavGraph()
			}
		}
	}
}