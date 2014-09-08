package View.Tools;

import Controller.Launcher;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 *
 * @author Sneyder G
 */
public abstract class Tool {

    public void mousePressed(final MouseEvent mePressed) {
        this.mePressed = mePressed;
        Launcher.getInstance().repaint();
    }

    public abstract void processMouseReleased(final Point mePressed, final Point meReleased);

    // template method
    public void mouseReleased(final MouseEvent meReleased) {
        this.meReleased = meReleased;
        processMouseReleased(mePressed.getPoint(), meReleased.getPoint());
    }

    // TODO: set mouse pointer
    public void mouseMoved(final MouseEvent me) {
    }

    public abstract void mouseDragged(final MouseEvent me);

    // TODO: to be redefined in ZoomTool
    public void mouseWheeled(final MouseWheelEvent me) {

        // placeholder: does nothing by default
    }

    public Cursor getCursor() {
        return Cursor.getDefaultCursor();
    }

    protected MouseEvent mePressed;
    protected MouseEvent meReleased;
    protected Toolkit toolKit = Toolkit.getDefaultToolkit();

}
