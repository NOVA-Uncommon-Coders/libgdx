package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;



public class MyGdxGame extends ApplicationAdapter {

	SpriteBatch batch;
	TextureRegion[][] grid;
	TextureRegion[][] grid2;
	TextureRegion zombieImg;
	TextureRegion down, up, right, left, leftWalk, upWalk, downWalk, tree, zombieDown, zombieUp, zombieRight, zombieLeft, zombieDownWalk, zombieUpWalk, zombieRightWalk, zombieLeftWalk, heart;
	Animation walkUp, walkDown, walkRight, walkLeft, zombieWalkUp, zombieWalkDown, zombieWalkRight, zombieWalkLeft;
	float time, heartCount;
	int direction;
	float x, y, xv, yv, zombieX, zombieY, zombieSX, zombieSY, treeX, treeY;
	boolean zombieStarted = false;

	static final int WIDTH = 18;
	static final int HEIGHT = 26;
	static final int DRAW_WIDTH = WIDTH*2;
	static final int DRAW_HEIGHT = HEIGHT*2;
	static final float MAX_VELOCITY = 50;

	private TiledMap tileMap;
	private OrthogonalTiledMapRenderer tileMapRenderer;
	private OrthographicCamera cam;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		Texture tiles = new Texture("tiles.png");
		grid = TextureRegion.split(tiles, 16, 16);
		grid2 = TextureRegion.split(tiles,9, 8);
		heart = new TextureRegion(grid[0][5]);
		tree = new TextureRegion(grid[1][0]);
		treeRandom();
		zombieRandom();
		makePlayer();
		makeZombie();
		heartCount = 8;
		cam = new OrthographicCamera();
		cam.setToOrtho(true, 400, 400);

		tileMap = new TmxMapLoader().load("level1.tmx");
		tileMapRenderer = new OrthogonalTiledMapRenderer(tileMap);
	}

	@Override
	public void render () {

		time += Gdx.graphics.getDeltaTime();
		move();
		zombieMove();

		TextureRegion img = right;
		switch(direction){
			case 0:
				img = up;
				if (yv != 0) {
					img = walkUp.getKeyFrame(time, true);
				}
				checkPosition();
				break;
			case 1:
				img = down;
				if (yv != 0) {
					img = walkDown.getKeyFrame(time, true);
				}
				checkPosition();
				break;
			case 2:
				img = right;
				if (xv != 0) {
					img = walkRight.getKeyFrame(time, true);
				}
				checkPosition();
				break;
			case 3:
				img = left;
				if (xv != 0) {
					img = walkLeft.getKeyFrame(time, true);
				}
				checkPosition();
				break;
		}
		Gdx.gl.glClearColor(1, 150, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		tileMapRenderer.setView(cam);
		tileMapRenderer.render();


		batch.begin();
		batch.draw(tree, treeX, treeY, DRAW_WIDTH, DRAW_HEIGHT);
		if (zombieStarted){
			batch.draw(zombieImg, zombieX,zombieY,DRAW_WIDTH, DRAW_HEIGHT);
		} else {
			zombieX = zombieSX;
			zombieY = zombieSY;
			batch.draw(zombieImg, zombieSX, zombieSY, DRAW_WIDTH, DRAW_HEIGHT);
		}
		heartDraw();
		batch.draw(img, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
	public void makeZombie(){
		zombieRight = new TextureRegion(grid[6][6]);
		zombieLeft = new TextureRegion(zombieRight);
		zombieLeft.flip(true, false);
		zombieUp = new TextureRegion(grid[6][4]);
		zombieDown = new TextureRegion(grid[6][5]);
		zombieUpWalk = new TextureRegion(zombieUp);
		zombieUpWalk.flip(true, false);
		zombieDownWalk = new TextureRegion(zombieDown);
		zombieDownWalk.flip(true, false);
		zombieLeftWalk = new TextureRegion(grid[6][7]);
		zombieLeftWalk.flip(true, false);
		zombieWalkUp = new Animation(0.3f, zombieUpWalk, zombieUp);
		zombieWalkDown = new Animation (0.3f, zombieDownWalk, zombieDown);
		zombieWalkRight = new Animation(0.3f, grid[6][7], grid[6][6]);
		zombieWalkLeft = new Animation(0.3f, zombieLeftWalk, zombieLeft);
	}
	public void makePlayer(){
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
			yv += speed;
			direction = 0;
			zombieStarted = true;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			yv = MAX_VELOCITY * -1;
			yv+=speed*-1;
			direction = 1;
			zombieStarted = true;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			xv = MAX_VELOCITY;
			xv+=speed;
			direction = 2;
			zombieStarted = true;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			xv = MAX_VELOCITY * -1;
			xv+=speed*-1;
			direction = 3;
			zombieStarted = true;
		}
		//checkPosition();

			y += yv * Gdx.graphics.getDeltaTime();
			x += xv * Gdx.graphics.getDeltaTime();

			yv = decelerate(yv);
			xv = decelerate(xv);
	}
	public boolean checkPosition(){
		if (y <= -10){
			y=460;
			yv=0;
			return true;
		} else if (y >= 460) {
			y=0;
			yv=0;
			return true;
		} else if (x <= -10) {
			x=630;
			xv=0;
			return true;
		} else if (x >=630) {
			x=0;
			xv=0;
			return true;
		} else {
			return false;
		}
	}public void zombieMove(){
		if (y > zombieY){
			zombieY+=1;
			zombieImg = zombieWalkUp.getKeyFrame(time, true);
		} else {
			zombieY-=1;
			zombieImg = zombieWalkDown.getKeyFrame(time, true);
		}
		if (x > zombieX){
			zombieX+=1;
			zombieImg = zombieWalkRight.getKeyFrame(time, true);
		} else {
			zombieX-=1;
			zombieImg = zombieWalkLeft.getKeyFrame(time, true);
		}
		if (Math.ceil(y) == Math.ceil(zombieY) && Math.ceil(x) == Math.ceil(zombieX)){
			System.out.println("Reached you!");
			heartCount--;
			zombieRandom();
			zombieStarted = false;
		}
	}
	public void treeRandom(){
		treeX = (float) (Math.random()*300) + 10;
		treeY = (float) (Math.random()*300) + 10;
	}
	public void zombieRandom(){
		zombieSX = (float) (Math.random()*350) + 10;
		zombieSY = (float) (Math.random()*350) + 10;
	}
	public void heartDraw(){
		int i = 1;
		float xPos = 20;
		if (heartCount == 0){
			System.out.println("Game OVER!");
			batch.end();
			dispose();
			System.exit(0);
		}
		while (i < heartCount){
			xPos+=20;
			batch.draw(heart,xPos, 420, DRAW_WIDTH, DRAW_HEIGHT);
			i++;
		}
	}
}
