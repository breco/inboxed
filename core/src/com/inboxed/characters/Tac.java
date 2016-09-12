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

public class Tac extends Character{
    public Texture target;
    public Array<Block> newBlocks, removeBlocks;
    public Array<Sprite> possibleSprites;

    public Tac(int x, int y, String name, Stage stage, RoundController round) {
        super(x, y, name, stage,round);
        target = new Texture(Gdx.files.internal("blocks/targetAttack.png"));
        newBlocks = new Array<Block>();
        removeBlocks = new Array<Block>();
        abilityCost = 2;
        possibleSprites = new Array<Sprite>();
    }

    @Override
    public void draw(SpriteBatch batch) {
        for(Sprite sprite : possibleSprites){
            sprite.draw(batch);
        }
        sprite.draw(batch);

    }

    public boolean areNearbyBlocks(){ //check if are blocks available for the ability effect
        return true;
    }
    public void getNearbyBlocks(){
        possibleSprites.clear();
        Sprite sprite;
        for(Character chr : round.players){
            if(!chr.lose && !chr.equals(this)){
                sprite = new Sprite(target);
                sprite.setBounds(chr.block.pos_x*MainGame.SPRITESIZE,chr.block.pos_y*MainGame.SPRITESIZE,MainGame.SPRITESIZE,MainGame.SPRITESIZE);
                possibleSprites.add(sprite);
            }
        }
    }
    public boolean possibleContains(Block block){
        for(Sprite sprite : possibleSprites){
            if(sprite.getX()/MainGame.SPRITESIZE == block.pos_x && sprite.getY()/MainGame.SPRITESIZE == block.pos_y){
                return true;
            }
        }
        return false;
    }
    public void ability() { // SWAP ! -> Switch positions with one of your opponents!
        Block block = stage.blocks.touched;
        System.out.println("ability!");
        if(block ==null) return;
        if(possibleSprites.size == 0){
            doingAbility = false;
            return;
        }
        if(possibleContains(block)){
            Character swapped = null;
            for(Character chr : round.players){
                if(chr.getBlock().equals(block)){
                    swapped = chr;
                    break;
                }
            }
            int tempX = pos_x, tempY = pos_y;
            Block tempBlock;
            tempBlock = this.block;
            pos_x = swapped.pos_x;
            pos_y = swapped.pos_y;
            this.block = swapped.block;
            swapped.pos_y = tempY;
            swapped.pos_x = tempX;
            swapped.block = tempBlock;
            sprite.setPosition(pos_x*MainGame.SPRITESIZE,pos_y*MainGame.SPRITESIZE);
            swapped.sprite.setPosition(swapped.pos_x*MainGame.SPRITESIZE,swapped.pos_y*MainGame.SPRITESIZE);
            swapped.status = "";
            status = "";
            doingAbility = false;
            possibleSprites.clear();

        }
    }

    @Override
    public void startAbility() {
        energyPoints -= abilityCost;
        doingAbility = true;
        getNearbyBlocks();

    }

    @Override
    public boolean canAbility() {
        if(energyPoints < abilityCost) return false;
        if(!areNearbyBlocks()) return false;
        return true;
    }

}
