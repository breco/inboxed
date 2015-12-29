package com.inboxed.stages;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inboxed.blocks.Blocks;

public abstract class Stage {
	public Blocks blocks;
	public Stage(String name){
		blocks = new Blocks(name);
	}
	public abstract void effect();
	public abstract void update();
	public abstract void draw(SpriteBatch batch);
	public abstract void input();
}
