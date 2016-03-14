package com.inboxed.stages;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inboxed.blocks.Block;
import com.inboxed.screens.ClassicMode;

import java.util.Random;

public class AncientRuins extends Stage{

    private Random rand;
    private int restoreProb;
    public AncientRuins(String name) {
        super(name);
        rand = new Random();
        for(Block block : blocks.blocks){
            block.restore(rand.nextInt(2)+1);
        }
        restoreProb = 10;


    }
    public void effect(){
        int r;

        for(Block block : blocks.blocks){
            if(block.points <= 0){
                r = rand.nextInt(99)+1;
                if(r <= restoreProb){
                    block.restore(rand.nextInt(2)+1);
                }
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
