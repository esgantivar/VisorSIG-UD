package Model;

import Model.Shapefile.*;
import Util.*;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.util.ArrayList;

/**
 *
 * @author Sneyder G
 */
public class Document {

    private ArrayList<ShapeFile> shapefiles;
    private Envoltura EnvelopeAll;
    private AffineTransform tx;
    private boolean txModified = true;
    private boolean evModified = true;
    private Graphics2D gg;

    public Document() {
        EnvelopeAll = new Envoltura(0.0, 0.0, 0.0, 0.0);
        shapefiles = new ArrayList<>();
        tx = new AffineTransform();
    }

    public boolean addShapefile(ShapeFile shape) {
        txModified = true;
        evModified = true;
        return shapefiles.add(shape);
    }

    public void deleteAllShapes() {
        txModified = true;
        evModified = true;
        shapefiles.clear();
    }

    /*Inicio Funcionalidades de Zoom*/
    public void ZoomMouse(BoundBox bbox, Dimension d) throws NoninvertibleTransformException {
        int tempx1 = bbox.x;
        int tempx2 = bbox.x + bbox.width;
        int tempy1 = bbox.y;
        int tempy2 = bbox.y + bbox.height;

        double xMin = (tempx1 * tx.createInverse().getScaleX()) + tx.createInverse().getTranslateX();
        double yMin = (tempy2 * tx.createInverse().getScaleY()) + tx.createInverse().getTranslateY();
        double xMax = (tempx2 * tx.createInverse().getScaleX()) + tx.createInverse().getTranslateX();
        double yMax = (tempy1 * tx.createInverse().getScaleY()) + tx.createInverse().getTranslateY();

        EnvelopeAll = new Envoltura(xMin, yMin, xMax, yMax);
        evModified = false;
    }

    public void ZoomExtend() {
        evModified = true;
        txModified = true;
    }

    public void ZoomToLayer(final Envoltura env) {
        EnvelopeAll = env;
        evModified = false;
        txModified = true;
    }

    public void ZoomIn(final java.awt.Point pt, Dimension d) throws NoninvertibleTransformException {
        int x = pt.x - 275;
        int y = pt.y - 190;
        ZoomMouse(new BoundBox(x, y, 550, 380), d);

    }

    public void ZoomOut(final java.awt.Point pt, Dimension d) throws NoninvertibleTransformException {
        int x = pt.x - (int) ((d.width * 1.2) / 2);
        int y = pt.y - (int) ((d.height * 1.2) / 2);
        ZoomMouse(new BoundBox(x, y, (int) (d.width * 1.2), (int) (d.height * 1.2)), d);
    }

    public void paneo(final int distWidth, final int distHeight, Dimension d) throws NoninvertibleTransformException {
        int tempx1 = distWidth;
        int tempy1 = distHeight;

        double trasX = (tempx1 * tx.createInverse().getScaleX());
        double trasY = (tempy1 * tx.createInverse().getScaleY());

        double xMin = EnvelopeAll.getXmin() - trasX;
        double yMin = EnvelopeAll.getYmin() - trasY;
        double xMax = EnvelopeAll.getXmax() - trasX;
        double yMax = EnvelopeAll.getYmax() - trasY;

        EnvelopeAll = new Envoltura(xMin, yMin, xMax, yMax);
        evModified = false;
    }
    /*Fin Funcionalidades de Zoom*/

    public boolean remove(ShapeFile s) {
        txModified = true;
        evModified = true;
        return shapefiles.remove(s);
    }

    private void drawingCalculate(Dimension d) {
        if (evModified) {
            EnvelopeAll = shapefiles.get(0).getEnvoltura();
            for (int i = 1; i < shapefiles.size(); i++) {
                EnvelopeAll = shapefiles.get(i).getEnvoltura().mergeEnvelope(EnvelopeAll);
            }
        }

        if (txModified) {
            tx = defineTransformation(d);
        }

    }

    public void paint(Graphics g, Dimension d) {
        gg = (Graphics2D) g;

        if (shapefiles.isEmpty()) {
            return;
        }

        drawingCalculate(d);

        gg.setTransform(tx);
        gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gg.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        for (ShapeFile shapeFile : shapefiles) {
            ShapeFile s = shapeFile;
            for (int i = 0; i <= s.getNumShapes(); i++) {
                gg.setColor(s.getColor());
                s.getShape(i).paint(gg);
            }
        }
    }

    public Util.Punto coordinateQuery(final java.awt.Point pt) throws NoninvertibleTransformException {
        double tempX = (pt.getX() * tx.createInverse().getScaleX()) + tx.createInverse().getTranslateX();
        double tempY = (pt.getY() * tx.createInverse().getScaleY()) + tx.createInverse().getTranslateY();

        return new Util.Punto(tempX, tempY);
    }

    private AffineTransform defineTransformation(Dimension dimPanel) {
        double h = Math.max((EnvelopeAll.getXmax() - EnvelopeAll.getXmin()), (EnvelopeAll.getYmax() - EnvelopeAll.getYmin()));
        double scale1 = (dimPanel.height - 20) / ((EnvelopeAll.getYmax() - EnvelopeAll.getYmin()));
        double scale2 = (dimPanel.width - 20) / ((EnvelopeAll.getXmax() - EnvelopeAll.getXmin()));
        double scale = Math.min(scale1, scale2) * 0.95;
        double deltax = EnvelopeAll.getXmin() - h * 0.05;
        double deltay = EnvelopeAll.getYmax() + h * 0.05;

        AffineTransform transform = new AffineTransform();
        transform.translate(deltax * scale * -1, deltay * scale);
        transform.scale(scale, scale * -1);
        tx = new AffineTransform(transform);
        return transform;
    }

    public void deselect() {
        for (ShapeFile shapefile : shapefiles) {
            shapefile.setSelected(false);
        }
    }
}
