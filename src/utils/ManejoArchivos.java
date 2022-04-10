package utils;

import constants.Mensajes;
import constants.Rutas;
import hilos.HiloLeer;

import javax.swing.*;
import java.io.File;

public class ManejoArchivos {
    private int subdirectorio;
    private int[] archivos;
    private String nombreSubdirectorio;

    public void listarSubdirectorios() {
        File carpeta = new File(Rutas.DIRECTORIO_PATH);
        if (carpeta.exists()) {
            File[] ficheros = carpeta.listFiles();
            if (!(ficheros == null)) {
                StringBuilder listaSubcarpetas = new StringBuilder(Mensajes.INGRESO_OPCION_SUBDIRECTORIO);
                for (int i = 0; i < ficheros.length; i++) {
                    listaSubcarpetas.append(String.format(Mensajes.FORMATO_OPCION_LISTA, (i + 1), ficheros[i].getName()));
                }
                int subdirectorio;
                do {
                    try {
                        subdirectorio = Integer.parseInt(JOptionPane.showInputDialog(listaSubcarpetas.toString()));
                        if (ficheros[subdirectorio-1].exists()) {
                            this.nombreSubdirectorio = ficheros[subdirectorio - 1].getName();
                        } else{
                            JOptionPane.showMessageDialog(null, Mensajes.OPCION_INVALIDA, "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                        JOptionPane.showMessageDialog(null, Mensajes.OPCION_INVALIDA, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } while (true);
            } else {
                JOptionPane.showMessageDialog(null, Mensajes.CARPETA_NO_EXISTE, "Sin archivos", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, Mensajes.CARPETA_NO_EXISTE, "Sin archivos", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void listarArchivos(){
        File subdirectorio = new File(String.format(Rutas.FORMATO_PATH,Rutas.DIRECTORIO_PATH,nombreSubdirectorio));
        if (subdirectorio.exists()) {
            File[] ficheros = subdirectorio.listFiles();
            if (!(ficheros == null)) {
                StringBuilder listaArchivos = new StringBuilder(Mensajes.INGRESO_OPCION_ARCHIVOS);
                for (int i = 0; i < ficheros.length; i++) {
                    listaArchivos.append(new StringBuilder(String.format(Mensajes.FORMATO_OPCION_LISTA, (i + 1), ficheros[i].getName())));
                }
                String[] archivos;
                do {
                    try {
                        archivos = (JOptionPane.showInputDialog(null, listaArchivos.toString())).trim().split(",");
                        for (String a : archivos){
                            Thread hiloLeer = new Thread(new HiloLeer(a, nombreSubdirectorio));
                            hiloLeer.start();
                        }
                        break;
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, Mensajes.OPCION_INVALIDA, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } while (true);
            } else {
                JOptionPane.showMessageDialog(null, Mensajes.CARPETA_NO_EXISTE, "Sin archivos", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, Mensajes.CARPETA_NO_EXISTE, "Sin archivos", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
