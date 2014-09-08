/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View.Tools;

import java.awt.Point;
import java.awt.event.MouseEvent;

import Controller.Launcher;
import java.awt.Cursor;
import java.awt.geom.NoninvertibleTransformException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sneyder G
 */
public class ZoomIn extends Tool{

    @Override
    public void processMouseReleased(Point mePressed, Point meReleased) {
        try {
            Launcher.getInstance().ZoomIn(mePressed);
        } catch (NoninvertibleTransformException ex) {
            Logger.getLogger(ZoomIn.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        
    }
    
    @Override
    public Cursor getCursor(){
        javax.swing.ImageIcon c = new javax.swing.ImageIcon(getClass().getResource("/Resources/Zoom-In-black-32.png"));
        return toolKit.createCustomCursor(c.getImage(), new Point(0, 0), null);
    }
    
}
