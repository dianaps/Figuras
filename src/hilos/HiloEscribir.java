package hilos;

import constants.Mensajes;
import constants.Rutas;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class HiloEscribir implements Runnable {

    private String archivo;
    private final String datos;
    private String rutaNuevoSubdirectorio;

    public HiloEscribir(String datos, String archivo) {
        this.datos = datos;
        this.archivo = String.format(Rutas.FORMATO_EXTENSION_ARCHIVO, archivo, Rutas.EXTENSION_ARCHIVO);
    }

    public void CrearDirectorioRaiz() throws IOException {
        String rutaDirectorioRaiz = String.format(Rutas.DIRECTORIO_PATH);
        File directorioRaiz = new File(rutaDirectorioRaiz);
        if (!directorioRaiz.exists()) {
            directorioRaiz.mkdir();
        }
    }

    public void CrearSubdirectorio() throws IOException {
        LocalDate fecha = LocalDate.now();
        rutaNuevoSubdirectorio = String.format(Rutas.FORMATO_PATH, Rutas.DIRECTORIO_PATH,fecha);
        File nuevoSubdirectorio = new File(rutaNuevoSubdirectorio);
        if (!nuevoSubdirectorio.exists()) {
            nuevoSubdirectorio.mkdir();
        }
    }

    public void CrearArchivo() throws IOException{
        File rutaNuevoArchivo = new File(String.format(Rutas.FORMATO_PATH, rutaNuevoSubdirectorio, this.archivo));
        if (rutaNuevoArchivo.createNewFile()) {
            FileWriter nuevoArchivo = new FileWriter(rutaNuevoArchivo);
            nuevoArchivo.write(this.datos);
            nuevoArchivo.close();
            JOptionPane.showMessageDialog(null, Mensajes.CREAR_ARCHIVO_EXITO);
        } else {
            int sobreescribir = JOptionPane.showConfirmDialog(null, Mensajes.PREGUNTA_SOBREESCRIBIR,"Sobreescribir", JOptionPane.YES_NO_OPTION);
            if (sobreescribir == 0){
                rutaNuevoArchivo.delete();
            } else {
                this.archivo = String.format(Rutas.FORMATO_EXTENSION_ARCHIVO, String.valueOf(JOptionPane.showInputDialog(Mensajes.INGRESO_NOMBRE_ARCHIVO)), Rutas.EXTENSION_ARCHIVO);
            }
            CrearArchivo();
        }

    }

    @Override
    public void run() {
        try {
            CrearDirectorioRaiz();
            CrearSubdirectorio();
            CrearArchivo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
