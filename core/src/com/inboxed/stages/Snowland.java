package com.inboxed.stages;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inboxed.blocks.Block;
import com.inboxed.blocks.IceBlock;
import com.inboxed.screens.ClassicMode;

import java.util.Random;

public class Snowland extends Stage{
	public Random rand;
	public Block delete;
	public Snowland(String name, OrthographicCamera cam) {
		super(name,cam);
		rand = new Random();
	}


	public void effect() { 
		int r;
		for(int i = 1;i<=4;i++){
			r = rand.nextInt(blocks.blocks.size);
			while(blocks.blocks.get(r).pressed || blocks.blocks.get(r).points <= 0){
				r = rand.nextInt(blocks.blocks.size);
			}
			delete = blocks.blocks.get(r);
			System.out.println(delete.points);
			blocks.blocks.add(new IceBlock(this,delete.pos_x,delete.pos_y,delete.points,"snowland",ClassicMode.images.getTexture("C0")));
			blocks.blocks.removeIndex(r);
			System.out.println("STAGE EFFECT!");
		}
		
	}

	public void update() {
		blocks.update();
		
	}


	public void draw(SpriteBatch batch) {
		blocks.draw(batch);
		
	}


	public void input(OrthographicCamera cam){
		//
		blocks.input(cam);
	}

}
