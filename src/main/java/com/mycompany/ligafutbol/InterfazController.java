package com.mycompany.ligafutbol;

import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;


public class InterfazController {

    @FXML
    private  FicheroFutbol fichDatos = new FicheroFutbol();
    @FXML
    private Label error,ordenado, mod, borrador;
    @FXML
    private  TextField nombreEquipo, partidosJugados, partidosGanados, partidosEmpatados;
    @FXML
    private TextArea mostrarEquipos;
    @FXML
    private TextArea buscarEq;
    @FXML
    private TextField modificarEquipo;
    @FXML
    private TextField nombreEquipobusc;
    @FXML
    private TextField nombreEquipoMod, modPG, modPJ, modPE;
    @FXML
    private TextField nombreEquipoDelete;
    @FXML
    public void salir() {
        System.exit(0);
    }
    @FXML
    public void volverAtras() throws IOException {
        App.setRoot("vistaMain");
    }
    @FXML
    public void abrirVistaAnadir() throws IOException {
        App.setRoot("vistaAnadir");
    }
    @FXML
    public void abrirVistaMostrar() throws IOException {
        App.setRoot("vistaMostrar");
    }
    @FXML
    public void abrirVistaBuscar() throws IOException {
        App.setRoot("vistaBuscar");
    }
    @FXML
    public void abrirVistaBorrar() throws IOException {
        App.setRoot("vistaBorrar");
    }
    @FXML
    public void abrirVistaModificar() throws IOException {
        App.setRoot("vistaModificar");
    }
    @FXML
    public void abrirVistaOrdenar() throws IOException {
        App.setRoot("vistaOrdenar");
    }



// Metodos para las vistasssssssssssssssssssssssssssssss
    @FXML
    public  void anadirEquipo() {
        int pj, pg, pp, pe, puntos;  // Partidos jugados, ganados, perdidos y empatados


        // Nombre del equipo
        String nombreEq = nombreEquipo.getText();

        // Partidos jugados
        pj = Integer.parseInt(partidosJugados.getText());

        // Partidos ganados (no pueden ser más que los partidos jugados)

            pg = Integer.parseInt(partidosGanados.getText());


        // Partidos empatados (no pueden ser más que los partidos jugados menos los ganados)

            pe = Integer.parseInt(partidosEmpatados.getText());

            boolean correcto = true;

            if(pg > pj){
                error.setText("Error, escribe los datos bien");
                correcto = false;
            }
            else {
                // Partidos perdidos (no se preguntan sino que se calculan)
                pp = pj - pg - pe;

                // Puntos. Se calcula como partGanados * 3 + partEmpatados
                puntos = pg * 3 + pe;

                // Escribimos todos los datos en el fichero
                int result = fichDatos.save(nombreEq, pj, pg, pe, pp, puntos);
                if (result == 0) {
                    error.setText("Equipo guardado con éxito");
                } else {
                    error.setText("Error al guardar el equipo");
                }
            }
    }

    @FXML
    public void mostrarEquipos() {
        String[] listaEquipos = fichDatos.getAll(); // Recuperamos la lista de equipos

        if (listaEquipos == null) {
            System.out.println("No se puede mostrar la lista de equipos");
        } else {
            StringBuilder equiposMostrados = new StringBuilder();

            for (String equipoStr : listaEquipos) {
                if (equipoStr != null) {
                    String[] equipo = equipoStr.split(";");
                    String equipoFormateado = String.format("|%-22s|%7s|%8s|%8s|%7s|%9s|\n", equipo[0], equipo[1], equipo[2], equipo[3], equipo[4], equipo[5]);
                    equiposMostrados.append(equipoFormateado);
                }
            }
            mostrarEquipos.appendText("|Equipo                | JUG | GAN | EMP | PER | PUNT |" + "\n");
            mostrarEquipos.appendText("+-------------------------+---+---+---+---+----+" + "\n");
            mostrarEquipos.appendText(equiposMostrados.toString());
        }
    }

    @FXML
    public  void buscarEquipo() {


        String nombreEq = nombreEquipobusc.getText();
        String datosEquipoStr = fichDatos.get(nombreEq);
        if (datosEquipoStr == null) {
            System.out.println("No se encontró ese equipo");
        } else {

            String[] datosEquipo = datosEquipoStr.split(";");
            buscarEq.appendText("NOMBRE DEL EQUIPO: " + datosEquipo[0] + "\n");
            buscarEq.appendText(" -Partidos jugados: " + datosEquipo[1] + "\n");
            buscarEq.appendText(" -Partidos ganados: " + datosEquipo[2] + "\n");
            buscarEq.appendText(" -Partidos empatados: " + datosEquipo[3] + "\n");
            buscarEq.appendText(" -Partidos perdidos: " + datosEquipo[4] + "\n");
            buscarEq.appendText("- PUNTOS: " + datosEquipo[5]);
        }

    }

    @FXML

    public void modificarEquipo() {
        String nombreEq = nombreEquipoMod.getText();
        String datosEquipo = fichDatos.get(nombreEq);
        int posicionEquipo = fichDatos.getPos(nombreEq);

        if (datosEquipo == null || posicionEquipo == -1) {
            modificarEquipo.setText("No se encontró ese equipo");
        } else {
            String[] datosAntiguos = datosEquipo.split(";");
            String entrada;
            int pj, pg, pe, puntos;

            // Nombre del equipo
            if (nombreEq.equals("")) nombreEq = datosAntiguos[0];
            // Partidos jugados
            entrada = modPJ.getText();
            pj = entrada.equals("") ? Integer.parseInt(datosAntiguos[1]) : Integer.parseInt(entrada);
            // Partidos ganados
            entrada = modPG.getText();
            pg = entrada.equals("") ? Integer.parseInt(datosAntiguos[2]) : Integer.parseInt(entrada);
            // Validación de partidos ganados
            if (pg > pj) {
                System.out.println("Error: El número de partidos ganados no puede ser mayor que los partidos jugados.");
                return;
            }

            // Partidos empatados
            entrada = modPE.getText();
            pe = entrada.equals("") ? Integer.parseInt(datosAntiguos[3]) : Integer.parseInt(entrada);

            // Validación de partidos empatados
            if (pe > (pj - pg)) {
                System.out.println("Error: El número de partidos empatados no puede ser mayor que los partidos jugados menos los ganados.");
                return;
            }

            // Partidos perdidos
            int pp = pj - pg - pe;
            // Puntos
            puntos = pg * 3 + pe;
            // Actualización de datos en el fichero
            int result = fichDatos.update(posicionEquipo, nombreEq, pj, pg, pe, pp, puntos);
            if (result == -1) {
                System.out.println("Error al actualizar el fichero");
            } else {
                System.out.println("Datos actualizados exitosamente");
            }
            if (result == 0) {
                mod.setText("Equipo modificado con éxito");
            } else {
                mod.setText("Error al modificar el equipo");
            }
        }
    }

    @FXML
    public void borrarEquipo() {
        String nombreEq = nombreEquipoDelete.getText();
        int resultado = fichDatos.delete(nombreEq);
        if (resultado == -1) {
            System.out.println("No se encontró ese equipo");
        } else {
            System.out.println("Equipo borrado exitosamente");
        }
        if (resultado == 0) {
            borrador.setText("Equipo borrado con éxito");
        } else {
            borrador.setText("Error al borrar el equipo");
        }
    }

    @FXML
    public  void ordenarFichero() {
        int result = fichDatos.sort();
        if (result == 0) {
            ordenado.setText("Fichero ordenado exitosamente");
        } else {
            ordenado.setText("Error al ordenar el fichero");
        }
    }

}
