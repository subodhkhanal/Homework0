/** Math2670 Homework06
 * @author Subodh Khanal
 * @author Christiaan Masucci
 * File: Tetris.java
 * This is a simple tetris game with some twist. The board has pattern which make the shapes camoflauged for added difficulty
 * and we have added our own unique shape. The shapes speed up as the game moves forward nad once the player loses the game
 * waits for ten  seconds before restrating.
 * We have utilized the knowledge of Swing, Array and Thread
 */


package tetris;
import java.util.Random;
import java.awt.Color;
import javax.swing.*;
import java.awt.Container;
import java.awt.Graphics;



/** Initializing Parameters*/

public class Tetris extends JFrame implements Runnable {
    public static Random rand = new Random();
    public int[][][] Board = new int[15][30][3];
    public int[] counter = new int[15];
    public int[][] format = new int[4][2];
    public int[] position = new int[2];
    public int[] column = new int[3];
    public static final int Board_Width = 530;
    public static final int Board_Height = 700;
    public int time = 60;
    public boolean restarting = false;
    int nextShape = rand.nextInt(8);
    int currentShape;



    // counter for the score
    public int scorePoint = 0;

    /** Method to calculate and increase the score*/

    public void save()
    {
        boolean reset = false;
        int total = 0;
        for (int i = 0; i < 4; i++)
        {
            for (int z = 0; z < 3; z++)
            {
                try {
                    this.Board[this.format[i][0] / 20 + this.position[0] / 20][this.format[i][1] / 20 + this.position[1] / 20][z] = this.column[z];
                    if (scored(this.format[i][1] / 20 + this.position[1] / 20))
                        total++;
                } catch (Exception ie) {}
            }
            if (this.format[i][1] / 20 + this.position[1] / 20 <= 1)
                reset = true;
        }
        this.scorePoint += (15 + total * 3) * total;
        //resets the score
        if (reset)
        {
            restarting = true;
            pause(10000);      //pauses 10 second telling you what you scores then resets
            reset();
            restarting = false;
        }




    }

    /** Check if scored */

    public boolean scored(int row) {
        boolean counter2 = true;
        int i;
        for (i = 0; i < 15; i++) {
            for (int e = 0; e < 3; e++) {
                if (this.Board[i][row][e] < 90)
                    counter2 = false;
            }
        }
        if (counter2) {
            for (i = 0; i < 15; i++) {
                for (int z = row - 1; z > 0; z--) {
                    this.Board[i][z + 1][0] = this.Board[i][z][0];
                    this.Board[i][z + 1][1] = this.Board[i][z][1];
                    this.Board[i][z + 1][2] = this.Board[i][z][2];
                }
            }
            for (i = 0; i < 15; i++) {
                this.Board[i][1][0] = rand.nextInt(80) + 1;
                this.Board[i][1][1] = rand.nextInt(80) + 1;
                this.Board[i][1][2] = rand.nextInt(80) + 1;
            }
        }
        return counter2;
    }
    public void newColumn() {
        this.column[0] = rand.nextInt(150) + 100;
        this.column[1] = rand.nextInt(150) + 100;
        this.column[2] = rand.nextInt(150) + 100;
    }

    public void Column(int it, int num1, int num2) {
        this.format[it][0] = num1 * 20;
        this.format[it][1] = num2 * 20;
    }

    public void move() {   //starts a new shape at the top
        currentShape = nextShape;
        Shape();
        this.position[0] = 140;
        this.position[1] = 0;
    }

    /** Method to generate various shapes including our an unique shape of ours*/

