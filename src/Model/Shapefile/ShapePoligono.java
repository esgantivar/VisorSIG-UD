/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Shapefile;

import Util.Punto;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author user
 */
public class ShapePoligono extends Shape {

    private Punto[] puntos;

    ShapePoligono(Envoltura tmpenvp, int numpartsp, Punto[] puntopol) {
        this.NumPartes = numpartsp;
        this.NumPuntos = puntopol.length;
        this.envoltura = tmpenvp;
        this.puntos = puntopol;
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
        g.fillPolygon(tempx, tempy, points.size());
        g.setColor(Color.BLACK);
        g.drawPolygon(tempx, tempy, points.size());
    }
}
