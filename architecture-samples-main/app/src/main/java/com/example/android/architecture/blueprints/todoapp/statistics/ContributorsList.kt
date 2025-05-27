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
import com.example.android.architecture.blueprints.todoapp.contributor.data.Contributor
import com.example.android.architecture.blueprints.todoapp.util.LoadingContent

@Composable
fun ContributorListContent(
    loading: Boolean,
    contributors: List<Contributor>,
    @StringRes currentFilteringLabel: Int,
    @StringRes noTasksLabel: Int,
    @DrawableRes noTasksIconRes: Int,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    LoadingContent(
        loading = loading,
        empty = contributors.isEmpty() && !loading,
        emptyContent = { ContributorEmptyContent(noTasksLabel, noTasksIconRes, modifier) },
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
                items(contributors) { contributor ->
                    ContributorItem(
                        contributor = contributor
                    )
                }
            }
        }
    }
}

@Composable
private fun ContributorItem(
    contributor: Contributor,
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
            text = contributor.name+"( "+contributor.address+" )",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen.horizontal_margin)
            ),
        )
        Text(
            text = contributor.amount.toString(),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen.horizontal_margin)
            ),
        )
    }
}

@Composable
private fun ContributorEmptyContent(
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
private fun ContributorsContent(
    loading: Boolean,
    contributors: List<Contributor>,
    @StringRes currentFilteringLabel: Int,
    @StringRes noTasksLabel: Int,
    @DrawableRes noTasksIconRes: Int,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    LoadingContent(
        loading = loading,
        empty = contributors.isEmpty() && !loading,
        emptyContent = { ContributorEmptyContent(noTasksLabel, noTasksIconRes, modifier) },
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
                items(contributors) { contributor ->
                    ContributorItem(
                        contributor = contributor,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ContributorsContentPreview() {
    MaterialTheme {
        Surface {
            ContributorsContent(
                loading = false,
                contributors = listOf(
                    Contributor(
                        name = "Name 1",
                        address = "Address 1",
                        amount = 200.0,
                        id = "ID 1"
                    ),
                    Contributor(
                        name = "Name 2",
                        address = "Address 2",
                        amount = 200.0,
                        id = "ID 2"
                    ),
                    Contributor(
                        name = "Name 3",
                        address = "Address 3",
                        amount = 200.0,
                        id = "ID 3"
                    ),
                    Contributor(
                        name = "Name 4",
                        address = "Address 4",
                        amount = 200.0,
                        id = "ID 4"
                    ),
                    Contributor(
                        name = "Name 5",
                        address = "Address 5",
                        amount = 200.0,
                        id = "ID 5"
                    ),
                ),
                currentFilteringLabel = R.string.label_all,
                noTasksLabel = R.string.no_contributors_all,
                noTasksIconRes = R.drawable.logo_no_fill,
                onRefresh = { },
            )
        }
    }
}

@Preview
@Composable
private fun ContributorsContentEmptyPreview() {
    MaterialTheme {
        Surface {
            ContributorsContent(
                loading = false,
                contributors = emptyList(),
                currentFilteringLabel = R.string.label_all,
                noTasksLabel = R.string.no_contributors_all,
                noTasksIconRes = R.drawable.logo_no_fill,
                onRefresh = { },
            )
        }
    }
}

@Composable
private fun ContributorsEmptyContent(
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
private fun ContributorsEmptyContentPreview() {
    TodoTheme {
        Surface {
            ContributorsEmptyContent(
                noTasksLabel = R.string.no_contributors_all,
                noTasksIconRes = R.drawable.logo_no_fill
            )
        }
    }
}

@Preview
@Composable
private fun ContributorItemPreview() {
    MaterialTheme {
        Surface {
            ContributorItem(
                contributor = Contributor(
                    name = "Name",
                    address = "Address",
                    amount = 200.0,
                    id = "ID"
                )
            )
        }
    }
}

@Preview
@Composable
private fun ContributorItemCompletedPreview() {
    MaterialTheme {
        Surface {
            ContributorItem(
                contributor = Contributor(
                    name = "Name",
                    address = "Address",
                    amount = 200.0,
                    id = "ID"
                )
            )
        }
    }
}