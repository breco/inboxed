package com.inboxed.setuphud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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
	public CharacterSelect(int players){
		this.players = players;
		showing = false;
		characters = new Array<Pair<Sprite,String>>();
		shader = new ShaderProgram("ASD","SAD");
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
		for(Pair<Sprite,String> pair : characters){
			if(!playerNames.contains(pair.string,false)) pair.sprite.draw(batch);
		}
	}
	public void input(Vector3 vec){
		for(Pair<Sprite,String> pair : characters){
			if(pair.sprite.getBoundingRectangle().contains(vec.x,vec.y)){
				if(!playerNames.contains(pair.string,false)){
					playerNames.add(pair.string);
					players--;
					if(players == 0){
						showing = false;
						ClassicSetup.stageSelect.showing = true;
					}
				}
				
			}
		}
	}
}
