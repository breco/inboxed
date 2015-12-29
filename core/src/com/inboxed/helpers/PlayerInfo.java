package com.inboxed.helpers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inboxed.characters.Character;
import com.inboxed.main.MainGame;


public class PlayerInfo {
	public String playerName;
	public Character chr;
	public Texture logo,lose,current, epBg;
	public Sprite logoSprite;
	public int pivotX, pivotY;
	public boolean curr;
	public BitmapFont font;
	public int base = 110, dif= 15; //base = 120
	public PlayerInfo(Character chr, String name, int place){
		this.chr = chr;
		current = new Texture("hud/current.png");
		logo = new Texture("characterLogos/"+chr.name+"_logo.png");
		lose = new Texture("hud/wrong.png");
		epBg = new Texture("hud/energyPointHud.png");
		logoSprite = new Sprite(logo);
		curr= false;
		playerName = name;
		pivotX = MainGame.width - 150;
		if(place == 1) pivotY = base*3+dif;
		else if(place == 2) pivotY = base*2+dif;
		else if(place == 3) pivotY = base*1+dif;
		else if(place == 4) pivotY = base*0+dif;
		logoSprite.setBounds(pivotX, pivotY, 100, 100);
		font = new BitmapFont();
		font.getData().setScale(2,2);
		font.setColor(Color.BLACK);
		
		
		
	}
	
	public void draw(SpriteBatch batch){
		logoSprite.draw(batch);
		if(curr) batch.draw(current,pivotX-40,pivotY-10);
		batch.draw(epBg, pivotX+60,pivotY+60);
		if(chr.energyPoints < 10)font.draw(batch,Integer.toString(chr.energyPoints),pivotX+80,pivotY+95);
		else font.draw(batch,Integer.toString(chr.energyPoints),pivotX+73,pivotY+95);
		if(chr.lose) batch.draw(lose,pivotX,pivotY);
		
	}
	public void update(){
		
	}

}
