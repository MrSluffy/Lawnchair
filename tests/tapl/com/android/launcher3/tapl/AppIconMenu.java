/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.launcher3.tapl;

import static org.junit.Assert.assertTrue;

import androidx.test.uiautomator.UiObject2;

/**
 * Context menu of an app icon.
 */
public class AppIconMenu {
    private final LauncherInstrumentation mLauncher;
    private final UiObject2 mDeepShortcutsContainer;

    AppIconMenu(LauncherInstrumentation launcher,
            UiObject2 deepShortcutsContainer) {
        mLauncher = launcher;
        mDeepShortcutsContainer = deepShortcutsContainer;
    }

    /**
     * Returns a menu item with a given number. Fails if it doesn't exist.
     */
    public AppIconMenuItem getMenuItem(int itemNumber) {
        assertTrue(mDeepShortcutsContainer.getChildCount() > itemNumber);

        final UiObject2 shortcut = mLauncher.waitForObjectInContainer(
                mDeepShortcutsContainer.getChildren().get(itemNumber), "bubble_text");
        return new AppIconMenuItem(mLauncher, shortcut);
    }
}