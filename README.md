colocar una base de datos y mostrar el tiempo real en el tablero

package internet;

import java.io.*;

public class Cabina {
    private Cliente[] computadoras;
    private final String archivoClientes = "clientes.txt";

    public Cabina(int capacidad) {
        computadoras = new Cliente[capacidad];
        leerClientes(); // Cargar datos al iniciar
    }

    public boolean registrarCliente(Cliente cliente) {
        int pc = cliente.computadora - 1;
        if (pc >= 0 && pc < computadoras.length && computadoras[pc] == null) {
            computadoras[pc] = cliente;
            return true;
        }
        return false;
    }

    public Cliente buscarCliente(String dni) {
        for (Cliente c : computadoras) {
            if (c != null && c.dni.equals(dni)) {
                return c;
            }
        }
        return null;
    }

    public boolean eliminarCliente(String dni) {
        for (int i = 0; i < computadoras.length; i++) {
            if (computadoras[i] != null && computadoras[i].dni.equals(dni)) {
                computadoras[i].timer.stop();
                computadoras[i] = null;
                return true;
            }
        }
        return false;
    }

    public void liberarComputadora(int pc) {
        if (pc >= 0 && pc < computadoras.length) {
            computadoras[pc] = null;
        }
    }

    public String mostrar() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < computadoras.length; i++) {
            sb.append("PC ").append(i + 1).append(" → ");
            if (computadoras[i] != null) {
                sb.append(computadoras[i]).append("\n");
            } else {
                sb.append("[vacío]\n");
            }
        }
        return sb.toString();
    }

    //  Método para leer clientes desde el archivo .txt
    private void leerClientes() {
        File archivo = new File(archivoClientes);
        if (!archivo.exists()) return; // No hacer nada si no existe el archivo

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                if (datos.length == 5) {
                    String dni = datos[0];
                    String nombre = datos[1];
                    int horas = Integer.parseInt(datos[2]);
                    int pc = Integer.parseInt(datos[3]);
                    double pago = Double.parseDouble(datos[4]);

                    Cliente cliente = new Cliente(dni, nombre, horas, pago, pc,
                            () -> liberarComputadora(pc - 1));
                    computadoras[pc - 1] = cliente;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    // Método para guardar los clientes en el archivo .txt
    public void guardarClientes() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoClientes))) {
            for (Cliente c : computadoras) {
                if (c != null) {
                    String linea = c.dni + ";" + c.nombre + ";" + c.horas + ";" + c.computadora + ";" + c.pago;
                    bw.write(linea);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error al guardar los clientes: " + e.getMessage());
        }
    }
}
.
