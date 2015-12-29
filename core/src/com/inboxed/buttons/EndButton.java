package com.inboxed.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
public class EndButton {
	public Sprite sprite;
	
	public String name;
	public EndButton(String name){
		this.name = name;
		sprite = new Sprite(new Texture(Gdx.files.internal("hud/"+name+".png")));
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