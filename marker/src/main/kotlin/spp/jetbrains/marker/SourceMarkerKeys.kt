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
package spp.jetbrains.marker

import spp.jetbrains.SourceKey
import spp.jetbrains.marker.source.info.EndpointDetector
import spp.jetbrains.marker.source.info.LoggerDetector
import spp.jetbrains.marker.source.mark.api.SourceMark
import spp.jetbrains.state.LiveStateBar
import spp.protocol.instrument.LiveInstrumentType
import spp.protocol.service.listen.LiveInstrumentListener
import spp.protocol.service.listen.LiveViewEventListener

/**
 * Used to associate custom data to [SourceMark]s.
 *
 * @since 0.1.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
object SourceMarkerKeys {
    @JvmStatic
    val ENDPOINT_DETECTOR = SourceKey<EndpointDetector<*>>("ENDPOINT_DETECTOR")

    @JvmStatic
    val LOGGER_DETECTOR = SourceKey<LoggerDetector>("LOGGER_DETECTOR")

    @JvmStatic
    val INSTRUMENT_ID = SourceKey<String>("INSTRUMENT_ID")

    @JvmStatic
    val VIEW_SUBSCRIPTION_ID = SourceKey<String>("VIEW_SUBSCRIPTION_ID")

    @JvmStatic
    val GROUPED_MARKS = SourceKey<MutableList<SourceMark>>("GROUPED_MARKS")

    @JvmStatic
    val INSTRUMENT_EVENT_LISTENERS = SourceKey<MutableSet<LiveInstrumentListener>>("INSTRUMENT_EVENT_LISTENERS")

    @JvmStatic
    val VIEW_EVENT_LISTENERS = SourceKey<MutableSet<LiveViewEventListener>>("LIVE_VIEW_EVENT_LISTENERS")

    @JvmStatic
    val STATE_BAR = SourceKey<LiveStateBar>("STATE_BAR")

    @JvmStatic
    val INSTRUMENT_TYPE = SourceKey<LiveInstrumentType>("INSTRUMENT_TYPE")

    @JvmStatic
    val VCS_MODIFIED = SourceKey<Boolean>("VCS_MODIFIED")
}
