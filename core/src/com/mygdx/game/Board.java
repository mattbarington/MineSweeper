package com.mygdx.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Music;


public class Board {
	private Tile[] board;
	private final int size;
	private final int mines;
	private final int x;
	public final int y;
	SpriteBatch batch;
	public boolean gameOver;
	private boolean gameON;
	int winHeight;
	Music theTUNESMAN;

	public void draw() {
		for (int i = 0; i < size; i++) {
			board[i].draw(i % x, i / x);
		}
		if (this.isWon()){
			if (winHeight > 300)
				winHeight -= 20;	
			batch.draw(new Texture("winner.png"),100,winHeight,800,500);
		}

		// for (Tile t : board) {
		// 	System.out.println(t.value());
		// }
	}

	public boolean isWon() {
		for (Tile t : board) {
			if (!t.isMine() && !t.naked)
				return false;
			if (t.isMine() && t.naked)
				return false;
		}
		return true;
	}

	//public int howManyDangersLeft(int falgs) {return mines - flags;}

	public int stillHidden() {
		int count = 0;
		for (Tile t : board) {
			if (t.isNaked())
				count ++;
		}
		return size - count;
	}

	// public Board(int x, int y, int mines, SpriteBatch batch) {
	// 	this.x = x;
	// 	this.y = y;
	// 	this.mines = mines;
	// 	this.size = x * y;
	// 	this.batch = batch;
	// 	board = new Tile[size];
	// 	List<Tile> shuffler = new ArrayList<Tile>();

	// 	for (int i = 0; i < mines; i++) {
	// 		shuffler.add(new Tile(batch, -1));  //adds mines to field
	// 	}

	// 	for (int i = mines; i < size; i++) {   //adds not mines to field
	// 		shuffler.add(new Tile(batch));
	// 	}
		
	// 	// batch.begin();
	// 	// for (int i = 0; i < size; i++) {
	// 	// 	shuffler.get(i).draw(i % x, i / x);
	// 	// }
		
	// 	Collections.shuffle(shuffler);


	// 	// while(!Gdx.input.justTouched()) {

	// 	// }

	// 	// int click = Gdx.input.getX() / Tile.tileSize + x*(((450 - Gdx.input.getY()) / Tile.tileSize) - 1);

	// 	// redistributeAbout(click, shuffler);
	// 	// System.err.printf("size:%d, shuflsize:%d\n", size, shuffler.size());
	// 	for (int i = 0; i < size; i++) {
	// 		board[i] = shuffler.get(i); //.setValue(shuffler.get(i).value());
	// 		//board[i].setValue(i % 9);
	// 	}

	// 	for (int i = 0; i < size; i++) {
	// 		if (!board[i].isMine())
	// 			mineCheck(i);
	// 	}
	// 	//reveal(click);
	// }

	private void firstClick(int click) {
		List<Tile> shuffler = new ArrayList<Tile>();

		for (int i = 0; i < mines; i++) {
			board[i].setValue(-1);
		}
		for (Tile t : board) {
			shuffler.add(t);
		}

		Collections.shuffle(shuffler);
		redistributeAbout(click, shuffler);
		System.err.printf("size:%d, shuflsize:%d\n", size, shuffler.size());
		for (int i = 0; i < size; i++) {
			board[i] = shuffler.get(i); //.setValue(shuffler.get(i).value());
			//board[i].setValue(i % 9);
		}

		for (int i = 0; i < size; i++) {
			if (!board[i].isMine())
				mineCheck(i);
		}
		reveal(click);
	}

	public Board(int x, int y, int mines, int clickX, int clickY, SpriteBatch batch) {
		this(x,y,mines,clickX + x*clickY,batch);
		gameON = true;
		//this(x,y,mines, clickX / Tile.tileSize + x*((clickY / Tile.tileSize) - 1), batch);
	}

