package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import static java.awt.image.ImageObserver.HEIGHT;
import static java.awt.image.ImageObserver.WIDTH;


public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	TextureRegion stand;
	boolean faceRight = true;
	boolean faceUp = true;
	float x, y, xv, yv;
//	Animation<TextureRegion> walk;
	//I did not need "<TextureRegion> above.... "Animation walk;" worked...
	Animation walk;
	Animation walkUp;
	Animation walkDown;

	float time;

	static final int WIDTH = 18;
	static final int HEIGHT = 26;
	static final int DRAW_WIDTH = WIDTH*3;
	static final int DRAW_HEIGHT = HEIGHT*3;
	static final float MAX_VELOCITY = 200;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("tiles.png");
//		Texture sheet = new Texture("tiles.png");
//		TextureRegion[][] tiles = TextureRegion.split(sheet, WIDTH, HEIGHT);

		Texture tiles = new Texture("tiles.png");
		TextureRegion[][] grid = TextureRegion.split(tiles, 16, 16);
		TextureRegion down = grid[6][0];
		TextureRegion up = grid[6][1];
		TextureRegion right = grid[6][3];
		TextureRegion left = new TextureRegion(right);
		left.flip(true, false);


//		stand = tiles[0][0];
		stand = grid[6][2];
//		walk = new Animation(0.2f, tiles[0][2], tiles[0][3], tiles[0][4]);
		walk = new Animation(0.1f, grid[6][2], grid [6][3]);
//		walkUp = new Animation(0.1f, grid [6][1], grid[7][1]);



	}

	@Override
	public void render () {
		time += Gdx.graphics.getDeltaTime();
		move();
		TextureRegion img;
		if (xv != 0) {
			img = walk.getKeyFrame(time, true);
		}
		else  {
			img = stand;
		}
//		if (yv !=0){
//			img = walkUp.getKeyFrame(time, true);
//		}
//		else {
//			img = stand;
//		}

		Gdx.gl.glClearColor(0.5f, 0.5f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
//		batch.draw(img, 0, 0);
//		batch.draw(img, x, y);
		if (faceRight) {
			batch.draw(img, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		}
		else {
			batch.draw(img, x + DRAW_WIDTH, y, DRAW_WIDTH * -1, DRAW_HEIGHT);
			//when you multiply it by -1, it reverses the image.......
		}
		batch.end();

//		batch.begin();
//		if (faceUp) {
//			batch.draw(img, x, y, DRAW_WIDTH, DRAW_HEIGHT);
//		}
//		else {
//			batch.draw(img, x + DRAW_WIDTH, y, DRAW_WIDTH * -1, DRAW_HEIGHT);
//		}
//		batch.end();
	}

	void move(){
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			yv = MAX_VELOCITY;
			faceUp = true;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			yv = MAX_VELOCITY * -1;
			faceUp = false;

		}
//		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
//			xv = MAX_VELOCITY;
//		}
//		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
//			xv = MAX_VELOCITY * -1;
//		}

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

		if (x > 620){
			x = 0;
		}
		if (x < 0){
			x = 620;
		}

		if (y > 475){
			y = 0;
		}
		if (y < 0){
			y = 475;
		}
//
//		yv = decelerate(yv);
//		xv = decelerate(xv);
	}

	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
