package com.inboxed.stages;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inboxed.main.MainGame;
import com.inboxed.screens.ClassicMode;

public class Forest extends Stage{
	
	public Random rand;
	public Forest(String name) {
		super(name);
		rand = new Random();
	}
	public void effect(){
		int r;
		for(int i = 1;i<=4;i++){
			r = rand.nextInt(blocks.blocks.size);
			while(blocks.blocks.get(r).pressed || blocks.blocks.get(r).points <= 0){
				r = rand.nextInt(blocks.blocks.size);
			}
			blocks.blocks.get(r).points = 0;
			blocks.blocks.get(r).pointSprite = new Sprite(ClassicMode.images.blocked);
			blocks.blocks.get(r).pointSprite.setBounds(blocks.blocks.get(r).pos_x*MainGame.SPRITESIZE, blocks.blocks.get(r).pos_y*MainGame.SPRITESIZE, MainGame.SPRITESIZE, MainGame.SPRITESIZE);
			System.out.println("STAGE EFFECT!");
		}
	}
	
	public void update() {
		blocks.update();
		
	}
	
	public void draw(SpriteBatch batch) {
		blocks.draw(batch);
		
	}
	public void input(){
		blocks.input(ClassicMode.cam);
	}

}
