/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Shapefile;

import Util.Punto;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author user
 */
public class ShapeLinea extends Shape {

    Punto[] puntos;

    public ShapeLinea(Envoltura tmpenvl, int numpartsl, Punto[] puntolin) {
        this.NumPartes = numpartsl;
        this.NumPuntos = puntolin.length;
        this.envoltura = tmpenvl;
        this.puntos = puntolin;
    }

    public ArrayList<Punto> getPuntos() {
        ArrayList<Punto> points = new ArrayList<>();
        points.addAll(Arrays.asList(puntos));
        return points;
    }

    @Override
    public void paint(Graphics2D g) {
        ArrayList<Punto> points = new ArrayList<>();
        points.addAll(Arrays.asList(puntos));
        int k = 0;
        int tempx[] = new int[points.size()];
        int tempy[] = new int[points.size()];
        for (Punto p : points) {
            tempx[k] = (int) Math.round(p.getX());
            tempy[k] = (int) Math.round(p.getY());
            k++;
        }
        g.drawPolyline(tempx, tempy, points.size());
    }
}
