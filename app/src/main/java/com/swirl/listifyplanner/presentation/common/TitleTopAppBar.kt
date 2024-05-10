import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.swirl.listifyplanner.ui.constants.topAppBarTextStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleTopAppBar(title: String) {
    TopAppBar(
        title = { Text(text = title, style = topAppBarTextStyle) }
    )
}