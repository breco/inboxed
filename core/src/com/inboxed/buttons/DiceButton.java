package com.inboxed.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.inboxed.screens.ClassicMode;

import java.util.Random;

public class DiceButton {
	public Texture image,movesImage,movesBg;
	public Sprite sprite;
	public int value;
	public Random rand;
	public String name;
	public boolean rolled;
	public Sprite cross;
	public DiceButton(String name){
		this.name = name;
		image = ClassicMode.images.dices.get(0);
		cross = new Sprite(ClassicMode.images.wrong);
		movesImage = ClassicMode.images.numbers.get(0);
		movesBg = ClassicMode.images.moveBG;
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
	public void setNumber(int value){
		this.value = value;
		movesImage = ClassicMode.images.numbers.get(value);
	}
	public void roll(){
		value = rand.nextInt(6)+1;
		sprite.setRegion(ClassicMode.images.dices.get(value-1));
		movesImage = ClassicMode.images.numbers.get(value);
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
