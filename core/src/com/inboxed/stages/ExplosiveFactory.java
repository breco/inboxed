package com.inboxed.stages;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inboxed.blocks.Block;
import com.inboxed.blocks.LightningBlock;
import com.inboxed.blocks.MegaLightningBlock;
import com.inboxed.screens.ClassicMode;

import java.util.Random;

public class ExplosiveFactory extends Stage{

    private Random rand;
    public ExplosiveFactory(String name) {
        super(name);
        rand = new Random();
        for(Block block : blocks.blocks){
            if(block instanceof LightningBlock || block instanceof MegaLightningBlock){
                block.sprite.setTexture(ClassicMode.images.getTexture("C4"));
            }
        }
    }
    public void effect(){

    }

    public void update() {
        blocks.update();

    }

    public void draw(SpriteBatch batch) {
        blocks.draw(batch);

    }
    public void input(){
        blocks.input(ClassicMode.cam);
    }

}
