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
package spp.jetbrains.marker.js

import com.intellij.icons.AllIcons
import com.intellij.ide.projectView.PresentationData
import com.intellij.ui.SimpleTextAttributes
import com.intellij.ui.treeStructure.SimpleNode
import com.intellij.xdebugger.impl.ui.DebuggerUIUtil
import com.intellij.xdebugger.impl.ui.XDebuggerUIConstants
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import org.apache.commons.lang3.EnumUtils
import spp.jetbrains.marker.ErrorVariableSimpleNode
import spp.protocol.instrument.variable.LiveVariable
import spp.protocol.instrument.variable.LiveVariableScope

/**
 * todo: description.
 *
 * @since 0.6.10
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@Suppress("MagicNumber")
class JavascriptVariableSimpleNode(
    private val variable: LiveVariable,
    private val nodeMap: MutableMap<String, Array<SimpleNode>>
) : SimpleNode() {

    private val scheme = DebuggerUIUtil.getColorScheme(null)

    //todo: LiveSimpleNode super class
    override fun getChildren(): Array<SimpleNode> {
        if (variable.value == null && variable.liveIdentity != null) {
            //found reference, use children of referenced node
            return nodeMap[variable.liveIdentity!!] ?: arrayOf()
        }

        val children = if (variable.value is JsonArray) {
            (variable.value as JsonArray).map { JsonObject.mapFrom(it) }.map {
                if (it.getString("@skip") != null) {
                    ErrorVariableSimpleNode(JsonObject.mapFrom(it).map)
                } else {
                    JavascriptVariableSimpleNode(toLiveVariable(it), nodeMap)
                }
            }.toList().toTypedArray()
        } else if (variable.value is LiveVariable) {
            arrayOf(JavascriptVariableSimpleNode(variable.value as LiveVariable, nodeMap) as SimpleNode)
        } else {
            emptyArray()
        }

        //add children to nodeMap for reference lookup
        if (variable.liveIdentity != null) {
            nodeMap[variable.liveIdentity!!] = children
        }
        return children
    }

    private fun toLiveVariable(it: JsonObject): LiveVariable {
        var varValue = it.getValue("value")
        if (varValue is JsonArray && varValue.size() == 1 &&
            (varValue.first() as JsonObject).containsKey("liveClazz")
        ) {
            varValue = toLiveVariable(varValue.first() as JsonObject)
        }
        return LiveVariable(
            name = it.getString("name"),
            value = varValue,
            lineNumber = it.getInteger("lineNumber") ?: -1,
            scope = EnumUtils.getEnum(LiveVariableScope::class.java, it.getString("scope")),
            liveClazz = it.getString("liveClazz"),
            liveIdentity = it.getString("liveIdentity")
        )
    }

    override fun update(presentation: PresentationData) {
        if (variable.scope == LiveVariableScope.GENERATED_METHOD) {
            presentation.addText(variable.name + " = ", SimpleTextAttributes.GRAYED_ATTRIBUTES)
            presentation.setIcon(AllIcons.Nodes.Method)
        } else {
            presentation.addText(variable.name + " = ", XDebuggerUIConstants.VALUE_NAME_ATTRIBUTES)
        }

        if (childCount > 0) {
            presentation.setIcon(AllIcons.Nodes.Variable)
            presentation.addText("todo", SimpleTextAttributes.REGULAR_ATTRIBUTES)
        } else {
            presentation.setIcon(AllIcons.Nodes.Field)
            presentation.addText(variable.value.toString(), SimpleTextAttributes.REGULAR_ATTRIBUTES)
        }
    }

    override fun getEqualityObjects(): Array<Any> = arrayOf(variable)
}
