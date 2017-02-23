package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	float x, y, zv;
	char directionality;
	TextureRegion upish, downish, rightish, leftish;
	boolean faceRight = true;

    static final int TILEWIDTH = 16;
    static final int TILEHEIGHT = 16;

    static final int WIDTH = 18;
    static final int HEIGHT = 26;

    static final int DRAW_WIDTH = WIDTH*5;
    static final int DRAW_HEIGHT = HEIGHT*5;

    static final float MAX_VELOCITY = 500;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		Texture tiles = new Texture("tiles.png");
		TextureRegion[][] grid = TextureRegion.split(tiles, TILEWIDTH, TILEHEIGHT);
		downish = grid[6][0];
        upish = grid[6][1];
        rightish = grid[6][3];
		TextureRegion right = grid[6][3];
        leftish = new TextureRegion(right);
		leftish.flip(true, false);
	}

	@Override
	public void render () {
	    move();

        TextureRegion img;

        switch (directionality) {

            case 'u':
                img = upish;
                break;
            case 'd':
                img = downish;
                break;
            case 'r':
                img = rightish;
                break;
            case 'l':
                img = leftish;
                break;
            default:
                img = downish;
                break;
        }

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
        if (faceRight) {
            batch.draw(img, x, y, DRAW_WIDTH, DRAW_HEIGHT);
        }
        else {
            batch.draw(img, x + DRAW_WIDTH, y, DRAW_WIDTH * -1, DRAW_HEIGHT);
        }
        batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	//gets character to move about the screen
	void move() {
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            //rate = *10;
        }
	    if (Gdx.input.isKeyPressed(Input.Keys.UP) /*&& Gdx.input.isKeyPressed(Input.Keys.SPACE)*/) {
            y++;
            directionality = 'u';
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
             y--;
            directionality = 'd';
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x++;
            directionality = 'r';
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x--;
            directionality = 'l';
        }
    }
}
