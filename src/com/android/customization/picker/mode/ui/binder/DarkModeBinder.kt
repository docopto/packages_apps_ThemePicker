/*
 * Copyright (C) 2024 The Android Open Source Project
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

package com.android.customization.picker.mode.ui.binder

import android.widget.Switch
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.android.customization.picker.mode.ui.viewmodel.DarkModeViewModel
import kotlinx.coroutines.launch

object DarkModeBinder {
    fun bind(darkModeToggle: Switch, viewModel: DarkModeViewModel, lifecycleOwner: LifecycleOwner) {
        lifecycleOwner.lifecycleScope.launch {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { viewModel.isEnabled.collect { darkModeToggle.isEnabled = it } }
                launch { viewModel.previewingIsDarkMode.collect { darkModeToggle.isChecked = it } }
                launch {
                    viewModel.toggleDarkMode.collect {
                        darkModeToggle.setOnCheckedChangeListener { _, _ -> it.invoke() }
                    }
                }
            }
        }
    }
}
