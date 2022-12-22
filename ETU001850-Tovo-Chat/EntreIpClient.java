package client;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class EntreIpClient
	{
		JFrame cadre;
		JTextField nomTexte;
		JTextField ipTexte;
		public static void main(String[] args)
			{
				EntreIpClient client = new EntreIpClient();
				client.go();
				
			}
		
		public void go()
			{
			cadre= new JFrame("Chat");
				JPanel panneau= new JPanel();
				JLabel textedemande= new JLabel("Veuillez entrez votre pseudo...");
				nomTexte= new JTextField(20);
				JLabel textedemande2= new JLabel("Veuillez entrez un IP...");
				ipTexte= new JTextField(20);
				ipTexte.setText("localhost");
				JButton boutonEnvoi = new JButton("Envoyer");
				boutonEnvoi.addActionListener(new EcouteBoutonEnvoi());
				panneau.add(textedemande);
				panneau.add(nomTexte);
				panneau.add(textedemande2);
				panneau.add(ipTexte);
				panneau.add(boutonEnvoi);
				cadre.getContentPane().add(BorderLayout.CENTER, panneau);
				cadre.setSize(300,350);
				cadre.setVisible(true);
			}
		public class EcouteBoutonEnvoi implements ActionListener
			{
				public void actionPerformed(ActionEvent ev)
					{
						try
							{
								cadre.setVisible(false);
								FrameClient chatfinal= new FrameClient(nomTexte.getText(),ipTexte.getText());
								chatfinal.go();
							}
						catch(Exception e)
							{
								e.printStackTrace();
							}
					}
			}
	}