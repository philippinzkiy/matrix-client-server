package client;

import java.io.Serializable;
import java.util.Random;

public class Matrix implements Serializable {
    private static final long serialVersionUID = 1L;
    private final double[][] matrix;
    private int rows;
    private int columns;

    public Matrix(int rows, int columns){
        this.rows = rows;
        this.columns = columns;
        Random random = new Random();
        matrix = new double[rows][columns];

        for(int i = 0 ; i < rows; i++){
            for(int j = 0; j < columns; j++){
                matrix[i][j] = random.nextDouble() * 200;
            }
        }
    }

    public int getRows(){ return rows;}
    public int getColumns(){return columns;}

    public double getValue(int indexRows, int indexColumns){
        if(indexRows > rows || indexRows < 0 || indexColumns > columns || indexColumns < 0){
            throw new ArrayIndexOutOfBoundsException("Выход за границу матрицы");
        }
        else{
            return matrix[indexRows][indexColumns];
        }
    }

    public void setValue(int indexRows, int indexColumns, double value){
        if(indexRows > rows || indexRows < 0 || indexColumns > columns || indexColumns < 0){
            throw new ArrayIndexOutOfBoundsException("Выход за границу матрицы");
        }
        else{
            matrix[indexRows][indexColumns] = value;
        }
    }

    public static double sumOdd(Matrix array){
        double sum = 0;
        for(int i = 0; i < array.getRows(); i++){
            for(int j = 0; j < array.getColumns(); j++){
                double current = array.getValue(i,j);
                if(current % 2 != 0){
                    sum += current;
                }
            }
        }
        return sum;
    }
    @Override
    public String toString(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("Матрица размером: " + rows + " x " + columns);
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                buffer.append(String.format("%.2f", getValue(i,j)) + " ");
            }
            buffer.append("\n");
        }
        return buffer.toString();
    }

}
