/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Launcher;
import View.Tools.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 *
 * @author Sneyder G
 */
public class Canvas extends javax.swing.JPanel {

    private static final long serialVersionUID = 1L;
    private Cursor cursor = Cursor.getDefaultCursor();

    public Canvas() {
        //setBackground(new java.awt.Color(153, 204, 255));
        createTools();
        registerMouseListeners();
    }

    @Override
    public void paint(final Graphics g) {
        if (rubberBand == null) {
            super.paint(g);
            Launcher.getInstance().paint(g);
        } else {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setXORMode(this.getBackground());
            g2d.setStroke(dashed);
            g2d.drawRect(rubberBand.x, rubberBand.y, rubberBand.width, rubberBand.height);
        }
    }

    private void createTools() {
        tools = new Tool[NUM_TOOLS];
        tools[ZOOM_IN] = new ZoomIn();
        tools[ZOOM_OUT] = new ZoomOut();
        tools[ZOOM_MOUSE] = new ZoomMouse();
        tools[PANEO] = new Paneo();
        tools[COORDINATE] = new CoordinateQuery();
        activeTool = tools[DEFAULT_TOOL];
    }

    public void setRubberBand(final Util.BoundBox rb) {
        rubberBand = (rb != null ? rb.normalized() : null);
        paintImmediately(getVisibleRect());
    }

    // state/adapter
    private void registerMouseListeners() {

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(final MouseEvent me) {
                activeTool.mousePressed(me);
            }

            @Override
            public void mouseReleased(final MouseEvent me) {

                activeTool.mouseReleased(me);
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(final MouseEvent me) {
                activeTool.mouseDragged(me);
            }

            @Override
            public void mouseMoved(final MouseEvent me) {
                activeTool.mouseMoved(me);
            }
        });

        addMouseWheelListener(new MouseAdapter() {

            @Override
            public void mouseWheelMoved(final MouseWheelEvent me) {
                activeTool.mouseWheeled(me);
            }
        });
    }

    public void setActiveTool(final int t) {
        if (0 <= t && t < tools.length) {
            activeTool = tools[ t];
        }
        this.setCursor(activeTool.getCursor());
    }

    private Util.BoundBox rubberBand;
    private final BasicStroke dashed = new BasicStroke(
            1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{4.0f, 4.0f}, 0.0f);

    private Tool[] tools;
    public static final int ZOOM_IN = 0;
    public static final int ZOOM_OUT = 1;
    public static final int ZOOM_MOUSE = 2;
    public static final int PANEO = 3;
    public static final int COORDINATE = 4;
    public static final int NUM_TOOLS = 5;

    private Tool activeTool;

    public static final int DEFAULT_TOOL = PANEO;
}
