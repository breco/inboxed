package com.inboxed.setuphud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.inboxed.main.MainGame;
import com.inboxed.screens.ClassicSetup;

public class PlayerNumber {
	public Array<Sprite> numbers;
	public Sprite left,right,ok;
	public int current;
	public BitmapFont font;
	public boolean showing;
	public PlayerNumber(){
		font =  new BitmapFont();
		font.setColor(Color.BLACK);
		font.getData().setScale(2);
		numbers = new Array<Sprite>();
		showing = true;
		for(int i = 1; i<=4; i++){
			numbers.add(new Sprite(new Texture(Gdx.files.internal("numbers/"+i+".png"))));
		}
		for(Sprite sprite : numbers){
			sprite.setBounds(MainGame.width/2-10,MainGame.height/2,MainGame.SPRITESIZE,MainGame.SPRITESIZE);
		}
		left = new Sprite(new Texture(Gdx.files.internal("setupHud/DirrL.png")));
		right = new Sprite(new Texture(Gdx.files.internal("setupHud/DirrR.png"))); 
		ok = new Sprite(new Texture(Gdx.files.internal("setupHud/ok.png")));
		left.setBounds(MainGame.width/2 - MainGame.SPRITESIZE*3,MainGame.height/2,MainGame.SPRITESIZE,MainGame.SPRITESIZE);
		right.setBounds(MainGame.width/2 + MainGame.SPRITESIZE*3,MainGame.height/2,MainGame.SPRITESIZE,MainGame.SPRITESIZE);
		ok.setBounds(MainGame.width/2, 10, MainGame.SPRITESIZE, MainGame.SPRITESIZE);
		current = 0;
	}
	public void draw(SpriteBatch batch){
		numbers.get(current).draw(batch);
		left.draw(batch);
		right.draw(batch);
		font.draw(batch, "PLAYER SETTINGS", MainGame.width/2-100,MainGame.height-40);
		ok.draw(batch);
	}
	public void input(Vector3 vec){
		if(left.getBoundingRectangle().contains(vec.x,vec.y)){
			current--;
			if(current < 0) current = 0;
		}
		else if(right.getBoundingRectangle().contains(vec.x,vec.y)){
			current++;
			if(current > numbers.size-1) current = numbers.size -1;
		}
		else if(ok.getBoundingRectangle().contains(vec.x,vec.y)){
			showing = false;
			ClassicSetup.characterSelect.players = current + 1;
			ClassicSetup.characterSelect.showing = true;
		}
	}
}
