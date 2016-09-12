package com.inboxed.stages;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.inboxed.blocks.ArrowBlock;
import com.inboxed.blocks.Block;

import java.util.Random;

public class BigCity extends Stage{

    private Random rand;
    private Array<String> directions;
    public BigCity(String name, OrthographicCamera cam) {
        super(name, cam);
        rand = new Random();
        directions = new Array<String>();
        directions.add("L");
        directions.add("R");
        directions.add("U");
        directions.add("D");
        effect();
    }
    public void effect(){
        System.out.println("doing effect");
        int r = 0;

        for(Block block : blocks.blocks){
            r = rand.nextInt(4);
            if(block instanceof ArrowBlock){
                ((ArrowBlock)(block)).changeDir(directions.get(r));
                r = rand.nextInt(100)+1;
                if(r <= 50){
                    //r = rand.nextInt(6)+1;
                    if(block.points <= 0) block.restore(1);
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
    public void input(OrthographicCamera cam){
        blocks.input(cam);
    }

}
