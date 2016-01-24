package com.inboxed.stages;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inboxed.blocks.Blocks;
import com.inboxed.screens.ClassicMode;

public abstract class Stage {
	public Blocks blocks;
	public String name;
	public Stage(String name){
		blocks = new Blocks(name);
		this.name = name;
	}
	public abstract void effect();
	public abstract void update();
	public abstract void draw(SpriteBatch batch);
	public abstract void input();
	public void setBounds(ClassicMode scr){
		if(blocks.size.equals("small")){ // 7 x 7 board
			scr.PANLIMITL = -2;
			scr.PANLIMITR = 446;
			scr.PANLIMITU = 446;
			scr.PANLIMITD = 30;
		}
	}
}
