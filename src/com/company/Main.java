package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;


public class Main {

    //    private static int[][] arr = { {4, 8, 7, 3},
//                                    {2, 5, 9, 3},
//                                    {6, 3, 2, 5},
//                                    {4, 4, 1, 6} };
    private static int[][] arr;
    private static final String FILE_NAME = "map.txt";
    private static List<int[]> bestPath = new ArrayList<>();
    private static int column, row;

    public static void main(String[] args) {

        readFile();
//        bestPath = getPath(1, 2);
        for (int i = 0 ; i<row ; i++)
            for (int j = 0; j<column ; j++){
                List<int[]> path = getPath(i,j);
                if (path.size() > bestPath.size()){
                    bestPath = path;
                } else if (path.size() == bestPath.size()){
                    if (calDrop(path) > calDrop(bestPath))
                        bestPath = path;
                }
            }

        //Done, time for some drawing
        System.out.println("Path taken: \n ༼ つ ◕_◕ ༽つ*"+arr[bestPath.get(bestPath.size()-1)[0]][bestPath.get(bestPath.size()-1)[1]]+" ["+bestPath.get(bestPath.size()-1)[0]+"]["+bestPath.get(bestPath.size()-1)[1]+"]");
        for (int i = bestPath.size()-2 ; i > -1 ; i--){
            System.out.print("            ");
            for (int j = 0 ; j<bestPath.size()-i ; j++){
                System.out.print("*");
            }
            System.out.println(""+arr[bestPath.get(i)[0]][bestPath.get(i)[1]]+" ["+bestPath.get(i)[0]+"]["+bestPath.get(i)[1]+"]");
        }

        System.out.println("Drop:"+calDrop(bestPath));

    }

    //TODO: issue when 2 directions are workable but one is overridden by the latter
    //      changing traversal order will change the result
    public static List<int[]> getPath(int x, int y) {

        List<int[]> path = new ArrayList<int[]>();
        List<int[]> currentPath = new ArrayList<int[]>();

        //go to upper box
        if (x > 0 && arr[x][y] > arr[x - 1][y]) {
            currentPath = getPath(x - 1, y);
            if (currentPath.size() > path.size())
                path = currentPath;
        }
        //go to lower box
        if (x < row-1 && arr[x][y] > arr[x + 1][y]) {
            currentPath = getPath(x + 1, y);
            if (currentPath.size() > path.size())
                path = currentPath;

        }
        //go to left box
        if (y > 0 && arr[x][y] > arr[x][y - 1]) {
            currentPath = getPath(x, y - 1);
            if (currentPath.size() > path.size())
                path = currentPath;
        }

        //go to right box
        if (y < column-1 && arr[x][y] > arr[x][y + 1]) {
            currentPath = getPath(x, y + 1);
            if (currentPath.size() > path.size())
                path = currentPath;
        }
        path.add(new int[]{x,y});
        return path;
    }

    private static void readFile(){
        try {
            FileReader reader = new FileReader(FILE_NAME);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String firstLine = bufferedReader.readLine();
            String[] rowAndCol = firstLine.split(" ");
            row = Integer.valueOf(rowAndCol[0]);
            column = Integer.valueOf(rowAndCol[1]);
            //init arr based on row and column number
            arr = new int[row][column];

            for (int i = 0; i< row; i++){
                String line = bufferedReader.readLine();
                String[] lineArr = line.split(" ");
                for (int j = 0; j < column; j++){
                    arr[i][j] = Integer.valueOf(lineArr[j]);
                }
            }

            bufferedReader.close();

        } catch (IOException e){
            e.printStackTrace();
        } catch (NullPointerException e){
            System.out.println("Line is empty");
        }

    }

    public static int calDrop(List<int[]> path){
        int pathLength = path.size();
        //if there is just 1 item then return its value, used for assessment when finding path
        if (path.size()==1)
            return arr[path.get(0)[0]][path.get(0)[1]];

        return arr[path.get(pathLength-1)[0]][path.get(pathLength-1)[1]] - arr[path.get(0)[0]][path.get(0)[1]];
    }

}

