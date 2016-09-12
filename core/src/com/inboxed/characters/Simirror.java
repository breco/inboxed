package com.inboxed.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.inboxed.blocks.Block;
import com.inboxed.helpers.RoundController;
import com.inboxed.main.MainGame;
import com.inboxed.stages.Stage;

public class Simirror extends Character {
	
	public Texture target;
	public Array<Sprite> possibleSprites;
	public Simirror(int x, int y, String name, Stage stage, RoundController round) {
		super(x, y, name, stage,round);
		target = new Texture(Gdx.files.internal("blocks/targetMove.png"));
		possibleSprites = new Array<Sprite>();
		abilityCost = 2;
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
		for(Block block : stage.blocks.blocks){
			if(!block.pressed && block.points > 0){
				if((block.pos_x == pos_x -1 && block.pos_y == pos_y - 1) ||
					(block.pos_x == pos_x + 1 && block.pos_y == pos_y + 1) ||
					(block.pos_x == pos_x + 1 && block.pos_y == pos_y - 1) ||
					(block.pos_x == pos_x - 1 && block.pos_y == pos_y + 1) 
					){
					sprite = new Sprite(target);
					sprite.setBounds(block.pos_x*MainGame.SPRITESIZE,block.pos_y*MainGame.SPRITESIZE,MainGame.SPRITESIZE,MainGame.SPRITESIZE);
					possibleSprites.add(sprite);
				}

			}
		}	
	}
	public boolean areNearbyBlocks(){
		for(Block block : stage.blocks.blocks){
			if(!block.pressed && block.points > 0){
				if((block.pos_x == pos_x -1 && block.pos_y == pos_y - 1) ||
						(block.pos_x == pos_x + 1 && block.pos_y == pos_y + 1) ||
						(block.pos_x == pos_x + 1 && block.pos_y == pos_y - 1) ||
						(block.pos_x == pos_x - 1 && block.pos_y == pos_y + 1) 
						){
						return true;
					}			
			}
		}
		return false;
	}
	public void ability() { //moves diagonally to the selected square
		Block block = stage.blocks.touched;
		if(block ==null) return;
		if(block.pressed || block.points <= 0) return;
		if(possibleSprites.size == 0){
			doingAbility = false;
			return;
		}
		if(possibleContains(block)){
			sprite.setPosition(block.pos_x*MainGame.SPRITESIZE,block.pos_y*MainGame.SPRITESIZE);
			doingAbility = false;
			possibleSprites.clear();
			////////////////
			pos_x = (int) (sprite.getX()/MainGame.SPRITESIZE);
			pos_y = (int) (sprite.getY()/MainGame.SPRITESIZE);
			Block temp;
			temp = this.block;
			status = "";
			this.block = getBlock();
			this.block.pressed = true;
			this.block.beforeAbility(this);
			temp.discount();
			temp.pressed = false;
			dir = " ";
			moves--;
			movesImage = new Texture(Gdx.files.internal("numbers/"+moves+".png"));
			
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
		if(moves == 0) return false;
		return true;
	}

	
}
