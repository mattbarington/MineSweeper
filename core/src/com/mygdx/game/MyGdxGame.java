package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Scanner;



public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	//Texture img;
	//Tile[][] board;
	Board board;

	
	final int tileSize = 30;
	boolean first = false;

	final int width = 10;
	final int height = 10;
	@Override
	public void create () {
		batch = new SpriteBatch();
		//		board = new Board(15, 15, 40, batch);
		
		try {
			Scanner cin = new Scanner(System.in);
			System.out.print("x y mines:");
			board = new Board(cin.nextInt(),cin.nextInt(),cin.nextInt(), batch);
		} catch (Exception e) {
			System.err.println(e);
		}
		
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		board.draw();
		if (!board.gameOver){
			if (Gdx.input.justTouched()){
				if (Gdx.input.isButtonPressed(0)){
					System.out.printf("x:%d, y:%d\n", Gdx.input.getX(), 940 - Gdx.input.getY());
					board.touchMeSoftly(Gdx.input.getX(), 940 - Gdx.input.getY(), false);
				}
				if (Gdx.input.isButtonPressed(1)){
					System.out.printf("x:%d, y:%d\n", Gdx.input.getX(), 940 - Gdx.input.getY());
					board.touchMeSoftly(Gdx.input.getX(), 940 - Gdx.input.getY(), true);
				}
				// if (Gdx.input.isKeyPressed(47)){
				// 	System.out.printf("x:%d, y:%d\n", Gdx.input.getX(), 940 - Gdx.input.getY());
				// 	board.spreadMe(Gdx.input.getX(), 940 - Gdx.input.getY());
				// }
			}
			// for (int i = 0; i < width; i++) {
			// 	for (int j = 0; j < height; j++) {
			// 		board[i][j].draw(i, j);  //Tile[i][j].img()
			// 	}
			// }
			//batch.draw(img, 0, 0);
		}
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		//img.dispose();
	}
}
