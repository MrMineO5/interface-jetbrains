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
package spp.jetbrains.view

/**
 * todo: description.
 *
 * @since 0.7.6
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
abstract class ResumableViewCollection : ResumableView {

    private val views: MutableList<ResumableView> = mutableListOf()
    override val isRunning: Boolean
        get() = views.any { it.isRunning }
    val size: Int
        get() = views.size

    fun addView(view: ResumableView) {
        views.add(view)
    }

    fun getViews(): List<ResumableView> {
        return views.toList()
    }

    override fun resume() {
        if (isRunning) return
        views.forEach { it.resume() }
    }

    override fun pause() {
        if (!isRunning) return
        views.forEach { it.pause() }
    }

    override fun setRefreshInterval(interval: Int) {
        pause()
        views.forEach { it.setRefreshInterval(interval) }
        resume()
    }

    override fun dispose() = pause()
}
