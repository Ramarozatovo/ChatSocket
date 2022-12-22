package client;


import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class FrameClient
	{
		JTextArea entrants;
		JTextField sortants;
		BufferedReader lecture;
		PrintWriter ecriture;
		Socket sock;
		String nompseudo;
		String iprotocol= "localhost";
		public FrameClient(String nom)
			{
				nompseudo=nom;
			}
		public FrameClient(String nom, String internetprotocol)
			{
				iprotocol=internetprotocol;
				nompseudo=nom;
			}
		public FrameClient()
			{
			}
		public static void main(String[] args)
			{
				FrameClient client = new FrameClient();
				client.go();
			}
		public void go()
			{
				JFrame fenetre = new JFrame("Chat");
				fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				JPanel panneau= new JPanel();
				entrants=new JTextArea(15,30);
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
				Thread threadLecture= new Thread(new LectureEntrants());
				threadLecture.start();
				fenetre.getContentPane().add(BorderLayout.CENTER, panneau);
				fenetre.setSize(400,350);
				fenetre.setVisible(true);
			}
		private void installerReseau()
			{
				try
					{
						sock= new Socket(iprotocol,5000);
						InputStreamReader isr= new InputStreamReader(sock.getInputStream());
						lecture= new BufferedReader(isr);
						ecriture= new PrintWriter(sock.getOutputStream());
						System.out.println("Connection etablie");
					}
				catch(Exception e)
					{
						e.printStackTrace();
					}
			}
		public class EcouteBoutonEnvoi implements ActionListener
			{
				public void actionPerformed(ActionEvent ev)
					{
						try
							{
								ecriture.println(nompseudo+" dit: "+sortants.getText());
								ecriture.flush();
							}
						catch(Exception e)
							{
								e.printStackTrace();
							}
						sortants.setText("");
						sortants.requestFocus();
					}
			}
		public class LectureEntrants implements Runnable
			{
				public void run()
					{
						String message;
						try
							{
								while((message=lecture.readLine()) !=null)
									{
										System.out.println("lire "+message);
										entrants.append(message +"\n");
									}
							}
						catch(Exception e)
							{
								e.printStackTrace();
							}
					}
			}
	}