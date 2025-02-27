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
package com.android.customization.picker.qs

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import androidx.lifecycle.LifecycleOwner
import com.android.customization.picker.qs.ui.binder.QSSectionBinder
import com.android.customization.picker.qs.ui.view.QSSectionView
import com.android.customization.picker.qs.ui.viewmodel.QSSectionViewModel
import com.android.themepicker.R
import com.android.wallpaper.model.CustomizationSectionController

/** Controls a section with UI that lets the user toggle QS settings. */
class QSSectionController(
    private val viewModel: QSSectionViewModel,
    private val lifecycleOwner: LifecycleOwner,
) : CustomizationSectionController<QSSectionView> {
    override fun isAvailable(context: Context): Boolean = true

    @SuppressLint("InflateParams") // We don't care that the parent is null.
    override fun createView(context: Context): QSSectionView {
        val view = LayoutInflater.from(context).inflate(
            R.layout.qs_section,
            null,
        ) as QSSectionView

        QSSectionBinder.bind(
            view = view,
            viewModel = viewModel,
            lifecycleOwner = lifecycleOwner,
        )

        return view
    }
}
