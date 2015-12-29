package com.inboxed.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.inboxed.blocks.Block;
import com.inboxed.blocks.LightningBlock;
import com.inboxed.main.MainGame;
import com.inboxed.screens.ClassicMode;


public class Rocky extends Character {
	
	public Texture target;
	public Array<Sprite> possibleSprites;
	public Rocky(int x, int y, String name) {
		super(x, y, name);
		target = new Texture(Gdx.files.internal("blocks/targetAttack.png"));
		possibleSprites = new Array<Sprite>();
		abilityCost = 10;
	}


	public void draw(SpriteBatch batch) {
		for(Sprite sprite : possibleSprites){
			sprite.draw(batch);
		}
		sprite.draw(batch);
	}
	public boolean possibleContains(Block block){
		for(Sprite sprite : possibleSprites){
			if(sprite.getX()/MainGame.SPRITESIZE == block.pos_x && sprite.getY()/MainGame.SPRITESIZE == block.pos_y){
				return true;
			}
		}
		return false;
	}
	public void getNearbyBlocks(){
		possibleSprites.clear();
		Sprite sprite;
		for(Block block : ClassicMode.stage.blocks.blocks){
			if(!block.pressed && block.points > 0){
				for(int i = pos_x -1; i <= pos_x +1; i++){
					for(int j = pos_y -1; j <= pos_y+1;j++){
						if(block.pos_x == i && block.pos_y == j){
							sprite = new Sprite(target);
							sprite.setBounds(block.pos_x*MainGame.SPRITESIZE,block.pos_y*MainGame.SPRITESIZE,MainGame.SPRITESIZE,MainGame.SPRITESIZE);
							possibleSprites.add(sprite);
						}
					}
				}			
			}
		}	
	}
	public boolean areNearbyBlocks(){
		for(Block block : ClassicMode.stage.blocks.blocks){
			if(!block.pressed && block.points > 0){
				for(int i = pos_x -1; i <= pos_x +1; i++){
					for(int j = pos_y -1; j <= pos_y+1;j++){
						if(block.pos_x == i && block.pos_y == j){
							return true;
						}
					}
				}			
			}
		}
		return false;
	}
	public void ability() { //turns a square into a lightning square
		Block block = ClassicMode.stage.blocks.touched;
		if(block ==null) return;
		if(block.pressed || block.points <= 0) return;
		if(possibleSprites.size == 0){
			doingAbility = false;
			
			return;
		}
		if(possibleContains(block)){
			/*block.points = 0;
			block.pointSprite = new Sprite(new Texture(Gdx.files.internal("blocks/blocked.png")));
			block.pointSprite.setBounds(block.pos_x*128, block.pos_y*128, 128, 128);*/
			ClassicMode.stage.blocks.blocks.add( new LightningBlock(block.pos_x,block.pos_y,block.points,"forest",ClassicMode.images.getTexture("C0")));
			ClassicMode.stage.blocks.blocks.removeValue(block, false);
			doingAbility = false;
			possibleSprites.clear();
			
		}	
		
	}


	public void startAbility() {
		energyPoints -= abilityCost;
		doingAbility = true;
		getNearbyBlocks();
	}
	
	public boolean canAbility(){
		if(energyPoints < abilityCost) return false;
		if(!areNearbyBlocks()) return false;
		return true;
	}

	
}
