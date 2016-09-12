package com.inboxed.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.inboxed.characters.Character;
import com.inboxed.main.MainGame;
import com.inboxed.screens.ClassicMode;
import com.inboxed.stages.Stage;

/**
 * Created by victor on 1/3/16.
 */
public class MovingBlock extends Block {

    public int curr,maxCurr;
    public String dirr;
    public Array<Integer> positions;

    public MovingBlock(Stage stage, String poses, int points, String name, Texture image){
        super(stage,Integer.parseInt(poses.split("_")[0]),Integer.parseInt(poses.split("_")[1]), points, name, image);
        positions = new Array<Integer>();
        for(String num : poses.split("_")){
            positions.add(Integer.parseInt(num));
        }
        curr = 0;
        dirr = "R";
        maxCurr = positions.size/2 -1;
    }

    public void changePosition(){
        if(dirr.equals("R")){
            curr++;
            if(curr == maxCurr) dirr = "L";
        }
        else if(dirr.equals("L")){
            curr--;
            if(curr == 0) dirr = "R";
        }
        pos_x = positions.get(curr*2);
        pos_y = positions.get(curr*2 + 1);
        sprite.setPosition(pos_x* MainGame.SPRITESIZE,pos_y*MainGame.SPRITESIZE);
        this.pointSprite.setPosition(pos_x* MainGame.SPRITESIZE,pos_y*MainGame.SPRITESIZE);
        for(Character chr : ClassicMode.round.players){
            if(chr.block.equals(this)){
                chr.pos_x = pos_x;
                chr.pos_y = pos_y;
                chr.sprite.setPosition(pos_x*MainGame.SPRITESIZE,pos_y* MainGame.SPRITESIZE);
            }
        }
    }
    @Override
    public void update() {

    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);

        pointSprite.draw(batch);
    }

    @Override
    public void ability() {

    }

    @Override
    public void discount() {
        if(points <=0) return;
        points-=1;
        if (points <= 0){
            pointSprite = new Sprite(ClassicMode.images.blocked);
            sprite.setTexture(ClassicMode.images.getTexture("C0"));
        }
        else{
            pointSprite = new Sprite(ClassicMode.images.normalPoints.get(points-1));;
        }
        pointSprite.setBounds(pos_x*MainGame.SPRITESIZE, pos_y*MainGame.SPRITESIZE, MainGame.SPRITESIZE, MainGame.SPRITESIZE);
    }

    @Override
    public void beforeAbility(Character chr) {

    }
}
