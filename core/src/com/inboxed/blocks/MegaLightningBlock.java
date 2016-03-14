package com.inboxed.blocks;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.inboxed.characters.Character;
import com.inboxed.main.MainGame;
import com.inboxed.screens.ClassicMode;


public class MegaLightningBlock extends Block{

    public Sprite icon;
    public Array<Sprite> possibleSprites;
    public Texture target;
    public Character chr;
    public MegaLightningBlock(int x, int y, int points, String name, Texture image) {
        super(x, y, points, name,image);
        icon = new Sprite(new Texture("blocks/lightningBlock.png"));
        pointSprite = new Sprite(ClassicMode.images.specialPoints.get(points-1));
        target = new Texture(Gdx.files.internal("blocks/targetBlock.png"));
        pointSprite.setBounds(x*MainGame.SPRITESIZE, y*MainGame.SPRITESIZE, MainGame.SPRITESIZE, MainGame.SPRITESIZE);
        icon.setBounds(x*MainGame.SPRITESIZE+7,y*MainGame.SPRITESIZE+10, 50, 50);
        icon.setColor(Color.ORANGE);
        possibleSprites = new Array<Sprite>();


    }

    public void update() {
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
        icon.draw(batch);
        pointSprite.draw(batch);
        for(Sprite sprite : possibleSprites){
            sprite.draw(batch);
        }

    }

    public void startAbility(){
        for(Block block : ClassicMode.stage.blocks.blocks){
            if(!block.pressed && block.points > 0 && (block.pos_x == pos_x || block.pos_y == pos_y)){
                block.restore(0);
            }
        }
    }
    public void ability() {
    }

    public void discount() {
        if(points <=0) return;
        points-=1;
        if (points <= 0){
            pointSprite = new Sprite(ClassicMode.images.blocked);
            startAbility();
        }
        else{
            pointSprite = new Sprite(ClassicMode.images.specialPoints.get(points-1));;
            chr = null;
        }
        pointSprite.setBounds(pos_x*MainGame.SPRITESIZE, pos_y*MainGame.SPRITESIZE, MainGame.SPRITESIZE, MainGame.SPRITESIZE);

    }
    public void beforeAbility(Character chr){

    }
    public void changePosition(int x, int y){
        pos_x = x;
        pos_y = y;
        sprite.setPosition(pos_x*MainGame.SPRITESIZE,pos_y*MainGame.SPRITESIZE);
        pointSprite.setPosition(pos_x*MainGame.SPRITESIZE,pos_y*MainGame.SPRITESIZE);
        icon.setBounds(x * MainGame.SPRITESIZE + 7, y * MainGame.SPRITESIZE + 10, 50, 50);
    }
}
