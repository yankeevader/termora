package app.termora

import java.awt.Dimension
import java.awt.Graphics
import javax.swing.JComponent

/**
 * Tunnelkeeper uses a clean empty workspace instead of the upstream ASCII banner.
 *
 * The component remains as a no-op for compatibility with existing layouts.
 */
class BannerPanel(fontSize: Int = 11, val beautiful: Boolean = false) : JComponent() {
    init {
        preferredSize = Dimension(0, 0)
        minimumSize = Dimension(0, 0)
        maximumSize = Dimension(0, 0)
    }

    public override fun paintComponent(g: Graphics) {
        // Intentionally blank.
    }
}
