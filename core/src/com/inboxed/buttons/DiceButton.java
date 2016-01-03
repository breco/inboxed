package com.inboxed.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

public class DiceButton {
	public Texture image;
	public Sprite sprite;
	public int value;
	public Random rand;
	public String name;
	public boolean rolled;
	public Sprite cross;
	public DiceButton(String name){
		this.name = name;
		image = new Texture(Gdx.files.internal("dices/"+name+"-1.png"));
		cross = new Sprite(new Texture(Gdx.files.internal("hud/wrong.png")));
		cross.setBounds(10,10,65,65);
		sprite = new Sprite(image);
		sprite.setBounds(10,10, 65, 65);
		value = 1;
		rand = new Random();
		rolled = false;
	}
	public void draw(SpriteBatch batch){
		sprite.draw(batch);
		if(rolled) cross.draw(batch);
	}
	public void update(){

	}
	public void roll(){
		value = rand.nextInt(6)+1;
		sprite.setRegion(new Texture(Gdx.files.internal("dices/"+name+"-"+value+".png")));
		rolled = true;
	}
	public boolean touched(Vector3 touchPos){
		if(sprite.getBoundingRectangle().contains(touchPos.x,touchPos.y)){
			return true;
		}
		return false;
	}
	public int getValue(){
		return value;
	}

}
