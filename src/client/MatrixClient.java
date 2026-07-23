package client;

import  java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import javax.swing.*;

public class MatrixClient {

    public static void main(String[] args) throws Exception{
        createGUI();
    }
    public static void createGUI(){
        JFrame frame = new JFrame("Матрица");
        frame.setLayout(new BorderLayout());

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        JTextField jRows = new JTextField();
        jRows.setMaximumSize(new Dimension(200, 30));
        JTextField jCols = new JTextField();
        jCols.setMaximumSize(new Dimension(200, 30));

        JButton btn = new JButton("Создать");

        left.add(new JLabel("Строки:"));
        left.add(jRows);
        left.add(new JLabel("Столбцы:"));
        left.add(jCols);
        left.add(btn);

        JTextArea text = new JTextArea();
        JScrollPane scroll = new JScrollPane(text);

        frame.add(left, BorderLayout.WEST);
        frame.add(scroll, BorderLayout.CENTER);

        btn.addActionListener(e -> {
            try{
                text.setText("");

                String rowsText = jRows.getText();
                String columnsText = jCols.getText();

                int rows = Integer.parseInt(rowsText);
                int columns = Integer.parseInt(columnsText);

                if(rows <= 0 || columns <= 0 || rows >= 1000 || columns >= 1000){
                    throw new IllegalArgumentException();
                }
                try {
                    Socket socket = new Socket("localhost", 8081);
                    System.out.println("Успешно подключение!");

                    Matrix matrix = new Matrix(rows, columns);
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

                    out.writeObject(matrix);
                    out.flush();
                    System.out.println("Объект отправлен!");

                    ObjectInputStream st = new ObjectInputStream(socket.getInputStream());

                    double d = (double) st.readObject();

                    text.append(matrix.toString());
                    text.append("\nСумма: " + Double.toString(d));
                }
                catch(ConnectException | SocketTimeoutException con){
                    text.append("Ошибка: подключения");
                }
            }
            catch(NumberFormatException s){
                text.append("Ошибка: размеры матрицы должны быть целые числа от 1 до 1000");
            }
            catch(IllegalArgumentException i){
                text.append("Ошибка: размеры матрицы должны быть целые числа от 1 до 1000");
            }
            catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        });

        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}



