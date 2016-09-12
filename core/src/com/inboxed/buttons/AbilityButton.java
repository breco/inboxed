package com.inboxed.buttons;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.inboxed.screens.ClassicMode;


public class AbilityButton {
	public Sprite sprite;	
	public String name;
	public boolean used;
	public AbilityButton(String name){
		this.name = name;
		sprite = new Sprite(ClassicMode.images.power);
		sprite.setBounds(10,95, 65, 65);
		used = false;
	}
	public void draw(SpriteBatch batch){
		sprite.draw(batch);
	}
	public void update(){
	}
	public boolean touched(Vector3 touchPos){
		if(sprite.getBoundingRectangle().contains(touchPos.x,touchPos.y)){
			return true;
		}

		return false;
	}


}
