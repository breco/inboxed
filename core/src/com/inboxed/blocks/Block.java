package com.inboxed.blocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.inboxed.characters.Character;
import com.inboxed.main.MainGame;
import com.inboxed.screens.ClassicMode;
import com.inboxed.stages.Stage;


public abstract class Block {
	public int pos_x,pos_y;
	public int points;
	public String name;
	public int size = 128;
	public Texture image;
	public Sprite sprite,pointSprite;
	public boolean pressed;
	public boolean abilityActivated;
	public boolean finished;
	//TEST
	public Stage stage;
	public Block(Stage stage, int x, int y, int points, String name, Texture image){
		this.stage = stage;
		pos_x = x;
		pos_y = y;
		this.points = points;
		if(image != null) sprite = new Sprite(image);
		else sprite = new Sprite(ClassicMode.images.getTexture("DEFAULT"));
		sprite.setBounds(x*MainGame.SPRITESIZE, y*MainGame.SPRITESIZE, MainGame.SPRITESIZE, MainGame.SPRITESIZE);
        if(points == 0) pointSprite = new Sprite(ClassicMode.images.blocked);
		else pointSprite = new Sprite(ClassicMode.images.normalPoints.get(points-1));
		pointSprite.setBounds(x*MainGame.SPRITESIZE, y*MainGame.SPRITESIZE,MainGame.SPRITESIZE, MainGame.SPRITESIZE);
		pressed = false;
		abilityActivated = false;
		finished = false;
		this.name = name;
	}
	public Block(int x, int y, int points, String name){
		pos_x = x;
		pos_y = y;
		this.points = points;
		image = new Texture(Gdx.files.internal("blocks/"+name+".png"));
		
		sprite = new Sprite(image);
		sprite.setBounds(x*MainGame.SPRITESIZE, y*MainGame.SPRITESIZE, MainGame.SPRITESIZE, MainGame.SPRITESIZE);
        if(points == 0) pointSprite = new Sprite(ClassicMode.images.blocked);
        else pointSprite = new Sprite(new Texture(Gdx.files.internal("points/point-"+points+".png")));
		pointSprite.setBounds(x*MainGame.SPRITESIZE, y*MainGame.SPRITESIZE,MainGame.SPRITESIZE, MainGame.SPRITESIZE);
		pressed = false;
		abilityActivated = false;
		finished = false;
		this.name = name;
	}
	public abstract void update();
	public abstract void draw(SpriteBatch batch);
	public abstract void ability();
	public abstract void discount();
	public abstract void beforeAbility(Character chr);
	public boolean touched(Vector3 touchPos){
		if(sprite.getBoundingRectangle().contains(touchPos.x,touchPos.y)){
			return true;
		}
		return false;
	}
	public void restore(int points){
        this.points = points;
		if(points > 0) pointSprite.setTexture(ClassicMode.images.specialPoints.get(points-1));
        else pointSprite.setTexture(ClassicMode.images.blocked);
    }
    public void changePosition(int x, int y){
        pos_x = x;
        pos_y = y;
        sprite.setPosition(pos_x*MainGame.SPRITESIZE,pos_y*MainGame.SPRITESIZE);
        pointSprite.setPosition(pos_x*MainGame.SPRITESIZE,pos_y*MainGame.SPRITESIZE);
    }
}
