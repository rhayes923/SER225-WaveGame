package mainGame.enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

import mainGame.*;
import mainGame.gfx.*;

/**
 * A type of enemy in the game
 * 
 * @author Eamon Duffy 10/18/17
 *
 */

public class EnemyExpand extends GameObject {

	private Handler handler;
	private int sizeX;
	private int sizeY;
	private int timer;
	@SuppressWarnings("unused")
	private GameObject player;
	private Image explosion = Toolkit.getDefaultToolkit().getImage(Game.class.getResource("images/Explosion.gif"));

	public EnemyExpand(double x, double y, int sizeX, int sizeY, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		this.velX = 0;
		this.velY = 0;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.timer = 3;

		for (int i = 0; i < handler.object.size(); i++) {
			if (handler.object.get(i).getId() == ID.Player)
				player = handler.object.get(i);
		}
	}

	@Override
	public void tick() {
		this.x += velX;
		this.y += velY;

		if (this.y <= 0 || this.y >= Game.HEIGHT - 40)
			velY *= -1;
		if (this.x <= 0 || this.x >= Game.WIDTH - 16)
			velX *= -1;

		timer--;
		if (timer <= 0) {
			updateEnemy();
			timer = 3;
		}

	}

	public void updateEnemy() {
		this.sizeX+=6;
		this.sizeY+=6;
		this.x-=3;
		this.y-=3;

		if (sizeX > 350) {
			this.handler.removeObject(this);
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(explosion, (int) x, (int) y, this.sizeX, this.sizeY, null);

	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, this.sizeX, this.sizeY);
	}

}
