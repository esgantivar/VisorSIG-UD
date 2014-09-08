package Model.Shapefile;

/**
 *
 * @author Sneyder G
 */
public class Envoltura {

    private double Xmin;
    private double Ymin;
    private double Xmax;
    private double Ymax;

    public Envoltura(double Xmin, double Ymin, double Xmax, double Ymax) {
        this.Xmin = Xmin;
        this.Ymin = Ymin;
        this.Xmax = Xmax;
        this.Ymax = Ymax;
    }

    public double getXmin() {
        return Xmin;
    }

    public double getYmin() {
        return Ymin;
    }

    public double getXmax() {
        return Xmax;
    }

    public double getYmax() {
        return Ymax;
    }

    public Envoltura mergeEnvelope(Envoltura env1) {
        return new Envoltura(Math.min(this.Xmin, env1.getXmin()), Math.min(this.Ymin, env1.getYmin()), Math.max(this.Xmax, env1.getXmax()), Math.max(this.Ymax, env1.getYmax()));
    }
    
    @Override
    public String toString(){
        return "X: " + Xmin + " Width: " +(Xmax - Xmin) + " Y: "+ Ymin + "Heigth: "+ (Ymax - Ymin);
    }
}
