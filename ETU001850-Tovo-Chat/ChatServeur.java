package server;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class ChatServeur
	{
		JFrame fenetre;
		JTextField nomTexte;
		JTextField ip;
		JTextField port;
		public static void main(String[] args)
			{
				ChatServeur serveur = new ChatServeur();
				serveur.go();
				
			}
			public ChatServeur(){

			}
		
		public void go()
			{
			fenetre= new JFrame("Chat");
			
				JPanel panneau= new JPanel();
				JPanel infopanneau= new JPanel();
				JPanel boutonpanneau= new JPanel();
				JPanel ippanneau= new JPanel();
				JPanel portpanneau= new JPanel();
				JLabel texteinfo= new JLabel("Veuillez entrez les infos...");
				JLabel iptexte= new JLabel("IP: ");
				JLabel porttexte= new JLabel("Port: ");
				ip= new JTextField(20);
				ip.setText("localhost");
				port= new JTextField(20);
				port.setText("5000");
				JButton boutonEnvoi = new JButton("Envoyer");
				boutonEnvoi.addActionListener(new EcouteBoutonEnvoi());
				panneau.setLayout(new BoxLayout(panneau, BoxLayout.Y_AXIS));
				infopanneau.add(texteinfo);
				ippanneau.add(iptexte);
				ippanneau.add(ip);
				portpanneau.add(porttexte);
				portpanneau.add(port);
				
				panneau.add(infopanneau);
				panneau.add(portpanneau);
				panneau.add(ippanneau);
				boutonpanneau.add(boutonEnvoi);
				
				panneau.add(boutonpanneau);
				fenetre.getContentPane().add(BorderLayout.CENTER, panneau);
				fenetre.setSize(300,350);
				fenetre.setVisible(true);
				fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		public class EcouteBoutonEnvoi implements ActionListener
			{
				public void actionPerformed(ActionEvent ev)
					{
						try
							{
								fenetre.setVisible(false);
								ChatServer serverfinal= new ChatServer(port.getText());
								serverfinal.go();
							}
						catch(Exception e)
							{
								e.printStackTrace();
							}
					}
			}
	}