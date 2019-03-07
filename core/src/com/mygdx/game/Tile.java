package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Tile {
	
	public int value() {      //int something = biard[18].valueOf()
		return this.value;
	}
	
	public void setValue(int n) {
		if (n < -1 || 9 < n) return; 
		value = n;
	}
	
	public void nakedify() { naked = true; }

	public boolean isNaked() { return naked; }

	public boolean isMine() {return value == -1;}
	
	public final static int tileSize = 40;
	private SpriteBatch batch;
	private Texture img;
	private int value;
	public boolean naked;
	public boolean flagged;
	//Board board;
	
	
	public Tile(SpriteBatch batch) {
		img = new Texture("Icons.jpg");
		value = 0;
		naked = true;
		this.batch = batch;
		//naked = true;
		naked = false;
		flagged = false;
	}
	
	public Tile(SpriteBatch batch, int value) {
		img = new Texture("Icons.jpg");
		this.value = value;
		this.batch = batch;
		this.value = value;
		//naked = true;
		naked = false;
		flagged = false;
	}
	
	/**
	 * does a really bad job of overloading ++
	 */
	public void plusPlus() {
		this.value += 1;
	}
	
	private int xCoord(int val) { //-1 = bomb, 9 = flag
		if (flagged) 
			return 128;
		if (naked == false) 
			return 0;
		if (val == 1 || val == 5) 
			return 0;
		if (val == 9 || val == 2 || val == 6)
			return 128;
		if (val == -1 || val == 3 || val == 7) 
			return 256;
		if (val == 0 || val == 4 || val == 8)
			return 384;
		System.err.println("soooo there wasn't a valid x....");
		return 0;
	}
	
	private int yCoord(int val) { //-1 = bomb, 9 = flag
		if (flagged)
			return 0;
		if (naked == false) 
			return 0; 
		if (val < 5 && 0 < val)
			return 128;
		if (val < 9 && 4 < val)
			return 256;
		else 
			return 0;
		//System.err.println("soooo there wasn't a valid y....");
	}
	
	public Texture getImg() {
		return img;
	}
	
	public void draw(int i, int j) {
		//batch.draw(img, x * tileSize, y * tileSize, tileSize, tileSize);
		// if (djClick.clicked(new InputEvent(), i, j))
		// 	naked = true;
		batch.draw(img, 
		   i * tileSize, j * tileSize, 
		   tileSize, tileSize, 
		   //256,256,
		   xCoord(value), yCoord(value),
		   128, 128,
		   false, false
		   );
		//System.out.println(xCoord(value) + "," + yCoord(value) + ": " + value);
	}










}