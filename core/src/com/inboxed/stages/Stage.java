package com.inboxed.stages;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inboxed.blocks.Blocks;
import com.inboxed.screens.ClassicMode;
import com.inboxed.screens.OnlineMode;

public abstract class Stage {
	public Blocks blocks;
	public String name;
	public Stage(String name, OrthographicCamera cam){
		blocks = new Blocks(name, cam,this);
		this.name = name;
	}
	public abstract void effect();
	public abstract void update();
	public abstract void draw(SpriteBatch batch);
	public abstract void input(OrthographicCamera cam);
	public void setBounds(OnlineMode scr){
		if(blocks.size.equals("small")){ // 7 x 7 board
			scr.PANLIMITL = -2;
			scr.PANLIMITR = 446;
			scr.PANLIMITU = 446;
			scr.PANLIMITD = 30;
		}
	}
	public void setBounds(ClassicMode scr){
		if(blocks.size.equals("small")){ // 7 x 7 board
			scr.PANLIMITL = -2;
			scr.PANLIMITR = 446;
			scr.PANLIMITU = 446;
			scr.PANLIMITD = 30;
		}
	}

}
