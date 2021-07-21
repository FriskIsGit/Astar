package pathfinding;

import java.awt.Point;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

class MapRoom {
    public static void CLS(){
        for (int i = 0; i < 2; ++i) System.out.println();
    }
    public static void printAndModify(char[][]arr, boolean modify){
        CLS();
        for(int i= 0; i< arr.length;i++){
            for(int k = 0; k<arr.length;k++){
                if(modify) arr[i][k]='0';
                System.out.print(arr[i][k] +"  ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int size = 7;

        char [][] myMap = new char[size][size];
        char [][] layout2 = {
                {'0','0','X','0','X','0','0'},
                {'X','X','X','0','X','X','0'},
                {'X','0','0','0','0','X','0'},
                {'X','0','0','0','0','X','0'},
                {'X','X','X','X','X','X','0'},
                {'0','0','0','0','0','0','0'},
                {'0','0','0','0','0','0','0'}
        };
        char [][] layout1={
        {'0','0','X','X','X','X','X'},
        {'X','X','X','0','X','0','X'},
        {'X','0','0','0','X','0','X'},
        {'X','0','X','0','X','0','X'},
        {'X','0','X','0','0','0','X'},
        {'X','0','X','X','0','0','X'},
        {'X','X','X','X','0','0','0'}};
        char [][] layout4 = {
                {'0','0','X','0','X','X','X'},
                {'X','X','X','0','X','0','X'},
                {'X','0','0','0','X','0','X'},
                {'X','0','X','0','X','0','X'},
                {'X','0','X','0','0','0','X'},
                {'X','0','X','X','0','0','X'},
                {'X','X','X','X','0','0','X'}
        };
        char [][] layout3 = {
                {'0','0','X','0','X','X','X'},
                {'X','X','X','0','X','0','X'},
                {'X','0','0','0','0','0','X'},
                {'X','0','0','0','0','0','X'},
                {'X','0','0','X','X','X','0'},
                {'X','0','0','X','0','0','0'},
                {'X','X','X','X','0','0','0'}
        };

        printAndModify(layout1,false);
        printAndModify(myMap,true);
        //Point point = new Point(0,3);
        Point point = new Point(6,6);
        mapRoom(myMap,layout1,point);
        printAndModify(layout1,false);
        printAndModify(myMap,false);
    }
    private static void mapRoom(char [][] myMap,char[][]layout, Point location){
        HashMap<Character, LinkedList<Character>> directions = new HashMap<>();
        Point point = new Point((int)location.getX(), (int)location.getY());
        char dir = 'D';
        directions.put('D', new LinkedList<>(Arrays.asList('L','D','R','U')));
        directions.put('L', new LinkedList<>(Arrays.asList('U','L','D','R')));
        directions.put('R', new LinkedList<>(Arrays.asList('D','R','U','L')));
        directions.put('U', new LinkedList<>(Arrays.asList('R','U','L','D')));

        do{
            for(int i = 0;i<4;i++){
                Point newPoint = checkDir(directions.get(dir).get(i),myMap,layout,point);
                if(!point.equals(newPoint)){
                    point=newPoint;
                    dir=directions.get(dir).get(i);
                    break;
                }

            }
        }while(location.getX()!=point.getX() || location.getY()!=point.getY());
    }
    private static Point checkDir(char dir, char[][] myMap,char [][] layout, Point point){
        int row,col;
        boolean flag;
        switch (dir){
            case 'L':
                row = (int)point.getX();
                col = (int)point.getY()-1;
                flag = isWithinBounds(row,col,layout.length);
                if(flag && layout[row][col]!='X'){
                    return new Point(row,col);
                }else if(flag && layout[row][col]=='X'){
                    myMap[row][col]='H';
                }
                break;
            case 'D':
                row = (int)point.getX()+1;
                col = (int)point.getY();
                flag = isWithinBounds(row,col,layout.length);
                if(flag && layout[row][col]!='X'){
                    return new Point(row,col);
                }else if(flag && layout[row][col]=='X'){
                    myMap[row][col]='H';
                }
                break;
            case 'R':
                row = (int)point.getX();
                col = (int)point.getY()+1;
                flag = isWithinBounds(row,col,layout.length);
                if(flag && layout[row][col]!='X'){
                    return new Point(row,col);
                }else if(flag && layout[row][col]=='X'){
                    myMap[row][col]='H';
                }
                break;
            case 'U':
                row = (int)point.getX()-1;
                col = (int)point.getY();
                flag = isWithinBounds(row,col,layout.length);
                if(flag && layout[row][col]!='X'){
                    return new Point(row,col);
                }else if(flag && layout[row][col]=='X'){
                    myMap[row][col]='H';
                }
                break;
        }
        return new Point((int)point.getX(),(int)point.getY());
    }
    private static boolean isWithinBounds(int x, int y, int size){
        return (x > -1 && y > -1 && x < size && y < size);
    }




}
