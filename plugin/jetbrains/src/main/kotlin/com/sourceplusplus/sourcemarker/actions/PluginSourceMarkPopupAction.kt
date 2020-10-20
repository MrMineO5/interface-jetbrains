package com.sourceplusplus.sourcemarker.actions

import com.intellij.ide.ui.laf.IntelliJLaf
import com.intellij.openapi.editor.Editor
import com.sourceplusplus.marker.source.mark.SourceMarkPopupAction
import com.sourceplusplus.marker.source.mark.api.ClassSourceMark
import com.sourceplusplus.marker.source.mark.api.SourceMark
import com.sourceplusplus.marker.source.mark.api.component.jcef.SourceMarkJcefComponent
import com.sourceplusplus.marker.source.mark.api.event.SourceMarkEventCode
import com.sourceplusplus.portal.SourcePortal
import com.sourceplusplus.portal.model.PageType
import com.sourceplusplus.sourcemarker.SourceMarkKeys.SOURCE_PORTAL
import javax.swing.UIManager

/**
 * todo: description.
 *
 * @since 0.1.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
class PluginSourceMarkPopupAction : SourceMarkPopupAction() {

    private var lastDisplayedInternalPortal: SourcePortal? = null

    override fun performPopupAction(sourceMark: SourceMark, editor: Editor) {
        if (sourceMark.getUserData(SOURCE_PORTAL) == null) {
            //register source portal
            val sourcePortal = SourcePortal.getPortal(
                //todo: appUuid/portalUuid
                SourcePortal.register("null", sourceMark.artifactQualifiedName, false)
            )
            sourceMark.putUserData(SOURCE_PORTAL, sourcePortal!!)
            if (sourceMark is ClassSourceMark) {
                //class-based portals only have overview page
                sourcePortal.currentTab = PageType.OVERVIEW
                sourcePortal.configuration.visibleActivity = false
                sourcePortal.configuration.visibleTraces = false
            } else {
                //method-based portals don't have overview page
                sourcePortal.configuration.visibleOverview = false
            }

            sourceMark.addEventListener { event ->
                if (event.eventCode == SourceMarkEventCode.MARK_REMOVED) {
                    event.sourceMark.getUserData(SOURCE_PORTAL)!!.close()
                }
            }
        }
        val sourcePortal = sourceMark.getUserData(SOURCE_PORTAL)!!

        refreshPortalIfNecessary(sourceMark, sourcePortal)
        super.performPopupAction(sourceMark, editor)
    }

    private fun refreshPortalIfNecessary(sourceMark: SourceMark, sourcePortal: SourcePortal) {
        val jcefComponent = sourceMark.sourceMarkComponent as SourceMarkJcefComponent
        if (sourcePortal != lastDisplayedInternalPortal) {
            sourcePortal.configuration.darkMode = UIManager.getLookAndFeel() !is IntelliJLaf
            val currentUrl = "/${sourcePortal.currentTab.name.toLowerCase()}.html" +
                    "?portalUuid=${sourcePortal.portalUuid}"
            jcefComponent.getBrowser().cefBrowser.executeJavaScript(
                "window.location.href = '$currentUrl';", currentUrl, 0
            )

            lastDisplayedInternalPortal = sourcePortal
        }
    }
}
