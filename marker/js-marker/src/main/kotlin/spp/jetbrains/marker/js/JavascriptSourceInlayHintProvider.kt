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

import com.intellij.codeInsight.hints.InlayHintsSink
import com.intellij.codeInsight.hints.presentation.InlayPresentation
import com.intellij.lang.javascript.psi.JSCallExpression
import com.intellij.lang.javascript.psi.JSFunction
import com.intellij.lang.javascript.psi.JSStatement
import com.intellij.psi.PsiElement
import spp.jetbrains.marker.plugin.SourceInlayHintProvider
import spp.jetbrains.marker.source.mark.inlay.InlayMark
import spp.jetbrains.marker.source.mark.inlay.config.InlayMarkVirtualText

/**
 * todo: description.
 *
 * @since 0.6.10
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
class JavascriptSourceInlayHintProvider : SourceInlayHintProvider() {

    override fun createInlayMarkIfNecessary(element: PsiElement): InlayMark? {
        TODO("Not yet implemented")
    }

    override fun displayVirtualText(
        element: PsiElement,
        virtualText: InlayMarkVirtualText,
        sink: InlayHintsSink,
        representation: InlayPresentation
    ) {
        var statement = if (element is JSStatement) element else element
        if (virtualText.useInlinePresentation) {
            if (virtualText.showAfterLastChildWhenInline) {
                if (statement is JSCallExpression) {
                    statement = statement.parent //todo: more dynamic
                }
                sink.addInlineElement(
                    statement.lastChild.textRange.endOffset,
                    virtualText.relatesToPrecedingText,
                    representation
                )
            } else {
                sink.addInlineElement(
                    statement.textRange.startOffset,
                    virtualText.relatesToPrecedingText,
                    representation
                )
            }
        } else {
            if (statement.parent is JSFunction) {
                virtualText.spacingTillMethodText = statement.parent.prevSibling.text
                    .replace("\n", "").count { it == ' ' }
            }

            var startOffset = statement.textRange.startOffset
//            if (virtualText.showBeforeAnnotationsWhenBlock) {
//                if (statement.parent is JSFunction) {
//                    val annotations = (statement.parent as JSFunction).decoratorList?.decorators ?: emptyArray()
//                    if (annotations.isNotEmpty()) {
//                        startOffset = annotations[0].textRange.startOffset
//                    }
//                }
//            }
            sink.addBlockElement(
                startOffset,
                virtualText.relatesToPrecedingText,
                virtualText.showAbove,
                0,
                representation
            )
        }
    }
}