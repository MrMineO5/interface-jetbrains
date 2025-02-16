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
package spp.jetbrains.insight.pass.artifact

import spp.jetbrains.artifact.model.ArtifactElement
import spp.jetbrains.artifact.model.CallArtifact
import spp.jetbrains.insight.InsightKeys
import spp.jetbrains.insight.pass.ArtifactPass
import spp.jetbrains.insight.path.ProceduralPath

/**
 * Loads the [ProceduralPath] set from [CallArtifact]s that have already been processed. Allows for basic
 * interprocedural analyses for artifacts with known [ProceduralPath]s.
 */
class LoadPsiPass : ArtifactPass {

    override fun analyze(element: ArtifactElement) {
        if (element !is CallArtifact) return //only interested in calls

        val resolvedFunction = element.getResolvedFunction()
        if (resolvedFunction != null) {
            val multiPath = resolvedFunction.getUserData(InsightKeys.PROCEDURAL_MULTI_PATH.asPsiKey())
            if (multiPath != null) {
                element.data[InsightKeys.PROCEDURAL_MULTI_PATH] = multiPath
            }
        }
    }
}
