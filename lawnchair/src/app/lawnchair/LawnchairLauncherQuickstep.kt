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

package app.lawnchair

import android.content.Context
import android.content.ContextWrapper
import com.android.launcher3.uioverrides.QuickstepLauncher
import com.android.systemui.plugins.shared.LauncherOverlayManager
import app.lawnchair.nexuslauncher.OverlayCallbackImpl
import app.lawnchair.util.restartLauncher
import com.android.launcher3.LauncherAppState

open class LawnchairLauncherQuickstep : QuickstepLauncher() {
    private var paused = false
    var defaultOverlay: OverlayCallbackImpl? = null

    override fun getDefaultOverlay(): LauncherOverlayManager {
        defaultOverlay = OverlayCallbackImpl(this)
        return defaultOverlay!!
    }

    override fun onResume() {
        super.onResume()

        restartIfPending()

        paused = false
    }

    override fun onPause() {
        super.onPause()

        paused = true
    }

    open fun restartIfPending() {
        if (sRestart) {
            lawnchairApp.restart(false)
        }
    }

    fun scheduleRestart() {
        if (paused) {
            sRestart = true
        } else {
            restartLauncher(this)
        }
    }

    companion object {
        var sRestart = false
        @JvmStatic
        fun getLauncher(context: Context): LawnchairLauncherQuickstep {
            return context as? LawnchairLauncherQuickstep
                    ?: (context as ContextWrapper).baseContext as? LawnchairLauncherQuickstep
                    ?: LauncherAppState.getInstance(context).launcher as LawnchairLauncherQuickstep
        }
    }
}