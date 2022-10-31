package MineSweeperProject;

import java.util.Random;
import java.util.Arrays;
import java.util.Scanner;

public class MineSweeper {
    private int rowNumber;
    private int columnNumber;
    private String[][] map;
    private int[][] locationMine;
    private int mineNum;
    private boolean isFinished;


    public MineSweeper(int rowNumber, int columnNumber){
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
        this.map = new String[rowNumber][columnNumber];
        this.mineNum = (rowNumber * columnNumber) / 4;
        this.locationMine = new int[mineNum][];
        this.isFinished = false;

    }
    private boolean checkMine(int x, int y){
        int[] controlledLoc = {x,y};
        boolean isMatched = false;
        for (int i = 0;i < this.locationMine.length;i++){
            if (Arrays.compare(this.locationMine[i],controlledLoc) == 0){
                isMatched = true;
                break;
            }
        }
        return isMatched;
    }
    public void specifyMineLocations(){
        Random random = new Random();
        int i = 0;
        while (i < mineNum){
            int xAxis = random.nextInt(rowNumber);
            int yAxis = random.nextInt(columnNumber);
            if (!checkMine(xAxis,yAxis)){
                int[] loc = {xAxis,yAxis};
                this.locationMine[i] = loc;
                i++;
            }
        }

    }

    public void initializeMap(){
        for (int i = 0;i < rowNumber;i++){
            for (int j = 0;j < columnNumber;j++){
                this.map[i][j] = "_";
            }
        }
    }
    public void select(int x, int y){
        if ((x < 0 || x >= rowNumber) || (y < 0 || y >= columnNumber)){
            System.out.println("Incorrect Selection!");
        }
        else{
            if (checkMine(x,y)){
                System.out.println("You selected the mine area! You lost!!!");
                this.map[x][y] = "*";
                printMap();
                isFinished = true;
            }
            else if (!this.map[x][y].equals("_")){
                System.out.println("You entered this location!");
            }
            else{
                int mineNearby = 0;

                int right = y + 1;
                int left = y - 1;
                int up = x - 1;
                int down = x + 1;

                if (right < columnNumber && checkMine(right,y)){
                    mineNearby++;
                }
                if (left >= 0 && checkMine(left,y)){
                    mineNearby++;
                }
                if (up >= 0 && checkMine(x,up)){
                    mineNearby++;
                }
                if (down < rowNumber && checkMine(x,down)){
                    mineNearby++;
                }
                if ((right < columnNumber && up >= 0) && checkMine(right,up)){
                    mineNearby++;
                }
                if ((right < columnNumber && down < rowNumber) && checkMine(right,down)){
                    mineNearby++;
                }
                if ((left >= 0 && up >= 0) && checkMine(left,up)){
                    mineNearby++;
                }
                if ((left >= 0 && down < rowNumber) && checkMine(left,down)){
                    mineNearby++;
                }
                this.map[x][y] = String.valueOf(mineNearby);
                printMap();
            }
        }
    }

    public void checkFinished(){
        int remainedPlaces = 0;
        for (int i = 0;i < rowNumber;i++){
            for (int j = 0;j < columnNumber;j++){
                if (this.map[i][j].equals("_")){
                    remainedPlaces++;
                }
            }
        }
        if (remainedPlaces == mineNum){
            placeMine();
            printMap();
            System.out.println("Congratulations! You won!!!");
            isFinished = true;
        }

    }

    public void printMap(){
        for (int i = 0;i < rowNumber;i++){
            for (int j = 0;j < columnNumber;j++){
                System.out.print(this.map[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void placeMine(){
        for (int i = 0;i < rowNumber;i++){
            for (int j = 0;j < columnNumber;j++){
                if (checkMine(i,j)){
                    this.map[i][j] = "*";
                }
            }
        }
    }



    public void run(){
        initializeMap();
        specifyMineLocations();
        Scanner scanner = new Scanner(System.in);
        printMap();
        while (!isFinished){
            System.out.print("Enter xAxis:");
            int x = scanner.nextInt();
            System.out.print("Enter yAxis:");
            int y = scanner.nextInt();
            select(x,y);
            checkFinished();
        }
    }
}
