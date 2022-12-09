package serveur.server;


import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class ChatServer{
    int portfinal=5555;
    JTextArea boiteInformation;
    public ChatServer (String port){
        portfinal=Integer.parseInt(port);
    }
    public ChatServer(){

    }
    ArrayList clientOutputStreams;
    public class GestionClient implements Runnable{
        BufferedReader lecture;
        Socket socket;
        public GestionClient(Socket socketClient){
            try {
                    socket=socketClient;
                    InputStreamReader isr = new InputStreamReader(socket.getInputStream());
                    lecture = new BufferedReader(isr);
            } catch (Exception e) {
                //TODO: handle exception
                e.printStackTrace();
            }
        }
        public void run(){
            String message;
            try {
                while((message=lecture.readLine()) !=null){
                    boiteInformation.setText(boiteInformation.getText()+"\nlire "+message);
                    afficherTous(message);
                }
            } catch (Exception e) {
                //TODO: handle exception
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        ChatServer chatserver = new ChatServer();
        chatserver.ihminstall();
        chatserver.go();
    }
    public void ihminstall()
			{
				JFrame fenetre= new JFrame("Serveur");
				JButton fermerfenetre= new JButton("Fermer la frnetre");
				fermerfenetre.addActionListener(new EcouteBoutonEnvoi());
				JPanel panneau= new JPanel();
				JPanel infopanneau= new JPanel();
				JPanel boutonpanneau= new JPanel();
				JLabel encourstexte= new JLabel("Status actuel: ");
				boiteInformation = new JTextArea(10,20);
				boiteInformation.setLineWrap(true);
				boiteInformation.setWrapStyleWord(true);
				boiteInformation.setEditable(false);
				infopanneau.add(encourstexte);
				infopanneau.add(boiteInformation);
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
						//fermer la fenetre
					}
			}
           public void go()
			{
					clientOutputStreams = new ArrayList();
				try
					{
						ServerSocket serverSocket = new ServerSocket(portfinal);
						while(true)
							{
								Socket socketClient=serverSocket.accept();
								PrintWriter ecriture = new PrintWriter(socketClient.getOutputStream());
								clientOutputStreams.add(ecriture);
								Thread t = new Thread(new GestionClient(socketClient));
								t.start();
								
								boiteInformation.setText(boiteInformation.getText()+"\nConnection etablie sur le port "+portfinal);
							}
					}
				catch(Exception e)
					{
						e.printStackTrace();
					}
			}
            public void afficherTous(String message)
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