package mainGame.spawn;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import mainGame.Game.STATE;
import mainGame.enemy.*;
import mainGame.*;
import mainGame.gui.*;

public class Spawn10to15 {
	public static int LEVEL_SET_3 = 0;
	private Handler handler;
	private HUD hud;
	private Game game;
	private Random r = new Random();
	private int spawnTimer;
	private int levelTimer;
	private String[] side = { "left", "right", "top", "bottom" };
	ArrayList<Integer> levels = new ArrayList<Integer>(); // MAKE THIS AN ARRAY LIST SO I CAN REMOVE OBJECTS
	private int levelsRemaining;
	private int levelNumber = 0;
	private int tempCounter = 0;
	private Color trackerColor;
	private int trackerTimer;
	private int differentEnemies;
	private Player player;
	private int randIntHolder;

	public Spawn10to15(Handler handler, HUD hud, Game game, Player player) {
		restart();
		this.handler = handler;
		this.hud = hud;
		this.game = game;
		handler.object.clear();
		hud.health = 100;
		hud.setScore(0);
		spawnTimer = 10;
		levelTimer = 150;
		levelsRemaining = 5;
		hud.setLevel(1);
		tempCounter = 0;
		levelNumber = -1;
		trackerColor = Color.blue;
		trackerTimer = 1000;
		differentEnemies = 9;
		addLevels();
		this.player = player;
		randIntHolder = 50;

	}

	/**
	 * Pre-load every level
	 */
	public void addLevels() {
		for (int i = 0; i <= differentEnemies; i++) {
			levels.add(i);
		}
	}

