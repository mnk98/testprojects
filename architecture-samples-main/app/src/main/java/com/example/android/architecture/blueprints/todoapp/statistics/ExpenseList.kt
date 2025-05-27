package com.example.android.architecture.blueprints.todoapp.statistics

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.TodoTheme
import com.example.android.architecture.blueprints.todoapp.expense.data.Expense
import com.example.android.architecture.blueprints.todoapp.util.LoadingContent

//@Composable
//fun ExpenseListContent(
//    loading: Boolean,
//    expenses: List<Expense>,
//    @StringRes currentFilteringLabel: Int,
//    @StringRes noTasksLabel: Int,
//    @DrawableRes noTasksIconRes: Int,
//    onRefresh: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    LoadingContent(
//        loading = loading,
//        empty = expenses.isEmpty() && !loading,
//        emptyContent = { ExpenseEmptyContent(noTasksLabel, noTasksIconRes, modifier) },
//        onRefresh = onRefresh
//    ) {
//        Column(
//            modifier = modifier
//                .fillMaxSize()
//                .padding(horizontal = dimensionResource(id = R.dimen.horizontal_margin))
//        ) {
//            Text(
//                text = stringResource(currentFilteringLabel),
//                modifier = Modifier.padding(
//                    horizontal = dimensionResource(id = R.dimen.list_item_padding),
//                    vertical = dimensionResource(id = R.dimen.vertical_margin)
//                ),
//                style = MaterialTheme.typography.headlineSmall
//            )
//            LazyColumn {
//                items(expenses) { expense ->
//                    ExpenseItem(
//                        expense = expense
//                    )
//                }
//            }
//        }
//    }
//}

@Composable
private fun ExpenseItem(
    expense: Expense,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
//                horizontal = dimensionResource(id = R.dimen.horizontal_margin),
                vertical = dimensionResource(id = R.dimen.list_item_padding),
            )
    ) {
        Text(
            text = expense.name,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen.horizontal_margin)
            ),
        )
        Text(
            text = expense.amount.toString(),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen.horizontal_margin)
            ),
        )
    }
}

@Composable
private fun ExpenseEmptyContent(
    @StringRes noTasksLabel: Int,
    @DrawableRes noTasksIconRes: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = noTasksIconRes),
            contentDescription = stringResource(R.string.no_tasks_image_content_description),
            modifier = Modifier.size(96.dp)
        )
        Text(stringResource(id = noTasksLabel))
    }
}

@Composable
private fun ExpensesContent(
    loading: Boolean,
    expenses: List<Expense>,
    @StringRes currentFilteringLabel: Int,
    @StringRes noTasksLabel: Int,
    @DrawableRes noTasksIconRes: Int,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    LoadingContent(
        loading = loading,
        empty = expenses.isEmpty() && !loading,
        emptyContent = { ExpenseEmptyContent(noTasksLabel, noTasksIconRes, modifier) },
        onRefresh = onRefresh
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = dimensionResource(id = R.dimen.horizontal_margin))
        ) {
            Text(
                text = stringResource(currentFilteringLabel),
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.list_item_padding),
                    vertical = dimensionResource(id = R.dimen.vertical_margin)
                ),
                style = MaterialTheme.typography.headlineSmall
            )
            LazyColumn {
                items(expenses) { expense ->
                    ExpenseItem(
                        expense = expense,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ExpensesContentPreview() {
    MaterialTheme {
        Surface {
            ExpensesContent(
                loading = false,
                expenses = listOf(
                    Expense(
                        name = "Name 1",
                        amount = 200.0,
                        id = "ID 1"
                    ),
                    Expense(
                        name = "Name 2",
                        amount = 200.0,
                        id = "ID 2"
                    ),
                    Expense(
                        name = "Name 3",
                        amount = 200.0,
                        id = "ID 3"
                    ),
                    Expense(
                        name = "Name 4",
                        amount = 200.0,
                        id = "ID 4"
                    ),
                    Expense(
                        name = "Name 5",
                        amount = 200.0,
                        id = "ID 5"
                    ),
                ),
                currentFilteringLabel = R.string.label_all_expense,
                noTasksLabel = R.string.no_expenses_all,
                noTasksIconRes = R.drawable.logo_no_fill,
                onRefresh = { },
            )
        }
    }
}

@Preview
@Composable
private fun ExpensesContentEmptyPreview() {
    MaterialTheme {
        Surface {
            ExpensesContent(
                loading = false,
                expenses = emptyList(),
                currentFilteringLabel = R.string.label_all_expense,
                noTasksLabel = R.string.no_expenses_all,
                noTasksIconRes = R.drawable.logo_no_fill,
                onRefresh = { },
            )
        }
    }
}

@Composable
private fun ExpensesEmptyContent(
    @StringRes noTasksLabel: Int,
    @DrawableRes noTasksIconRes: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = noTasksIconRes),
            contentDescription = stringResource(R.string.no_tasks_image_content_description),
            modifier = Modifier.size(96.dp)
        )
        Text(stringResource(id = noTasksLabel))
    }
}

@Preview
@Composable
private fun ExpensesEmptyContentPreview() {
    TodoTheme {
        Surface {
            ExpensesEmptyContent(
                noTasksLabel = R.string.no_expenses_all,
                noTasksIconRes = R.drawable.logo_no_fill
            )
        }
    }
}

@Preview
@Composable
private fun ExpenseItemPreview() {
    MaterialTheme {
        Surface {
            ExpenseItem(
                expense = Expense(
                    name = "Name",
                    amount = 200.0,
                    id = "ID"
                )
            )
        }
    }
}

@Preview
@Composable
private fun ExpenseItemCompletedPreview() {
    MaterialTheme {
        Surface {
            ExpenseItem(
                expense = Expense(
                    name = "Name",
                    amount = 200.0,
                    id = "ID"
                )
            )
        }
    }
}