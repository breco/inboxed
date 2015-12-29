package com.inboxed.blocks;


import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inboxed.characters.Character;
import com.inboxed.main.MainGame;

public class BackgroundBlock extends Block {
	public BackgroundBlock(int x, int y, int points, String name, Texture image) {
		super(x, y, points,name,image);
		pressed = true;
	}
	public BackgroundBlock(int x, int y, int points, String name, String dir) {
		super(x, y, points,name+dir);
		pressed = true;
		if(!dir.equals("C") && !dir.equals("U") && !dir.equals("L") && !dir.equals("R") && !dir.equals("D")) return;
		Random rand = new Random();
		int r = rand.nextInt(100)+1;
		if(r >25) image = new Texture(Gdx.files.internal("blocks/"+name+dir+".png"));
		else{
			r = rand.nextInt(2)+1;
			image = new Texture(Gdx.files.internal("blocks/"+name+dir+r+".png"));
			System.out.println("wea");
		}
		sprite = new Sprite(image);
		sprite.setBounds(x*MainGame.SPRITESIZE, y*MainGame.SPRITESIZE, MainGame.SPRITESIZE,MainGame.SPRITESIZE);
		
	}
	
	public void update() {
		
	}


	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
	}

	public void ability() {
		
	}

	public void discount(){
	}
	@Override
	public void beforeAbility(Character chr) {
		// TODO Auto-generated method stub
		
	}
}