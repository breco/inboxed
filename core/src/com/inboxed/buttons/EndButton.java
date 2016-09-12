package com.inboxed.buttons;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.inboxed.screens.ClassicMode;

public class EndButton {
	public Sprite sprite;
	
	public String name;
	public EndButton(String name){
		this.name = name;
		sprite = new Sprite(ClassicMode.images.end);
		sprite.setBounds(10,170, 65, 65);

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