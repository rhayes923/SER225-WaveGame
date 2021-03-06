package mainGame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.net.URL;
import java.awt.Dimension;
import javafx.embed.swing.JFXPanel;
import javax.swing.JFrame;
import mainGame.audio.SoundPlayer;
import mainGame.audio.SoundClip;
import mainGame.spawn.*;
import mainGame.gui.*;
import mainGame.input.*;
import javax.swing.JOptionPane;

/**
 * Main game class. This class is the driver class and it follows the Holder
 * pattern. It houses references to ALL of the components of the game
 * 
 * @author Brandon Loehle 5/30/16
 */

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 1280, HEIGHT = 720;
	private Thread thread;
	private boolean running = false;

	private Handler handler;
	private HUD hud;
	private Spawn1to5 spawner;
	private Spawn5to10 spawner2;
	private Spawn10to15 spawner3;
	private Spawn15to20 spawner4;
	private SpawnSurvival spawnSurvival;
	private Menu menu;
	public GameOver gameOver;
	private UpgradeScreen upgradeScreen;
	private MouseListener mouseListener;
	private Upgrades upgrades;
	private Player player;
	public STATE gameState = STATE.Menu;
	public static int TEMP_COUNTER;
	private SoundPlayer soundplayer;
	public SoundClip damageSound, healthSound, speedSound, scoreSound, dpSound, nukeSound, shrinkSound, healthIncreaseSound;

	private WonWaves wonWaves;
	private SpawnBosses spawnBosses;
	private JFrame frame;
	private boolean isPaused = false;
	public static boolean isMusicPlaying = true;
	private ColorPickerScreen colorScreen;
	private Image spaceBackground1, spaceBackground2;
	private int spaceYValue = 0;
	


	/**
	 * Used to switch between each of the screens shown to the user
	 */
	public enum STATE {
		Menu, gameMode, Help, Join, Host, Wave, GameOver, Upgrade, Bosses, Survival, Multiplayer, 
		Color, Credits, WonWaves, EnemyJournal
	};

	/**
	 * Initialize the core mechanics of the game
	 */
	public Game() {
		handler = new Handler(this);
		hud = new HUD(this);
		player = new Player(WIDTH / 2 - 42, HEIGHT / 2 - 42, ID.Player, handler, this.hud, this);
		spawner = new Spawn1to5(this.handler, this.hud, this, player);
		spawner2 = new Spawn5to10(this.handler, this.hud, this.spawner, this, player);
		spawner3 = new Spawn10to15(this.handler, this.hud, this, player);
		spawner4 = new Spawn15to20(this.handler, this.hud, this, player);
		spawnSurvival = new SpawnSurvival(this.handler, this.hud, this, player);
		spawnBosses = new SpawnBosses(this.handler, this.hud, this.player);
		menu = new Menu(this, this.handler);
		upgradeScreen = new UpgradeScreen();
		upgrades = new Upgrades(this, this.handler, this.hud, this.upgradeScreen, this.player, this.spawner, this.spawner2, this.spawner3, this.spawner4);
		gameOver = new GameOver(this.handler, this.hud, player);
		wonWaves = new WonWaves(this.handler, this.hud);
		mouseListener = new MouseListener(this, this.handler, this.hud, this.spawner, 
				this.spawner2, this.spawnSurvival, this.upgradeScreen, this.player, 
				this.upgrades, this.spawnBosses);
		this.addKeyListener(new KeyInput(this.handler, this, this.player, this.upgrades));
		this.addMouseListener(mouseListener);
		
		// technically, this is bad practice but I don't care right now
		this.setSize(new Dimension(WIDTH, HEIGHT));
		@SuppressWarnings("unused")
		JFXPanel jfxp = new JFXPanel(); // trust
		soundplayer = new SoundPlayer("sounds/main.mp3", true);
		damageSound = new SoundClip("sounds/damage.mp3", 1.0);
		healthSound = new SoundClip("sounds/health.mp3", 1.0);
		speedSound = new SoundClip("sounds/speed.mp3", 1.0);
		scoreSound = new SoundClip("sounds/points.mp3", 1.0);
		dpSound = new SoundClip("sounds/doublepoints.mp3", 1.0);
		nukeSound = new SoundClip("sounds/nuke.mp3", 1.0);
		shrinkSound = new SoundClip("sounds/shrink.mp3", 1.0);
		healthIncreaseSound = new SoundClip("sounds/healthincrease.mp3", 1.0);
		
		soundplayer.start();
		new Window(WIDTH, HEIGHT, "Space Matrix", this);
		colorScreen = new ColorPickerScreen(player, this);	
		
		try {
			URL imgURL = Game.class.getResource("images/spaceloop.jpg");
			spaceBackground1 = Toolkit.getDefaultToolkit().getImage(imgURL);
			spaceBackground2 = Toolkit.getDefaultToolkit().getImage(imgURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * The thread is simply a programs path of execution. This method ensures
	 * that this thread starts properly.
	 */
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Best Java game loop out there (used by Notch!)
	 */
	@Override
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		@SuppressWarnings("unused")
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			
			
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();// 60 times a second, objects are being updated
				delta--;
			}
			if (running)
				render();// 60 times a second, objects are being drawn
			frames++;
			//System.out.println(frames); DEBUG
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frames = 0;
			}
		}
		stop();

	}

	/**
	 * Constantly ticking (60 times per second, used for updating smoothly).
	 * Used for updating the instance variables (DATA) of each entity (location,
	 * health, appearance, etc).
	 */
	private void tick() {
		if (!isPaused()) { // only tick the game modes and stuff if the game is not paused
			handler.tick();// ALWAYS TICK HANDLER, NO MATTER IF MENU OR GAME
			// SCREEN
			if (gameState == STATE.Wave) {// game is running
				if (!handler.isPaused()) {
					hud.tick();
					spaceYValue++;
					if (Spawn1to5.LEVEL_SET == 1) {// user is on levels 1 thru 5, update them
						spawner.tick();
					} else if (Spawn1to5.LEVEL_SET == 2) {// user is on levels 5 thru 10, update them
						spawner2.tick();
					} else if (Spawn1to5.LEVEL_SET == 3) {
						spawner3.tick();
					} else if (Spawn1to5.LEVEL_SET == 4) {
						spawner4.tick();
					}
				}
				// switch the sound that's playing if the mode is waves
				if (!soundplayer.getSong().equals("sounds/Megalovania.mp3")) {
					soundplayer.stop_playing();
					soundplayer = new SoundPlayer("sounds/Megalovania.mp3", true);
					soundplayer.start();
				}
			} else if (gameState == STATE.Menu || gameState == STATE.Help) {// user is on menu, update the menu items
				menu.tick();
				// make sure the menu is playing the right song
				if (!soundplayer.getSong().equals("sounds/main.mp3")) {
					soundplayer.stop_playing();
					soundplayer = new SoundPlayer("sounds/main.mp3", true);
					soundplayer.start();
				}
			} else if (gameState == STATE.Upgrade) {// user is on upgrade
				// screen, update the
				// upgrade screen
				upgradeScreen.tick();
			} else if (gameState == STATE.GameOver) {// game is over, update the
				// game over screen
				gameOver.tick();
			} else if (gameState == STATE.Bosses) {
				hud.tick();
				spaceYValue++;
				spawnBosses.tick();
				if (!soundplayer.getSong().equals("sounds/bosses.mp3")) {
					soundplayer.stop_playing();
					soundplayer = new SoundPlayer("sounds/bosses.mp3", true);
					soundplayer.start();
				}
			} else if (gameState == STATE.Survival) {
				hud.tick();
				spaceYValue++;
				spawnSurvival.tick();
				if (!soundplayer.getSong().equals("sounds/Survival.mp3")) {
					soundplayer.stop_playing();
					soundplayer = new SoundPlayer("sounds/Survival.mp3", true);
					soundplayer.start();
				}
			} else if (gameState == STATE.WonWaves) {
				wonWaves.tick();
			} else if (gameState == STATE.gameMode) {
				hud.resetHealth();
				player.playerSpeed = 10;
				player.setPlayerSize(31);
			}
			if (spaceYValue > HEIGHT) {
				spaceYValue = 0;
			}
		}

		SoundPlayer.setVolume();
		
	}

	/**
	 * Constantly drawing to the many buffer screens of each entity requiring
	 * the Graphics objects (entities, screens, HUD's, etc).
	 */
	private void render() {
		if (gameState == STATE.Join || gameState == STATE.Host) {
			this.setVisible(false);
			this.setEnabled(false);
		} else {
			this.setVisible(true);
			this.setEnabled(true);
			/*if (!this.hasFocus())
				this.requestFocus();*/
			
			/*
			 * BufferStrategies are used to prevent screen tearing. In other words,
			 * this allows for all objects to be redrawn at the same time, and not
			 * individually
			 */
			BufferStrategy bs = this.getBufferStrategy();
			if (bs == null) {
				this.createBufferStrategy(3);
				return;
			}
			Graphics g = bs.getDrawGraphics();

			///////// Draw things below this /////////

			if (gameState == STATE.Wave || gameState == STATE.Bosses || gameState == STATE.Survival) {
				g.drawImage(spaceBackground1, 0, spaceYValue, WIDTH, HEIGHT, null);
				g.drawImage(spaceBackground2, 0, spaceYValue - HEIGHT, WIDTH, HEIGHT, null);
			} else {
				g.setColor(Color.black);
				g.fillRect(0, 0, WIDTH, HEIGHT);
			}
			
			// SCREEN
			if (!isPaused()) {
				if (gameState == STATE.Wave || gameState == STATE.Multiplayer 
						|| gameState == STATE.Bosses || gameState == STATE.Survival) {
					// user is playing game, draw game objects
					hud.render(g);
				} else if (gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.Credits || gameState == STATE.EnemyJournal || gameState == STATE.gameMode) {
					// user is in the main menu, help, credits, or enemy journal, draw the menu and help objects
					menu.render(g);
				} else if (gameState == STATE.Upgrade) {// user is on the upgrade
					// screen, draw the upgrade
					// screen
					upgradeScreen.render(g);
				} else if (gameState == STATE.GameOver) {// game is over, draw the game
					// over screen
					gameOver.render(g);
				} else if (gameState == STATE.WonWaves) {// game is over, draw the game
					wonWaves.render(g);
				} else if (gameState == STATE.Color) {
					colorScreen.render(g);
				}

			} 
			
			else {
				player.render(g);
				hud.render(g);			

			}
			if(!isPaused()){
				handler.render(g);} // ALWAYS RENDER HANDLER, NO MATTER IF MENU OR GAME
			///////// Draw things above this//////////////
			g.dispose();
			bs.show();
			}
		}

	/**
	 * 
	 * Constantly checks bounds, makes sure players, enemies, and info doesn't
	 * leave screen
	 * 
	 * @param var
	 *            x or y location of entity
	 * @param min
	 *            minimum value still on the screen
	 * @param max
	 *            maximum value still on the screen
	 * @return value of the new position (x or y)
	 */
	public static double clamp(double var, double min, double max) {
		if (var >= max)
			return max;
		else if (var <= min)
			return min;
		else
			return var;
	}

	public static double clampX(double x, double width) {
		return clamp(x, 0, Game.WIDTH - width);
	}

	public static double clampY(double y, double height) {
		return clamp(y, 0, Game.HEIGHT - height);
	}

	public static void main(String[] args) {
		new Game();
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public JFrame getFrame() {
		return this.frame;
	}

	public void pause() {
		isPaused = true;
	}

	public void unPause() {
		isPaused = false;
	}

	public boolean isPaused() {
		return isPaused;
	}

	public STATE getGameState() {
		return gameState;
	}


	public void musicKeyPressed() {
		isMusicPlaying = !isMusicPlaying;
	}


	public void popup(String text) {
		JOptionPane.showMessageDialog(this, text);
	}
}
