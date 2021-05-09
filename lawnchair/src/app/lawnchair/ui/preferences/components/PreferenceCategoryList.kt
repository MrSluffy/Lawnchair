/*
 * Copyright 2021, Lawnchair
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.lawnchair.ui.preferences.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import app.lawnchair.ui.preferences.getPreferenceCategories

@Composable
fun PreferenceCategoryList(navController: NavController) {
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier.fillMaxHeight()
    ) {
        items(getPreferenceCategories(context)) { item ->
            PreferenceCategoryListItem(
                label = item.label,
                description = item.description,
                iconResource = item.iconResource,
                onClick = { navController.navigate(item.route) })
        }
    }
}