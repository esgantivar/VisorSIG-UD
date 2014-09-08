/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Document;
import Model.Shapefile.Envoltura;
import Model.Shapefile.ShapeFile;
import Util.BoundBox;
import Util.Punto;
import View.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import javax.swing.SwingUtilities;

/**
 *
 * @author Sneyder G
 */
public class Launcher {

    private static Launcher instance;
    private Document workingDocument;
    private GUIMain view;
    

    private Launcher() {
        // 1. create the model
        workingDocument = new Document();
        // 2. create the view
        view = new GUIMain("Visor UD V 2.0");
    }

    private void run() {
        view.setVisible(true);
    }

    public void paint(Graphics g) {
        workingDocument.paint(g, view.getDimension());
    }

    public void repaint() {
        view.repaintCanvas();
    }

    public void paintBoxZoom() {

    }

    public void deselect() {
        workingDocument.deselect();
        repaint();
    }

    public void setActiveTool(final int t) {
        view.setActiveTool(t);
    }

    // singleton
    public synchronized static Launcher getInstance() {
        if (instance == null) {
            instance = new Launcher();
        }
        return instance;
    }

    public void setRubberBand(final BoundBox rb) {
        view.setRubberBand(rb);
    }

    /**
     * Funcionalidades de Zoom
     */
    public void ZoomMouse(final BoundBox bbox) throws NoninvertibleTransformException {
        workingDocument.ZoomMouse(bbox, view.getDimension());
        repaint();
    }
    
    public void ZoomExtend(){
        workingDocument.ZoomExtend();
        repaint();
    }
    
    public void ZoomToLayer(final Envoltura env){
        workingDocument.ZoomToLayer(env);
        repaint();
    }
    
    public void coordinateQuery(final java.awt.Point pt) throws NoninvertibleTransformException{
        Punto puntoQuery = workingDocument.coordinateQuery(pt);
        view.showQueryCoordinates(puntoQuery);
    }
    
    public void ZoomIn(final java.awt.Point pt) throws NoninvertibleTransformException{
        workingDocument.ZoomIn(pt,view.getDimension());
        repaint();
    }
    
    public void ZoomOut(final java.awt.Point pt) throws NoninvertibleTransformException{
        workingDocument.ZoomOut(pt,view.getDimension());
        repaint();
    }
    
    public void paneo(final int distWidth, final int distHeight) throws NoninvertibleTransformException{
        workingDocument.paneo(distWidth, distHeight,view.getDimension());
        repaint();
    }
    
    /**
     * Fin de la declaración Funcionalidades de Zoom
     */

    public void addShapeFile(ShapeFile s) {
        workingDocument.addShapefile(s);
        repaint();
    }
    
    public void deleteAllShapes(){
        workingDocument.deleteAllShapes();
        repaint();
    }
    
    public void deleteShape(ShapeFile s){
        workingDocument.remove(s);
        repaint();
    }

    public static void main(String[] args) {
        
        final Launcher launch = getInstance();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                launch.run();
            }
        });
    }
}
