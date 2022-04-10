package constants;

public enum OpcionesEnum {

    OPCION_REGISTRAR("Registrar datos de Figura", 1),
    OPCION_ABRIR("Abrir archivos de Figuras", 2),
    OPCION_SALIR("Salir", 3);

    private final String nombre;
    private final int opcion;

    OpcionesEnum(String nombre, int opcion) {
        this.nombre = nombre;
        this.opcion = opcion;
    }

    public String getNombre() {
        return nombre;
    }

    public int getOpcion() {
        return opcion;
    }
}