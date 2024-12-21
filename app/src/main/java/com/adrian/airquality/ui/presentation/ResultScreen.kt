package com.adrian.airquality.ui.presentation

import android.icu.lang.UCharacter
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.adrian.airquality.ui.theme.*
import java.time.format.DateTimeFormatter

@Composable
fun ResultScreen( mainViewModel: MainViewModel , navController: NavController) {

    LazyColumn(
        Modifier
            .fillMaxSize()
            .background(
                color = couldbeanicebackground2
            )
    ) {

        item {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.height(1.dp))
                    RnCard(
                        state = mainViewModel.state,
                        backgroundColor = Color.White,
                        cityName = mainViewModel.city,
                        mainViewModel = mainViewModel,
                        navController = navController
                    )
                    Spacer(modifier = Modifier.height(1.dp))

                }
                if (mainViewModel.state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(140.dp),
                        color = Color.White
                    )
                }
                mainViewModel.state.error?.let { error ->
                    Text(
                        text = error,
                        color = Color.White,
                        style = TextStyle(fontSize = 20.sp),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(top = 50.dp)
                    )
                }
            }
        }
    }

}


@Composable
fun RnCard(
    state: AirQualityState,
           backgroundColor: Color,
           cityName: String,
    modifier : Modifier = Modifier,
    mainViewModel: MainViewModel,
    navController: NavController
    ) {
        state.weatherInfo?.currentData?.let { data ->
            Card(
                backgroundColor = couldbeanicebackground,
                shape = RoundedCornerShape(20.dp),
                modifier = modifier.padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "$cityName, ${
                            data.time.format(
                                DateTimeFormatter.ofPattern("HH:mm")
                            )
                        }",
                        modifier = Modifier.align(Alignment.Start),
                        color = Color.Black ,
                        style = TextStyle(fontWeight = FontWeight.Bold , fontSize = 25.sp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "Main indexes: ", color = Color.Black , fontWeight = FontWeight.Thin, fontSize = 20.sp)
                    Row (modifier = Modifier
                        .background(getColor("pm25", data.pm25))
                        .fillMaxWidth()){
                        Text(text =  "PM2.5: ", color = Color.White , style = TextStyle( fontSize = 20.sp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = "${data.pm25} µg/m³", color = Color.White, style = TextStyle(fontWeight = FontWeight.Bold , fontSize = 20.sp))
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row (modifier = Modifier
                        .background(getColor("pm10", data.pm10))
                        .fillMaxWidth()){
                        Text(text =  "PM10: ", color = Color.White , style = TextStyle( fontSize = 20.sp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = "${data.pm10} µg/m³", color = Color.White, style = TextStyle(fontWeight = FontWeight.Bold , fontSize = 20.sp))
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row (modifier = Modifier
                        .background(getColor("nitrogenDioxide", data.nitrogenDioxide))
                        .fillMaxWidth()){
                        Text(text =  "Nitrogen Dioxide: ", color = Color.White , style = TextStyle( fontSize = 20.sp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = "${data.nitrogenDioxide} µg/m³", color = Color.White, style = TextStyle(fontWeight = FontWeight.Bold , fontSize = 20.sp))
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row (modifier = Modifier
                        .background(getColor("sulphurDioxide", data.sulphurDioxide))
                        .fillMaxWidth()){
                        Text(text =  "Sulphur Dioxide: ", color = Color.White , style = TextStyle( fontSize = 20.sp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = "${data.sulphurDioxide} µg/m³", color = Color.White, style = TextStyle(fontWeight = FontWeight.Bold , fontSize = 20.sp))
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row (modifier = Modifier
                        .background(getColor("ozone", data.ozone))
                        .fillMaxWidth()){
                        Text(text =  "Ozone: ", color = Color.White , style = TextStyle(fontSize = 20.sp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = "${data.ozone} µg/m³", color = Color.White, style = TextStyle(fontWeight = FontWeight.Bold , fontSize = 20.sp))
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(modifier = Modifier
                        .fillMaxWidth()) {
                        Text(text = "Good", color = good, style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 11.sp))
                        Spacer(modifier =Modifier.width(5.dp))
                        Text(text = "Fair", color = fair, style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 11.sp))
                        Spacer(modifier =Modifier.width(5.dp))
                        Text(text = "Moderate", color = moderate, style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 11.sp))
                        Spacer(modifier =Modifier.width(5.dp))
                        Text(text = "Poor", color = poor, style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 11.sp))
                        Spacer(modifier =Modifier.width(5.dp))
                        Text(text = "Very Poor", color = veryPoor, style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 11.sp))
                        Spacer(modifier =Modifier.width(5.dp))
                        Text(text = "Extremely Poor", color = extremelyPoor, style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 11.sp))
                        Spacer(modifier =Modifier.width(5.dp))
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(text = "Other indexes: ", color = Color.Black , fontWeight = FontWeight.Thin, fontSize = 20.sp)
                    Row (modifier = Modifier
                        .fillMaxWidth()){
                        Text(text =  "Carbon Monoxide: ", color = Color.Black , style = TextStyle( fontSize = 20.sp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = "${data.carbonMonoxide} µg/m³", color = Color.Black, style = TextStyle(fontWeight = FontWeight.Bold , fontSize = 20.sp))
                    }
                    Row (modifier = Modifier
                        .fillMaxWidth()){
                        Text(text =  "Aerosol Optical Depth: ", color = Color.Black , style = TextStyle( fontSize = 20.sp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = "${data.aerosolOpticalDepth}", color = Color.Black, style = TextStyle(fontWeight = FontWeight.Bold , fontSize = 20.sp))
                    }
                    Row (modifier = Modifier
                        .fillMaxWidth()){
                        Text(text =  "Dust: ", color = Color.Black , style = TextStyle( fontSize = 20.sp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = "${data.dust} µg/m³", color = Color.Black, style = TextStyle(fontWeight = FontWeight.Bold , fontSize = 20.sp))
                    }
                    Row (modifier = Modifier
                        .fillMaxWidth()){
                        Text(text =  "UV Index: ", color = Color.Black , style = TextStyle( fontSize = 20.sp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = "${data.uvIndex}", color = Color.Black, style = TextStyle(fontWeight = FontWeight.Bold , fontSize = 20.sp))
                    }
                    Row (modifier = Modifier
                        .fillMaxWidth()){
                        Text(text =  "UV Index Clear Sky: ", color = Color.Black , style = TextStyle( fontSize = 20.sp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = "${data.uvIndexClearSky}", color = Color.Black, style = TextStyle(fontWeight = FontWeight.Bold , fontSize = 20.sp,textAlign = TextAlign.End))
                    }
                }
            }

            var locationExists : Boolean = mainViewModel.locationExists(mainViewModel.city)

            if(locationExists){
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                    Button(onClick = {
                        mainViewModel.deleteLocation(mainViewModel.city )
                        navController.navigate("main_screen")
                    },colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                        contentColor = Color.Red)
                    ) {
                        Text(text = "Delete Location")
                    }
                }
            }else{
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Button(onClick = {
                        mainViewModel.saveLocation(
                            mainViewModel.city,
                            mainViewModel.lat,
                            mainViewModel.long
                        )
                        navController.navigate("main_screen")
                    },colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White,
                        contentColor = Color.Red)
                    ) {
                        Text(text = "Save Location")
                    }
                }
            }

        }
}


fun getColor(index : String , num : Double): Color {
    if(index == "pm25"){
        return when {
            num <= 10 -> good
            num <= 20 -> fair
            num <= 25 -> moderate
            num <= 50 -> poor
            num <= 75 -> veryPoor
            else -> extremelyPoor
        }
    }else if (index == "pm10") {
        return when {
            num <= 20 -> good
            num <= 40 -> fair
            num <= 50 -> moderate
            num <= 100 -> poor
            num <= 150 -> veryPoor
            else -> extremelyPoor
        }

    }else if (index == "nitrogenDioxide") {
        return when {
            num <= 40 -> good
            num <= 90 -> fair
            num <= 120 -> moderate
            num <= 230 -> poor
            num <= 340 -> veryPoor
            else -> extremelyPoor
        }

    }else if (index == "ozone") {
        return when {
            num <= 50 -> good
            num <= 100 -> fair
            num <= 130 -> moderate
            num <= 240 -> poor
            num <= 380 -> veryPoor
            else -> extremelyPoor
        }

    }else if (index == "sulphurDioxide") {
        return when {
            num <= 100 -> good
            num <= 200 -> fair
            num <= 350 -> moderate
            num <= 500 -> poor
            num <= 750 -> veryPoor
            else -> extremelyPoor
        }
    }else return Color.Red

}
