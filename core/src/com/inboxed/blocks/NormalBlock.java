package com.inboxed.blocks;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inboxed.characters.Character;
import com.inboxed.main.MainGame;
import com.inboxed.screens.ClassicMode;

import java.util.Random;

public class NormalBlock extends Block {
	public NormalBlock(int x, int y, int points, String name, Texture image) {
		super(x, y, points,name,image);
	}
	public NormalBlock(int x, int y, int points, String name) {
		super(x, y, points,name);
		Random rand = new Random();
		int r = rand.nextInt(100)+1;
		if(r >15) image = new Texture(Gdx.files.internal("blocks/"+name+".png"));
		else{
			r = rand.nextInt(2)+1;
			image = new Texture(Gdx.files.internal("blocks/"+name+r+".png"));
		}
		sprite = new Sprite(image);
		sprite.setBounds(x * MainGame.SPRITESIZE, y * MainGame.SPRITESIZE, MainGame.SPRITESIZE, MainGame.SPRITESIZE);
		
	}
	
	public void update() {
		
	}


	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
		pointSprite.draw(batch);
	}

	public void ability() {
		
	}

	public void discount(){
		if(points <=0) return;
		points-=1;
		if (points <= 0){
			pointSprite = new Sprite(ClassicMode.images.blocked);
		}
		else{
			pointSprite = new Sprite(ClassicMode.images.normalPoints.get(points-1));
		}
		pointSprite.setBounds(pos_x*MainGame.SPRITESIZE, pos_y*MainGame.SPRITESIZE, MainGame.SPRITESIZE, MainGame.SPRITESIZE);


		
		
	}
	@Override
	public void beforeAbility(Character chr) {
		// TODO Auto-generated method stub
		
	}
	public void restore(int points){
		this.points = points;
		if(points > 0) pointSprite.setTexture(ClassicMode.images.normalPoints.get(points-1));
		else pointSprite.setTexture(ClassicMode.images.blocked);
	}
}
