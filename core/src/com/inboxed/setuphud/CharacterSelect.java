package com.inboxed.setuphud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.inboxed.main.MainGame;
import com.inboxed.screens.ClassicSetup;

public class CharacterSelect {
	
	public Array<Pair<com.badlogic.gdx.graphics.g2d.Sprite, java.lang.String>> characters;
	
	@SuppressWarnings("hiding")
	private class Pair<Sprite,String>{
		String string;
		Sprite sprite;
		private Pair(Sprite sprite, String string){
			this.string = string;
			this.sprite = sprite;
		}
	}

	public Array<String> playerNames;
	public int players;
	public boolean showing;
	public ShaderProgram shader;
	public BitmapFont font;
	public Array<Sprite> playerDone;
	public int playerNum;
	public Sprite ok;
	public CharacterSelect(int players){
		this.players = players;
		showing = false;
		characters = new Array<Pair<Sprite,String>>();
		ok = new Sprite(new Texture(Gdx.files.internal("setupHud/ok.png")));
		ok.setBounds(MainGame.width/2, 10, MainGame.SPRITESIZE, MainGame.SPRITESIZE);
		font = new BitmapFont();
		font.setColor(Color.BLACK);
		font.getData().setScale(2);
		playerDone = new Array<Sprite>();
		for(int i = 1; i <= players; i++){
			playerDone.add(new Sprite(new Texture(Gdx.files.internal("setupHud/player"+i+".png"))));
		}
		playerNum = 0;
		playerNames = new Array<String>();
		characters.add(new Pair<Sprite, String>(new Sprite(new Texture(Gdx.files.internal("characters/biospark.png"))),"biospark"));
		characters.add(new Pair<Sprite, String>(new Sprite(new Texture(Gdx.files.internal("characters/chilly.png"))),"chilly"));
		characters.add(new Pair<Sprite, String>(new Sprite(new Texture(Gdx.files.internal("characters/plasmawisp.png"))),"plasmawisp"));
		characters.add(new Pair<Sprite, String>(new Sprite(new Texture(Gdx.files.internal("characters/rocky.png"))),"rocky"));
		characters.add(new Pair<Sprite, String>(new Sprite(new Texture(Gdx.files.internal("characters/simirror.png"))),"simirror"));
		characters.add(new Pair<Sprite, String>(new Sprite(new Texture(Gdx.files.internal("characters/tac.png"))),"tac"));
		characters.add(new Pair<Sprite, String>(new Sprite(new Texture(Gdx.files.internal("characters/waddledoo.png"))),"waddledoo"));
		characters.add(new Pair<Sprite, String>(new Sprite(new Texture(Gdx.files.internal("characters/wheelie.png"))),"wheelie"));
		int x = 0,y = 0;
		for(Pair<Sprite,String> pair : characters){
			pair.sprite.setBounds(200+150*x,300-y*150,MainGame.SPRITESIZE,MainGame.SPRITESIZE);
			x++;
			if(x > 3){
				x = 0;
				y++;
			}
		}
	}
	public void draw(SpriteBatch batch){
		font.draw(batch, "CHARACTER SELECT", MainGame.width/2-100,MainGame.height-40);
		for(Pair<Sprite,String> pair : characters){
			pair.sprite.draw(batch);
		}
		for(Sprite sprite : playerDone){
			sprite.draw(batch);
		}
		ok.draw(batch);
	}
	public void input(Vector3 vec){
		for(Pair<Sprite,String> pair : characters){
			if(pair.sprite.getBoundingRectangle().contains(vec.x,vec.y)){
				if(!playerNames.contains(pair.string,false)){
					if(players == 0) break;
					playerNames.add(pair.string);
					players--;
					playerDone.get(playerNum).setPosition(pair.sprite.getX(),pair.sprite.getY()-MainGame.SPRITESIZE/2);

					playerNum++;
				}
				
			}
		}
		if(ok.getBoundingRectangle().contains(vec.x,vec.y)){
			if(players == 0) {
				showing = false;
				ClassicSetup.stageSelect.showing = true;
			}
		}
	}
}
