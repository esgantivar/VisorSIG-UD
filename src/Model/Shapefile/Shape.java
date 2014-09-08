
package Model.Shapefile;

import java.awt.Graphics2D;

/**
 *
 * @author user
 */
public abstract class Shape {

    protected Envoltura envoltura;
    protected int NumPuntos, NumPartes;

    public Envoltura getEnvoltura() {
        return envoltura;
    }

    public int getNumPuntos() {
        return NumPuntos;
    }

    public int getNumPartes() {
        return NumPartes;
    }
    public abstract void paint(Graphics2D g);

}
