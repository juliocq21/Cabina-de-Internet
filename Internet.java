package internet;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author LAB-USR-AREQUIPA
 */
public class Internet extends JFrame{
    private Cabina cabina = new Cabina(21);
    private JTextField txtDni, txtNombre, txtHoras, txtPc;
    private JTextArea areaMostrar;
    private double tarifa = 3.0;

    public Internet() {
        setTitle("Cabina de Internet con Temporizador");
        setSize(650, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel de inputs
        JPanel panelInputs = new JPanel(new GridLayout(5, 2, 5, 5));
        panelInputs.add(new JLabel("Usuario:"));
        txtDni = new JTextField();
        panelInputs.add(txtDni);

        panelInputs.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelInputs.add(txtNombre);

        panelInputs.add(new JLabel("Horas:"));
        txtHoras = new JTextField();
        panelInputs.add(txtHoras);

        panelInputs.add(new JLabel("PC (1-20):"));
        txtPc = new JTextField();
        panelInputs.add(txtPc);

        add(panelInputs, BorderLayout.NORTH);

        // Área de salida
        areaMostrar = new JTextArea();
        areaMostrar.setEditable(false);
        add(new JScrollPane(areaMostrar), BorderLayout.CENTER);

        // Botones
        JPanel panelBotones = new JPanel();
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnBuscar = new JButton("Buscar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnMostrar = new JButton("Mostrar");

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnMostrar);
        add(panelBotones, BorderLayout.SOUTH);

        // Acciones
        btnRegistrar.addActionListener(e -> {
            try {
                String dni = txtDni.getText();
                String nombre = txtNombre.getText();
                int horas = Integer.parseInt(txtHoras.getText());
                int pc = Integer.parseInt(txtPc.getText());

                Cliente cliente = new Cliente(dni, nombre, horas, tarifa, pc,
                        () -> cabina.liberarComputadora(pc));

                if (cabina.registrarCliente(cliente)) {
                    RegistroClientes.guardarRegistro(cliente);
                    areaMostrar.setText(" Cliente registrado en PC " + pc + "\n");
                } else {
                    areaMostrar.setText(" La PC ya está ocupada.\n");
                }
                
            } catch (Exception ex) {
                areaMostrar.setText("️ Datos inválidos.\n");
            }
        });

        btnBuscar.addActionListener(e -> {
            String dni = txtDni.getText();
            Cliente c = cabina.buscarCliente(dni);
            if (c != null) {
                areaMostrar.setText(" Encontrado: " + c + "\n");
            } else {
                areaMostrar.setText(" Cliente no encontrado.\n");
            }
        });

        btnEliminar.addActionListener(e -> {
            String usuario = txtDni.getText();
            if (cabina.eliminarCliente(usuario)) {
                areaMostrar.setText(" Cliente eliminado.\n");
            } else {
                areaMostrar.setText(" Cliente no encontrado.\n");
            }
        });

        btnMostrar.addActionListener(e -> {
            areaMostrar.setText(cabina.mostrar());
        });
    }
    
    public class RegistroClientes {

    private static final String ARCHIVO = "registro_clientes.txt";

    public static void guardarRegistro(Cliente cliente) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            String linea = String.format("[%s] Usuario: %s, Nombre: %s, PC: %d, Horas: %d, Pago: S/%.2f",
                    LocalDateTime.now(),
                    cliente.usuario,
                    cliente.nombre,
                    cliente.computadora,
                    cliente.horas,
                    cliente.pago);
            writer.write(linea);
            writer.newLine(); // Salto de línea
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
}
}