    public void Shape()
    {
        nextShape = rand.nextInt(8);
        if(time>=25)
            time--;

        switch (currentShape) {
            case 0:                                 //I
                newColumn();
                Column(0, 1, 0);
                Column(1, 1, 1);
                Column(2, 1, 2);
                Column(3, 1, 3);
                break;
            case 1:                                 //L
                newColumn();
                Column(0, 1, 1);
                Column(1, 1, 2);
                Column(2, 1, 3);
                Column(3, 2, 3);
                break;
            case 2:                                 //Backward L
                newColumn();
                Column(0, 1, 1);
                Column(1, 1, 2);
                Column(2, 1, 3);
                Column(3, 0, 3);
                break;
            case 3:                                 //square
                newColumn();
                Column(1, 1, 1);
                Column(0, 2, 1);
                Column(2, 1, 2);
                Column(3, 2, 2);
                break;
            case 4:                                 //Backward Z
                newColumn();
                Column(1, 1, 1);
                Column(0, 2, 1);
                Column(2, 1, 2);
                Column(3, 0, 2);
                break;
            case 5:                                 //Z
                newColumn();
                Column(1, 1, 1);
                Column(0, 0, 1);
                Column(2, 1, 2);
                Column(3, 2, 2);
                break;
            case 6:                                 //T
                newColumn();
                Column(0, 1, 1);
                Column(1, 1, 2);
                Column(3, 1, 3);
                Column(2, 2, 2);
                break;
            case 7:                                 //Special Shape
                newColumn();
                Column(1, 1, 1);
                Column(0, 2, 1);
                Column(2, 1, 2);
                Column(3, 0, 0);
                break;
        }

    }

    public static void main(String[] args) {
        Tetris w = new Tetris();
        Thread t = new Thread(w);
        t.start();
        w.setVisible(true);
    }

    private void pause(double time)     //pauses for certain period
    {
        try
        {
            Thread.sleep((long) time);
        } catch (Exception e)
        {
            print("Error when trying to sleep: " + e);
        }
    }

    private void print(String s) {
    }

    /**  Main method for graphics */

    public Tetris() {
        super("Welcome to CAMO-TETRIS");
        setSize(Board_Width, Board_Height);
        setDefaultCloseOperation(3);
        setBackground(Color.white);
        setResizable(false);
        Container contentPane = getContentPane();
        SamplePanel p = new SamplePanel();
        contentPane.add(p);

    }
    /**  Method to check if the shapes collide */
    public boolean impact(int[][] format, int x, int y) {
        try {
            for (int i = 0; i < 4; i++) {
                if (check(format[i][0] + x, format[i][1] + y))
                    return false;
            }
        } catch (Exception ie) {
            return false;
        }
        return true;
    }

    public boolean check(int x, int y)
    {
        return (this.Board[x / 20 + this.position[0] / 20][y / 20 + this.position[1] / 20][0] >= 100);
    }

    /** Method to rotate the various shapes*/

    public void rotate()
    {
        int[][] tempPosition = new int[4][2];
        boolean valid = true;
        for (int i = 0; i < 4; i++)
        {
            if (this.format[i][0] == 0 && this.format[i][1] == 0)
            {
                tempPosition[i][0] = 60;tempPosition[i][1] = 0;
            } else if (this.format[i][0] == 0 && this.format[i][1] == 20)
            {
                tempPosition[i][0] = 40;tempPosition[i][1] = 0;
            } else if (this.format[i][0] == 0 && this.format[i][1] == 40)
            {
                tempPosition[i][0] = 20;tempPosition[i][1] = 0;
            } else if (this.format[i][0] == 0 && this.format[i][1] == 60)
            {
                tempPosition[i][0] = 0;tempPosition[i][1] = 0;
            } else if (this.format[i][0] == 20 && this.format[i][1] == 0)
            {
                tempPosition[i][0] = 60;tempPosition[i][1] = 20;
            } else if (this.format[i][0] == 20 && this.format[i][1] == 20)
            {
                tempPosition[i][0] = 40;tempPosition[i][1] = 20;
            } else if (this.format[i][0] == 20 && this.format[i][1] == 40)
            {
                tempPosition[i][0] = 20;tempPosition[i][1] = 20;
            } else if (this.format[i][0] == 20 && this.format[i][1] == 60)
            {
                tempPosition[i][0] = 0;tempPosition[i][1] = 20;
            } else if (this.format[i][0] == 40 && this.format[i][1] == 0)
            {
                tempPosition[i][0] = 60;tempPosition[i][1] = 40;
            } else if (this.format[i][0] == 40 && this.format[i][1] == 20)
            {
                tempPosition[i][0] = 40;tempPosition[i][1] = 40;
            } else if (this.format[i][0] == 40 && this.format[i][1] == 40)
            {
                tempPosition[i][0] = 20;tempPosition[i][1] = 40;
            } else if (this.format[i][0] == 40 && this.format[i][1] == 60)
            {
                tempPosition[i][0] = 0;tempPosition[i][1] = 40;
            } else if (this.format[i][0] == 60 && this.format[i][1] == 0)
            {
                tempPosition[i][0] = 60;tempPosition[i][1] = 60;
            } else if (this.format[i][0] == 60 && this.format[i][1] == 20)
            {
                tempPosition[i][0] = 40;tempPosition[i][1] = 60;
            } else if (this.format[i][0] == 60 && this.format[i][1] == 40)
            {
                tempPosition[i][0] = 20;tempPosition[i][1] = 60;
            } else if (this.format[i][0] == 60 && this.format[i][1] == 60)
            {
                tempPosition[i][0] = 0;tempPosition[i][1] = 60;
            }
            try {
                if (check(tempPosition[i][0], tempPosition[i][1]))
                    valid = false;
            } catch (Exception ie) {
                valid = false;
            }
        }
        if (valid)
            this.format = tempPosition;
    }

