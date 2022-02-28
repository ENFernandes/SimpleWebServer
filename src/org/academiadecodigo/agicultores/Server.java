package org.academiadecodigo.agicultores;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;

public class Server {

    private Socket clientSocket;
    private ServerSocket serverSocket;
    private BufferedReader in;
    private OutputStream out;
    private static int port;
    private Scanner sc = new Scanner(System.in);
    private String client;

    public Server() {
        System.out.printf("Qual a porta a Abrir?:");
        port = sc.nextInt();
        initServer();
    }

    public static void main(String[] args) {
        new Server();
    }

    public void initServer ()  {
        try {
            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();
            System.out.println("Cliente conectado: " + clientSocket.getInetAddress().getHostAddress());
            setupSocketStream();
        }
        catch (IOException ex) {
            System.out.printf("Erro a criar o Servidor");
        }
    }

    public void setupSocketStream() throws IOException {
        while (serverSocket.isBound()) {
            try {
                try {
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    client = in.readLine().split(" ")[1];
                    in.close();
                    File file =new File(client);
                    BufferedInputStream fromFile = new BufferedInputStream(new FileInputStream(file));
                    byte[] read=new byte[(int)file.length()-1];
                    int readBytes=0;
                    while ((readBytes = fromFile.read(read))!=-1){
                        out = clientSocket.getOutputStream();
                        out.write(read,0,readBytes);
                    }


                } catch (IOException e) {
                    System.out.printf(e.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                out.close();
            }
        }
    }
}
