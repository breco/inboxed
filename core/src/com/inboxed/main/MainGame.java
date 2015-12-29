package com.inboxed.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inboxed.inputs.MyGestures;
import com.inboxed.screens.ClassicSetup;

public class MainGame extends Game {
	public SpriteBatch batch;
	public SpriteBatch hudBatch;
	public static int width, height;
	public static int SPRITESIZE = 64;
	@Override
	public void create () {
		batch = new SpriteBatch();
		hudBatch = new SpriteBatch();
		MyGestures ges = new MyGestures();
		Gdx.input.setInputProcessor(ges.gd);
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		this.setScreen(new ClassicSetup(this));
		
	}

	@Override
	public void render () {
		super.render();
		MyGestures.update();
	}
	public void dispose(){
		batch.dispose();
		hudBatch.dispose();
	}
}
