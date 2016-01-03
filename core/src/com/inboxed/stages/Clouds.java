package com.inboxed.stages;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.inboxed.blocks.Block;
import com.inboxed.blocks.MovingBlock;
import com.inboxed.screens.ClassicMode;

import java.util.Random;

public class Clouds extends Stage{

    public Random rand;
    public Array<Block> movingBlocks;
    public Clouds(String name) {
        super(name);
        rand = new Random();
        movingBlocks = new Array<Block>();
        for(Block block : blocks.blocks){
            if(block.pos_x == 1 && block.pos_y == 2){
                movingBlocks.add(block);
            }
        }


    }
    public void effect(){
        System.out.println("EFFEEEEEEECT MIERDDAAAA");
        for(Block block : blocks.blocks){
            if(block instanceof MovingBlock){
                ((MovingBlock)(block)).changePosition();
            }
        }
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
