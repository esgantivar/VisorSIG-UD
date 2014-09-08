/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Tools;


import java.awt.Point;
import java.awt.event.MouseEvent;

import Controller.Launcher;
import Util.BoundBox;
import java.awt.Cursor;
import java.awt.geom.NoninvertibleTransformException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sneyder G
 */
public class ZoomMouse extends Tool {

    @Override
    public void mousePressed(final MouseEvent me) {
        super.mousePressed(me);
        rubberBand.x = me.getX();
        rubberBand.y = me.getY();
        rubberBand.setSize(0, 0);
        Launcher.getInstance().setRubberBand(rubberBand);
    }

    @Override
    public void mouseDragged(final MouseEvent me) {

        // delete previous bound box
        Launcher.getInstance().setRubberBand(rubberBand);
        

        rubberBand.setSize(me.getX() - rubberBand.x, me.getY() - rubberBand.y);
        // draw new one
        Launcher.getInstance().setRubberBand(rubberBand);
        
    }

    @Override
    public void processMouseReleased(final Point ptPressed, final Point ptReleased) {

        Launcher.getInstance().setRubberBand(null);

        boolean checkOver = (ptReleased.x == ptPressed.x || ptReleased.y == ptPressed.y);

        if (!checkOver) {
            BoundBox bbox = new BoundBox(
                    ptPressed.x, ptPressed.y,
                    ptReleased.x - ptPressed.x, ptReleased.y - ptPressed.y);
            try {
                Launcher.getInstance().ZoomMouse(bbox);
            } catch (NoninvertibleTransformException ex) {
                Logger.getLogger(ZoomMouse.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public Cursor getCursor(){
        javax.swing.ImageIcon c = new javax.swing.ImageIcon(getClass().getResource("/Resources/zoom-mouse-black-32.png"));
        return toolKit.createCustomCursor(c.getImage(), new Point(0, 0), null);
    }

    private BoundBox rubberBand = new BoundBox(0, 0, 0, 0);

}
