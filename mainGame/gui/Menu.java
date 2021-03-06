package mainGame.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import mainGame.Game.STATE;
import mainGame.*;

/**
 * The main menu
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class Menu {

	private Game game;
	private Handler handler;
	private Image img;
	private Image img2;
	private Image raytracing;
	private int timer;
	
	private Image trackerBlack = Toolkit.getDefaultToolkit().getImage(Game.class.getResource("images/TrackerBlack.png"));
	private Image trackerBlue = Toolkit.getDefaultToolkit().getImage(Game.class.getResource("images/TrackerBlue.png"));
	private Image ufoPorcupine = Toolkit.getDefaultToolkit().getImage(Game.class.getResource("images/UFOPorcupine.png"));
	private Image leftRight = Toolkit.getDefaultToolkit().getImage(Game.class.getResource("images/LeftRight.png"));
	private Image miniShooter = Toolkit.getDefaultToolkit().getImage(Game.class.getResource("images/MiniShooter.png"));
	private Image explosion = Toolkit.getDefaultToolkit().getImage(Game.class.getResource("images/Explosion.gif"));
	

	private Image basicShip = Toolkit.getDefaultToolkit().getImage(Game.class.getResource("images/basicShip.png"));
	private Image bigShip = Toolkit.getDefaultToolkit().getImage(Game.class.getResource("images/bigShip.png"));
	private Image shooterShip = Toolkit.getDefaultToolkit().getImage(Game.class.getResource("images/shooterShip.png"));
	private Image smartShip = Toolkit.getDefaultToolkit().getImage(Game.class.getResource("images/smartShip.png"));
	private Image sweepShip = Toolkit.getDefaultToolkit().getImage(Game.class.getResource("images/sweepShip.png"));

	public Menu(Game game, Handler handler) {
		this.game = game;
		this.handler = handler;
		timer = 10;
		img = null;
		img2 = null;

		try {
			URL imageURL = Game.class.getResource("images/mainMenu.png");
			img = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public void tick() {
		timer--;
		if (timer <= 0) {
			handler.object.clear();
			timer = 300;
		}
		handler.tick();
	}

	public void render(Graphics g) {
		if (game.gameState == STATE.Menu) {
			g.drawImage(img, 0, 0, Game.WIDTH, Game.HEIGHT, null);
			g.drawImage(img2, 850, 450, 100, 100, null);
			handler.render(g);
			
			try {
				URL imageURL = Game.class.getResource("images/mainMenu.png");
				img = Toolkit.getDefaultToolkit().getImage(imageURL);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
      
		} else if (game.gameState == STATE.Help) {// if the user clicks on
			// "help"
			g.drawImage(img, 0, 0, Game.WIDTH, Game.HEIGHT, null);
			handler.render(g);
			img = null;

			try {
				URL imageURL = Game.class.getResource("images/dust-particles.png");
				img = Toolkit.getDefaultToolkit().getImage(imageURL);
			} catch (Exception e) {
				e.printStackTrace();
			}

			Font help = new Font("arial black", 1, 50);
			Font font = new Font("arial black", 1, 24);
			Font font2 = new Font("arial", 1, 20);

			g.setFont(help);
			g.setColor(Color.WHITE);
			g.drawString("Help", 550, 70);
			
			g.setFont(font);
			g.drawString("Controls:", 100, 130);
			g.setFont(font2);
			g.drawString(" Move: WASD or Arrow keys", 100, 160);
			g.drawString(" ESC: Pause", 100, 180);
			g.drawString(" Shift: Use item", 100, 200);
			g.drawString(" Mute sounds: M " , 100, 220);
			
			g.setFont(font);
			g.drawString("Power-ups: ", 900, 130);
			g.setFont(font2);
			g.drawString(" Ham bone: Increased health", 900, 160);
			g.drawString(" Shoes: Speed boost", 900, 180);
			g.drawString(" Coin: +1000 points", 900, 200);
			g.drawString(" x2: Double points for a short time", 900, 220);
			g.drawString(" Mushroom: Shrinks the player", 900, 240);
			g.drawString(" Heart: Increased max health", 900, 260);
			g.drawString(" Nuke: Clears the screen (Survival only)", 900, 280);
			
			g.setFont(font);
			g.drawString("Waves:", 100, 350);
			g.setFont(font2);
			g.drawString(" Avoid getting hit by the enemies as you", 100, 380);
			g.drawString(" progress through the various waves", 100, 400);
			g.drawString(" every 5 levels there will be a boss", 100, 420);
			g.drawString(" beating the boss will earn you an upgrade", 100, 440);
			g.drawString(" Power-ups will spawn to help you stay alive", 100, 460);
			
			g.setFont(font);
			g.drawString("Bosses:", 550, 350);
			g.setFont(font2);
			g.drawString(" Unlimited waves of bosses,", 550, 380);
			g.drawString(" avoid getting hit for as long as", 550, 400);
			g.drawString(" possible", 550, 420);
	
			g.setFont(font);
			g.drawString("Survival:", 900, 350);
			g.setFont(font2);
			g.drawString(" Stay alive as long as possible", 900, 380);
			g.drawString(" enemies will spawn endlessly", 900, 400);
			g.drawString(" Power-ups will spawn to help you", 900, 420);
			g.drawString(" stay alive.", 900, 440);
			
			g.setFont(font2);
			g.setColor(Color.white);
			g.drawRect(566, 590, 133, 42);
			g.drawString("Back", 613, 620);
		}
		//If the user clicks on the credit menu option
		else if (game.gameState == STATE.Credits) {
			
			g.drawImage(img, 0, 0, Game.WIDTH, Game.HEIGHT, null);
			g.drawImage(raytracing, 852, 0, 426 , 360, null);
			handler.render(g);

			img = null;
			raytracing = null;
		

			try {
				URL imageURL = Game.class.getResource("images/dust-particles.png");
				img = Toolkit.getDefaultToolkit().getImage(imageURL);
			} catch (Exception e) {
				e.printStackTrace();
			}

			
			
			Font font = new Font("arial black", 1, 33);
			Font font2 = new Font("arial", 1, 20);

			g.setFont(font);
			g.setColor(Color.WHITE);
			g.drawString("Credits", 550, 70);

			g.drawString("Team B2: Lego Kit Fisto", 420, 130);
			
			g.setFont(font);
			g.drawString("Team Managers:", 100, 200);
			g.setFont(font2);
			g.drawString("Kyle Gorman", 100, 230);
			g.drawString("Will Eccles", 100, 250);
			
			g.setFont(font);
			g.drawString("Team Members:", 100, 300);
			
			g.setFont(font2);
			g.drawString("Will Varsalona: Scrum Master, Art and UI", 100, 330);				
			g.drawString("Ryan Hayes: Programming and Bug Fixing", 100, 350);						
			g.drawString("Henry Staunton: Level Balancing and Bug Fixing", 100, 370);			
			g.drawString("Neel Bains: Programming, Bug Fixing, Character Art", 100, 390);			
			g.drawString("Rishi Parikh: Programming and Bug Fixing", 100, 410);
	
			
			g.setColor(Color.white);
			g.drawRect(566, 650, 133, 42);
			g.drawString("Back", 613, 680);
		}
		
		//if the user clicks on the enemy journal button
		else if (game.gameState == STATE.EnemyJournal) {
			g.drawImage(img, 0, 0, Game.WIDTH, Game.HEIGHT, null);
			handler.render(g);	
			img = null;

			try {
				URL imageURL = Game.class.getResource("images/dust-particles.png");
				img = Toolkit.getDefaultToolkit().getImage(imageURL);
			} catch (Exception e) {
				e.printStackTrace();
			}
					
			Font font = new Font("arial black", 1, 33);
			Font font2 = new Font("arial", 1, 18);
					
			g.setFont(font);
			g.setColor(Color.WHITE);
			g.drawString("Enemy Journal", 540, 46);
					
			g.setFont(font2);
			g.drawRect(566, 590, 140, 50);
			g.drawString("Back", 613, 620);
			
			//Enemy Basic
			g.drawImage(basicShip, 60, 70, 75, 75, null);	
			//Enemy Burst
			g.drawImage(bigShip, 60, 170, 75, 75, null);
			//Enemy Sweep
			g.drawImage(sweepShip, 60, 270, 75, 75, null);
			//Enemy Smart
			g.drawImage(smartShip, 60, 365, 75, 75, null);
			//Enemy Shooter
			g.drawImage(shooterShip, 60, 480, 75, 75, null);
			//Enemy Tracker
			g.drawImage(trackerBlue, 75, 575, 50,50, null);
			//Enemy Expansion
			g.drawImage(explosion, 700, 65, 100, 100, null);
			//Enemy Minishooter
			g.drawImage(miniShooter, 715, 175, 75,75, null);
			//Enemy Porcupine
			g.drawImage(ufoPorcupine, 715, 275, 75, 75, null);
			//Enemy LeftRight
			g.drawImage(leftRight, 715, 375, 75, 75, null);
			
			//Draw the text
			g.setFont(font2);
			g.setColor(Color.white);
			
			g.drawString("Basic: An enemy that will move diagonally and bounces off ", 150, 110);
			g.drawString("of the sides of the screen", 150, 130);
			
			g.drawString("Burst: A large enemy that will come from a random direction.", 150, 200);
			g.drawString("The edge of the screen that it comes from will flash orange ", 150, 220);
			g.drawString("as a warning", 150, 240);
			
			g.drawString("Sweep: An enemy that will sweep side to side across the ", 150, 310);
			g.drawString("screen and move vertically", 150, 330);
			
			g.drawString("Smart: An enemy that will follow the player", 150, 415);
			
			g.drawString("Shooter: A large stationary enemy that will shoot ", 150, 515);
			g.drawString("projectiles at the player", 150, 535);
			
			g.drawString("Tracker: An enemy that will follow the player", 150, 600);
			g.drawString("but will either be blue or black", 150, 620);
			
			g.drawString("Expansion: An enemy that expands in size until ", 825, 100);
			g.drawString("it eventually fades away", 825, 120);
			
			g.drawString("Minishooter: A stationary enemy that is a ", 825, 200);
			g.drawString("smaller version of the shooter enemy ", 825, 220);
			g.drawString("that shoots projectiles at the player", 825, 240);
			
			g.drawString("Porcupine: A large enemy that will follow the ", 825, 300);
			g.drawString("player and shoot a large amount of projectiles ", 825, 320);
			g.drawString("all at once at the player", 825, 340);
			
			g.drawString("Left/Right: Two enemies that will move in ", 825, 415);
			g.drawString("opposite directions", 825, 435);
			//sup
		} else if (game.gameState == STATE.gameMode) {
			g.drawImage(img, 0, 0, Game.WIDTH, Game.HEIGHT, null);
			handler.render(g);	
			img = null;
			
			try {
				URL imageURL = Game.class.getResource("images/gameModeMenu.png");
				img = Toolkit.getDefaultToolkit().getImage(imageURL);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			Font font2 = new Font("arial", 1, 33);
			
			g.setFont(font2);
			g.setColor(Color.white);
			g.drawRect(566, 590, 133, 42);
			g.drawString("Back", 595, 620);
		}
	}
}