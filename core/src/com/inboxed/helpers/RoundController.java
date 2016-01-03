package com.inboxed.helpers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.inboxed.characters.Character;
import com.inboxed.characters.Chilly;
import com.inboxed.characters.Rocky;
import com.inboxed.characters.Simirror;
import com.inboxed.screens.ClassicMode;


public class RoundController {
	public int roundNumber;
	public Array<Character> players;
	public TurnController turn;
	public int turnNumber;
	//TEST
	public int playersPlaying;
	public ClassicHud hud;
	public RoundController(){
		players = new Array<Character>();
		roundNumber = 1;
		turnNumber =1;
	}
	public void update(){
		turn.update();
		if(turn.finish) nextTurn();
		for(Character player : players){
			player.update();		
		}

	}
	public void addHUD(ClassicHud hud){
		this.hud = hud;
	}
	public void addPlayer(String name, int number){
		int x = 0, y = 0;
		if(number == 1){
			x = 0;
			y = 10;
		}
		else if(number == 2){
			x = 10;
			y = 10;
		}
		else if(number == 3){
			x = 10;
			y = 0;
		}
		if(name.equals("rocky")){
			players.add(new Rocky(x,y,"rocky"));
		}
		else if(name.equals("simirror")){
			players.add(new Simirror(x,y,"simirror"));
		}
		else if(name.equals("chilly")){
			players.add(new Chilly(x,y,"chilly"));
		}
		else{
			players.add(new Rocky(x,y,name));
		}
	}
	public void nextTurn(){
		turnNumber++;
		if(turnNumber > players.size){
			turnNumber =1;
			roundNumber++;
			ClassicMode.stage.effect();
		}
		setTurn(true);
		
	}
	public void setTurn(boolean changeHud){
		turn = new TurnController(players.get(turnNumber-1));
		while(turn.current.lose){
			turnNumber++;
			if(turnNumber > players.size){
				turnNumber = 1;
				roundNumber++;
				ClassicMode.stage.effect();
			}
			turn = new TurnController(players.get(turnNumber-1));
		}
		if (changeHud)
			hud.startMoveCam();
	}
	public void input(OrthographicCamera cam){
		turn.input();
	}
	public void draw(SpriteBatch batch){
		turn.draw(batch);
		for(Character player : players){
			player.draw(batch);
		}
	}
	public void drawHUD(SpriteBatch batch){
		turn.drawHUD(batch);
	}
}
