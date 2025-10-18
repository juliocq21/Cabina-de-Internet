package internet;

import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Cliente {
    String usuario;
    String nombre;
    int horas;
    double pago;
    int computadora;
    Timer timer; // temporizador regresivo

    public Cliente(String usuario, String nombre, int horas, double tarifa, int computadora, Runnable alFinalizar) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.horas = horas;
        this.pago = horas * tarifa;
        this.computadora = computadora;

        // Temporizador regresivo (1 segundo = 1 hora simulada)
        timer = new Timer(3600000, e -> {
            if (this.horas > 0) {
                this.horas--;
            } else {
                ((Timer) e.getSource()).stop();
                JOptionPane.showMessageDialog(null,
                        "Tiempo terminado para " + nombre + " en la PC " + computadora,
                        "Fin de Sesión", JOptionPane.INFORMATION_MESSAGE);
                alFinalizar.run(); // libera la computadora
            }
        });
        timer.start();
    }

    @Override
    public String toString() {
        return nombre + " (Usuario: " + usuario + ", PC: " + computadora + ", Horas: " + horas + ", Pago: S/" + pago + ")";
    }
}

