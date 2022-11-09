package vistas;

import javax.swing.*;
import java.awt.*;

public class vista2 extends JFrame{
    private JButton enviarButton;
    private JTextField textField1;
    private JPanel mainPanel;

    public vista2(){
        setContentPane(mainPanel);
        setTitle("Hola");
        setSize(450,300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public JButton getEnviarButton() {
        return enviarButton;
    }

    public void setEnviarButton(JButton enviarButton) {
        this.enviarButton = enviarButton;
    }

    public JTextField getTextField1() {
        return textField1;
    }

    public void setTextField1(JTextField textField1) {
        this.textField1 = textField1;
    }
}
