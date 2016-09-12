package com.inboxed.stages;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inboxed.blocks.Block;
import com.inboxed.blocks.IceBlock;
import com.inboxed.screens.ClassicMode;

import java.util.Random;

public class IceRink extends Stage{

    public Random rand;
    private Block delete;
    private int changes;
    public IceRink(String name, OrthographicCamera cam) {
        super(name, cam);
        rand = new Random();
        int r;
        if(blocks.size.equals("small")){
            changes = 6;
        }
        else if(blocks.size.equals("big")){
            changes = 15;
        }
        for(int i = 1;i<=changes;i++){
            r = rand.nextInt(blocks.blocks.size);
            while(blocks.blocks.get(r).pressed || !(blocks.blocks.get(r) instanceof IceBlock || blocks.blocks.get(r).points <= 0)){
                r = rand.nextInt(blocks.blocks.size);
            }
            delete = blocks.blocks.get(r);
            while(delete.points > 0) {
                delete.discount();
            }
        }

    }
    public void effect(){
        int r;
        System.out.println("doing effect");
        for(int i = 1;i<=changes;i++) {
            r = rand.nextInt(blocks.blocks.size);
            delete = blocks.blocks.get(r);
            while (delete.pressed || !(delete instanceof IceBlock) || delete.points > 0) {
                r = rand.nextInt(blocks.blocks.size);
                delete = blocks.blocks.get(r);
            }
            blocks.blocks.add(new IceBlock(this,delete.pos_x, delete.pos_y, rand.nextInt(6) + 1, "snowland", ClassicMode.images.getTexture("C0")));
            blocks.blocks.removeIndex(r);

        }
        for(int i = 1;i<=changes;i++){
            r = rand.nextInt(blocks.blocks.size);
            while(blocks.blocks.get(r).pressed || !(blocks.blocks.get(r) instanceof IceBlock) || blocks.blocks.get(r).points <= 0){
                r = rand.nextInt(blocks.blocks.size);
            }
            delete = blocks.blocks.get(r);
            while(delete.points > 0) {
                delete.discount();
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
