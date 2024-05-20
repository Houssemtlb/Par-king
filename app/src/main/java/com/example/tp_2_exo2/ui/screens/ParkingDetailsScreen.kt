package com.example.tp_2_exo2.ui.screens

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tp_2_exo2.R
import com.example.tp_2_exo2.data.model.ParkingData
import com.example.tp_2_exo2.ui.composables.BottomNavigationBar
import com.example.tp_2_exo2.ui.composables.ButtonComponent
import com.example.tp_2_exo2.ui.composables.NormalTextComponent
import com.example.tp_2_exo2.ui.navigation.routes.ParkingDestination
import com.example.tp_2_exo2.ui.theme.Primary
import com.example.tp_2_exo2.ui.theme.Purple40
import com.example.tp_2_exo2.ui.theme.Purple60
import com.example.tp_2_exo2.ui.theme.Purple80
import com.example.tp_2_exo2.ui.theme.PurpleGrey40
import com.example.tp_2_exo2.ui.theme.PurpleGrey80
import com.example.tp_2_exo2.ui.theme.Secondary
import com.example.tp_2_exo2.ui.theme.TextColor
import com.example.tp_2_exo2.ui.theme.poppinsFontFamily
import com.example.tp_2_exo2.ui.theme.purpleGrey200
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import java.util.Calendar

@Composable
fun parkingDetails(){
    val navController = rememberNavController()
    ParkingDetailsScreen(com.example.tp_2_exo2.data.utils.parkingsList.get(0),navController)
}

