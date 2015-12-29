package com.inboxed.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.inboxed.blocks.Block;
import com.inboxed.blocks.IceBlock;
import com.inboxed.screens.ClassicMode;

public class Chilly extends Character{
	public Texture target;
	public Array<Block> newBlocks, removeBlocks;
	public Chilly(int x, int y, String name) {
		super(x, y, name);
		target = new Texture(Gdx.files.internal("blocks/targetAttack.png"));
		newBlocks = new Array<Block>();
		removeBlocks = new Array<Block>();
		abilityCost = 20;
	}

	@Override
	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
		
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
	public void ability() {
		
		for(Block block : ClassicMode.stage.blocks.blocks){
			if(!block.pressed && block.points > 0 && !(block instanceof IceBlock)){
				for(int i = pos_x - 1; i <= pos_x + 1; i++){
					for(int j = pos_y - 1; j <= pos_y + 1;j++){
	
						if(block.pos_x == i && block.pos_y == j){
							System.out.println("freezing: "+block.pos_x+","+block.pos_y);
							newBlocks.add( new IceBlock(block.pos_x,block.pos_y,block.points,"forest",ClassicMode.images.getTexture("C0")));
							removeBlocks.add(block);
						}
					}
				}			
			}
		}
		for(Block block : removeBlocks){
			ClassicMode.stage.blocks.blocks.removeValue(block, false);
		}
		for(Block block : newBlocks){
			ClassicMode.stage.blocks.blocks.add(block);
		}
		newBlocks.clear();
		removeBlocks.clear();
		doingAbility = false;

	}

	@Override
	public void startAbility() {
		energyPoints -= abilityCost;
		doingAbility = true;
		
	}

	@Override
	public boolean canAbility() {
		if(energyPoints < abilityCost) return false;
		if(!areNearbyBlocks()) return false;
		return true;
	}

}
