package com.inboxed.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.inboxed.blocks.Block;
import com.inboxed.main.MainGame;
import com.inboxed.screens.ClassicMode;

public class Wheelie extends Character{
    public Texture target;
    public Array<Block> newBlocks, removeBlocks;
    public Array<Sprite> possibleSprites;

    public Wheelie(int x, int y, String name) {
        super(x, y, name);
        target = new Texture(Gdx.files.internal("blocks/targetAttack.png"));
        newBlocks = new Array<Block>();
        removeBlocks = new Array<Block>();
        abilityCost = 1;
        possibleSprites = new Array<Sprite>();
    }


    public void draw(SpriteBatch batch) {
        for(Sprite sprite : possibleSprites){
            sprite.draw(batch);
        }
        sprite.draw(batch);

    }

    public boolean areNearbyBlocks(){ //check if are blocks available for the ability effect
        for(Block block : ClassicMode.stage.blocks.blocks){
            if(!block.pressed && block.points > 0){
                if(block.pos_x == pos_x + 2 && block.pos_y == pos_y){
                    return true;
                }
                else if(block.pos_x == pos_x - 2 && block.pos_y == pos_y){
                    return true;
                }
                else if(block.pos_y == pos_y + 2 && block.pos_x == pos_x){
                    return true;
                }
                else if(block.pos_y == pos_y - 2 && block.pos_x == pos_x){
                    return true;
                }
            }
        }
        return false;
    }
    public void getNearbyBlocks(){
        possibleSprites.clear();
        Sprite sprite;
        boolean flag = false;
        for(Block block : ClassicMode.stage.blocks.blocks){
            if(!block.pressed && block.points > 0){
                if(block.pos_x == pos_x + 2 && block.pos_y == pos_y){
                    flag = true;
                }
                else if(block.pos_x == pos_x - 2 && block.pos_y == pos_y){
                    flag = true;
                }
                else if(block.pos_y == pos_y + 2 && block.pos_x == pos_x){
                    flag = true;
                }
                else if(block.pos_y == pos_y - 2 && block.pos_x == pos_x){
                    flag = true;
                }
                if(flag){
                    sprite = new Sprite(target);
                    sprite.setBounds(block.pos_x*MainGame.SPRITESIZE,block.pos_y*MainGame.SPRITESIZE,MainGame.SPRITESIZE,MainGame.SPRITESIZE);
                    possibleSprites.add(sprite);
                    flag = false;
                }
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
    public void ability() { // JUMP ! -> You can jump through an adyacent block !
        Block block = ClassicMode.stage.blocks.touched;
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
        if(moves <= 0) return false;
        return true;
    }

}
