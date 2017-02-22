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
	float x, y;
	TextureRegion upish, downish, rightish, leftish;
	boolean faceRight, faceLeft;

    static final int TILEWIDTH = 16;
    static final int TILEHEIGHT = 16;

    static final int WIDTH = 18;
    static final int HEIGHT = 26;

    static final int DRAW_WIDTH = WIDTH*5;
    static final int DRAW_HEIGHT = HEIGHT*5;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg"); //tried to change badlogic.jpg -> tiles.png

		Texture tiles = new Texture("tiles.png");
		TextureRegion[][] grid = TextureRegion.split(tiles, TILEWIDTH, TILEHEIGHT);
		downish = grid[6][0];
        upish = grid[6][1];
        rightish = grid[6][2];
        leftish = grid[6][3];
		TextureRegion right = grid[6][3];
		TextureRegion left = new TextureRegion(right);
		left.flip(true, false);
	}

	@Override
	public void render () {
	    move();
        //x++; these two just increment the image indefinitely
        //y++;

        TextureRegion img;
        //use a while loop in place of 'y > 0', so that the character faces the screen UNLESS moving upward
        if (y > 0) {
            img = upish;
        }
        else {
            img = downish;
        }

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		//attempting to change character image to appropriate facing direction
		if (faceRight) {
		    batch.draw(img,x,y,DRAW_WIDTH,DRAW_HEIGHT);
        }
        else {
		    batch.draw(img,x + DRAW_WIDTH,y,DRAW_WIDTH*-1,DRAW_HEIGHT);
        }

		//batch.draw(img, x, y); // x, = start location
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	//gets character to move about the screen
	void move() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            y++;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            y--;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x++;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            x--;
    }
}
