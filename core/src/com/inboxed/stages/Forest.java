package com.inboxed.stages;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inboxed.main.MainGame;
import com.inboxed.screens.ClassicMode;

import java.util.Random;

public class Forest extends Stage{
	
	private Random rand;
	private int spawn;
	public Forest(String name, OrthographicCamera cam ) {
		super(name, cam);
		rand = new Random();
		if(blocks.size.equals("small")){
			spawn = 1;
		}
		else if(blocks.size.equals("big")){
			spawn = 4;
		}
	}
	public void effect(){
		int r;
		for(int i = 1;i<=spawn;i++){
			r = rand.nextInt(blocks.blocks.size);
			while(blocks.blocks.get(r).pressed || blocks.blocks.get(r).points <= 0){
				r = rand.nextInt(blocks.blocks.size);
			}
			blocks.blocks.get(r).points = 0;
			blocks.blocks.get(r).pointSprite = new Sprite(ClassicMode.images.blocked);
			blocks.blocks.get(r).pointSprite.setBounds(blocks.blocks.get(r).pos_x*MainGame.SPRITESIZE, blocks.blocks.get(r).pos_y*MainGame.SPRITESIZE, MainGame.SPRITESIZE, MainGame.SPRITESIZE);

		}
	}
	
	public void update() {
		blocks.update();
		
	}
	
	public void draw(SpriteBatch batch) {
		blocks.draw(batch);
		
	}
	public void input(OrthographicCamera cam){
		blocks.input(cam);
	}

}
