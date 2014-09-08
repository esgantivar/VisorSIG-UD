
package Util;

import java.awt.geom.Point2D;

/**
 *
 * @author Sneyder G
 */

public class Punto extends Point2D {

    private double X, Y;

    @Override
    public double getX() {
        return X;
    }

    @Override
    public double getY() {
        return Y;
    }

    @Override
    public void setLocation(double X, double Y) {
        this.X = X;
        this.Y = Y;
    }

    public Punto(double X, double Y) {
        this.X = X;
        this.Y = Y;
    }
    
    @Override
    public String toString(){
        return "Nortes: " + Y + ", Estes: "+ X;
    }

    public Punto() {
    }
}
