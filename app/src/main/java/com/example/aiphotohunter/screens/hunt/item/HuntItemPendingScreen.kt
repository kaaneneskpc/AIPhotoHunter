package com.example.aiphotohunter.screens.hunt.item

import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.aiphotohunter.R
import com.example.aiphotohunter.components.HuntProgress
import com.example.aiphotohunter.navigation.HandleBackPressToHome
import com.example.aiphotohunter.navigation.Screen
import com.example.aiphotohunter.screens.PredictionViewModel
import com.example.aiphotohunter.screens.hunt.HuntViewModel

@Composable
fun HuntItemPendingScreen(
    navController: NavHostController,
    huntViewModel: HuntViewModel,
    predictionViewModel: PredictionViewModel
) {
    val context = LocalContext.current
    HandleBackPressToHome(navController, huntViewModel)
    val selectedLanguage by huntViewModel.selectedLanguage.collectAsState()
    val isLoading by huntViewModel.isLoading.collectAsState()
    val galleryIconDescription by huntViewModel.galleryIconDescription.collectAsState()
    val nextItemText by huntViewModel.nextItemText.collectAsState()
    val noItemText by huntViewModel.noItemText.collectAsState()
    val skipButtonText by huntViewModel.skipButtonText.collectAsState()
    val takePhotoButtonText by huntViewModel.takePhotoButtonText.collectAsState()
    val currentItem by huntViewModel.currentItem.collectAsState()

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val bitmap = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            }

            predictionViewModel.setCapturedImage(bitmap)
            predictionViewModel.predictImageName(selectedLanguage = selectedLanguage ?: "English")
            navController.navigate(Screen.ItemValidating.route)
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        predictionViewModel.setCapturedImage(bitmap)
        predictionViewModel.predictImageName(selectedLanguage = selectedLanguage ?: "English")
        navController.navigate(Screen.ItemValidating.route)
    }

    LaunchedEffect(huntViewModel) {
        huntViewModel.onFinished = {
            navController.navigate(Screen.Finish.route)
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.image),
            contentDescription = "Arka Plan",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(modifier = Modifier.height(15.dp))



            Spacer(modifier = Modifier.height(15.dp))

            HuntProgress(huntViewModel)

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(
                    text = nextItemText,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = currentItem ?: noItemText,
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontSize = 35.sp,
                        fontWeight = FontWeight.W800
                    )
                )
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                OutlinedButton(onClick = { huntViewModel.pickNextItem() }) {
                    Text(text = skipButtonText)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(onClick = { launcher.launch(null) }) {
                    Text(text = takePhotoButtonText)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = { galleryLauncher.launch("image/*") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                    ),
                    modifier = Modifier.width(100.dp),
                    enabled = !isLoading
                ) {
                    Icon(
                        imageVector = Icons.Default.PhotoLibrary,
                        contentDescription = galleryIconDescription,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

            }

            Spacer(modifier = Modifier.height(35.dp))

        }
    }
}


@Preview(showBackground = true)
@Composable
fun HuntItemPendingScreenPreview() {
    val navController = rememberNavController()
    val huntViewModel = HuntViewModel()
    val predictionViewModel = PredictionViewModel()
    HuntItemPendingScreen(navController, huntViewModel, predictionViewModel)
}