package server;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class ChatServer
	{
		int portfinal=5000;
		JTextArea boiteinfo;

		public ChatServer(String port)
			{
				portfinal=Integer.parseInt(port);
			}
			public ChatServer()
			{
			}
		ArrayList clientOutputStreams;
		public class GestionClient implements Runnable
			{
				BufferedReader lecture;
				Socket sock;
				public GestionClient(Socket socketClient)
					{
						try
							{
								sock=socketClient;
								InputStreamReader isr = new InputStreamReader(sock.getInputStream());
								lecture= new BufferedReader(isr);
							}
						catch(Exception e)
							{
								e.printStackTrace();
							}
					}
				public void run()
					{
						String message;
						try
							{
								while((message=lecture.readLine()) !=null)
									{
									boiteinfo.setText(boiteinfo.getText()+"\nlire "+message);
									afficherATous(message);
									}
							}
						catch(Exception e)
							{
								e.printStackTrace();
							}
					}
			}
		public static void main(String[] args)
			{
				ChatServer Chatserver= new ChatServer();
				Chatserver.ihminstall();
				Chatserver.go();
			}
		public void ihminstall()
			{
				JFrame fenetre= new JFrame("Chat");
				JButton fermerfenetre= new JButton("Fermer");
				fermerfenetre.addActionListener(new EcouteBoutonEnvoi());
				JPanel panneau= new JPanel();
				JPanel infopanneau= new JPanel();
				JPanel boutonpanneau= new JPanel();
				JLabel encourstexte= new JLabel("Status actuel: ");
				boiteinfo = new JTextArea(10,20);
				boiteinfo.setLineWrap(true);
				boiteinfo.setWrapStyleWord(true);
				boiteinfo.setEditable(false);
				infopanneau.add(encourstexte);
				infopanneau.add(boiteinfo);
				boutonpanneau.add(fermerfenetre);
				panneau.add(infopanneau);
				panneau.add(boutonpanneau);
				panneau.setLayout(new BoxLayout(panneau, BoxLayout.Y_AXIS));
				fenetre.getContentPane().add(BorderLayout.CENTER, panneau);
				fenetre.setSize(300,350);
				fenetre.setVisible(true);
				fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		public class EcouteBoutonEnvoi implements ActionListener
			{
				public void actionPerformed(ActionEvent ev)
					{
						//fermer la fen�tre
					}
			}
		public void go()
			{
				clientOutputStreams = new ArrayList();
				try
					{
						ServerSocket serverSock = new ServerSocket(portfinal);
						while(true)
							{
								Socket socketClient=serverSock.accept();
								PrintWriter ecriture = new PrintWriter(socketClient.getOutputStream());
								clientOutputStreams.add(ecriture);
								Thread t = new Thread(new GestionClient(socketClient));
								t.start();
								
								boiteinfo.setText(boiteinfo.getText()+"\nBienvenue, vous etes connectee sur le port" +portfinal);
							}
					}
				catch(Exception e)
					{
						e.printStackTrace();
					}
			}
		public void afficherATous(String message)
			{
				Iterator it=clientOutputStreams.iterator();
				while(it.hasNext())
					{
						try
							{
								PrintWriter ecriture=(PrintWriter) it.next();
								ecriture.println(message);
								ecriture.flush();
							}
						catch(Exception e)
							{
								e.printStackTrace();
							}
					}
			}
	}