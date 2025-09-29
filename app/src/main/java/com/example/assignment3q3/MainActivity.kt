package com.example.assignment3q3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment3q3.ui.theme.Assignment3Q3Theme
import kotlinx.coroutines.launch
import kotlin.random.Random
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment3Q3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ContactsList(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun generateContacts(): List<String> {
    val firstNames = listOf(
        "Alice","Bob","Charlotte","David","Eve","Frank","Grace","Hank","Ivy","Jacky",
        "Karen","Leo","Monday","Nick","Olivia","Paula","Quincy","Rachel","Steven","Tracy",
        "Uma","Victor","Wendy","Xander","Yara","Zane"
    )
    val lastNames = listOf("Smith","Johnson","Brown","Taylor","Power","Lin","Lu","Chen")
    val contacts = mutableListOf<String>()
    for (i in 1..50) {
        val first = firstNames[Random.nextInt(firstNames.size)]
        val last = lastNames[Random.nextInt(lastNames.size)]
        contacts.add("$first $last")
    }
    return contacts.sorted()
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactsList(modifier: Modifier = Modifier) {
    val contacts = generateContacts()
    val grouped = contacts.groupBy { it.first().uppercaseChar() }.toSortedMap()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    var showFab by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect { index -> showFab = index > 10 }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
            grouped.forEach {(letter, names) ->
                stickyHeader {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.LightGray)
                            .padding(8.dp)
                    ) {
                        Text(text = letter.toString(), fontSize = 20.sp)
                    }
                }
                items(names) {name ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(text = name, fontSize = 18.sp)
                    }
                }
            }
        }

        if (showFab) {
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        listState.animateScrollToItem(0)
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Text(text = "Top")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Assignment3Q3Theme {
    }
}