    // resets the orientation and the score
    public void reset()
    {
        Key handle = new Key(this);
        addKeyListener(handle);
        scorePoint = 0;
        for (int i = 0; i < this.Board.length; i++) {
            for (int e = 0; e < (this.Board[i]).length; e++)
            {
                this.Board[i][e][0] = rand.nextInt(80) + 1;
                this.Board[i][e][1] = rand.nextInt(80) + 1;
                this.Board[i][e][2] = rand.nextInt(80) + 1;
            }
            this.counter[i] = 0;
        }
        move();
    }
    /** Generate shapes*/

    public void run() {
        Key handler = new Key(this);
        addKeyListener(handler);
        boolean pres = false;
        int key = 0;
        reset();


        while (true) {

            if (handler.buttons[39] && impact(this.format, 20, 0))  //move right
            {
                this.position[0] = this.position[0] + 10;
                key = 2;

            } else if (this.position[0] % 20 != 0 && key == 2 && !handler.buttons[37])
            {
                this.position[0] = this.position[0] + 10;
            }
            if (handler.buttons[37] && impact(this.format, -20, 0))    //move right
            {
                this.position[0] = this.position[0] - 10;
                key = 1;
            } else if (this.position[0] % 20 != 0 && key == 1 && !handler.buttons[39])    //move down
            {
                this.position[0] = this.position[0] - 10;
            }
            if (handler.buttons[38] && !pres)     //rotate piece
            {
                rotate();
                pres = true;
            } else {
                pres = handler.buttons[38];
            }
            if (handler.buttons[40] && !pres)
            {
                if (impact(this.format, 0, 20))     //checks for collisions
                {
                    this.position[1] = this.position[1] + 20;
                }
                else
                {
                    save();
                    move();
                }
            } else if ((this.position[1] + 4) % 20 == 0)
            {
                if (impact(this.format, 0, 20)) {
                    this.position[1] = this.position[1] + 4;
                }
                else
                {
                    save();
                    move();
                }
            } else {
                this.position[1] = this.position[1] + 4;
            }


            pause((time));

        }
    }


    private class SamplePanel extends JPanel
    {
        public Random random = new Random();

        public void update(Graphics g)
        {
            paint(g);
        }
        /** prints the shape and makes the background full of patterns*/
        public void background(Graphics g) {
            int i;
            for (i = 0; i < Tetris.this.Board.length; i++) {
                for (int e = 0; e < (Tetris.this.Board[i]).length; e++) {
                    if (Tetris.this.Board[i][e][0] != 0 || Tetris.this.Board[i][e][1] != 0 || Tetris.this.Board[i][e][2] != 0) {

                        g.setColor(new Color(Tetris.this.Board[i][e][0], Tetris.this.Board[i][e][1], Tetris.this.Board[i][e][2],200));
                        g.fillRect(i * 20+20 , e * 20+20 , 20, 20);
                    }
                }
            }
            for (i = 0; i < 4; i++)
            {
                g.setColor(new Color(Tetris.this.column[0], Tetris.this.column[1], Tetris.this.column[2],60));
                g.fillRect(20 + Tetris.this.format[i][0] + Tetris.this.position[0], Tetris.this.format[i][1] + Tetris.this.position[1], 20, 20);
            }
        }

