/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.architecture.blueprints.todoapp.util

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.TodoDestinations
import com.example.android.architecture.blueprints.todoapp.TodoNavigationActions
import com.example.android.architecture.blueprints.todoapp.TodoTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

enum class TodoDrawerItem(
    @StringRes val title: Int,
    @DrawableRes val iconRes: Int,
    val route: String
) {
    Tasks(R.string.list_title, R.drawable.ic_list, TodoDestinations.TASKS_ROUTE),
    Statistics(R.string.statistics_title, R.drawable.ic_statistics, TodoDestinations.STATISTICS_ROUTE),
    AddContributor(R.string.add_contributor, R.drawable.ic_person_add, TodoDestinations.ADD_CONTRIBUTOR_ROUTE),
    AddExpense(R.string.add_expense, R.drawable.ic_add_expense, TodoDestinations.ADD_EXPENSE_ROUTE);

    @Composable
    fun icon(): Painter = painterResource(id = iconRes)
}

@Composable
fun AppModalDrawer(
    drawerState: DrawerState,
    currentRoute: String,
    navigationActions: TodoNavigationActions,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                currentRoute = currentRoute,
                navigateToTasks = { navigationActions.navigateToTasks() },
                navigateToStatistics = { navigationActions.navigateToStatistics() },
                navigateToAddContributor = { navigationActions.navigateToAddContributor() },
                navigateToAddExpense = { navigationActions.navigateToAddExpense() },
                closeDrawer = { coroutineScope.launch { drawerState.close() } }
            )
        }
    ) {
        content()
    }
}

@Composable
private fun AppDrawer(
    currentRoute: String,
    navigateToTasks: () -> Unit,
    navigateToStatistics: () -> Unit,
    navigateToAddContributor: () -> Unit,
    navigateToAddExpense: () -> Unit,
    closeDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(color = MaterialTheme.colorScheme.background) {
        Column(modifier = modifier.fillMaxSize()) {
            DrawerHeader()
            DrawerItem(
                item = TodoDrawerItem.Tasks,
                selected = currentRoute == TodoDrawerItem.Tasks.route,
                onItemClick = { navigateToTasks(); closeDrawer() }
            )
            DrawerItem(
                item = TodoDrawerItem.Statistics,
                selected = currentRoute == TodoDrawerItem.Statistics.route,
                onItemClick = { navigateToStatistics(); closeDrawer() }
            )
            DrawerItem(
                item = TodoDrawerItem.AddContributor,
                selected = currentRoute == TodoDrawerItem.AddContributor.route,
                onItemClick = { navigateToAddContributor(); closeDrawer() }
            )
            DrawerItem(
                item = TodoDrawerItem.AddExpense,
                selected = currentRoute == TodoDrawerItem.AddExpense.route,
                onItemClick = { navigateToAddExpense(); closeDrawer() }
            )
        }
    }
}

@Composable
private fun DrawerItem(item: TodoDrawerItem, selected: Boolean, onItemClick: () -> Unit) {
    val backgroundColor = if (selected) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.surface
    }
    val contentColor = if (selected) {
        MaterialTheme.colorScheme.onPrimaryContainer
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        color = backgroundColor,
        shape = MaterialTheme.shapes.small
    ) {
        TextButton(
            onClick = onItemClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = item.icon(),
                    contentDescription = stringResource(id = item.title),
                    colorFilter = ColorFilter.tint(contentColor),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(Modifier.width(16.dp))
                Text(
                    text = stringResource(id = item.title),
                    style = MaterialTheme.typography.bodyLarge,
                    color = contentColor
                )
            }
        }
    }
}

@Composable
private fun DrawerHeader(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.header_height))
            .padding(dimensionResource(id = R.dimen.header_padding))
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_no_fill),
            contentDescription =
                stringResource(id = R.string.tasks_header_image_content_description),
            modifier = Modifier.width(dimensionResource(id = R.dimen.header_image_width))
        )
        Text(
            text = stringResource(id = R.string.navigation_view_header_title),
            color = MaterialTheme.colorScheme.surface
        )
    }
}

@Preview("Drawer contents")
@Composable
fun PreviewAppDrawer() {
    TodoTheme {
        Surface {
            AppDrawer(
                currentRoute = TodoDestinations.TASKS_ROUTE,
                navigateToTasks = {},
                navigateToStatistics = {},
                navigateToAddContributor = {},
                navigateToAddExpense = {},
                closeDrawer = {},
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
