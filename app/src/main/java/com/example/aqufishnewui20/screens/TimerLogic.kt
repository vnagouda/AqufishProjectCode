package com.example.aqufishnewui20.screens

//import androidx.compose.foundation.gestures.pointerInput

//import com.example.aqufishnewui20.workers.scheduleAlarm
import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.aqufishnewui20.viewModels.MainViewModel
import kotlinx.coroutines.flow.Flow
import java.util.UUID

//import java.util.concurrent.TimeUnit



@Entity(tableName = "alarms")
data class Alarm(
    @PrimaryKey val id: UUID,
    val hour: Int,
    val minute: Int,
    val timestamp: Long
)

@Dao
interface AlarmDao {
    @Query("SELECT * FROM alarms")
    fun getAllAlarms(): Flow<List<Alarm>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarm(alarm: Alarm)

    @Query("DELETE FROM alarms WHERE id = :id")
    suspend fun deleteAlarmById(id: UUID)
}


@Database(entities = [Alarm::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "alarm_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("NewApi")
@Composable
fun AlarmCard(context: Context, viewModel: MainViewModel) {
    var showDialog by remember { mutableStateOf(false) }


    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ){

        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                //.wrapContentHeight()
                .animateContentSize(),

            //elevation = CardDefaults.cardElevation(16.dp),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary)
        ) {
            Column(
                modifier = Modifier
                    //.wrapContentHeight()
                    .padding(16.dp)
            ) {
                // Display Motor Status
                val isAlarmTriggered by viewModel.isAlarmTriggered.collectAsState()

                Text(
                    text = if (isAlarmTriggered) "Daily Feeding Time" else "Daily Feeding Time",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isAlarmTriggered) Color.Green else Color.Red
                    ),
                    modifier = Modifier.padding(16.dp)
                )

                Button(
                    onClick = {
                        showDialog = true
                    },
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text("Set Alarm")
                }

                // Display Alarm Times
                val alarmTimes by viewModel.alarmTimes.collectAsState()

                if (alarmTimes.isNotEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        alarmTimes.forEach { (timePair, workId) ->
                            val (hour, minute) = timePair
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "${hour.toString().padStart(2, '0')}:${
                                        minute.toString().padStart(2, '0')
                                    }",
                                    style = TextStyle(
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                IconButton(
                                    onClick = {
                                        viewModel.deleteAlarm(hour, minute)
                                    }
                                ) {
                                    Icon(Icons.Filled.Delete, contentDescription = "Delete")
                                }
                            }
                        }
                    }
                }
            }
        }

        if (showDialog) {
            AlertDialog(onDismissRequest = { showDialog = false }) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .wrapContentHeight()
                    ) {
                        TimePickerWithAlarms(viewModel)

                        Button(
                            onClick = {
                                viewModel.addAlarm(context = context)
                                showDialog = false
                            },
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                                .align(Alignment.CenterHorizontally)
                        ) {
                            Text("Add Alarm")
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimePickerWithAlarms(viewModel: MainViewModel = viewModel()) {
    val numberStyle = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )
    val selectedNumberStyle = numberStyle.copy(fontSize = 36.sp)

    // Define the height for visible items
    val visibleItemCount = 3
    val visibleItemHeight = 50.dp
    val itemHeightModifier = if (visibleItemCount == 1) {
        Modifier.height(visibleItemHeight * 3) // Ensure at least 3 items height when visibleItemCount is 1
    } else {
        Modifier.height(visibleItemHeight * visibleItemCount)
    }

    val hoursScrollState = rememberLazyListState()
    val minutesScrollState = rememberLazyListState()

    val hoursList = listOf(-1) + (0..23).toList() + listOf(60)
    val minutesList = listOf(-1) + (0..59).toList() + listOf(60)

    @Composable
    fun CircularScrollPicker(
        items: List<Int>,
        selectedItem: Int,
        scrollState: LazyListState,
        onItemSelected: (Int) -> Unit
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 16.dp),
            state = scrollState,
            verticalArrangement = Arrangement.Center
        ) {
            items(items) { item ->
                val text = when (item) {
                    -1 -> ""
                    60 -> ""
                    else -> item.toString().padStart(2, '0')
                }
                val isSelected = item == selectedItem
                val style = if (isSelected) selectedNumberStyle else numberStyle

                Text(
                    text = text,
                    style = style,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                )
            }
        }

        LaunchedEffect(scrollState.firstVisibleItemIndex) {
            val visibleItems = items
            val centerItem = visibleItems[(scrollState.firstVisibleItemIndex + visibleItemCount / 2) % visibleItems.size]
            onItemSelected(centerItem)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .then(itemHeightModifier)
                    .padding(8.dp)
            ) {
                CircularScrollPicker(
                    items = hoursList,
                    selectedItem = viewModel.selectedHour,
                    scrollState = hoursScrollState,
                    onItemSelected = { viewModel.selectedHour = it }
                )
            }

            Text(
                text = ":",
                style = numberStyle,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .align(Alignment.CenterVertically)
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .then(itemHeightModifier)
                    .padding(8.dp)
            ) {
                CircularScrollPicker(
                    items = minutesList,
                    selectedItem = viewModel.selectedMinute,
                    scrollState = minutesScrollState,
                    onItemSelected = { viewModel.selectedMinute = it }
                )
            }
        }

        // Observe the alarm status from the ViewModel

    }
}


