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
	TextureRegion stand;

    static final int TILEWIDTH = 16;
    static final int TILEHEIGHT = 16;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		Texture tiles = new Texture("tiles.png");
		TextureRegion[][] grid = TextureRegion.split(tiles, TILEWIDTH, TILEHEIGHT);
		TextureRegion down = grid[6][0];
		TextureRegion up = grid[6][1];
		TextureRegion right = grid[6][3];
		TextureRegion left = new TextureRegion(right);
		left.flip(true, false);
		//stand = tiles[0][0];
	}

	@Override
	public void render () {
	    move();
        //x++;
        //y++;
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, x, y);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	void move() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            y++;
            System.out.println("Dpad Up pressed");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            y--;
            System.out.println("Dpad Down pressed");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x++;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            x--;
    }
}
