package enn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 * @author aftab
 */
public class MyCSV {

    File file;
    FileReader fileReader;
    BufferedReader myReader;

    double res[][];
    double train[][];
    double test[][];

    public MyCSV(String path) throws FileNotFoundException {
        file = new File(path);
        fileReader = new FileReader(file);
        myReader = new BufferedReader(fileReader);
    }

    public double[][] readCSV() throws IOException {
        LinkedList lines = new LinkedList();
        myReader.readLine(); //FIRST LINE IS HEADER i.e X1, X2, X3, Y omit it
        String l = myReader.readLine();        //USE SECOND LINE AND soon
        while (l != null) {
            //System.out.println(l);
            lines.add(l);
            l = myReader.readLine();
        }
        fileReader.close();
        myReader.close();

        int rows = lines.size();
        int cols = ((String) lines.getFirst()).split(",").length;

        res = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            String line = (String) lines.pop();
            String parts[] = line.split(",");
            for (int j = 0; j < cols; j++) {
                res[i][j] = Double.valueOf(parts[j].trim());
            }
        }

        //NORMALIZING THE DATA BETWEEN 0 AND 1
        double min_col_0 = min(res, 0);
        double min_col_1 = min(res, 1);
        double min_col_2 = min(res, 2);
        double min_col_3 = min(res, 3);
        double max_col_0 = max(res, 0);
        double max_col_1 = max(res, 1);
        double max_col_2 = max(res, 2);
        double max_col_3 = max(res, 3);

        for (int i = 0; i < rows; i++) {
            res[i][0] = (res[i][0] - min_col_0) / (max_col_0 - min_col_0);
            res[i][1] = (res[i][1] - min_col_1) / (max_col_1 - min_col_1);
            res[i][2] = (res[i][2] - min_col_2) / (max_col_2 - min_col_2);
            res[i][3] = (res[i][3] - min_col_3) / (max_col_3 - min_col_3);
        }
        return res;
    }

    private double min(double[][] array, int col) {
        double a = array[0][col];
        for (int i = 1; i < array.length; i++) {
            if (a > array[i][col]) {
                a = array[i][col];
            }
        }
        return a;
    }
    private double max(double[][] array, int col) {
        double a = array[0][col];
        for (int i = 1; i < array.length; i++) {
            if (a < array[i][col]) {
                a = array[i][col];
            }
        }
        return a;
    }
}