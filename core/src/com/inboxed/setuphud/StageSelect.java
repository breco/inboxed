package com.inboxed.setuphud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.inboxed.main.MainGame;


public class StageSelect {
	
	public Array<Pair<com.badlogic.gdx.graphics.g2d.Sprite, java.lang.String>> stages;
	public boolean showing;
	public String current;
	public Sprite ok,selected;
	public BitmapFont font;
	
	@SuppressWarnings("hiding")
	private class Pair<Sprite,String>{
		String string;
		Sprite sprite;
		private Pair(Sprite sprite, String string){
			this.string = string;
			this.sprite = sprite;
		}
	}
	public StageSelect(){
		font =  new BitmapFont();
		font.setColor(Color.BLACK);
		font.getData().setScale(2);
		showing = false;
		current = "";
		ok = new Sprite(new Texture(Gdx.files.internal("setupHud/ok.png")));
		ok.setBounds(MainGame.width/2, 10, MainGame.SPRITESIZE, MainGame.SPRITESIZE);
		selected = new Sprite(new Texture(Gdx.files.internal("setupHud/selected.png")));
		selected.setBounds(-100,-100,MainGame.SPRITESIZE,MainGame.SPRITESIZE);
		stages = new Array<Pair<Sprite,String>>();
		stages.add(new Pair<Sprite,String>(new Sprite(new Texture(Gdx.files.internal("blocks/forest/blocked.png"))),"forest"));
		stages.add(new Pair<Sprite,String>(new Sprite(new Texture(Gdx.files.internal("blocks/snowland/blocked.png"))),"snowland"));
		stages.add(new Pair<Sprite,String>(new Sprite(new Texture(Gdx.files.internal("blocks/clouds/blocked.png"))),"clouds"));
		int x = 0,y = 0;
		for(Pair<Sprite,String> pair : stages){
			pair.sprite.setBounds(200+150*x,300-y*150,MainGame.SPRITESIZE,MainGame.SPRITESIZE);
			x++;
			if(x > 3){
				x = 0;
				y++;
			}
		}
	}public void draw(SpriteBatch batch){
		font.draw(batch, "STAGE SELECT", MainGame.width/2-100,MainGame.height-40);
		for(Pair<Sprite,String> pair : stages){
			pair.sprite.draw(batch);
		}
		ok.draw(batch);
		selected.draw(batch);
	}
	public void input(Vector3 vec){
		for(Pair<Sprite,String> pair : stages){
			if(pair.sprite.getBoundingRectangle().contains(vec.x,vec.y)){
				current = pair.string;
				selected.setPosition(pair.sprite.getX(),pair.sprite.getY());
				return;
			}
		}
		if(ok.getBoundingRectangle().contains(vec.x,vec.y)){
			showing = false;
		}
	}
}