	public Board(int x, int y, int mines, SpriteBatch batch) {
		this.x = x;
		this.y = y;
		this.mines = mines;
		this.size = x * y;
		this.batch = batch;
		board = new Tile[size];
		gameON = false;
		winHeight = 900;
		theTUNESMAN = Gdx.audio.newMusic(Gdx.files.internal("Rocky.mp3"));
		theTUNESMAN.setLooping(true);
		theTUNESMAN.play();



		for (int i = 0; i < size; i++) {
			board[i] = new Tile(batch);
		}

		// List<Tile> shuffler = new ArrayList<Tile>();

		// for (int i = 0; i < mines; i++) {
		// 	shuffler.add(new Tile(batch, -1));  //adds mines to field
		// 	//System.out.println("added 1 mine");
		// }

		// for (int i = mines; i < size; i++) {   //adds not mines to field
		// 	shuffler.add(new Tile(batch));
		// 	//System.out.println("added 1 not mine");
		// }

		// Collections.shuffle(shuffler);
		
		// for (int i = 0; i < size; i++) {
		// 	board[i] = shuffler.get(i); //.setValue(shuffler.get(i).value());
		// 	//board[i].setValue(i % 9);
		// }

		// for (int i = 0; i < size; i++) {
		// 	if (!board[i].isMine())
		// 		mineCheck(i);
		// }
	}

	private Board(int x, int y, int mines, int click, SpriteBatch batch) {
		this.x = x;
		this.y = y;
		this.mines = mines;
		this.size = x * y;
		this.batch = batch;
		gameON = true;
		board = new Tile[size];
		List<Tile> shuffler = new ArrayList<Tile>();

		for (int i = 0; i < mines; i++) {
			shuffler.add(new Tile(batch, -1));  //adds mines to field
			//System.out.println("added 1 mine");
		}

		for (int i = mines; i < size; i++) {   //adds not mines to field
			shuffler.add(new Tile(batch));
			//System.out.println("added 1 not mine");
		}

		Collections.shuffle(shuffler);
		redistributeAbout(click, shuffler);
		System.err.printf("size:%d, shuflsize:%d\n", size, shuffler.size());
		for (int i = 0; i < size; i++) {
			board[i] = shuffler.get(i); //.setValue(shuffler.get(i).value());
			//board[i].setValue(i % 9);
		}

		for (int i = 0; i < size; i++) {
			if (!board[i].isMine())
				mineCheck(i);
		}
		reveal(click);
	}

	private List<Integer> getAdjacentPositions(int index) {
		if (index < 0 || index > size) 
			throw new IndexOutOfBoundsException("dont be tryin insert that there!");
		List<Integer> clique = new ArrayList<Integer>(); //stores the indexes of tiles surrounding click
		clique.add(index);

		//top left, if not in top row or left column
		if (index >= x && index % x != 0) clique.add(index - 1 - x);
		//top, if not in top row
		if ( index >= x)clique.add(index-x);
		//top if not, not in top row or right column
		if ( index >= x && index % x + 1 != x) clique.add(index - x + 1);
		//left, if not in left column
		if (index % x != 0)clique.add(index-1);
		//bottom left, if not in left column or bottom row
		if (index % x != 0 && index + x < size) clique.add(index + x - 1);
		//bottom, if not in bottom row
		if (index + x < size) clique.add(index + x);
		//bottom right, if not in bottom row or right column
		if (index + x < size && index % x + 1 != x) clique.add(index + x + 1);
		//right side, if not in right row
		if (index % x + 1 != x) clique.add(index + 1);

		return clique;
	}

		/**
	 * removes any mines around a given clickPoint
	 * @param index the index to check 
	 * @param objects the array to check over
	 */
	private void redistributeAbout(int click) {
		List<Integer> clique = new ArrayList<Integer>(getAdjacentPositions(click)); //stores the indexes of tiles surrounding click
		clique.add(click); //center aka @ click
		
		for (int i : clique)  {
			//System.err.print(i + " ");
			redistribute(i, clique);
		}
	}

