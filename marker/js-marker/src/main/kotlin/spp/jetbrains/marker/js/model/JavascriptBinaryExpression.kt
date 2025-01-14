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
package spp.jetbrains.marker.js.model

import com.intellij.lang.javascript.psi.JSBinaryExpression
import spp.jetbrains.artifact.model.ArtifactBinaryExpression
import spp.jetbrains.artifact.model.ArtifactElement
import spp.jetbrains.artifact.service.ArtifactModelService

class JavascriptBinaryExpression(override val psiElement: JSBinaryExpression) : ArtifactBinaryExpression(psiElement) {

    override fun getOperator(): String {
        return psiElement.operationNode?.text ?: ""
    }

    override fun getLeftExpression(): ArtifactElement? {
        return psiElement.lOperand?.let { ArtifactModelService.toArtifact(it) }
    }

    override fun getRightExpression(): ArtifactElement? {
        return psiElement.rOperand?.let { ArtifactModelService.toArtifact(it) }
    }

    override fun clone(): JavascriptBinaryExpression {
        return JavascriptBinaryExpression(psiElement)
    }
}
