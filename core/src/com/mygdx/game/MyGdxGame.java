package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyGdxGame extends ApplicationAdapter {

	SpriteBatch batch;
	TextureRegion down, up, right, left, leftWalk, upWalk, downWalk;
	Animation walkUp, walkDown, walkRight, walkLeft;
	float time;
	int direction;
	float x, y, xv, yv;

	static final int WIDTH = 18;
	static final int HEIGHT = 26;
	static final int DRAW_WIDTH = WIDTH*2;
	static final int DRAW_HEIGHT = HEIGHT*2;
	static final float MAX_VELOCITY = 50;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		Texture tiles = new Texture("tiles.png");
		TextureRegion[][] grid = TextureRegion.split(tiles, 16, 16);
		down = grid[6][0];
		up = grid[6][1];
		right = grid[6][2];
		left = new TextureRegion(right);
		left.flip(true, false);
		leftWalk = new TextureRegion(grid[6][3]);
		leftWalk.flip(true, false);
		upWalk = new TextureRegion(up);
		upWalk.flip(true, false);
		downWalk = new TextureRegion(down);
		downWalk.flip(true, false);
		walkUp = new Animation(0.3f, upWalk, up);
		walkDown = new Animation(0.3f, downWalk, grid[6][0]);
		walkRight = new Animation(0.3f, grid[6][3], grid[6][2]);
		walkLeft = new Animation(0.3f, left, leftWalk);
	}

	@Override
	public void render () {
		time += Gdx.graphics.getDeltaTime();
		move();

		TextureRegion img = right;
		switch(direction){
			case 0:
				img = up;
				if (yv != 0) {
					img = walkUp.getKeyFrame(time, true);
				}
				break;
			case 1:
				img = down;
				if (yv != 0) {
					img = walkDown.getKeyFrame(time, true);
				}
				break;
			case 2:
				img = right;
				if (xv != 0) {
					img = walkRight.getKeyFrame(time, true);
				}
				break;
			case 3:
				img = left;
				if (xv != 0) {
					img = walkLeft.getKeyFrame(time, true);
				}
				break;
		}
		Gdx.gl.glClearColor(1, 150, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
	float decelerate(float velocity) {
		float deceleration = 0.95f;
		velocity *= deceleration;
		if (Math.abs(velocity) < 15) {
			velocity = 0;
		}
		return velocity;
	}

	void move() {
		float speed = 0;
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
			speed=100;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			yv = MAX_VELOCITY;
			yv+=speed;
			direction = 0;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			yv = MAX_VELOCITY * -1;
			yv+=speed*-1;
			direction = 1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			xv = MAX_VELOCITY;
			xv+=speed;
			direction = 2;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			xv = MAX_VELOCITY * -1;
			xv+=speed*-1;
			direction = 3;
		}

		y += yv * Gdx.graphics.getDeltaTime();
		x += xv * Gdx.graphics.getDeltaTime();

		yv = decelerate(yv);
		xv = decelerate(xv);
	}
}
