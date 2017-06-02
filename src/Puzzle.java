import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Puzzle extends JPanel {
        private JList scoreList;
        private DefaultListModel scoreModel;
        private JButton Test;
        
	Segment[] segments;
	Image img;
        String highScore;
        boolean done = false;
        
	public boolean started = false;
	public boolean mixing = false;
        
        public void createUserInterface(){
        scoreModel= new DefaultListModel();
        scoreList = new JList(scoreModel);
        }
        
        
            
        
        
	public Puzzle(Image img,String HighScore) {
                Test = new JButton("Tests");
                
		this.img = img;
                this.highScore = HighScore;
		//init 9 segments
		segments = new Segment[9];
		int segmentSize = img.getWidth(null)/3;
		for (int i = 0; i != segments.length; i++) {
			//Sadala un identificē segmentus array listā
			segments[i] = new Segment(this, i, segmentSize);
		}
	}

	public void start() {
		started = true;
		//Noņem BR segmentu
		segments[8].isEmpty = true;
		mix.start();
	}
	
	//RNG
	Thread mix = new Thread(new Runnable() {
		public void run() {
                        mixing = true;
			while (mixing) {
				ArrayList<Integer> possibleMovements = new ArrayList<Integer>();
				for (Segment s : segments) {
					if (s.getPosition().x == segments[8].getPosition().x+1 && s.getPosition().y == segments[8].getPosition().y) possibleMovements.add(s.getID());
					if (s.getPosition().x == segments[8].getPosition().x-1 && s.getPosition().y == segments[8].getPosition().y) possibleMovements.add(s.getID());
					if (s.getPosition().x == segments[8].getPosition().x && s.getPosition().y == segments[8].getPosition().y-1) possibleMovements.add(s.getID());
					if (s.getPosition().x == segments[8].getPosition().x && s.getPosition().y == segments[8].getPosition().y+1) possibleMovements.add(s.getID());
				}
				
				int ind = (int) ((Math.random()*possibleMovements.size()));
				try {
					Point tmp = segments[possibleMovements.get(ind)].getPosition();
					segments[possibleMovements.get(ind)].setPosition(segments[8].getPosition());
					segments[8].setPosition(tmp);
				} catch (Exception e) {}
				repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {}
			}
		}
	});
	
	//Pārbauda klikšķi un vai iespējams veikt darbību
	public void onClick(MouseEvent e) {           
		for (Segment s : segments) {
			if (s.hitten(e.getPoint())) {
				Point tmp = s.getPosition();
				if (s.move(segments[8].getPosition())) {
					segments[8].setPosition(tmp);
					
					//Pārbauda vai pabeigts
					done = true;
					for (int i = 0; i != 9; i++) {
						if (segments[i].getPosition().x == ((i <= 2)? i:(i <= 5)? (i-3):(i-6)) && segments[i].getPosition().y == (int) Math.ceil((i/3))) {
							//System.out.println(i+": Y");
						} else {
							//System.out.println(i+": N");
							done = false;
						}
					}
					
					if (done) {
						started = false;
                                                segments[8].isEmpty = false;
                                                
					}
				}
			}
		}
		repaint();
	}
	
        @Override
	public void paint(Graphics g) {
		super.paint(g);
		for (int i = 0; i != segments.length; i++) {
			//System.out.print(segments[i].getID()+((i == 2 || i == 5 || i == 8)? "\n-----\n":"|"));
			segments[i].paint(g);
		}
                    // Atainot spēles Top 10
                    g.setColor(new Color(0,0,0));
                    g.drawString("Best Time: " + highScore, 350, 50);
                    
                    // Atainot rezultatu pec speles pabeigšanas
                    if (done == true){
                        g.setColor(new Color(0,0,0));
                        g.drawString("Apsveicu: " + highScore, 350, 70);
                    }
	}
        
        public String getHighScore() {
		return highScore;
	}
	
}