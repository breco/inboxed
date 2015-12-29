package com.inboxed.helpers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.inboxed.characters.Character;
import com.inboxed.main.MainGame;
import com.inboxed.screens.ClassicMode;

public class ClassicHud {
	public Array<PlayerInfo> players;
	public int turnNumber;
	public boolean movingCam;
	public int camSpeed;
	public Character current, next;
	//TEST
	public ClassicHud(Array<Character> players, String[] names){
		this.players = new Array<PlayerInfo>();
		int place = 1;
		for(Character chr : players){
			this.players.add(new PlayerInfo(chr,names[place-1],place));
			place++;
		}
		turnNumber= 1;
		this.players.get(0).curr = true;
		movingCam = false;
		camSpeed = 8;
		current = players.get(0);
		if(players.size == 1) next = players.get(0);
		else next = players.get(1);
	}
	public void update(){
		if(movingCam) moveCam(ClassicMode.cam);
		for(PlayerInfo player : players){
			player.update();
		}
	}
	public void draw(SpriteBatch batch){
		for(PlayerInfo player : players){
			player.draw(batch);
		}
		
	}
	public void setTurn(OrthographicCamera cam){
		current = next;
		
		players.get(turnNumber-1).curr = false;
		if (turnNumber <= players.size -1){
			turnNumber++;
		}
		else{
			turnNumber = 1;
		}
		while(players.get(turnNumber-1).chr.lose){
			turnNumber++;
			if(turnNumber > players.size){
				turnNumber = 1;
			} 
		}
		players.get(turnNumber-1).curr = true;
	}
	public void setCam(OrthographicCamera cam){
		cam.position.x = players.get(turnNumber-1).chr.sprite.getX() + MainGame.SPRITESIZE/2;
		cam.position.y = players.get(turnNumber-1).chr.sprite.getY() + MainGame.SPRITESIZE/2;
	}
	public void setNext(){
		if(turnNumber <=players.size-1) next = players.get(turnNumber).chr;
		else next = players.get(0).chr;
		int number = turnNumber;
		while(next.lose){
			number++;
			
			if(number >= players.size){
				number = 0;
			}
			next = players.get(number).chr; 
		}
		
	}
	public void startMoveCam(){
		movingCam = true;
		setNext();
	}
	public void moveCam(OrthographicCamera cam){
		boolean stopX = false, stopY = false;
		//if(current.sprite.getX() > next.sprite.getX()){ //mover camara hacia izq
		if(ClassicMode.cam.position.x > next.sprite.getX()){ //mover camara hacia izq
			if(cam.position.x <= next.sprite.getX()+MainGame.SPRITESIZE/2) stopX = true;
			else cam.translate(-camSpeed,0);
		}
		else if(ClassicMode.cam.position.x < next.sprite.getX()){ //mover camara hacia der
			if(cam.position.x >= next.sprite.getX()+MainGame.SPRITESIZE/2) stopX = true;
			else cam.translate(camSpeed,0);
		}
		else{
			stopX = true;
		}
		if(ClassicMode.cam.position.y > next.sprite.getY()){ //mover camara hacia abajo
			if(cam.position.y <= next.sprite.getY() + MainGame.SPRITESIZE/2) stopY = true;
			else cam.translate(0,-camSpeed);
		}
		else if(ClassicMode.cam.position.y < next.sprite.getY()){ //mover camara hacia arriba
			if(cam.position.y >= next.sprite.getY() + MainGame.SPRITESIZE/2) stopY = true;
			else cam.translate(0,camSpeed);
		}
		else{
			stopY = true;
		}
		if(stopX & stopY){
			movingCam = false;
			setTurn(cam);
		}
	}
}
