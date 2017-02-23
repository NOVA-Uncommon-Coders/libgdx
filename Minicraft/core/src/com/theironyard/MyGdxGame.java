package com.theironyard;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.badlogic.gdx.utils.Align.left;
import static com.badlogic.gdx.utils.Align.right;
import static javax.swing.SpringLayout.HEIGHT;
import static javax.swing.SpringLayout.WIDTH;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	TextureRegion stand;
	float x, y, xv, yv;
	static final float MAX_VELOCITY = 500;
	static final int GRAVITY = -50;

	static final int WIDTH = 18;
	static final int HEIGHT = 26;

	static final int DRAW_WIDTH = WIDTH*3;
	static final int DRAW_HEIGHT = HEIGHT*3;



	boolean faceUp = true;
	boolean faceDown = true;
	boolean faceRight = true;
	boolean faceLeft = true;

	TextureRegion right;
	TextureRegion left;
	TextureRegion up;
	TextureRegion down;

	boolean canBlock;


	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("tiles.png");
		Texture tiles = new Texture("tiles.png");
		TextureRegion[][] grid = TextureRegion.split(tiles, 16, 16);
		stand = grid[0][0];


//		Texture tiles = new Texture("tiles.png");
//		TextureRegion[][] grid = TextureRegion.split(tiles, 16, 16);
		down = grid[6][0];
		up = grid[6][1];
		right = grid[6][3];
		left = new TextureRegion(right);
		left.flip(true, false);
	}

	@Override
	public void render () {
		move();

		Gdx.gl.glClearColor(0.5f, 0.5f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		// batch.draw(stand, x, y, DRAW_WIDTH, DRAW_HEIGHT);

		if (faceRight) {
			batch.draw(right, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		}
		else if (faceLeft) {
			batch.draw(left, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		}
		else if (faceUp) {
			batch.draw(up, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		}
		else  {
			batch.draw(down, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		}
		batch.end();

	}
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	void move() {
//		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
//			y++;
//		}
//		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
//			y--;
//		}
//		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
//			x++;
//		}
//		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
//			x--;
//		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			yv = MAX_VELOCITY;
			faceRight = false;
			faceLeft = false;
			faceUp = true;
			faceDown = false;

		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			yv = MAX_VELOCITY * -1;
			faceRight = false;
			faceLeft = false;
			faceUp = false;
			faceDown = true;
		}
//		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
//			xv = MAX_VELOCITY;
//		}
//		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
//			xv = MAX_VELOCITY * -1;
//		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && canBlock) {
			xv = MAX_VELOCITY;
			canBlock = true;
			faceRight = true;
			faceLeft = false;
			faceUp = false;
			faceDown = false;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			xv = MAX_VELOCITY * -1;
			faceRight = false;
			faceLeft = true;
			faceUp = false;
			faceDown = false;
		}

		xv += GRAVITY;

		y += yv * Gdx.graphics.getDeltaTime();
		x += xv * Gdx.graphics.getDeltaTime();

		if (x < 0) {
			x = 0;
			canBlock = true;
		}


		yv = decelerate(yv);
		xv = decelerate(xv);
	}


	float decelerate(float velocity) {
		float deceleration = 0.95f; // the closer to 1, the slower the deceleration
		velocity *= deceleration;
		if (Math.abs(velocity) < 1) {
			velocity = 0;
		}
		return velocity;
	}
}