	/**
	 * Called once every 60 seconds by the Game loop
	 */
	public void tick() {
		if (levelNumber < 0) {
			levelTimer--;
			if (tempCounter < 1) {// display intro game message ONE time
				handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, "Even Harder Now!!!!",
						ID.Levels1to10Text));
				tempCounter++;
			}
			if (levelTimer <= 0) {// time to play!
				handler.clearEnemies();
				tempCounter = 0;
				levelNumber = this.rand();
			}

		}
		/*
		 * EVERY LEVEL WORKS THE SAME WAY
		 * 
		 * Only the first level is commented
		 * 
		 * Please refer to this bit of code to understand how each level works
		 * 
		 */
		else if (levelNumber == 0) {// this is level 1
			spawnTimer--;// keep decrementing the spawning spawnTimer 60 times a second
			levelTimer--;// keep decrementing the level spawnTimer 60 times a second
			if (tempCounter < 1) {// called only once, but sets the levelTimer to how long we want this level to
				// run for
				levelTimer = 1200;// 2000 / 60 method calls a second = 33.33 seconds long
				tempCounter++;// ensures the method is only called once
			}
			if (spawnTimer == 0) {// time to spawn another enemy
				handler.addObject(
						new EnemyBasic(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), 11-levelsRemaining, 11-levelsRemaining, ID.EnemyBasic, handler));
				// add them to the handler, which handles all game objects
				spawnTimer = 100;// reset the spawn timer
			} else if (spawnTimer == 30) {
				handler.addObject(
						new EnemySweep(r.nextInt(((Game.WIDTH - 50) - 50) + 1 )+ 50, r.nextInt(Game.HEIGHT), 15-levelsRemaining, 1, ID.EnemySweep, handler));
			} else if (spawnTimer == 20) {
				handler.addObject(
						new EnemySweep(r.nextInt(((Game.WIDTH - 50) - 50) + 1 )+ 50, r.nextInt(Game.HEIGHT), 15-levelsRemaining, -1, ID.EnemySweep, handler));
			} else if (spawnTimer == 10) {
				handler.addObject(
						new EnemySweep(r.nextInt(((Game.WIDTH - 50) - 50) + 1 )+ 50, r.nextInt(Game.HEIGHT), 15-levelsRemaining, 3, ID.EnemySweep, handler));
			} else if (spawnTimer == 0) {
				handler.addObject(
						new EnemySweep(r.nextInt(((Game.WIDTH - 50) - 50) + 1 )+ 50, r.nextInt(Game.HEIGHT), 15-levelsRemaining, -3, ID.EnemySweep, handler));
				spawnTimer = 80;
			}

			if (levelTimer == 0) {// level is over
				handler.clearEnemies();// clear the enemies
				hud.setLevel(hud.getLevel() + 1);// Increment level number on HUD
				spawnTimer = 40;
				tempCounter = 0;// reset tempCounter
				if (levelsRemaining == 1) {// time for the boss!
					levelNumber = 101;// arbitrary number for the boss level
				} else {// not time for the boss, just go to the next level
					levelsRemaining--;
					levelNumber = levels.get(this.rand());// set levelNumber to whatever index was randomly selected
				}
			}
		} else if (levelNumber == 1) {
			spawnTimer--;
			levelTimer--;
			if (tempCounter < 1) {
				levelTimer = 1200;
				handler.addObject(new EnemyPorcupine(r.nextInt(((Game.WIDTH - 50) - 50) + 1 )+ 50, r.nextInt(((Game.HEIGHT - 50) - 50) + 1 )+ 50, 100, 100, ID.EnemyPorcupine, this.handler, -1, -7+levelsRemaining, 10, this.game));
				handler.addObject(new EnemyPorcupine(r.nextInt(((Game.WIDTH - 50) - 50) + 1 )+ 50, r.nextInt(((Game.HEIGHT - 50) - 50) + 1 )+ 50, 100, 100, ID.EnemyPorcupine, this.handler, -1, -7+levelsRemaining, 10, this.game));
				tempCounter++;
			}

			if (spawnTimer == 30) {
				handler.addObject(
						new EnemySweep(r.nextInt(((Game.WIDTH - 50) - 50) + 1 )+ 50, r.nextInt(Game.HEIGHT), 15-levelsRemaining, 1, ID.EnemySweep, handler));
			} else if (spawnTimer == 20) {
				handler.addObject(
						new EnemySweep(r.nextInt(((Game.WIDTH - 50) - 50) + 1 )+ 50, r.nextInt(Game.HEIGHT), 15-levelsRemaining, -1, ID.EnemySweep, handler));
			} else if (spawnTimer == 10) {
				handler.addObject(
						new EnemySweep(r.nextInt(((Game.WIDTH - 50) - 50) + 1 )+ 50, r.nextInt(Game.HEIGHT), 15-levelsRemaining, 3, ID.EnemySweep, handler));
			} else if (spawnTimer == 0) {
				handler.addObject(
						new EnemySweep(r.nextInt(((Game.WIDTH - 50) - 50) + 1 )+ 50, r.nextInt(Game.HEIGHT), 15-levelsRemaining, -3, ID.EnemySweep, handler));
				spawnTimer = 80;
			}

			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				tempCounter = 0;
				if (levelsRemaining == 1) {
					levelNumber = 101;
				} else {// not time for the boss, just go to the next level
					levelsRemaining--;
					levelNumber = levels.get(this.rand());// set levelNumber to whatever index was randomly selected
				}
			}
		} else if (levelNumber == 2) {
			spawnTimer--;
			levelTimer--;
			if (tempCounter < 1) {
				levelTimer = 1200;
				handler.addObject(new EnemyShooter(r.nextInt(((Game.WIDTH - 50) - 50) + 1 )+ 50 - 100, r.nextInt(((Game.HEIGHT - 50) - 50) + 1 )+ 50-100, 100, 100, -25+(levelsRemaining*3), 5+(levelsRemaining*2), ID.EnemyShooter, this.handler));
				tempCounter++;
			}
			if (spawnTimer == 0) {
				handler.addObject(
						new EnemySmart(r.nextInt(((Game.WIDTH - 50) - 50) + 1 )+ 50, r.nextInt(Game.HEIGHT), -7+levelsRemaining, ID.EnemySmart, handler));
				spawnTimer = 100;
			} 
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				spawnTimer = 10;
				tempCounter = 0;
				if (levelsRemaining == 1) {
					levelNumber = 101;
				} else {// not time for the boss, just go to the next level
					levelsRemaining--;
					levelNumber = levels.get(this.rand());// set levelNumber to whatever index was randomly selected
				}
			}
		} else if (levelNumber == 3) {
			levelTimer--;
			if (tempCounter < 1) {
				handler.addObject(new EnemyShooter(r.nextInt(((Game.WIDTH - 50) - 50) + 1 )+ 50 - 100, r.nextInt(((Game.HEIGHT - 50) - 50) + 1 )+ 50-100, 100, 100, -23+(levelsRemaining*3), 8+(levelsRemaining*2), ID.EnemyShooter, this.handler));
				handler.addObject(new EnemyShooter(r.nextInt(((Game.WIDTH - 50) - 50) + 1 )+ 50 - 100, r.nextInt(((Game.HEIGHT - 50) - 50) + 1 )+ 50-100, 100, 100, -23+(levelsRemaining*3), 8+(levelsRemaining*2), ID.EnemyShooter, this.handler));
				handler.addObject(new EnemyShooter(r.nextInt(((Game.WIDTH - 50) - 50) + 1 )+ 50 - 100, r.nextInt(((Game.HEIGHT - 50) - 50) + 1 )+ 50-100, 100, 100, -23+(levelsRemaining*3), 8+(levelsRemaining*2), ID.EnemyShooter, this.handler));
				handler.addObject(new EnemyShooter(r.nextInt(((Game.WIDTH - 50) - 50) + 1 )+ 50 - 100, r.nextInt(((Game.HEIGHT - 50) - 50) + 1 )+ 50-100, 100, 100, -23+(levelsRemaining*3), 8+(levelsRemaining*2), ID.EnemyShooter, this.handler));
				levelTimer = 1200;
				tempCounter++;
			}

			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				spawnTimer = 10;
				tempCounter = 0;
				if (levelsRemaining == 1) {
					levelNumber = 101;
				} else {// not time for the boss, just go to the next level
					levelsRemaining--;
					levelNumber = levels.get(this.rand());// set levelNumber to whatever index was randomly selected
				}
			}
		} else if (levelNumber == 4) {
			spawnTimer--;
			levelTimer--;
			if (tempCounter < 1) {
				levelTimer = 1200;
				tempCounter++;
			}
			
			if(trackerTimer == 999) {
				trackerColor = Color.blue;
			} else if (trackerTimer == 500) {
				trackerColor = new Color(10, 10, 10);
			} else if (trackerTimer == 0) {
				trackerTimer = 1000;
			}
			trackerTimer--;
			
			if (spawnTimer <= 0) {
				handler.addObject(new EnemyBurst(-200, 200, 50, 50, 200, side[r.nextInt(4)], ID.EnemyBurst, handler));
				handler.addObject(new EnemyTracker(r.nextInt(((Game.WIDTH - 50) - 50) + 1 )+ 50, r.nextInt(Game.HEIGHT), -5, ID.EnemyTracker, handler, trackerColor, trackerTimer, game));
				spawnTimer = 70+(levelsRemaining*10);
			}

			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				spawnTimer = 10;
				tempCounter = 0;
				if (levelsRemaining == 1) {
					levelNumber = 101;
				} else {// not time for the boss, just go to the next level
					levelsRemaining--;
					levelNumber = levels.get(this.rand());// set levelNumber to whatever index was randomly selected
				}
			}
		} else if (levelNumber == 5) {
			spawnTimer--;
			levelTimer--;
			if (tempCounter < 1) {
				levelTimer = 1200;
				tempCounter++;
			}
			if(trackerTimer == 999) {
				trackerColor = Color.blue;
			} else if (trackerTimer == 500) {
				trackerColor = new Color(10, 10, 10);
			} else if (trackerTimer == 0) {
				trackerTimer = 1000;
			}
			trackerTimer--;
			if(spawnTimer == 0) {
				handler.addObject(
						new EnemyTracker(r.nextInt(((Game.WIDTH - 50) - 50) + 1 )+ 50, r.nextInt(Game.HEIGHT), -9+levelsRemaining, ID.EnemyTracker, handler, trackerColor, trackerTimer, game));
				spawnTimer = 100;
			} else if (spawnTimer == 30) {
				handler.addObject(
						new EnemySweep(r.nextInt(((Game.WIDTH - 50) - 50) + 1 )+ 50, r.nextInt(Game.HEIGHT), 15-levelsRemaining, 1, ID.EnemySweep, handler));
			} else if (spawnTimer == 20) {
				handler.addObject(
						new EnemySweep(r.nextInt(((Game.WIDTH - 50) - 50) + 1 )+ 50, r.nextInt(Game.HEIGHT), 15-levelsRemaining, -1, ID.EnemySweep, handler));
			} else if (spawnTimer == 10) {
				handler.addObject(
						new EnemySweep(r.nextInt(((Game.WIDTH - 50) - 50) + 1 )+ 50, r.nextInt(Game.HEIGHT), 15-levelsRemaining, 3, ID.EnemySweep, handler));
			} else if (spawnTimer == 0) {
				handler.addObject(
						new EnemySweep(r.nextInt(((Game.WIDTH - 50) - 50) + 1 )+ 50, r.nextInt(Game.HEIGHT), 15-levelsRemaining, -3, ID.EnemySweep, handler));
				spawnTimer = 80;
			}

			if (levelTimer == 0) {
				tempCounter = 0;
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				spawnTimer = 10;
				trackerColor = Color.blue;
				trackerTimer = 1000;
				if (levelsRemaining == 1) {
					levelNumber = 101;
				} else {// not time for the boss, just go to the next level
					levelsRemaining--;
					levelNumber = levels.get(this.rand());// set levelNumber to whatever index was randomly selected
				}
			}
		}
		else if (levelNumber == 6) {
			spawnTimer--;
			levelTimer--;
			if (tempCounter < 1) {
				levelTimer = 1200;
				handler.addObject(new EnemyShooter(r.nextInt(((Game.WIDTH - 50) - 50) + 1 )+ 50 - 100, r.nextInt(((Game.HEIGHT - 50) - 50) + 1 )+ 50-100, 100, 100, -25+(levelsRemaining*3), 5+(levelsRemaining*2), ID.EnemyShooter, this.handler));
				tempCounter++;
			}
			if (spawnTimer == 0) {
				handler.addObject(new EnemyExpand(r.nextInt(((Game.WIDTH - 50) - 50) + 1 )+ 50, r.nextInt(((Game.HEIGHT - 50) - 50) + 1 )+ 50, 100, 100, ID.EnemyExpand, this.handler));
				spawnTimer = 50+(levelsRemaining*10);
			}
			if (levelTimer == 0) {
				tempCounter = 0;
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				spawnTimer = 10;
				if (levelsRemaining == 1) {
					levelNumber = 101;
				} else {// not time for the boss, just go to the next level
					levelsRemaining--;
					levelNumber = levels.get(this.rand());// set levelNumber to whatever index was randomly selected
				}
			}
		}
		else if (levelNumber == 7) {
			spawnTimer--;
			levelTimer--;
			if (tempCounter < 1) {
				levelTimer = 1200;
				tempCounter++;
				handler.addObject(new EnemyMiniShooter(r.nextInt(((Game.WIDTH - 50) - 50) + 1 )+ 50, r.nextInt(((Game.HEIGHT - 50) - 50) + 1 )+ 50, 75, 75, -5, 20+(levelsRemaining*5), ID.EnemyMiniShooter, this.handler, this.game));
				handler.addObject(new EnemyMiniShooter(r.nextInt(((Game.WIDTH - 50) - 50) + 1 )+ 50, r.nextInt(((Game.HEIGHT - 50) - 50) + 1 )+ 50, 75, 75, -5, 20+(levelsRemaining*5), ID.EnemyMiniShooter, this.handler, this.game));
				handler.addObject(new EnemyMiniShooter(r.nextInt(((Game.WIDTH - 50) - 50) + 1 )+ 50, r.nextInt(((Game.HEIGHT - 50) - 50) + 1 )+ 50, 75, 75, -5, 20+(levelsRemaining*5), ID.EnemyMiniShooter, this.handler, this.game));
			}
			if (spawnTimer == 0) {// time to spawn another enemy
				handler.addObject(
						new EnemyBasic(r.nextInt(((Game.WIDTH - 50) - 50) + 1 )+ 50, r.nextInt(Game.HEIGHT), 11-levelsRemaining, 11-levelsRemaining, ID.EnemyBasic, handler));
				// add them to the handler, which handles all game objects
				spawnTimer = 100;// reset the spawn timer
			} 
			if (levelTimer == 0) {
				tempCounter = 0;
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				spawnTimer = 10;
				if (levelsRemaining == 1) {
					levelNumber = 101;
				} else {// not time for the boss, just go to the next level
					levelsRemaining--;
					levelNumber = levels.get(this.rand());// set levelNumber to whatever index was randomly selected
				}
			}
		}
		else if (levelNumber == 8) {
			spawnTimer--;
			levelTimer--;
			if (tempCounter < 1) {
				levelTimer = 1200;
				handler.addObject(new EnemyPorcupine(r.nextInt(((Game.WIDTH - 50) - 50) + 1 )+ 50, r.nextInt(((Game.HEIGHT - 50) - 50) + 1 )+ 50, 100, 100, ID.EnemyPorcupine, this.handler, -1, -7+levelsRemaining, 10, this.game));
				handler.addObject(new EnemyPorcupine(r.nextInt(((Game.WIDTH - 50) - 50) + 1 )+ 50, r.nextInt(((Game.HEIGHT - 50) - 50) + 1 )+ 50, 100, 100, ID.EnemyPorcupine, this.handler, -1, -7+levelsRemaining, 10, this.game));
				tempCounter++;
			}
			if (spawnTimer <= 0) {
				handler.addObject(new EnemyBurst(-200, 200, 50, 50, 200, side[r.nextInt(4)], ID.EnemyBurst, handler));
				spawnTimer = 70+(levelsRemaining*10);
			}
			if (levelTimer == 0) {
				tempCounter = 0;
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				spawnTimer = 10;
				if (levelsRemaining == 1) {
					levelNumber = 101;
				} else {// not time for the boss, just go to the next level
					levelsRemaining--;
					levelNumber = levels.get(this.rand());// set levelNumber to whatever index was randomly selected
				}
			}
		}
		else if (levelNumber == 101) {// arbitrary number for the boss
			if(tempCounter < 1) {
				hud.setLevel(101);
				player.resetCount();
				handler.addObject(new RollBoss1(r.nextInt(Game.WIDTH-300), r.nextInt(Game.HEIGHT-300), r.nextInt(5)+10, r.nextInt(5)+10, ID.RollBoss1));
				handler.addObject(new RollBoss2(r.nextInt(Game.WIDTH-300), r.nextInt(Game.HEIGHT-300), r.nextInt(5)+10, r.nextInt(5)+10, ID.RollBoss2));
				tempCounter++;
			} else if (tempCounter >= 1) {
				for (int i = 0; i < handler.object.size(); i++) {
					GameObject tempObject = handler.object.get(i);
					if (tempObject.getId() == ID.RollBoss1) {
						if (tempObject.getHealth() <= 0) {
							this.hud.setLevel(16);
							handler.clearEnemies();
							player.resetCount();
							Spawn1to5.LEVEL_SET++;
							game.gameState = STATE.Upgrade;
						}
					}
				}
			}
		}
	}


	public void skipLevel() {
		if (levelsRemaining == 1) {
			this.hud.setLevel(16);
			handler.clearEnemies();
			player.resetCount();
			Spawn1to5.LEVEL_SET++;
			game.gameState = STATE.Upgrade;
		} else if (levelsRemaining > 1) {
			spawnTimer = 10;
			levelsRemaining--;
			tempCounter = 0;
			levelNumber = levels.get(this.rand());// set levelNumber to whatever index was randomly selected
		}
	}

	public void restart() {
		spawnTimer = 10;
		levelNumber = -5;
		tempCounter = 0;
		levelTimer = 150;
		levelsRemaining = 5;
	}
	
	public int rand() {
		int randomInt = r.nextInt(differentEnemies);
		if (randIntHolder == randomInt) {
			randIntHolder = r.nextInt(differentEnemies);
			while (randIntHolder == randomInt) {
				randIntHolder = r.nextInt(differentEnemies);
			}
			return randIntHolder;
		}
		else {
			randIntHolder = randomInt;
			return randIntHolder;
		}
	}

}
