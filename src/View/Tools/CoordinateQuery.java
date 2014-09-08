/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View.Tools;

import Controller.Launcher;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.NoninvertibleTransformException;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Sneyder G
 */
public class CoordinateQuery extends Tool{

    @Override
    public void processMouseReleased(Point mePressed, Point meReleased) {
        try {
            Launcher.getInstance().coordinateQuery(mePressed);
        } catch (NoninvertibleTransformException ex) {
            Logger.getLogger(CoordinateQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        
    }
    @Override
    public Cursor getCursor(){
        javax.swing.ImageIcon c = new javax.swing.ImageIcon(getClass().getResource("/Resources/consulta-coordenada-black-32.png"));
        return toolKit.createCustomCursor(c.getImage(), new Point(0, 0), null);
    }
}
