import com.sun.xml.internal.ws.wsdl.writer.document.Message;
import constants.Mensajes;
import constants.OpcionesEnum;
import constants.TipoFigurasEnum;
import figuras.*;
import hilos.HiloEscribir;
import interfaces.IMedidas;
import utils.ManejoArchivos;

import javax.swing.*;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class Main {

    //validar que el nombre del fichero no se encuentre previamente creado .txt, sino,
    // mencionarle y darle la opci[on de sobreescribirlo(eliminar y crear nuevo) o
    // de cambiarle el nombre
    //Los archivos .txt se crea en una carpeta llamada Calculos, se crea desde el c[odigo, se encuentra en la ra[iz del programa
    //arriba de src
    //dentro de esa carpeta se van a ir formando subcarpetas por d[ia
    //ir validando el d[ia Carpetas con nombre 2022-04-04

    //Circulo
    //radio: 12 cm
    //perimetro: 10 cm

    //l[ogica para crear el archivo
    //hilo mientras se valida el nombre  de la carpeta y escribir en el archivo
    // mostrar men[u principal de manera as[incrona
    // l
    //segunda parte
    //imprimir en una lista el nombre de las carpetas
    //al seleccionar carpeta mostrar otra ventana con lista de archivos
    //escribir los archivos que se quieran abrir separados por comas, los va a abrir
    // cada apertura lo har[a en un hilo diferente  for each?r

    //enumerables
    //Para concataenar = StringBuilder y StringFormat
    //

   public static void main(String[] args) {
       //Menú principal
       StringBuilder sop = new StringBuilder(Mensajes.INGRESO_OPCION_MENU);
       OpcionesEnum[] opciones = OpcionesEnum.values();
       for(OpcionesEnum f : opciones) {
           sop.append(String.format(Mensajes.FORMATO_OPCIONES_MENU, f.getOpcion(), f.getNombre()));
       }
       int numeroOpcion = 0;
       OpcionesEnum opcion = null;
       boolean continua = true;
       do {
           try {
               numeroOpcion = Integer.parseInt(JOptionPane.showInputDialog(sop.toString()));
               opcion = getNombreOpcion(numeroOpcion);
               switch (opcion) {
                   case OPCION_REGISTRAR:
                       MenuFiguras();
                       break;

                   case OPCION_ABRIR:
                       ManejoArchivos manejoArchivos = new ManejoArchivos();
                       manejoArchivos.listarSubdirectorios();
                       manejoArchivos.listarArchivos();
                       break;

                   case OPCION_SALIR:
                       int salir = JOptionPane.showConfirmDialog(null, Mensajes.PREGUNTA_SALIR,"Salir", JOptionPane.YES_NO_OPTION);
                       if (salir == 0){
                           JOptionPane.showMessageDialog(null, Mensajes.SALIDA,"Salir", JOptionPane.INFORMATION_MESSAGE);
                           System.exit(0);
                           continua = false;
                       }
                       break;
                   default:
                       JOptionPane.showMessageDialog(null, Mensajes.OPCION_INVALIDA,"Error", JOptionPane.ERROR_MESSAGE);
                       break;
               }
           }catch (NumberFormatException | NoSuchElementException e){
               JOptionPane.showMessageDialog(null, Mensajes.OPCION_INVALIDA,"Error", JOptionPane.ERROR_MESSAGE);
           }
       } while(continua);

    }

    public static void MenuFiguras(){
        StringBuilder sb = new StringBuilder(Mensajes.INGRESO_OPCION_FIGURA);
        TipoFigurasEnum[] figuras = TipoFigurasEnum.values();

        for(TipoFigurasEnum f : figuras) {
            sb.append(String.format(Mensajes.FORMATO_OPCIONES_FIGURAS, f.getOpcion(), f.getNombre()));
        }
        int nombreFigura = 0;
        TipoFigurasEnum figura = null;
        IMedidas medidas = null;
        StringBuilder datosFigura= null;
        boolean continua = true;
        do{
            try {
                nombreFigura = Integer.parseInt(JOptionPane.showInputDialog(sb.toString()));
                figura = getNombreFigura(nombreFigura);
                datosFigura = new StringBuilder(figura.getNombre());
                continua = false;
            }catch (NumberFormatException | NoSuchElementException e) {
                JOptionPane.showMessageDialog(null, Mensajes.OPCION_INVALIDA, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }while (continua);
        switch (figura) {
            case CIRCULO:
                double radio = Double.parseDouble(JOptionPane.showInputDialog(String.format(Mensajes.INGRESO_RADIO, Mensajes.UNIDAD_MEDIDA_CM)));
                medidas = new Circulo(radio);
                datosFigura.append(String.format(Mensajes.FORMATO_DATOS_FIGURA,Mensajes.ETIQUETA_RADIO,radio, Mensajes.UNIDAD_MEDIDA_CM));
                break;

            case CUADRADO:
                double lado = Double.parseDouble(JOptionPane.showInputDialog(String.format(Mensajes.INGRESO_LADO, Mensajes.UNIDAD_MEDIDA_CM)));
                medidas = new Cuadrado(lado);
                datosFigura.append(String.format(Mensajes.FORMATO_DATOS_FIGURA,Mensajes.ETIQUETA_LADO,lado, Mensajes.UNIDAD_MEDIDA_CM));
                break;

            case RECTANGULO:
                double base = Double.parseDouble(JOptionPane.showInputDialog(String.format(Mensajes.INGRESO_BASE, Mensajes.UNIDAD_MEDIDA_CM)));
                double altura = Double.parseDouble(JOptionPane.showInputDialog(String.format(Mensajes.INGRESO_ALTURA, Mensajes.UNIDAD_MEDIDA_CM)));
                medidas = new Rectangulo(base, altura);
                datosFigura.append(String.format(Mensajes.FORMATO_DATOS_FIGURA,Mensajes.ETIQUETA_BASE,base, Mensajes.UNIDAD_MEDIDA_CM));
                datosFigura.append(String.format(Mensajes.FORMATO_DATOS_FIGURA,Mensajes.ETIQUETA_ALTURA,altura, Mensajes.UNIDAD_MEDIDA_CM));
                break;

            case TRIANGULO_EQUILATERO:
                lado = Double.parseDouble(JOptionPane.showInputDialog(String.format(Mensajes.INGRESO_LADO, Mensajes.UNIDAD_MEDIDA_CM)));
                medidas = new TrianguloEquilatero(lado);
                datosFigura.append(String.format(Mensajes.FORMATO_DATOS_FIGURA,Mensajes.ETIQUETA_LADO,lado, Mensajes.UNIDAD_MEDIDA_CM));
                break;

            case TRIANGULO_ISOSCELES:
                lado = Double.parseDouble(JOptionPane.showInputDialog(String.format(Mensajes.INGRESO_LADO, Mensajes.UNIDAD_MEDIDA_CM)));
                base = Double.parseDouble(JOptionPane.showInputDialog(String.format(Mensajes.INGRESO_BASE, Mensajes.UNIDAD_MEDIDA_CM)));
                medidas = new TrianguloIsosceles(lado, base);
                datosFigura.append(String.format(Mensajes.FORMATO_DATOS_FIGURA,Mensajes.ETIQUETA_LADO,lado, Mensajes.UNIDAD_MEDIDA_CM));
                datosFigura.append(String.format(Mensajes.FORMATO_DATOS_FIGURA,Mensajes.ETIQUETA_BASE,base, Mensajes.UNIDAD_MEDIDA_CM));
                break;
        }

        datosFigura.append(String.format(Mensajes.FORMATO_DATOS_FIGURA,Mensajes.ETIQUETA_AREA,medidas.calcularArea(), Mensajes.UNIDAD_MEDIDA_CM));
        datosFigura.append(String.format(Mensajes.FORMATO_DATOS_FIGURA,Mensajes.ETIQUETA_PERIMETRO,medidas.calcularPerimetro(), Mensajes.UNIDAD_MEDIDA_CM));
        String archivo = String.valueOf(JOptionPane.showInputDialog(Mensajes.INGRESO_NOMBRE_ARCHIVO));
        Thread hiloEscribir = new Thread(new HiloEscribir(String.valueOf(datosFigura), archivo));
        hiloEscribir.start();
        String mensaje = "La figura ingresada fue " + figura.getNombre() + ", que tiene como perímetro: " + medidas.calcularPerimetro() + "cm y área: " + medidas.calcularArea() + "cm2";
        System.out.println(mensaje);

    }

    public static TipoFigurasEnum getNombreFigura(int opcion) {
        return Arrays.stream(TipoFigurasEnum.values()).filter(f -> f.getOpcion() == opcion).findFirst().orElseThrow(NoSuchElementException::new);
    }
    public static OpcionesEnum getNombreOpcion(int opcion) {
        return Arrays.stream(OpcionesEnum.values()).filter(f -> f.getOpcion() == opcion).findFirst().orElseThrow(NoSuchElementException::new);
    }
}