enum class ShowDialog {
    None,
    ReservationDialog,
    InTimePicker,
    OutTimePicker,
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ParkingDetailsScreen(
    parking: ParkingData,
    navController: NavHostController
) {
    var showDialog by remember { mutableStateOf(ShowDialog.None) }
    var inTime by remember { mutableStateOf("") }
    var outTime by remember { mutableStateOf("") }
    var reservationDate by remember { mutableStateOf("") }

    val showModal: () -> Unit = {
        showDialog = ShowDialog.ReservationDialog
    }

    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _, year, month, dayOfMonth ->
            reservationDate = "$dayOfMonth/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val inTimePickerDialog = TimePickerDialog(
        LocalContext.current,
        { _, hourOfDay, minute ->
            inTime = String.format("%02d:%02d", hourOfDay, minute)
            showDialog = ShowDialog.ReservationDialog
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )

    val outTimePickerDialog = TimePickerDialog(
        LocalContext.current,
        { _, hourOfDay, minute ->
            outTime = String.format("%02d:%02d", hourOfDay, minute)
            showDialog = ShowDialog.ReservationDialog
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )

    if (showDialog == ShowDialog.ReservationDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = ShowDialog.None
            },
            title = {
                Text(text = "Reserve your spot")
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = inTime,
                        onValueChange = { inTime = it },
                        label = { Text("In-Time") },
                        trailingIcon = {
                            IconButton(onClick = {
                                showDialog = ShowDialog.InTimePicker
                                inTimePickerDialog.show()
                            }) {
                                Icon(Icons.Default.Schedule, contentDescription = "Pick Time")
                            }
                        }
                    )
                    OutlinedTextField(
                        value = outTime,
                        onValueChange = { outTime = it },
                        label = { Text("Out-Time") },
                        trailingIcon = {
                            IconButton(onClick = {
                                showDialog = ShowDialog.OutTimePicker
                                outTimePickerDialog.show()
                            }) {
                                Icon(Icons.Default.Schedule, contentDescription = "Pick Time")
                            }
                        }
                    )
                    OutlinedTextField(
                        value = reservationDate,
                        onValueChange = { reservationDate = it },
                        label = { Text("Reservation Date") },
                        trailingIcon = {
                            IconButton(onClick = { datePickerDialog.show() }) {
                                Icon(Icons.Default.DateRange, contentDescription = "Pick Date")
                            }
                        }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = ShowDialog.None
                        // Handle your reservation logic here
                    }
                ) {
                    Text("Reserve")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDialog = ShowDialog.None
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }


    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Parking Details") },
                navigationIcon =
                {
                    IconButton(onClick = {navController.popBackStack()}){
                        Icon(Icons.Filled.ArrowBackIosNew, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = purpleGrey200)
            )
        },
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            Image(
                painter = painterResource(id = R.drawable.pic5),
                contentDescription = null,
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(0.dp, 0.dp, 0.dp, 55.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row (
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column(
                        modifier = Modifier
                            .padding(10.dp, 0.dp, 0.dp, 0.dp),
                    ) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = parking.name,
                            modifier = Modifier
                                .heightIn(min = 20.dp),
                            style = TextStyle(
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                fontStyle = FontStyle.Normal,
                                fontFamily = poppinsFontFamily,
                            ),
                            color = Purple40,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(7.dp))
                        Text(
                            text = parking.wilaya,
                            modifier = Modifier
                                .heightIn(min = 30.dp),
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Normal,
                                fontFamily = poppinsFontFamily,
                            ),
                            color = Purple40,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = parking.address,
                            modifier = Modifier
                                .heightIn(min = 0.dp),
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Normal,
                                fontFamily = poppinsFontFamily,
                            ),
                            color = Purple40,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Row(horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "opening time : ",
                                modifier = Modifier
                                    .heightIn(min = 0.dp),
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Normal,
                                    fontStyle = FontStyle.Normal,
                                    fontFamily = poppinsFontFamily,
                                ),
                                color = Purple40,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = parking.opening_time,
                                modifier = Modifier
                                    .heightIn(min = 0.dp)
                                    .padding(0.dp, 0.dp, 20.dp, 0.dp),
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Normal,
                                    fontStyle = FontStyle.Normal,
                                    fontFamily = poppinsFontFamily,
                                ),
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Row(horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()) {
                            Text(
                                text = "closing time : ",
                                modifier = Modifier
                                    .heightIn(min = 0.dp),
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Normal,
                                    fontStyle = FontStyle.Normal,
                                    fontFamily = poppinsFontFamily,
                                ),
                                color = Purple40,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = parking.closing_time,
                                modifier = Modifier
                                    .heightIn(min = 0.dp)
                                    .padding(0.dp, 0.dp, 20.dp, 0.dp),
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Normal,
                                    fontStyle = FontStyle.Normal,
                                    fontFamily = poppinsFontFamily,
                                ),
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Row(horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            Text(
                                text = "price per hour : ",
                                modifier = Modifier
                                    .heightIn(min = 0.dp),
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Normal,
                                    fontStyle = FontStyle.Normal,
                                    fontFamily = poppinsFontFamily,
                                ),
                                color = Purple40,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = parking.price_per_hour.toString(),
                                modifier = Modifier
                                    .heightIn(min = 0.dp)
                                    .padding(0.dp, 0.dp, 20.dp, 0.dp),
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Normal,
                                    fontStyle = FontStyle.Normal,
                                    fontFamily = poppinsFontFamily,
                                ),
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                        }

                    }
                }

                Text(
                    text = parking.description,
                    modifier = Modifier
                        .padding(10.dp),

                    style = TextStyle(
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                        fontFamily = poppinsFontFamily,
                    ),
                    color = Color.Black,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(10.dp))
                ElevatedButton(
                    onClick = showModal,
                    modifier = Modifier
                        .shadow(elevation = 4.dp, shape = RoundedCornerShape(25.dp))
                        .heightIn(48.dp)
                    ,
                    contentPadding = PaddingValues(),
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                    shape = RoundedCornerShape(50.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .heightIn(48.dp)
                            .background(
                                brush = Brush.horizontalGradient(listOf(Purple40, Purple60)),
                                shape = RoundedCornerShape(25.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "reservez votre place",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = poppinsFontFamily,
                            modifier = Modifier.padding(15.dp),
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun ParkingDetailsPreview() {
    parkingDetails()
}