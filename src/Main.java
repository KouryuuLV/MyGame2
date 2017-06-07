/*

Puzzle Game made with the tutorila from Sheldon online tutorial on Youtube
https://www.youtube.com/watch?v=IuQYQxVV9So

Highscore Part implimented according to BrandonioProductions

JFrame
https://www.youtube.com/playlist?list=PLcnfNKaqA7zQAGZEWZuWwonCTEHJ01D6G

http://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html#input

*/

import java.awt.event.*;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main 
{
	static JFrame frame;
	static Puzzle puzzle;
        private int score = 2000;
        private String HighScore = "";
        
        public String GetHighScoreValue()
            {
                FileReader readFile = null;
                BufferedReader reader = null;
        
                try
                {
                  readFile = new FileReader ("src/highscore.txt");               
                  
                  reader = new BufferedReader(readFile);
                  System.out.println(reader);
                 return reader.readLine();
                }

                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                    return "Nobody:0";
                }

                finally 
                {
                    try 
                    {
                        reader.close();
                    } 
                    
                    catch (IOException ex) 
                    {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        
	public static void main(String [] args) 
        {
            new Main().run();
        }
        
        public void run()
        {
            HighScore = GetHighScoreValue();
            System.out.println(HighScore);
            
            frame = new JFrame("Puzzlegame RVT Logo");
            frame.setSize(627, 368);
		
            puzzle = new Puzzle(new ImageIcon(Main.class.getResource("/RVT_1.png")).getImage(), HighScore);
		
                
            //frame.add(puzzle);
            frame.setContentPane(puzzle);
                
            puzzle.setLayout(null);
		
            frame.setLocationRelativeTo(null);
                		
            frame.setResizable(false);
            frame.setVisible(true);
		
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
            frame.addMouseListener(new MouseAdapter() 
            {
                public void mousePressed(MouseEvent e) 
                {
                    if (!puzzle.started) 
                    {
                        puzzle.start();                                
                    }
                    
                    else if (puzzle.mixing) puzzle.mixing = false;
                    else 
                    
                    puzzle.onClick(e);
                                    
                                
		}
            });
        }
        
}
