![image](image.png)
# Astar Java
Implementation of A* algorithm in Java
## [More information here]
Lorem ipsum dolor sit amet, `consectetur adipiscing elit`, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ~~ullamco laboris nisi ut aliquip~~ ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint **occaecat cupidatat non proident**, sunt in culpa qui officia deserunt mollit anim id est laborum.
## Random list box:
- [ ] Item 1
- [ ] Item 2
- [x] Item 3
- [ ] ~~Item 4~~
## Random code block:
```java 
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
```
## Contributors:
- Best contributor: [Kihau](https://github.com/Kihau)