        /**  displays the score and instructions */

        public void extra(Graphics g)
        {
            g.setColor(Color.black);
            g.fillRect(398, 140, 50, 15);
            g.fillRect(415, 35, 50,61);
            g.setColor(Color.black);
            g.drawString("SCORE", 400, 135);
            g.drawString("Press UP arrow to rotate", 340, 200);
            g.drawString("Press RIGHT and LEFT arrow  ", 340, 220);
            g.drawString("to move sideways", 340, 240);
            g.drawString("Press DOWN arrow to go  down ", 340, 260);
            g.drawString("The game restarts in 10 seconds ", 340, 280);
            g.drawString("if you lose", 340, 300);
            g.drawString("The possible shapes", 350, 340);
            g.drawString("----------------------", 340, 360);
            g.drawString("o        o                o   ", 350, 380);
            g.drawString("o        o                o    ", 350, 390);
            g.drawString("o        ooo         ooo     ", 350, 400);
            g.drawString("oo       ooo           ooo ", 350, 420);
            g.drawString("oo          ooo     ooo            ", 350, 430);
            g.drawString("o             ", 350, 450);
            g.drawString("  oo       o        ooo            ", 350, 460);
            g.drawString("  o       ooo        o           ", 350, 470);
            g.drawString("----------------------", 340, 490);
            g.setColor(Color.white);

            g.drawString(Tetris.this.scorePoint + "", 415, 155);

            g.setColor(Color.black);

            /**  displays upcoming shape in the sidebar */

            switch(nextShape)
            {
                case 0:
                    g.drawString("Next shape:", 340, 50);
                    g.setColor(Color.white);
                    g.drawString("o",435,50);
                    g.drawString("o",435,65);
                    g.drawString("o",435,80);
                    g.drawString("o",435,95);
                    break;
                case 1:
                    g.drawString("Next shape:", 340, 50);
                    g.setColor(Color.white);
                    g.drawString("o",435,50);
                    g.drawString("o",435,65);
                    g.drawString("o",435,80);
                    g.drawString("o",450,80);
                    break;
                case 2:
                    g.drawString("Next shape:", 340, 50);
                    g.setColor(Color.white);
                    g.drawString("o",435,50);
                    g.drawString("o",435,65);
                    g.drawString("o",435,80);
                    g.drawString("o",420,80);
                    break;
                case 3:
                    g.drawString("Next shape:", 340, 50);
                    g.setColor(Color.white);
                    g.drawString("o",435,50);
                    g.drawString("o",435,65);
                    g.drawString("o",450,65);
                    g.drawString("o",450,50);
                    break;
                case 4:
                    g.drawString("Next shape:", 340, 50);
                    g.setColor(Color.white);
                    g.drawString("o",450,65);
                    g.drawString("o",435,65);
                    g.drawString("o",435,80);
                    g.drawString("o",420,80);
                    break;
                case 5:
                    g.drawString("Next shape:", 340, 50);
                    g.setColor(Color.white);
                    g.drawString("o",420,65);
                    g.drawString("o",435,65);
                    g.drawString("o",435,80);
                    g.drawString("o",450,80);
                    break;
                case 6:
                    g.drawString("Next shape:", 340, 50);
                    g.setColor(Color.white);
                    g.drawString("o",420,65);
                    g.drawString("o",435,65);
                    g.drawString("o",450,65);
                    g.drawString("o",435,80);
                    break;
                case 7:

                    g.drawString("Next shape:", 340, 50);
                    g.setColor(Color.white);
                    g.drawString("o",420,50);
                    g.drawString("o",435,65);
                    g.drawString("o",435,80);
                    g.drawString("o",450,65);
                    break;

            }


            if (Tetris.this.restarting) //prints when you lose

                g.drawString("You lost but you scored: " + Tetris.this.scorePoint, 100,180);





        }

        // main printer
        public void paint(Graphics g)
        {
            background(g);
            extra(g);
            Tetris.this.pause(35);
            repaint();
        }
    }
}
