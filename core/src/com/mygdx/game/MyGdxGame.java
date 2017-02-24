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
	Texture m;
	TextureRegion stand, flip;
	float x, y, xv, yv;
	boolean canFlip, faceRight = true;
	Animation walk;
	float time;
	static final float MAX_VELOCITY = 500;
	static final int WIDTH = 16;
	static final int HEIGHT = 16;
	static final int DRAW_WIDTH = WIDTH * 5;
	static final int DRAW_HEIGHT = HEIGHT * 5;
	static final float MAX_JUMP_VELOCITY = 2000;
	static final int GRAVITY = -50;

	TextureRegion down, up, right, left;



	@Override
	public void create() {
		batch = new SpriteBatch();
		m = new Texture("tiles.png");
		Texture tiles = new Texture("tiles.png");
		TextureRegion[][] grid = TextureRegion.split(tiles, 16, 16);
		down = grid[6][0];
		up = grid[6][1];
		right = grid[6][3];
		left = new TextureRegion(right);
		left.flip(true, false);
		walk = new Animation(0.2f, grid [6][0], grid[6][1], grid[6][3]);
	}

	@Override
	public void render() {

		time += Gdx.graphics.getDeltaTime();

		move();

		TextureRegion img;

//		if (y > 0) {
//			img = flip;
//		} else if (xv != 0) {
//			img = walk.getKeyFrame(time, true);
//		} else {
//			img = stand;

			Gdx.gl.glClearColor(0, 2, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.begin();
			if (faceRight) {
				batch.draw(right, x, y, DRAW_WIDTH, DRAW_HEIGHT);
			} else {
				batch.draw(left, y, x,  DRAW_WIDTH  , DRAW_HEIGHT);
			}
			batch.end();
		}

		@Override
		public void dispose () {
			batch.dispose();
			m.dispose();

		}
	float decelerate(float velocity) {
		float deceleration = 0.95f; // the closer to 1, the slower the deceleration
		velocity *= deceleration;
		if (Math.abs(velocity) < 1) {
			velocity = 0;
		}
		return velocity;


	}

	void move() {
		if (Gdx.input.isKeyPressed(Input.Keys.UP) && canFlip) {
			xv = MAX_JUMP_VELOCITY;
			canFlip = true;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {

		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			xv = MAX_VELOCITY;
			faceRight = true;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			xv = MAX_VELOCITY * -1;
			faceRight = false;
		}



		y += yv * Gdx.graphics.getDeltaTime();
		x += xv * Gdx.graphics.getDeltaTime();

		if (y < 0) {
			y = 0;
			canFlip = false;
		}

		yv = decelerate(yv);
		xv = decelerate(xv);
	}
}
