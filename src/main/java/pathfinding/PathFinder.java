package pathfinding;

import java.awt.*;
import java.util.*;

/**
 * G cost = distance from startingPoint
 * H cost(heuristic) = distance from targetPoint
 * F(full) cost(G+H)
 */
class PathFinder {
    HashMap<Point, double[]> pointsDataMap;
    HashMap<Point, Boolean> visitedPointsMap;
    private final Point startingPoint;
    private final Point targetPoint;
    private final char[][] map;

    protected PathFinder(Point startingPoint, Point targetPoint, char[][] map) {
        this.startingPoint = startingPoint;
        this.targetPoint = targetPoint;
        this.map = map;
        this.pointsDataMap = new HashMap<>();
        this.visitedPointsMap = new HashMap<>();
    }
    public static void main(String[] args) {
        System.out.println("Change");
    }
    protected boolean findPath() {
        LinkedList<Point> pathPoints = new LinkedList<>();
        Point currentPoint = this.startingPoint;
        visitedPointsMap.put(currentPoint,true);

        while(!currentPoint.equals(this.targetPoint)) {
            updateCostsFor(currentPoint);
            try {
                currentPoint = getNextPoint();
            } catch(NullPointerException exception){
                //return false for path not found
                System.out.println("\nUnable to find proper pathing for this one");
                return false;
            }
            visitedPointsMap.put(currentPoint,true);
            pathPoints.add(currentPoint);
        }
        drawPath(pathPoints);
        return true;
    }

    private Point getNextPoint() {
        HashMap<Double,Integer> fCostsMap = new HashMap<>();
        Point minPoint=new Point(-1,1);
        double minF = Integer.MAX_VALUE-1;
        for (Map.Entry<Point, double[]> set : pointsDataMap.entrySet()) {
            double currentF = set.getValue()[2];
            if(!visitedPointsMap.get(set.getKey()) && minF>currentF){
                minF=currentF;
                minPoint=set.getKey();
            }else{
                continue;
            }
            if(fCostsMap.containsKey(currentF)){
                fCostsMap.put(currentF,fCostsMap.get(currentF)+1);
            }
            else{
                fCostsMap.put(currentF,1);
            }
        }
        if(fCostsMap.get(minF) == 1) return minPoint;
        //just in case(rarely accessed), decide by lowest H cost
        /*else{
            //look for smallestF points, determine which has lowest H
            List<Pair<Point, Double>> listOfPairs = new ArrayList<>();
            for (Map.Entry<Point, double[]> set : pointsDataMap.entrySet()) {
                if (set.getValue()[2] == minF) {
                    listOfPairs.add(new Pair<>(set.getKey(), set.getValue()[1]));
                }
            }
            double minH = Integer.MAX_VALUE - 1;
            for (Pair<Point, Double> pair : listOfPairs) {
                if (minH > pair.getValue()) {
                    minH = pair.getValue();
                    minPoint = pair.getKey();
                }
            }
        }*/
        return minPoint;
    }

    private void streamMap(){
        pointsDataMap.forEach((k, v) -> System.out.println(k + " = " + Arrays.toString(v)));
    }
    private void updateCostsFor(Point point){

        for(int row = point.x-1;row<point.x+2;row++){
            for(int col = point.y-1;col<point.y+2;col++){
                //make sure it doesn't exceed map arr lengths && is not a wall point
                if((row>-1 && row<map.length ) && (col>-1 && col<map[0].length)) {
                    if (!(row == point.x && col == point.y) && map[row][col] != 'W') {
                        double[] costs = new double[3];
                        Point nextPoint = new Point(row, col);
                        if (!pointsDataMap.containsKey(nextPoint)) {
                            visitedPointsMap.put(nextPoint, false);
                            costs[0] = Math.round(Math.sqrt(Math.pow(row - this.startingPoint.x, 2) + Math.pow(col - this.startingPoint.y, 2)) * 100D) / 100D;
                            costs[1] = Math.round(Math.sqrt(Math.pow(row - this.targetPoint.x, 2) + Math.pow(col - this.targetPoint.y, 2)) * 100D) / 100D;
                            costs[2] = costs[1] + costs[0];
                            pointsDataMap.put(nextPoint, costs);
                        }
                    }
                }
            }
        }
    }
    //display
    private void drawPath(LinkedList<Point> listOfPoints) {
        //copy arr
        char [][] myMap = new char[map.length][map[0].length];
        for(int row = 0;row<map.length;row++){
            for(int col = 0;col<map[0].length;col++){
                myMap[row][col] = map[row][col];
            }
        }
        for (Point step : listOfPoints) {
            myMap[(int) step.getX()][(int) step.getY()] = 'S';
        }
        for(int row = 0;row<map.length;row++){
            System.out.println();
            for(int col = 0;col<map[0].length;col++){
                System.out.print(myMap[row][col] + "  ");
            }
        }
        System.out.println();
    }

    /*private void printFCostMap(){
        for(int i = 0;i<)
    }*/
}