	/**
	 * takes a mine from a given point and moves it away from the click point
	 * @param index the index to check 
	 * @param objects the array to check over
	 */
	private void redistribute(int index, List<Integer> clickSquad) {
		if (index < 0 || index > size) return;
		if (!board[index].isMine()) return;
		int switchPoint = (int) (Math.random() * size);
		while (board[switchPoint].isMine() || clickSquad.contains(switchPoint)) {
			if (++switchPoint >= size ) switchPoint = 0;
		}
		board[index].setValue(0);
		board[switchPoint].setValue(-1);
	}



	private void redistributeAbout(int click, List<Tile> board) {
		List<Integer> clique = getAdjacentPositions(click);
		clique.add(click);
		for (int possibleMine : clique)
			redistribute(possibleMine, clique, board);
	}

	private void redistribute(int index, List<Integer> clickSquad, List<Tile> array) {
		if (array.get(index).value() != -1) 
			return;
		int switchPoint = (int) (Math.random() * size);
		while (array.get(switchPoint).value() == -1 || clickSquad.contains(switchPoint)) {
			if (++ switchPoint >= size ) switchPoint = 0;
		}
		array.get(index).setValue(0);
		array.get(switchPoint).setValue(-1);
	}

	private void mineCheck(int index) {
		if (board[index].value() == -1) 
			return;

		List<Integer> neighbors = getAdjacentPositions(index);
		for (int neighbor : neighbors) {
			if (board[neighbor].value() == -1) 
				board[index].plusPlus();
		}
	}

	public boolean reveal(int clickX, int clickY) {

		return reveal (clickX + x*clickY);
	}

	private boolean reveal(int click) {
		if (click < 0 || size <= click) return true;
		if (board[click].isMine())
			return false;
		if (board[click].isNaked()) return true;;
		if (board[click].value() == 0) {
			board[click].nakedify();
			for (int neighbor : getAdjacentPositions(click))
				reveal(neighbor);
			return true;
		}
		board[click].nakedify();
		return true;
	}

	
	public void spreadMe( int square) {
		//int square = mouseX / Tile.tileSize + x*((mouseY / Tile.tileSize) - 1);
		System.out.println("spreadME");

		for (int t : getAdjacentPositions(square)) {
			if (board[t].flagged && !board[t].isMine()) {   //shouldn't have swept bruh smh :()
				gameOver = true;
				return;
			}
			else if (board[t].isMine() && !board[t].flagged)
				return;
			else 
				reveal(t);
		}
		// for (int i : getAdjacentPositions(square))
		// 	reveal(i);

	}

	public void touchMeSoftly(int mouseX, int mouseY, boolean flag) {
		//mouseY = 450 - mouseY;
		System.out.printf("x:%d, y:%d\n",mouseX / Tile.tileSize, mouseY / Tile.tileSize);
		int square = mouseX / Tile.tileSize + x*((mouseY / Tile.tileSize) - 1);
		if (square < 0 || size < square) return;
		Tile selected = board[square];
		if (!gameON) {  //first clicky
			firstClick(square);
			gameON = true;
		}

		// if (!flag && selected.naked) { //for the spreading of things
		// 	sweepOut(square);
		// 	// for (int t : getAdjacentPositions(square)) {
		// 	// 	if (board[t].flagged && board[t]].isMine()) {

		// 	// 		if (!board[t].isMine()) {
		// 	// 			reveal(t);
		// 	// 		}
		// 	// 	}
		// 	// }
		// }

		if (!flag && selected.naked) {
			spreadMe(square);
		}

		if (flag && selected.naked) return;  //don't do anything to already clicked stuff
		
		if (flag) {   //flags a thing
			selected.flagged = selected.flagged ? false : true;
			return;
		}
		
		if (!reveal(square)) {
			//show mines;
			for (Tile t : board) {
				if (t.isMine())
					t.nakedify();
			}
			gameOver = true;
		}
	}







}