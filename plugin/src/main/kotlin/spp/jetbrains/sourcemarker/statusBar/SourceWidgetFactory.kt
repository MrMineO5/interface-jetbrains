/*
 * Source++, the open-source live coding platform.
 * Copyright (C) 2022 CodeBrig, Inc.
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
package spp.jetbrains.sourcemarker.statusBar

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.StatusBarWidget
import com.intellij.openapi.wm.impl.status.widget.StatusBarEditorBasedWidgetFactory

class SourceWidgetFactory : StatusBarEditorBasedWidgetFactory() {

    override fun getId(): String = "spp.jetbrains.sourcemarker"
    override fun getDisplayName(): String = "Source++"

    override fun createWidget(project: Project): StatusBarWidget {
        return SourceStatusBarWidget(project)
    }

    override fun disposeWidget(widget: StatusBarWidget) = Unit
}