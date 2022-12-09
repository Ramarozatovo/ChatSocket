package client.server;


import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChatClient {

    JFrame fenetre;
    JTextField text1;
    JTextField ipEcrire;

    public static void main(String[] args)throws Exception {
        ChatClient client = new ChatClient();
        client.go();
    }
    public void go(){
        fenetre = new JFrame("Chat Client");
        JPanel panneau = new JPanel();
        JLabel textedemande = new JLabel("Votre pseudo ....");
        text1 =new JTextField(20);
        JLabel textedemande2 = new JLabel("Votre addresse ip ....");
        ipEcrire = new JTextField(20);
        ipEcrire.setText("localhost");
        JButton boutonEnvoi = new JButton("Envoyer");
        boutonEnvoi.addActionListener(new EcouteBoutonEnvoi());
        panneau.add(textedemande);
        panneau.add(text1);
        panneau.add(textedemande2);
        panneau.add(ipEcrire);
        panneau.add(boutonEnvoi);
        fenetre.getContentPane().add(BorderLayout.CENTER, panneau);
        fenetre.setSize(300,350);
        fenetre.setVisible(true);
    }
    public class EcouteBoutonEnvoi implements ActionListener{
        public void actionPerformed(ActionEvent evt){
            try {
                fenetre.setVisible(false);
                ChatClient1 chatfinal = new ChatClient1(text1.getText(),ipEcrire.getText());
                chatfinal.go();
            } catch (Exception e) {
                //TODO: handle exception
                e.printStackTrace();
            }
        }
    }
}