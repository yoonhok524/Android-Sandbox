package com.aaron.sample.dragndroplist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.aaron.sample.dragndroplist.dragndrop.DragDropColumn
import com.aaron.sample.dragndroplist.dragndrop2.ReorderableItem
import com.aaron.sample.dragndroplist.dragndrop2.detectReorderAfterLongPress
import com.aaron.sample.dragndroplist.dragndrop2.rememberReorderableLazyListState
import com.aaron.sample.dragndroplist.dragndrop2.reorderable
import com.aaron.sample.dragndroplist.ui.theme.DragNDropListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DragNDropListTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val vm: MainViewModel by viewModels()
                    MyScreen2(vm)
                }
            }
        }
    }
}

@Composable
fun MyScreen(vm: MainViewModel) {
    val users = vm.users
    DragDropColumn(items = users, onReorder = vm::swapUser) { user ->
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row() {
                    Image(imageVector = ImageVector.vectorResource(id = R.drawable.ic_person), contentDescription = "")
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("${user.id}")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(user.name)
                }

                Image(imageVector = ImageVector.vectorResource(id = R.drawable.ic_menu), contentDescription = "")
            }

            Divider()
        }
    }
}

@Composable
fun MyScreen2(vm: MainViewModel) {
    val users = vm.users
    val state = rememberReorderableLazyListState(onMove = { from, to ->
        vm.swapUser(from.index, to.index)
    })
    LazyColumn(
        state = state.listState,
        modifier = Modifier
            .reorderable(state)
            .detectReorderAfterLongPress(state)
    ) {
        items(users, { it }) { user ->
            ReorderableItem(state, key = user) { isDragging ->
                val elevation = animateDpAsState(if (isDragging) 16.dp else 0.dp)
                Card(
                    modifier = Modifier.shadow(elevation.value),
                    elevation = if (isDragging) 16.dp else 0.dp
                ) {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.LightGray)
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row {
                                Image(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_person),
                                    contentDescription = ""
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text("${user.id}")
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(user.name)
                            }

                            Image(
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_menu),
                                contentDescription = ""
                            )
                        }

                        Divider()
                    }
                }
            }
        }
    }
}
