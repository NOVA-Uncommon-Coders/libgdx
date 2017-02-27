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
    Texture img;
    float x = 0, y = 0;
    TextureRegion up, down, left, right;
    Animation walk;
    Texture tiles;
    TextureRegion character;
    float width;
    float height;
    int i = 0;
    int j = 0;


    @Override
    public void create() {
        batch = new SpriteBatch();
        tiles = new Texture("tiles.png");
        TextureRegion[][] grid = TextureRegion.split(tiles, 16, 16);
        down = grid[6][0];
        up = grid[6][1];
        right = grid[6][3];
        left = new TextureRegion(right);
        character = up;
        left.flip(true, false);
        float width = Gdx.graphics.getWidth()-1;
        System.out.println("Width " + width);
        float height = Gdx.graphics.getHeight()-1;
        System.out.println("Height: " + height );
        //
//        stand = grid[0][0]; //setting an image frame at this location
//        jump = grid[0][1]; //setting the image frame at this location
//        walk = new Animation(0.9f, grid[0][2], grid[0][3], grid[0][4]); //I need to rearrange the x-y coords here

    }

    @Override
    public void render() {
        move();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(character, x, y);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
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
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                y++;
                character = up;
                if (y > height + 479) {
                    y = 0;
                }

            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                y--;
                character = down;
                if (y < -1) {
                    y = 479;
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                x++;
                character = right;
                if (x > width + 639) {
                    x = 0;
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                x--;
                character = left;
                if (x < -1)
                    x = 639;

            }
        }
    }







