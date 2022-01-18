package spp.jetbrains.sourcemarker.command

import spp.jetbrains.sourcemarker.PluginIcons
import javax.swing.Icon

/**
 * todo: description.
 *
 * @since 0.3.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@Suppress("unused", "MaxLineLength")
enum class LiveControlCommand(
    val command: String,
    private val description: String,
    val selectedIcon: Icon? = null,
    val unselectedIcon: Icon? = null
) : AutocompleteFieldRow {

    VIEW_ACTIVITY(
        "View Activity",
        "<html><span style=\"font-size: 80%; color: gray\">Live View ➛ Activity ➛ Scope: Method</span></html>",
        PluginIcons.Command.viewActivitySelected,
        PluginIcons.Command.viewActivityUnSelected
    ),
    VIEW_TRACES(
        "View Traces",
        "<html><span style=\"font-size: 80%; color: gray\">Live View ➛ Traces ➛ Scope: Method</span></html>",
        PluginIcons.Command.viewTracesSelected,
        PluginIcons.Command.viewTracesUnSelected
    ),
    VIEW_LOGS(
        "View Logs",
        "<html><span style=\"font-size: 80%; color: gray\">Live View ➛ Logs ➛ Scope: Method</span></html>",
        PluginIcons.Command.viewLogsSelected,
        PluginIcons.Command.viewLogsUnSelected
    ),
    WATCH_VARIABLE(
        "watch",
        "Manual Tracing ➛ Watched Variables ➛ Scope: Local / Add *variable* to watched variables"
    ),
    TRACE_METHOD(
        "trace",
        "Add method to distributed tracing system"
    ),
    ADD_LIVE_BREAKPOINT(
        "Add Breakpoint",
        "<html><span style=\"font-size: 80%; color: gray\">Live Instrument ➛ Add ➛ Location: </span><span style=\"font-size: 80%; color: #E6E6E6\">On line *lineNumber*</span></html>",
        PluginIcons.Command.liveBreakpointSelected,
        PluginIcons.Command.liveBreakpointUnSelected
    ),
    ADD_LIVE_LOG(
        "Add Log",
        "<html><span style=\"font-size: 80%; color: gray\">Live Instrument ➛ Add ➛ Location: </span><span style=\"font-size: 80%; color: #E6E6E6\">On line *lineNumber*</span></html>",
        PluginIcons.Command.livelogSelected,
        PluginIcons.Command.livelogUnSelected
    ),
    ADD_LIVE_METER(
        "Add Meter",
        "<html><span style=\"font-size: 80%; color: gray\">Live Instrument ➛ Add ➛ Location: </span><span style=\"font-size: 80%; color: #E6E6E6\">On line *lineNumber*</span></html>",
        PluginIcons.Command.liveMeterSelected,
        PluginIcons.Command.liveMeterUnSelected
    ),
    ADD_LIVE_SPAN(
        "Add Span",
        "<html><span style=\"font-size: 80%; color: gray\">Live Instrument ➛ Add ➛ Location: </span><span style=\"font-size: 80%; color: #E6E6E6\">On method *methodName*</span></html>",
        PluginIcons.Command.liveSpanSelected,
        PluginIcons.Command.liveSpanUnSelected
    ),
    CLEAR_LIVE_INSTRUMENTS(
        "Clear Instruments",
        "<html><span style=\"font-size: 80%; color: gray\">Live Instrument ➛ Clear All</span></html>",
        PluginIcons.Command.clearInstrumentSelected,
        PluginIcons.Command.clearInstrumentUnSelected
    ),
    CLEAR_LIVE_BREAKPOINTS(
        "Clear Breakpoints",
        "Clear all self-created live breakpoints",
        PluginIcons.Command.clearInstrumentSelected,
        PluginIcons.Command.clearInstrumentUnSelected
    ),
    CLEAR_LIVE_LOGS(
        "Clear Logs",
        "Clear all self-created live logs",
        PluginIcons.Command.clearInstrumentSelected,
        PluginIcons.Command.clearInstrumentUnSelected
    ),
    CLEAR_LIVE_METERS(
        "Clear Meters",
        "Clear all self-created live meters",
        PluginIcons.Command.clearInstrumentSelected,
        PluginIcons.Command.clearInstrumentUnSelected
    );

    override fun getText(): String {
        return command
    }

    override fun getDescription(): String? {
        return description
    }

    override fun getIcon(): Icon? {
        return unselectedIcon
    }
}
