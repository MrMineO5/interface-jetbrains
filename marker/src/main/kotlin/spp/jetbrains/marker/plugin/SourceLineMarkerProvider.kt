package spp.jetbrains.marker.plugin

import com.intellij.codeInsight.daemon.GutterIconNavigationHandler
import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.codeInsight.daemon.LineMarkerProviderDescriptor
import com.intellij.openapi.editor.markup.GutterIconRenderer.Alignment.CENTER
import com.intellij.psi.PsiElement
import spp.jetbrains.marker.SourceMarker
import spp.jetbrains.marker.source.mark.api.key.SourceKey
import spp.jetbrains.marker.source.mark.gutter.GutterMark
import org.slf4j.LoggerFactory

/**
 * Used to associate [GutterMark]s with IntelliJ PSI elements.
 *
 * @since 0.1.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
abstract class SourceLineMarkerProvider : LineMarkerProviderDescriptor() {

    private val log = LoggerFactory.getLogger(SourceLineMarkerProvider::class.java)

    abstract fun getLineMarkerInfo(parent: PsiElement?, element: PsiElement): LineMarkerInfo<PsiElement>?

    override fun getLineMarkerInfo(element: PsiElement): LineMarkerInfo<PsiElement>? {
        if (!SourceMarker.enabled) {
            return null
        }

        val parent = element.parent
        val el = getLineMarkerInfo(parent, element)
        if (el == null) {
            //expression gutter marks
            //todo: only works for manually created expression gutter marks atm
            if (element.getUserData(SourceKey.GutterMark) != null) {
                val gutterMark = element.getUserData(SourceKey.GutterMark)!!
                if (!gutterMark.isVisible()) {
                    return null
                }

                var navigationHandler: GutterIconNavigationHandler<PsiElement>? = null
                if (gutterMark.configuration.activateOnMouseClick) {
                    navigationHandler = GutterIconNavigationHandler { _, _ ->
                        element.getUserData(SourceKey.GutterMark)!!.displayPopup()
                    }
                }
                return LineMarkerInfo(
                    getFirstLeaf(element),
                    element.textRange,
                    gutterMark.configuration.icon,
                    null,
                    navigationHandler,
                    CENTER
                )
            }
        }

        return null
    }

    override fun collectSlowLineMarkers(
        elements: MutableList<out PsiElement>,
        result: MutableCollection<in LineMarkerInfo<*>>
    ) {
        if (!SourceMarker.enabled) {
            return
        }

        elements.stream().map { it.containingFile }.distinct().forEach {
            SourceMarker.getSourceFileMarker(it)?.removeInvalidSourceMarks()
        }
    }

    fun getFirstLeaf(element: PsiElement): PsiElement {
        var e = element
        while (e.children.isNotEmpty()) {
            e = e.firstChild
        }
        return e
    }
}