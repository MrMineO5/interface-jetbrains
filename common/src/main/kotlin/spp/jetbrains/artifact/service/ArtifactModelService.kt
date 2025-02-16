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
package spp.jetbrains.artifact.service

import com.intellij.psi.PsiElement
import spp.jetbrains.artifact.model.ArtifactElement
import spp.jetbrains.artifact.service.define.AbstractSourceMarkerService
import spp.jetbrains.artifact.service.define.IArtifactModelService

/**
 * Language-agnostic artifact model service.
 *
 * @since 0.7.5
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
object ArtifactModelService : AbstractSourceMarkerService<IArtifactModelService>(), IArtifactModelService {

    override fun toArtifact(element: PsiElement): ArtifactElement? {
        return getService(element.language).toArtifact(element)
    }
}

// Extensions

fun PsiElement?.toArtifact(): ArtifactElement? {
    return this?.let { ArtifactModelService.toArtifact(it) }
}
