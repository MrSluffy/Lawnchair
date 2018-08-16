package ch.deletescape.lawnchair.iconpack

import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import com.android.launcher3.FastBitmapDrawable
import com.android.launcher3.ItemInfo
import com.android.launcher3.ItemInfoWithIcon
import com.android.launcher3.ShortcutInfo
import com.android.launcher3.graphics.BitmapInfo
import com.google.android.apps.nexuslauncher.DynamicDrawableFactory
import com.google.android.apps.nexuslauncher.clock.CustomClock

class LawnchairDrawableFactory(context: Context) : DynamicDrawableFactory(context) {

    private val iconPackManager = IconPackManager.getInstance(context)
    val customClockDrawer = CustomClock(context)

    override fun newIcon(info: ItemInfoWithIcon): FastBitmapDrawable {
        return iconPackManager.newIcon((info as? ShortcutInfo)?.customIcon ?: info.iconBitmap, info, this)
    }
}