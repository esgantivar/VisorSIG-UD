/*
* Copyright (C) 2001, 2013. 
* Universidad Distrital Francisco Jóse de Caldas
* Ingeniería Catastral y Geodesia
* Programación de Interfaces SIG
* All Rights Reserved.
*/
package Model.Shapefile;;
/**
 * Atributo. Representa un atributo que puede ser usado por la clase
 * DbaseFile o la Clase Shapefile
 *
 * @author Yenny Espinosa Gómez
 * @author Javier Felipe Moncada Sánchez
 * @version 0.1
 */
public class Atributo {
    public static final String tipoS = "DBF.CampoTipoString";
    public static final String tipoI = "DBF.CampoTipoInteger";
    public static final String tipoD = "DBF.CampoTipoDouble";
    public static final String tipoF = "DBF.CampoTipoFecha";
    public static final String tipoB = "DBF.CampoTipoBoolean";
    
    private String Nombre;
    private String tipo;
    private int tamaño;
    private int precision = 0;
    private int posregistro = 0;
     /**
     * Constructor de la clase Atributo. 
     * 
     * @param Nombre string que contiene el nombre del atributo.
     * @param tipo string que contiene el tipo del atributo.
     * @param tamaño entero que contiene el tamaño del atributo.
     * @param precision entero que contiene el numero de decimales del atributo.
     * @param posregistro entero que representa la posicion del byte inicial en 
     * la cadena del atributo.
     */
    public Atributo (String Nombre,String tipo, int tamaño, int precision, int posregistro){
        this.Nombre=Nombre;
        this.precision=precision;
        this.tamaño=tamaño;
        this.tipo=tipo;
        if (this.tipo=="N") {
            if (precision == 0){this.tipo = "N";}
            else{this.tipo = "F";}
        }
        this.posregistro = posregistro;
    }
     /**
     *Retorna una cadena de texto que representa el tipo de atributo.
     */
    public String getTipo(){
        switch (tipo){
            case "C":return tipoS;
            case "D":return tipoF;
            case "F":return tipoD;
            case "L":return tipoB;
            case "N":return tipoI;
            default:return null;
        }
    }
     /**
     *Retorna una cadena de texto con el nombre del atributo.
     */
    public String getNombre(){return Nombre;}
     /**
     *Retorna un numero entero con el tamaño del atributo.
     */
    public int getTamaño(){return tamaño;}
     /**
     *Retorna un numero entero con el numero de decimales del atributo.
     */
    public int getPrecision(){return precision;}
     /**
     *Retorna un numero entero con la posición en la cadena de bytes del atributo.
     */
    public int getPosRegistro(){return posregistro;}
}
