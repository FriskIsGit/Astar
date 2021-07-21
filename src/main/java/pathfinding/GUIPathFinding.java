package pathfinding;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

class GUIPathFinding {
    JFrame frame;
    ArrayList<JPanel> panelList;
    JPanel currentPanel;
    Point targetPoint;
    Point startingPoint;
    private int lineBreadth;
    private int delay;
    private int row;
    private int column;
    private int cellWidth;
    private int cellHeight;
    private char [][] grid;
    boolean isInitialized;
    boolean isPaneled;
    boolean hasArrowControl;
    boolean isRunning;
    GUIPathFinding(){
        frame = new JFrame("Visual Path Finder");
        frame.setSize(400,400);
        frame.setLayout(null);
        isInitialized=false;
        isPaneled=false;
        hasArrowControl=false;
        panelList = new ArrayList<>();
        currentPanel = new JPanel();
        row = 0;
        column = 0;
        lineBreadth=3;
        delay = 500;
        targetPoint= new Point(-1,-1);
        startingPoint= new Point(-1,-1);
    }
    void runUserInterface(){
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBounds(frame.getWidth()-170,0,100,18);

        JMenuItem startItem = new JMenuItem("start");
        menuBar.add(startItem);
        startItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Start Selected");
                        if (isInitialized) {
                            if(validate()){
                                JOptionPane.showMessageDialog(frame,"Validation successful","Success",JOptionPane.INFORMATION_MESSAGE);
                                //startItem.setEnabled(false);
                                //frame.remove(menuBar);
                                frame.repaint();
                                //run
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        FinderForGUI finder = new FinderForGUI(startingPoint,targetPoint,grid,panelList,delay,frame);
                                        boolean outputResult = finder.findPath();
                                        if(outputResult){
                                            JOptionPane.showMessageDialog(frame,"Path found","Good news..",JOptionPane.INFORMATION_MESSAGE);
                                        }
                                        else{
                                            JOptionPane.showMessageDialog(frame,"Unable to find proper pathing","b-ba-badaad news..",JOptionPane.WARNING_MESSAGE);
                                        }
                                        if(JOptionPane.showConfirmDialog(frame,"Reset grid..?")==0){
                                            grid = padTwoDArray(grid.length, grid[0].length);
                                            cleanPanels();
                                        }
                                    }

                                    private void cleanPanels() {
                                        for(int ithPanel = 0;ithPanel<panelList.size();ithPanel++){
                                            JPanel thisPanel = panelList.get(ithPanel);
                                            thisPanel.setBackground(Color.gray);
                                            thisPanel.setBorder(null);
                                        }
                                        frame.repaint();
                                        displayGrid();
                                    }
                                }).start();

                            }
                            else{
                                JOptionPane.showMessageDialog(frame,"Validation failed","Alert",JOptionPane.WARNING_MESSAGE);
                            }

                        }
                    }
                });
            }

            private boolean validate() {
                int countA = 0,countB = 0;
                for(int iRow=0;iRow<grid.length;iRow++){
                    for(int iCol =0;iCol<grid[0].length;iCol++){
                        switch(grid[iRow][iCol]){
                            case 'A':
                                if(countA==1) return false;
                                startingPoint=new Point(iRow,iCol);
                                countA++;
                                break;
                            case 'B':
                                if(countB==1) return false;
                                targetPoint=new Point(iRow,iCol);
                                countB++;
                                break;
                        }
                    }
                }
                return countA != 0 && countB != 0;
            }
        });



        int numberOfRows=10,numberOfColumns = 10;
        String[] choices = {"Grid Size","Line Breadth","Step delay","Continue"};
        while (true) {
            String selected = (String) JOptionPane.showInputDialog(frame, "Modify settings", "Settings", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
            if (selected != null) {
                int tempVariable;
                String inputStr;
                if (selected.equals("Grid Size")) {

                    inputStr = JOptionPane.showInputDialog(frame, "Enter numberOfRows [0<rows<=100]: \n Current: " + numberOfRows);
                    if (inputStr == null) {
                        break;
                    } else if (inputStr.matches("[0-9]+")) {
                        tempVariable = Integer.parseInt(inputStr);
                        if(tempVariable<101 && tempVariable>0) {
                            numberOfRows = tempVariable;
                        }
                    }
                    inputStr = JOptionPane.showInputDialog(frame, "Enter numberOfColumns [0<columns<=100]: \n Current: " + numberOfColumns);
                    if (inputStr == null) {
                        break;
                    } else if (inputStr.matches("[0-9]+")) {
                        tempVariable = Integer.parseInt(inputStr);
                        if(tempVariable<101 && tempVariable>0) {
                            numberOfColumns = tempVariable;
                        }
                    }
                } else if (selected.equals("Line Breadth")) {
                    inputStr = JOptionPane.showInputDialog(frame, "Enter breadth [pixels] [0<breadth<=25]: \n Current: " + lineBreadth);
                    if (inputStr == null) {
                        break;
                    } else if (inputStr.matches("[0-9]+")) {
                        tempVariable = Integer.parseInt(inputStr);
                        if(tempVariable<25 && tempVariable>0) {
                            lineBreadth = tempVariable;
                        }
                    }
                } else if (selected.equals("Step delay")) {
                    inputStr = JOptionPane.showInputDialog(frame, "Enter delay [ms] [0<delay<=10000]: \n Current: " + delay);
                    if (inputStr == null) {
                        break;
                    } else if (inputStr.matches("[0-9]+")) {
                        tempVariable = Integer.parseInt(inputStr);
                        if(tempVariable<10000 && tempVariable>0) {
                            delay = tempVariable;
                        }
                    }
                } else if (selected.equals("Continue")) {
                    break;
                }
            //quit options
            }else{
                break;
            }

        }
        grid = padTwoDArray(numberOfRows,numberOfColumns);

        JMenuItem helpItem = new JMenuItem("help");
        menuBar.add(helpItem);
        helpItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Help: "  +Thread.currentThread());
                JOptionPane.showMessageDialog(helpItem, "#0 Visit settings to choose prefs \n#1 Press ENTER to confirm frame size and generate grid, gray fields are walkable. \n#2 Switch between cells using arrows or mouse \n#3 Currently selected cell is flickering yellow \n#4 Use SPACE to place walls, colored black \n#5 Set   - starting point by typing A         -target point by typing B \n#6 Choose start from the menu bar to simulate A* path finding");
            }

        });

        frame.add(menuBar);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.GRAY);
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                e.consume();
            }

            @Override
            public void keyPressed(KeyEvent press) {
                //consequently [arrows] left, up, right, down
                int keyCode = press.getKeyCode();
                switch (keyCode) {
                    case 37:
                        if (0 < column) {
                            column--;
                            displayGrid();
                            if(isInitialized) {
                                currentPanel = panelList.get(row * grid[0].length + column);
                            }
                        }
                        break;
                    case 38:
                        if (0 < row) {
                            row--;
                            displayGrid();
                            if(isInitialized) {
                                currentPanel = panelList.get(row * grid[0].length + column);
                            }
                        }
                        break;
                    case 39:
                        if (column + 1 < grid[row].length) {
                            column++;
                            displayGrid();
                            if(isInitialized) {
                                currentPanel = panelList.get(row * grid[0].length + column);
                            }
                        }
                        break;
                    case 40:
                        if (row + 1 < grid.length) {
                            row++;
                            displayGrid();
                            if(isInitialized) {
                                currentPanel = panelList.get(row * grid[0].length + column);
                            }
                        }
                        break;
                    //shrink window
                    case 45:
                        frame.setSize(frame.getWidth() - 10, frame.getHeight() - 10);
                        break;
                    //expand window
                    case 61:
                        frame.setSize(frame.getWidth() + 10, frame.getHeight() + 10);
                        break;
                    case 10:
                        initGrid();
                        createPanels();
                        initThread();
                        break;
                    case 32:
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                if (isPaneled) {
                                    JPanel localPanel = currentPanel = panelList.get(row * grid[0].length + column);
                                    currentPanel.setBorder(null);
                                    if (grid[row][column] == '0') {
                                        grid[row][column] = 'W';
                                        localPanel.setBackground(Color.BLACK);
                                     } else {
                                        localPanel.setBackground(Color.gray);
                                        grid[row][column] = '0';
                                    }
                                    displayGrid();
                                }
                            }
                        });
                        break;
                    case 65:
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                if (isPaneled) {
                                    currentPanel.setBorder(new TitledBorder("A"));
                                    currentPanel.setBackground(Color.gray);
                                    grid[row][column] = 'A';
                                    displayGrid();
                                }
                            }
                        });
                        break;
                    case 66:
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                if (isPaneled) {
                                    TitledBorder fCost = new TitledBorder(null,"B",TitledBorder.CENTER,TitledBorder.BELOW_TOP,null,null);
                                    currentPanel.setBorder(fCost);
                                    currentPanel.setBackground(Color.gray);
                                    grid[row][column] = 'B';
                                    displayGrid();
                                }
                            }
                        });
                        break;
                }
                System.out.println(keyCode);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                e.consume();
            }
        });

        frame.setVisible(true);
    }
    private void initThread(){
        //handle navigation
        if(hasArrowControl) return;
        else hasArrowControl = true;
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    JPanel localPanel = currentPanel;
                    Color tempColor = localPanel.getBackground();
                    localPanel.setBackground(Color.ORANGE);
                    sleep(300);
                    if(localPanel.getBackground()!=Color.ORANGE){
                        continue;
                    }
                    localPanel.setBackground(tempColor);
                    sleep(500);
                }

            }
        });
        t1.start();
    }
    private void createPanels() {
        if(isPaneled) return;
        else isPaneled = true;

        System.out.println("Cell Width: " + cellWidth + ";" + " CellHeight" + cellHeight);
        int index = 0;
        for (int iRow = 0, yPos = 0; iRow < grid.length; iRow++,yPos+=cellHeight+lineBreadth) {
            for (int iCol = 0, xPos = 0; iCol < grid[0].length; iCol++, xPos+=cellWidth+lineBreadth) {
                JPanel cellPanel = new JPanel();
                cellPanel.setLayout(null);
                cellPanel.setBackground(Color.GRAY);
                //cellPanel.setForeground(Color.ORANGE);
                cellPanel.setBounds(xPos,yPos,cellWidth,cellHeight);
                cellPanel.setName(String.valueOf(index));
                cellPanel.addMouseListener(new MouseListener() {
                    public void mouseClicked(MouseEvent e) {
                        e.consume();
                    }
                    public void mousePressed(MouseEvent pressed) {
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                currentPanel=cellPanel;
                                int panelSerialNumberXD = Integer.parseInt(cellPanel.getName());
                                row = panelSerialNumberXD/grid[0].length;
                                column = panelSerialNumberXD-(row*grid[0].length);
                            }
                        });
                    }
                    public void mouseReleased(MouseEvent e) {
                        e.consume();
                    }
                    public void mouseEntered(MouseEvent e) {
                        e.consume();
                    }
                    public void mouseExited(MouseEvent e) {
                        e.consume();
                    }
                });

                frame.add(cellPanel);
                panelList.add(cellPanel);
                index++;
            }
        }
        frame.repaint();
    }

    //test purposes
    private char[][] padTwoDArray(int rows, int columns) {
        char [][] arr = new char[rows][columns];
        for(char [] rowArr : arr){
            Arrays.fill(rowArr, '0');
        }
        return arr;
    }


    private void initGrid() {
        if(this.isInitialized) return;
        else this.isInitialized = true;
        //frame.getContentPane().removeAll();
        //frame.repaint();
        //look for dimensions with no remainder from parting
        int optimizedWidth = optimizeDimensions(frame.getWidth(), grid[0].length);
        int optimizedHeight = optimizeDimensions(frame.getHeight(), grid.length);
        cellWidth = optimizedWidth / grid[0].length;
        cellHeight = optimizedHeight / grid.length;
        //compensate for LINE_BREADTH to separate cells & for window border
        frame.setSize(optimizedWidth + lineBreadth*(grid[0].length-1)  +16, optimizedHeight + lineBreadth*(grid.length-1) +38);
        paintLines(cellWidth, cellHeight);
    }
    private void paintLines(int cellWidth, int cellHeight){
        for(int vLines = 0,xPosition = cellWidth+1;vLines<grid[0].length;vLines++, xPosition += cellWidth+lineBreadth){
            JPanel vLine = new JPanel();
            vLine.setBackground(Color.BLACK);
            vLine.setBounds(xPosition,0,lineBreadth,frame.getHeight()-38);
            frame.add(vLine);
        }
        for(int hLines = 0,yPosition = cellHeight+1;hLines<grid.length;hLines++, yPosition+=cellHeight+lineBreadth){
            JPanel hLine = new JPanel();
            hLine.setBackground(Color.BLACK);
            hLine.setBounds(0,yPosition,frame.getWidth()-16,lineBreadth);
            frame.add(hLine);
        }
        frame.repaint();
    }
    private int optimizeDimensions(int extent, int partings){
        while ((double) extent / (double) (partings) % 1 != 0) {
            extent++;
        }
        return extent;
    }
    private void displayGrid(){
        for (char[] chars : grid) {
            System.out.println();
            for (int col = 0; col < chars.length; col++) {
                System.out.print(chars[col] + "  ");
            }
        }
        System.out.println("\n\n");
    }
    private void sleep(long ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
