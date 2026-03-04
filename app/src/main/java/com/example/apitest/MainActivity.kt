package com.example.apitest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.Icon
import androidx.compose.ui.tooling.preview.Preview
import com.example.apitest.navigation.Routes
import com.example.apitest.ui.theme.GreenJC
import com.example.apitest.view.DinoDetails
import com.example.apitest.view.DinoList


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyBottomAppBar()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyBottomAppBar() {
    val navigationController = rememberNavController()
    val context = LocalContext.current.applicationContext
    val selected = remember {
        mutableStateOf(Icons.Default.Home)
    }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = GreenJC
            ) {
                IconButton(
                    onClick = {
                        selected.value = Icons.Default.Search
                        navigationController.navigate(Routes.DinoList.route) {
                            popUpTo(0)
                        }
                    },
                    modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.Home, contentDescription = null, modifier = Modifier.size(17.dp),
                        tint = if (selected.value == Icons.Default.Home) Color.Cyan else Color.DarkGray)
                }

                Box(modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                    contentAlignment = Alignment.Center) {
                    FloatingActionButton(
                        onClick = {
                            Toast.makeText(context, "Open Bottom Sheet", Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = null,
                            tint = GreenJC
                        )
                    }
                }
            }


        }
    ){paddingValues ->
                        NavHost(
        navController = navigationController,
        startDestination = Routes.DinoList.route,
                            modifier = Modifier.padding(paddingValues)){
                            composable (Routes.DinoList.route){DinoList()}
                            composable (Routes.DinoDetails.route){ DinoDetails()}
                        }
    }
}
@Composable
fun DinosaurCard(dinosaur: com.example.apitest.model.Dinosaur) {

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.Green
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(12.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Imagen (opcional)
            /*item.imageUrl?.let { url ->
                AsyncImage(
                    model = url,
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(12.dp))
            }*/

            Column(modifier = Modifier.weight(1f)) {

                // Nombre
                Text(
                    text = dinosaur.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Datos principales
                Text(text = "Peso: ${dinosaur.weight}")
                Text(text = "Altura: ${dinosaur.height}")
                Text(text = "Longitud: ${dinosaur.length}")

                Spacer(modifier = Modifier.height(6.dp))

                Text(text = "Dieta: ${dinosaur.diet}")
                Text(text = "Periodo: ${dinosaur.period}")
            }
        }
    }


}


/*     setContent {
         val navigationController = rememberNavController()
         NavHost(
             navController = navigationController,  {
             startDestination = Routes.DinoList.route
         }
             ) {
             composable(Routes.DinoList.route) { Routes.DinoList(navController = navigationController) }
             composable(Routes.DinoDetails.route) { Routes.DinoDetails(navController = navigationController) }
         }
     }
 }
}*/
