/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package internet;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author LAB-USR-AREQUIPA
 */
public class Login extends JFrame{
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private HashMap<String, String> usuarios;

    public Login() {
        setTitle("Login - Cabina de Internet");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Crear usuarios válidos (puedes cargar de un archivo si deseas)
        usuarios = new HashMap<>();
        usuarios.put("admin", "1234");
        usuarios.put("empleado", "cabina");

        // Panel de entrada
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(new JLabel("Usuario:"));
        txtUsuario = new JTextField();
        panel.add(txtUsuario);

        panel.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        JButton btnIngresar = new JButton("Ingresar");
        panel.add(new JLabel()); // espacio vacío
        panel.add(btnIngresar);

        add(panel, BorderLayout.CENTER);

        // Acción de login
        btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = txtUsuario.getText();
                String pass = new String(txtPassword.getPassword());

                if (usuarios.containsKey(usuario) && usuarios.get(usuario).equals(pass)) {
                    JOptionPane.showMessageDialog(Login.this, "Bienvenido " + usuario);
                    abrirSistema();
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Usuario o contraseña incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            private void abrirSistema() {
                dispose(); // cerrar ventana de login
        SwingUtilities.invokeLater(() -> new Internet().setVisible(true)); // abrir ventana principal
            }
        });
}
}

