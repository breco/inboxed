package com.inboxed.buttons;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.inboxed.main.MainGame;
import com.inboxed.screens.ClassicMode;

/**
 * Created by victor on 1/1/16.
 */
public class SurrenderButton {

    public Sprite sprite;

    public SurrenderButton(){

        sprite = new Sprite(ClassicMode.images.surrender);
        sprite.setBounds(10, MainGame.height - 75, 65, 65);

    }
    public void draw(SpriteBatch batch){
        sprite.draw(batch);

    }
    public void update(){

    }

    public boolean touched(Vector3 touchPos){

        if(sprite.getBoundingRectangle().contains(touchPos.x,touchPos.y)){
            return true;
        }
        return false;
    }
}

