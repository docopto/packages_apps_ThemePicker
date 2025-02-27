/*
 * Copyright (C) 2025 FloraOS Project
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
 *
 */
package com.android.customization.picker.qs.ui.viewmodel

import android.os.UserHandle
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.android.customization.model.theme.OverlayManagerCompat
import com.google.android.material.materialswitch.MaterialSwitch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/** Models UI state for a section that lets the user control the notification settings. */
class QSSectionViewModel
@VisibleForTesting
constructor(
    private val overlayManagerCompat: OverlayManagerCompat,
) : ViewModel() {
    /** Whether the switch should be on. */
    fun isSwitchOn(): Boolean = overlayManagerCompat.getEnabledPackageName(
        SYSTEMUI_PACKAGE, QS_GRADIENT_OVERLAY_CATEGORY
    ) != null

    /** Notifies that the section has been clicked. */
    fun onClicked(switch: MaterialSwitch) {
        viewModelScope.launch {
            if (switch.isChecked) {
                overlayManagerCompat.disableOverlay(
                    QS_GRADIENT_OVERLAY_PACKAGAE,
                    UserHandle.myUserId()
                )
                switch.isChecked = false
            } else {
                overlayManagerCompat.setEnabledExclusiveInCategory(
                    QS_GRADIENT_OVERLAY_PACKAGAE,
                    UserHandle.myUserId()
                )
                switch.isChecked = true
            }
        }
    }

    class Factory(
        private val overlayManagerCompat: OverlayManagerCompat,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = QSSectionViewModel(
            overlayManagerCompat = overlayManagerCompat,
        ) as T
    }

    companion object {
        const val SYSTEMUI_PACKAGE = "com.android.systemui"
        const val QS_GRADIENT_OVERLAY_CATEGORY = "android.theme.customization.qs_panel_gradient"
        const val QS_GRADIENT_OVERLAY_PACKAGAE = "org.lineageos.overlay.customization.qs.qsgradient"
    }
}
