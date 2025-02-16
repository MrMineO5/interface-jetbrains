/*
 * Source++, the continuous feedback platform for developers.
 * Copyright (C) 2022-2023 CodeBrig, Inc.
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
package spp.jetbrains.sourcemarker.instrument.ui

import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.table.JBTable
import com.intellij.util.ui.ListTableModel
import spp.jetbrains.sourcemarker.instrument.InstrumentEventWindowService
import spp.jetbrains.sourcemarker.instrument.ui.column.LiveInstrumentEventColumnInfo
import spp.jetbrains.sourcemarker.instrument.ui.model.InstrumentOverview
import spp.jetbrains.sourcemarker.instrument.ui.renderer.InstrumentTypeTableCellRenderer
import spp.protocol.instrument.event.LiveBreakpointHit
import spp.protocol.instrument.event.LiveInstrumentEvent
import java.awt.BorderLayout
import java.awt.Point
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*

/**
 * todo: description.
 *
 * @since 0.7.7
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
class InstrumentEventTab(val project: Project, val overview: InstrumentOverview) : Disposable {

    val model = ListTableModel<LiveInstrumentEvent>(
        arrayOf(
            LiveInstrumentEventColumnInfo("Occurred At"),
            LiveInstrumentEventColumnInfo("Event Type"),
            LiveInstrumentEventColumnInfo("Data")
        ),
        ArrayList(), 0, SortOrder.DESCENDING
    )
    private val table = JBTable(model)
    val component = JPanel(BorderLayout()).apply {
        add(JBScrollPane(table), BorderLayout.CENTER)
    }

    init {
        table.setShowColumns(true)
        table.selectionModel.selectionMode = ListSelectionModel.SINGLE_SELECTION
        table.setDefaultRenderer(
            InstrumentTypeTableCellRenderer::class.java,
            InstrumentTypeTableCellRenderer()
        )
        table.addMouseListener(object : MouseAdapter() {
            override fun mousePressed(mouseEvent: MouseEvent) {
                val point: Point = mouseEvent.point
                val row = table.rowAtPoint(point)
                if (mouseEvent.clickCount == 2 && row >= 0) {
                    val event = model.getItem(table.convertRowIndexToModel(row))
                    if (event is LiveBreakpointHit) {
                        ApplicationManager.getApplication().invokeLater {
                            InstrumentEventWindowService.getInstance(project).showBreakpointHit(event)
                        }
                    }
                }
            }
        })
        table.inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter")
        table.actionMap.put("enter", object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent) {
                val event = model.getItem(table.convertRowIndexToModel(table.selectedRow))
                if (event is LiveBreakpointHit) {
                    ApplicationManager.getApplication().invokeLater {
                        InstrumentEventWindowService.getInstance(project).showBreakpointHit(event)
                    }
                }
            }
        })

        overview.events.forEach { model.addRow(it) }
    }

    override fun dispose() = Unit
}
