package com.mycompany.ligafutbol;

import java.io.*;

/**
 * Clase FicheroFutbol
 *
 * Esta clase contiene el interfaz con el fichero de datos, es decir, todas las rutinas
 * para leer y escribir datos del fichero. No produce ninguna salida por consola.
 */
public class FicheroFutbol {

    private final String NOMBRE_FICH_DATOS = "liga.txt"; // Nombre del fichero de datos
    public final int MAX_EQUIPOS = 20; // Número máximo de equipos en la liga

    public int save(String nombreEq, int partJug, int partGan, int partEmp, int partPer, int puntos) {
        int resultado = -1; // Se inicializa como error
        try (FileWriter f = new FileWriter(NOMBRE_FICH_DATOS, true)) {
            f.write(nombreEq + ";" + partJug + ";" + partGan + ";" + partEmp + ";" + partPer + ";" + puntos + "\n");
            resultado = 0; // Se establece como éxito
        } catch (IOException e) {
            System.err.println("Error al escribir en el fichero de datos: " + e.getMessage());
        }
        return resultado;
    }

    public String[] getAll() {
        String[] datos = new String[MAX_EQUIPOS]; // MAX_EQUIPOS es el máximo de equipos que podemos tener en el fichero
        try (BufferedReader br = new BufferedReader(new FileReader(NOMBRE_FICH_DATOS))) {
            String linea;
            int i = 0;
            while ((linea = br.readLine()) != null) {
                datos[i++] = linea;
            }
        } catch (IOException e) {
            System.err.println("Error al leer el fichero de datos: " + e.getMessage());
            datos = null;
        }
        return datos;
    }

    public String get(String nombreEq) {
        String linea = null;
        try (BufferedReader br = new BufferedReader(new FileReader(NOMBRE_FICH_DATOS))) {
            while ((linea = br.readLine()) != null) {
                if (linea.contains(nombreEq)) {
                    return linea;
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el fichero de datos: " + e.getMessage());
        }
        return null;
    }

    public int getPos(String nombreEq) {
        int posicion = -1;
        try (BufferedReader br = new BufferedReader(new FileReader(NOMBRE_FICH_DATOS))) {
            String linea;
            int i = 0;
            while ((linea = br.readLine()) != null) {
                if (linea.contains(nombreEq)) {
                    posicion = i;
                    break;
                }
                i++;
            }
        } catch (IOException e) {
            System.err.println("Error al leer el fichero de datos: " + e.getMessage());
        }
        return posicion;
    }

    public int delete(String nombreEq) {
        int result = -1; // Resultado del borrado
        int pos = getPos(nombreEq); // Obtenemos la posición del equipo en el fichero
        if (pos != -1) {
            try {
                File fSource = new File(NOMBRE_FICH_DATOS);
                File fDest = new File("temp");
                try (BufferedReader br = new BufferedReader(new FileReader(fSource));
                     FileWriter newFile = new FileWriter(fDest)) {
                    int numLinea = 0; // Contador del número de líneas
                    String linea;
                    while ((linea = br.readLine()) != null) {
                        if (numLinea != pos) { // pos es la línea que no queremos copiar al nuevo fichero
                            newFile.write(linea + "\n");
                        }
                        numLinea++;
                    }
                }
                fSource.delete();
                fDest.renameTo(fSource);
                result = 0;
            } catch (IOException e) {
                System.err.println("Error al leer el fichero de datos: " + e.getMessage());
            }
        }
        return result;
    }

    public int update(int pos, String nombreEq, int partJug, int partGan, int partEmp, int partPer, int puntos) {
        int result = -1; // Resultado del update
        try {
            File fSource = new File(NOMBRE_FICH_DATOS);
            File fDest = new File("temp");
            try (BufferedReader br = new BufferedReader(new FileReader(fSource));
                 FileWriter newFile = new FileWriter(fDest)) {
                int numLinea = 0; // Contador del número de líneas
                String linea;
                while ((linea = br.readLine()) != null) {
                    if (numLinea == pos) { // pos es la línea que queremos modificar
                        newFile.write(nombreEq + ";" + partJug + ";" + partGan + ";" + partEmp + ";" + partPer + ";" + puntos + "\n");
                    } else {
                        newFile.write(linea + "\n");
                    }
                    numLinea++;
                }
            }
            fSource.delete();
            fDest.renameTo(fSource);
            result = 0;
        } catch (IOException e) {
            System.err.println("Error al leer el fichero de datos: " + e.getMessage());
        }
        return result;
    }

    public int sort() {
        int result = -1;

        // Cargamos todos los datos en un array de Strings
        String[] datos = getAll();

        if (datos == null) { // No hay datos para ordenar
            result = -1;
        } else {
            // Creamos un array con los puntos
            int[] puntos = new int[MAX_EQUIPOS];
            for (int i = 0; i < datos.length; i++) {
                if (datos[i] != null) {
                    puntos[i] = Integer.parseInt(datos[i].split(";")[5]);
                } else {
                    puntos[i] = -1;
                }
            }

            // Ordenamos el array de puntos con el método de la burbuja.
            // Cada vez que intercambiemos dos posiciones, intercambiaremos también el array con todos los datos
            for (int i = 0; i < datos.length; i++) {
                for (int j = 0; j < datos.length - i - 1; j++) {
                    if (puntos[j] < puntos[j + 1]) {
                        // Intercambiamos los puntos
                        int aux = puntos[j];
                        puntos[j] = puntos[j + 1];
                        puntos[j + 1] = aux;
                        // Intercambiamos equipos en el array de datos
                        String auxStr = datos[j];
                        datos[j] = datos[j + 1];
                        datos[j + 1] = auxStr;
                    }
                }
            }

            // Escribimos el array de datos ordenados en liga.txt, sustituyendo los datos antiguos
            try (FileWriter f = new FileWriter(NOMBRE_FICH_DATOS)) {
                for (String dato : datos) {
                    if (dato != null) {
                        f.write(dato + "\n");
                    }
                }
                result = 0;
            } catch (IOException e) {
                System.err.println("Error al escibir en el fichero: " + e.getMessage());
            }
        }
        return result;
    }
}
