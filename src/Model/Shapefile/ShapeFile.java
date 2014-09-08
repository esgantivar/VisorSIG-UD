/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Shapefile;

import Util.*;
import java.awt.Color;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.Random;

/**
 *
 * @author user
 */
public class ShapeFile {

    Punto punto;//pendiente revisar(1).
    Punto puntolin[];//pendiente de revision (2).
    Punto puntopol[];//pendiente de revision (3).

    private static final int FILE_CODE = 9994;
    private static final int HEADER_LONGITUD = 100;

    private FileInputStream fis = null;
    private DataInputStream entrada = null;
    private ByteBuffer buffer;
    private int ShapeType;
    private int Longitud;
    private int Filecode;
    private int Versionshp;
    private Envoltura envoltura;
    private double Zmin = 0, Zmax = 0, Mmin = 0, Mmax = 0;
    private int NumRegistros = 0;
    private DbaseFile basedatos;
    private String Nombre;
    private boolean selected = false;
    private Color c;
    
    @Override
    public String toString(){
        return Nombre;
    }
    

    public ShapeFile(File fileShape) {
        Random rd = new Random();
        try {
            c = new Color(rd.nextInt(255), rd.nextInt(255), rd.nextInt(255));
            fis = new FileInputStream(fileShape.getPath());
            String pathbase = fileShape.getPath().substring(0, fileShape.getPath().length() - 3) + "dbf";
            this.Nombre = fileShape.getName().substring(0, fileShape.getName().length() - 4);
            //System.out.println(pathbase);
            basedatos = new DbaseFile(pathbase);
            Longitud = fis.available();
            buffer = ByteBuffer.allocate(Longitud); //adicionado
            FileChannel canalArchivo = fis.getChannel();
            canalArchivo.read(buffer);
            buffer.flip();
            buffer.order(ByteOrder.BIG_ENDIAN);
            Filecode = buffer.getInt();
            buffer.getInt();
            buffer.getInt();
            buffer.getInt();
            buffer.getInt();
            buffer.getInt();
            buffer.getInt();
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            Versionshp = buffer.getInt();
            ShapeType = buffer.getInt();
            double a = buffer.getDouble(), b = buffer.getDouble(),
                    c = buffer.getDouble(), d = buffer.getDouble();
            envoltura = new Envoltura(a, b, c, d);
            Zmin = buffer.getDouble();
            Zmax = buffer.getDouble();
            Mmin = buffer.getDouble();
            Mmax = buffer.getDouble();

            //NumRegistros = getLeerRegistros(buffer);
            buffer.position(HEADER_LONGITUD);

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (EOFException e) {
            System.out.println("Fin de fichero");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (entrada != null) {
                    entrada.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int getLeerRegistros(ByteBuffer bufferReg) {
        int tamañoreg, posicion = HEADER_LONGITUD, numregistros = 0;
        bufferReg.order(ByteOrder.BIG_ENDIAN);
        while (posicion < Longitud) {
            //System.out.println(bufferReg.getInt());
            bufferReg.getInt();
            tamañoreg = bufferReg.getInt();
            //System.out.println(tamañoreg);
            bufferReg.position(bufferReg.position() + tamañoreg * 2);
            posicion = bufferReg.position();
            numregistros++;
        }
        return numregistros;
    }

    
    public String getShapeType() {
        switch(ShapeType){
            case 1:{
                return "Point";
            }
            case 3:{
                return "Polygon";
            }
            case 5:{
                return "PolyLine";
            }
            default:{
                return "Point";
            }
        }
    }

    public Envoltura getEnvoltura() {
        return envoltura;
    }
    public void setSelected(boolean s){
        this.selected = s;
    }
    public boolean isSelected(){
        return selected;
    }

    //public int getNumShapes() {return NumRegistros;}
    public int getNumShapes() {
        return basedatos.getNumTuplas();
    }

    public int getNumAtributos() {
        return basedatos.getNumAtributos();
    }

    public Object getValor(int iatributo, int ifila) {
        return basedatos.getValue(iatributo, ifila);
    }

    public Atributo getAtributo(int indice) {
        return basedatos.getAtributo(indice);
    }

    public double getZmin() {
        return Zmin;
    }

    public double getZmax() {
        return Zmax;
    }

    public double getMmin() {
        return Mmin;
    }

    public double getMmax() {
        return Mmax;
    }
    
    public Color getColor(){
        return c;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public Shape getShape(int registro) {
        Shape retorno = null;
        if (registro > getNumShapes()) {
            return retorno;
        }
        buffer.position(100);
        int contador = 0, tamañoreg = 0;
        buffer.order(ByteOrder.BIG_ENDIAN);

        while (contador < registro - 1) {
            //System.out.println(buffer.getInt());
            buffer.getInt();
            tamañoreg = buffer.getInt();
            buffer.position(buffer.position() + tamañoreg * 2);
            contador++;
        }
        buffer.getInt();
        tamañoreg = buffer.getInt();
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        //System.out.println(buffer.getInt());
        buffer.getInt();
        switch (getShapeType()) {
            case "Point":
                //Punto punto = new Punto(buffer.getDouble(),buffer.getDouble());  //pendiente de revision (1).
                punto = new Punto(buffer.getDouble(), buffer.getDouble());
                retorno = new ShapePunto(punto);
                break;
            case "Polygon":
                Envoltura tmpenvl = new Envoltura(
                        buffer.getDouble(), buffer.getDouble(),
                        buffer.getDouble(), buffer.getDouble());
                int numpartsl = buffer.getInt(),
                 numPuntosl = buffer.getInt();
                buffer.getInt();
                //Punto puntolin[] = new Punto[numPuntosl]; //pendiente de revision (2).
                puntolin = new Punto[numPuntosl];
                for (int i = 0; i < numPuntosl; i++) {
                    Punto ptemp = new Punto(buffer.getDouble(), buffer.getDouble());
                    puntolin[i] = ptemp;
                }
                retorno = new ShapeLinea(tmpenvl, numpartsl, puntolin);
                break;
            case "PolyLine":
                Envoltura tmpenvp = new Envoltura(
                        buffer.getDouble(), buffer.getDouble(),
                        buffer.getDouble(), buffer.getDouble());
                int numpartsp = buffer.getInt(),
                 numPuntosp = buffer.getInt();
                buffer.getInt();
                //Punto puntopol[] = new Punto[numPuntosp]; //pendiente de revision (3).
                puntopol = new Punto[numPuntosp];
                for (int i = 0; i < numPuntosp; i++) {
                    Punto ptemp = new Punto(buffer.getDouble(), buffer.getDouble());
                    puntopol[i] = ptemp;
                }
                retorno = new ShapePoligono(tmpenvp, numpartsp, puntopol);
                break;
        }
        return retorno;
    }

    private static byte[] extractArray(byte[] data, int from, int count) {
        byte[] $ret = new byte[count];
        for (int i = 0; i < count; i++) {
            $ret[i] = data[from + i];
        }
        return $ret;
    }
}
