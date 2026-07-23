package server;

import client.Matrix;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class sequentalServer {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8081);
        System.out.println("Сервер запущен на порту 8081...");

        while(true){
            Socket clientSocket = server.accept();

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
                clientSocket.close();
                System.out.println("Соединение закрыто");

            }
        }
    }
}
