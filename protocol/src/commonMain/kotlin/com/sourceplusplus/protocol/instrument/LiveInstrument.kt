package com.sourceplusplus.protocol.instrument

import kotlinx.serialization.Serializable

/**
 * todo: description.
 *
 * @since 0.2.2
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@Serializable
abstract class LiveInstrument {
    abstract val location: LiveSourceLocation
    abstract val condition: String?
    abstract val expiresAt: Long?
    abstract val hitLimit: Int
    abstract val id: String?
    abstract val type: LiveInstrumentType
    open var applyImmediately: Boolean = false
}