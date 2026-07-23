package server;

import client.Matrix;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class parallelServer{
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8081);
        System.out.println("Сервер запущен на порту 8081...");
        ExecutorService pool = Executors.newFixedThreadPool(2);

        while (true) {
            pool.execute(new Runnable() {
                Socket socket = serverSocket.accept();
                public void run() { hClient(socket);}
            });

        }
    }
    public static void hClient(Socket clientSocket){
        try{
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());

            Matrix matrix = (Matrix)in.readObject();
            double sum = Matrix.sumOdd(matrix);

            out.writeObject(sum);
            out.flush();

            in.close();
            out.close();

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            try{
                clientSocket.close();
                System.out.println("Соединение закрыто");
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}

