package pathfinding;

import java.awt.Point;

import org.junit.Test;
import static org.junit.Assert.*;
//PUBLIC CLASS
public class PathFinderTest {
    //traverses from A -> B, W stands for wall
    char[][] map1 =  {{'0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'},
                      {'0', '0', '0', 'W', 'B', '0', '0', '0', '0', '0', '0'},
                      {'0', '0', '0', 'W', 'W', 'W', 'W', 'W', '0', '0', '0'},
                      {'0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'},
                      {'0', '0', '0', '0', '0', '0', '0', 'A', '0', '0', '0'},
                      {'0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'}};

    char[][] map2 =  {{'0', '0', '0', '0', '0', '0', '0'},
                      {'0', '0', '0', 'W', 'W', '0', 'B'},
                      {'0', '0', '0', '0', 'W', '0', '0'},
                      {'0', 'A', '0', '0', '0', '0', '0'}};

    char[][] map3 =  {{'0', '0', '0', '0', '0', 'W', 'W'},
                      {'0', '0', '0', '0', '0', 'W', 'B'},
                      {'0', '0', '0', '0', '0', 'W', 'W'},
                      {'0', 'A', '0', '0', '0', '0', '0'}};
    char[][] grid
            =  {{ 'B', 'W', '0', '0', '0', '0', 'W', '0', '0', '0' },
                { '0', '0', '0', 'W', '0', '0', '0', 'W', '0', '0' },
                { '0', '0', '0', 'W', '0', '0', 'W', '0', 'W', '0' },
                { 'W', 'W', '0', 'W', '0', 'W', 'W', 'W', 'W', '0' },
                { '0', '0', '0', 'W', '0', '0', '0', 'W', '0', 'W' },
                { '0', 'W', '0', '0', '0', '0', 'W', '0', 'W', 'W' },
                { '0', 'W', 'W', 'W', 'W', '0', 'W', 'W', 'W', '0' },
                { '0', 'W', '0', '0', '0', '0', 'W', '0', '0', 'A' },
                { '0', '0', '0', 'W', 'W', 'W', '0', 'W', 'W', '0' }};
    char[][] _3by3
            =  {{ '0', '0', '0'},
                { '0', 'W', 'B'},
                { 'A', 'W', '0'}};
    char[][] surrounded
            =  {{ 'W', 'W', '0','B'},
                { 'A', 'W', '0','0'},
                { 'W', 'W', '0','0'}};

    @Test
    public void tutorialCase1(){
        assertTrue(new PathFinder(new Point(4,7),new Point(1,4),map1).findPath());
    }
    @Test
    public void tutorialCase2(){
        assertTrue(new PathFinder(new Point(3,1),new Point(1,6),map2).findPath());
    }
    @Test
    public void targetIsolated(){
        assertFalse(new PathFinder(new Point(3,1),new Point(1,6),map3).findPath());
    }
    @Test
    public void gridCaseFromGeeks(){
        assertTrue(new PathFinder(new Point(9,8),new Point(0,0),grid).findPath());
    }
    @Test
    public void _3by3Case(){
        assertTrue(new PathFinder(new Point(2,0),new Point(1,2),_3by3).findPath());
    }
    @Test
    public void surroundedCase(){
        assertFalse(new PathFinder(new Point(1,0),new Point(0,3),surrounded).findPath());
    }
}
