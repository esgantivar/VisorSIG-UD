package Model.Shapefile;

import Util.Punto;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author user
 */
public class ShapePunto extends Shape {

    private Punto punto;

    ShapePunto(Punto punto) {
        this.punto = punto;
        this.NumPartes = 1;
        this.NumPuntos = 1;
        this.envoltura
                = new Envoltura(punto.getX() - 0.5, punto.getY() - 0.5,
                        punto.getX() + 0.5, punto.getY() + 0.5);
    }

    public Punto getPunto() {
        return punto;
    }

    @Override
    public void paint(Graphics2D g) {
        g.fillOval((int) punto.getX(), (int) punto.getY(), 10, 10);
    }
}
