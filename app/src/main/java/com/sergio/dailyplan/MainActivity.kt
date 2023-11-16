package com.sergio.dailyplan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.sergio.dailyplan.ui.DailyPlanApp
import com.sergio.common.theme.DailyPlanTheme
import com.sergio.dailyplan.output.MainState
import com.sergio.dailyplan.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DailyPlanTheme {
                DailyPlanApp()
            }
        }
    }

    private fun ComponentActivity.setSplash() {
        val splash = installSplashScreen()
        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                splash.setKeepOnScreenCondition {
                    when(state) {
                        MainState.Loading -> true
                        MainState.Success -> false
                    }
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DailyPlanTheme {
        DailyPlanApp()
    }
}