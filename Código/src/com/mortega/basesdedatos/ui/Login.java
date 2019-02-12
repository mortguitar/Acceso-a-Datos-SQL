package com.mortega.basesdedatos.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Login extends JDialog implements ActionListener, KeyListener {

    public JTextField usuarioText;
    public JPasswordField passText;
    public JLabel lblNewLabel;
    public JLabel lblNewLabel_1;
    public JButton salirB;
    public JButton accederB;
    private JLabel lbMensaje;

    private String usuario;
    private String contrasena;

    public Login() {
        setTitle("Login");
        setBounds(100, 100, 295, 184);
        getContentPane().setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        lbMensaje = new JLabel();
        lbMensaje.setForeground(Color.RED);
        getContentPane().add(lbMensaje, BorderLayout.NORTH);

        usuarioText = new JTextField();
        usuarioText.setColumns(10);

        passText = new JPasswordField();
        passText.setColumns(10);

        lblNewLabel = new JLabel("Usuario");

        lblNewLabel_1 = new JLabel("Contrase\u00F1a");
        GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
        gl_contentPanel.setHorizontalGroup(
                gl_contentPanel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPanel.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
                                        .addComponent(lblNewLabel)
                                        .addComponent(lblNewLabel_1))
                                .addGap(21)
                                .addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
                                        .addComponent(passText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(usuarioText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(261, Short.MAX_VALUE))
        );
        gl_contentPanel.setVerticalGroup(
                gl_contentPanel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPanel.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(usuarioText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblNewLabel))
                                .addGap(18)
                                .addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(passText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblNewLabel_1))
                                .addContainerGap(149, Short.MAX_VALUE))
        );
        contentPanel.setLayout(gl_contentPanel);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        accederB = new JButton("Acceder");
        accederB.setActionCommand("acceder");
        buttonPane.add(accederB);

        salirB = new JButton("Salir");
        salirB.setActionCommand("salir");
        buttonPane.add(salirB);

        passText.addKeyListener(this);
        usuarioText.addKeyListener(this);

        accederB.addActionListener(this);
        salirB.addActionListener(this);

        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setModal(true);

    }

    public void mostrarDialogo() {
        setVisible(true);
    }

    public String getUsuario() { return usuario; }

    public String getContrasena() { return contrasena; }

    public void setMensaje(String mensaje) { lbMensaje.setText(mensaje); }

    public void limpiarContrasena() {
        passText.setText("");
        passText.requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "acceder":
                usuario = usuarioText.getText();
                contrasena = String.valueOf(passText.getPassword());
                setVisible(false);
                break;
            case "salir":
                System.exit(0);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            accederB.doClick();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
