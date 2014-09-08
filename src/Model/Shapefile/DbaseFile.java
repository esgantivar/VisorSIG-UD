/*
* Copyright (C) 2001, 2013. 
* Universidad Distrital Francisco Jóse de Caldas
* Ingeniería Catastral y Geodesia
* Programación de Interfaces SIG
* All Rights Reserved.
*/
package Model.Shapefile;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.Calendar;
import java.util.Date;

/**
 * Representa un archivo Data Base File - *.dbf.
 *
 * @author Yenny Espinosa Gómez
 * @author Javier Felipe Moncada Sánchez
 * @version 0.1
 */
public class DbaseFile {
    private FileInputStream fis = null;
    private int Longitud;
    private int inidatos = 0;
    private int numCampos = 0;
    private Atributo[] atributos;
    private int numTuplas = 0;
    private int longRegistro = 0;
    private Date fechamod;
    private ByteBuffer buffer;
    
    /**
     * Constructor de la clase DbaseFile. Con este se puede abrir un archivo 
     * de extension .dbf
     * 
     * @param path la dirección del archivo dbf en string.
     */
    public DbaseFile(String path){
        try {
            fis = new FileInputStream(path);
            Longitud = fis.available();
            buffer = ByteBuffer.allocate(Longitud);
            FileChannel canalArchivo = fis.getChannel();
            canalArchivo.read( buffer );
            buffer.flip();
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            buffer.get();
            int yy = 1900 + buffer.get(); // unsigned
            int mm = buffer.get();
            int dd = buffer.get();
            Calendar cal = Calendar.getInstance();
            cal.set(yy, mm - 1, dd);
            fechamod = cal.getTime();
            numTuplas = buffer.getInt();
            inidatos = buffer.getShort();
            numCampos = ((inidatos - 1)/32)-1;
            atributos = new Atributo[numCampos];
            longRegistro = buffer.getShort();
            
            buffer.position(32);
            int registro = 0,posatributo = 0;
            while(buffer.position()<inidatos-32){
                String nombre = ""; //Nombre del Campo
                for (int i = 0;i<=10;i++){
                    int j = buffer.get();
                    if (j!=0) {
                        nombre += new String(Character.toChars(j));
                    }
                }
                //Tipo de campo en string
                String AtriTipo = new String(Character.toChars(buffer.get())); 
                int inidato = buffer.getShort(); //Posicion inicial del dato
                buffer.getShort();
                int tamaño = buffer.get(); //Longitud del campo
                int precision = buffer.get(); //Precision
                buffer.position(buffer.position() + 14);
                //System.out.println("pos " + posatributo);
                Atributo AtTemp = new Atributo(nombre,AtriTipo,tamaño,precision, posatributo);
                this.atributos[registro] = AtTemp;
                
                posatributo += tamaño;
                registro++;
            }
 
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
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    /**
     *Retorna el numero de atributos del archivo dbf.
     */
    public int getNumAtributos(){return numCampos;}
    /**
     *Retorna el numero de tuplas o registros del archivo dbf.
     */
    public int getNumTuplas(){return numTuplas;}
    /**
     *Retorna un objeto de tipo Atributo del archivo dbf.
     * 
     * @param index indice del atributo dentro del archivo
     */
    public Atributo getAtributo(int index){
        if(index-1>=numCampos){return null;}
        else{return atributos[index-1];}    
    }
    private int getlongregistro(){return longRegistro;}
    /**
     *Retorna el valor de un atributo del archivo dbf.
     * 
     * @param atributo indice del atributo dentro del archivo
     * @param registro indice del registro dentro del archivo
     * 
     * @return Objeto que se puede convertir 
     * en la clase dependiendo del tipo de campo
     */    
    public Object getValue(int atributo, int registro){
        //System.out.println(numCampos * 32 + 32);
        Object retorno = null;
        int posregistro, posatributo, tamatributo;
        try{
            posregistro = (this.inidatos+1) + ((registro-1)*longRegistro);
            posatributo = atributos[atributo-1].getPosRegistro();
            tamatributo = atributos[atributo-1].getTamaño();
        }catch (Exception e) {
            return retorno;
        }
        String tipoatri = atributos[atributo-1].getTipo();

        byte[] miremos = extractArray(buffer.array(),posregistro+posatributo,tamatributo);

        switch(tipoatri){
            case "DBF.CampoTipoString": 
                retorno = new String(miremos).trim();break;
            case "DBF.CampoTipoInteger":
                retorno = new Integer(new String(miremos).trim());break;
            case "DBF.CampoTipoDouble":
                retorno = new Double(new String(miremos).trim());break;
            case "DBF.CampoTipoFecha":
                retorno = new Date(new String(miremos).trim());break;
            case "DBF.CampoTipoBoolean":
                retorno = new Boolean(new String(miremos).trim());break;
            default: retorno = null;
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
