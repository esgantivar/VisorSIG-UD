/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Tools;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;

import Controller.Launcher;
import java.awt.geom.NoninvertibleTransformException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sneyder G
 */
public class Paneo extends Tool {

    @Override
    public void mousePressed(final MouseEvent me) {
        super.mousePressed(me);
        x = me.getX();
        y = me.getY();
    }

    @Override
    public void processMouseReleased(Point mePressed, Point meReleased) {
        
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        try {
            Launcher.getInstance().paneo(me.getX() - x, me.getY() -y);
            x = me.getX();
            y = me.getY();
        } catch (NoninvertibleTransformException ex) {
            Logger.getLogger(Paneo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Cursor getCursor() {
        javax.swing.ImageIcon c = new javax.swing.ImageIcon(getClass().getResource("/Resources/Paneo-32.png"));
        return toolKit.createCustomCursor(c.getImage(), new Point(0, 0), null);
    }

    private int x;
    private int y;

}
