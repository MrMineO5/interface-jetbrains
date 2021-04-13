package com.sourceplusplus.sourcemarker.service.hindsight

import com.intellij.util.ui.ColumnInfo
import com.intellij.util.ui.table.IconTableCellRenderer
import com.sourceplusplus.protocol.artifact.debugger.event.BreakpointHit
import com.sourceplusplus.protocol.artifact.exception.methodName
import com.sourceplusplus.protocol.artifact.exception.qualifiedClassName
import com.sourceplusplus.protocol.artifact.exception.shortQualifiedClassName
import com.sourceplusplus.protocol.artifact.exception.sourceAsLineNumber
import com.sourceplusplus.sourcemarker.icons.SourceMarkerIcons
import kotlinx.datetime.toJavaInstant
import java.util.Comparator
import javax.swing.Icon
import javax.swing.table.TableCellRenderer
import java.time.ZoneId

import java.time.format.DateTimeFormatter

/**
 * todo: description.
 *
 * @since 0.2.2
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
class BreakpointHitColumnInfo(name: String) : ColumnInfo<BreakpointHit, String>(name) {

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S").withZone(ZoneId.systemDefault())

    override fun getColumnClass(): Class<*> {
        return when (name) {
            "Hindsight Data" -> Icon::class.java
            else -> super.getColumnClass()
        }
    }

    override fun getCustomizedRenderer(o: BreakpointHit, renderer: TableCellRenderer): TableCellRenderer {
        return when (name) {
            "Hindsight Data" -> IconTableCellRenderer.create(SourceMarkerIcons.EYE_ICON)
            else -> super.getCustomizedRenderer(o, renderer)
        }
    }

    override fun getComparator(): Comparator<BreakpointHit>? {
        return when (name) {
            "Time" -> Comparator { t: BreakpointHit, t2: BreakpointHit ->
                t.occurredAt.compareTo(t2.occurredAt) //todo: could add line number too
            }
            "Class Name" -> Comparator { t: BreakpointHit, t2: BreakpointHit ->
                t.stackTrace.first().qualifiedClassName().compareTo(t2.stackTrace.first().qualifiedClassName())
            }
            "Line No" -> Comparator { t: BreakpointHit, t2: BreakpointHit ->
                t.stackTrace.first().sourceAsLineNumber()!!.compareTo(t2.stackTrace.first().sourceAsLineNumber()!!)
            }
            else -> null
        }
    }

    override fun valueOf(item: BreakpointHit): String {
        return when (name) {
            "Time" -> formatter.format(item.occurredAt.toJavaInstant())
            "Host Name" -> item.host
            "Application Name" -> item.application
            "Class Name" -> item.stackTrace.first().shortQualifiedClassName()
            "Method Name" -> item.stackTrace.first().methodName()
            "Line No" -> item.stackTrace.first().sourceAsLineNumber()!!.toString()
            "Hindsight Data" -> "View Frames/Variables"
            else -> item.toString()
        }
    }
}
