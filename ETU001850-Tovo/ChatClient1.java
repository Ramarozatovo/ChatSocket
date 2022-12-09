package client.server;


import java.io.*;
import java.net.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

public class ChatClient1 {
    JTextArea entrants;
    JTextField sortants;
	BufferedReader lecture;
	PrintWriter ecriture;
	Socket sock;
	String nompseudo;
	String iprotocol= "localhost";
    public ChatClient1(String nom){
        nompseudo=nom;
    }
    public ChatClient1(String nom, String internetProtocol){
        iprotocol=internetProtocol;
        nompseudo=nom;
    }
    public ChatClient1(){

    }
    
    public static void main(String[] args) throws Exception {
        ChatClient1 client = new ChatClient1();
        client.go();
    }
    public void go(){
        JFrame fenetre = new JFrame("Client chat");
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panneau = new JPanel();
        entrants= new JTextArea(15,30);
        entrants.setLineWrap(true);
        entrants.setWrapStyleWord(true);
        entrants.setEditable(false);
        JScrollPane zoneTexte = new JScrollPane(entrants);
        zoneTexte.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        zoneTexte.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        sortants = new JTextField(20);
        JButton boutonEnvoi = new JButton("Envoyer");
        boutonEnvoi.addActionListener(new EcouteBoutonEnvoi());
        panneau.add(zoneTexte);
        panneau.add(sortants);
        panneau.add(boutonEnvoi);
        installerReseau();
        Thread threadLecture = new Thread(new LectureEntrants());
        threadLecture.start();
        fenetre.getContentPane().add(BorderLayout.CENTER, panneau);
        fenetre.setSize(400,350);
        fenetre.setVisible(true);
    }
    public void installerReseau(){
        try {
            sock = new Socket(iprotocol,5555);
            InputStreamReader isr = new InputStreamReader(sock.getInputStream());
            lecture = new BufferedReader(isr);
            ecriture = new PrintWriter(sock.getOutputStream());
            System.out.println("Connection etablie");
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
    }
    public class EcouteBoutonEnvoi implements ActionListener{
        public void actionPerformed(ActionEvent evt){
            try {
                ecriture.println(nompseudo+" dit: "+sortants.getText());
                ecriture.flush();
            } catch (Exception e) {
                //TODO: handle exception
                e.printStackTrace();
            }
            sortants.setText("");
            sortants.requestFocus();
        }
    }
    public class LectureEntrants implements Runnable{
        public void run(){
            String message;
            try {
                while((message=lecture.readLine()) !=null){
                    System.out.println("lire "+message);
                    entrants.append(message +"\n");
                }
            } catch (Exception e) {
                //TODO: handle exception
                e.printStackTrace();
            }
        }
    }
}
