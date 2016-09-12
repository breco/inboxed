package com.inboxed.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Clouds extends Stage{


    public Sprite bg,bg2;

    public Clouds(String name, OrthographicCamera cam) {
        //			if(ClassicMode.cam.frustum.sphereInFrustum(block.sprite.getX()+mid, block.sprite.getY()+mid, 0,FRUNTUM))

        super(name, cam);
        bg = new Sprite(new Texture(Gdx.files.internal("backgrounds/clouds.png")));
        bg2 = new Sprite(new Texture(Gdx.files.internal("backgrounds/clouds.png")));
        bg.setPosition(-300,-100);
        bg.setScale(2);
        bg2.setPosition(-300,1100);
        bg2.setScale(2);

    }
    public void effect(){
        /*for(Block block : blocks.blocks){
            if(block instanceof MovingBlock){
                ((MovingBlock)(block)).changePosition();
            }
        }*/
    }

    public void update() {
        blocks.update();

    }

    public void draw(SpriteBatch batch) {
        bg.draw(batch);
        bg2.draw(batch);
        blocks.draw(batch);

    }
    public void input(OrthographicCamera cam){
        blocks.input(cam);
    }

}
