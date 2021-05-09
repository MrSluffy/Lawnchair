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

package app.lawnchair.ui.preferences

import android.app.Application
import android.content.ComponentName
import android.content.Intent
import android.content.pm.ResolveInfo
import android.provider.Settings
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.android.launcher3.R
import com.android.launcher3.notification.NotificationListener

class PreferenceViewModel(application: Application) : AndroidViewModel(application), PreferenceInteractor {
    private val lawnchairNotificationListener = ComponentName(application, NotificationListener::class.java)
    private val enabledNotificationListeners: String? by lazy {
        Settings.Secure.getString(
            application.contentResolver,
            "enabled_notification_listeners"
        )
    }

    override val notificationDotsEnabled: MutableState<Boolean> =
        mutableStateOf(enabledNotificationListeners?.contains(lawnchairNotificationListener.flattenToString()) == true)

    override fun getIconPacks(): List<IconPackInfo> {
        val pm = getApplication<Application>().packageManager
        val iconPacks: MutableMap<String, IconPackInfo> = HashMap()
        val list: MutableList<ResolveInfo> = pm.queryIntentActivities(Intent("com.novalauncher.THEME"), 0)

        list.addAll(pm.queryIntentActivities(Intent("org.adw.launcher.icons.ACTION_PICK_ICON"), 0))
        list.addAll(pm.queryIntentActivities(Intent("com.dlto.atom.launcher.THEME"), 0))
        list.addAll(
            pm.queryIntentActivities(Intent("android.intent.action.MAIN").addCategory("com.anddoes.launcher.THEME"), 0)
        )

        for (info in list) {
            iconPacks[info.activityInfo.packageName] = IconPackInfo(
                info.loadLabel(pm).toString(),
                info.activityInfo.packageName,
                info.loadIcon(pm)
            )
        }

        val iconPackList = iconPacks.values.toMutableList()
        iconPackList.sortBy { it.name }
        iconPackList.add(
            0,
            IconPackInfo(
                "System Icons",
                "",
                AppCompatResources.getDrawable(getApplication(), R.drawable.ic_launcher_home)!!
            )
        )

        return iconPackList
    }
}
