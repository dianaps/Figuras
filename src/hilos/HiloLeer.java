package hilos;

import constants.Mensajes;
import constants.Rutas;

import javax.swing.*;
import java.io.*;

public class HiloLeer implements Runnable {

    private String nombreArchivo;
    private String rutaSubdirectorio;

    public HiloLeer (String nombreArchivo, String rutaSubdirectorio){
        this.nombreArchivo = String.format(Rutas.FORMATO_EXTENSION_ARCHIVO, nombreArchivo.trim(), Rutas.EXTENSION_ARCHIVO);
        this.rutaSubdirectorio = String.format(Rutas.FORMATO_PATH, Rutas.DIRECTORIO_PATH, rutaSubdirectorio);
    }

    @Override
    public void run() {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            archivo = new File (String.format(Rutas.FORMATO_PATH, this.rutaSubdirectorio, this.nombreArchivo));
            System.out.println(archivo); ////////////2

            if (archivo.exists()) {
                fr = new FileReader(archivo);
                br = new BufferedReader(fr);
                String linea;
                StringBuilder texto = new StringBuilder("");
                while ((linea = br.readLine()) != null)
                    texto.append(String.format(Mensajes.FORMATO_LISTAR_DATOS, linea));
                JOptionPane.showMessageDialog(null, String.valueOf(texto));
            } else {
                JOptionPane.showMessageDialog(null, String.format(Mensajes.ABRIR_ARCHIVOS_FALLO, this.nombreArchivo), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(null, Mensajes.ABRIR_ARCHIVOS_FALLO, "Error", JOptionPane.ERROR_MESSAGE);
        }finally{
            try{
                if( null != fr ){
                    fr.close();
                }
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
    }
}
