package com.inboxed.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inboxed.characters.Character;
import com.inboxed.main.MainGame;
import com.inboxed.screens.ClassicMode;

public class ArrowBlock extends Block{

	public Sprite icon;
	public String dir;
	public ArrowBlock(int x, int y, int points, String dir, String name) {
		super(x, y, points, name);
		this.dir = dir;
		icon = new Sprite(new Texture("blocks/arrowBlock"+dir+".png"));
		pointSprite = new Sprite(ClassicMode.images.specialPoints.get(points-1));
		pointSprite.setBounds(x*MainGame.SPRITESIZE, y*MainGame.SPRITESIZE, MainGame.SPRITESIZE, MainGame.SPRITESIZE);
		icon.setBounds(x*MainGame.SPRITESIZE+7,y*MainGame.SPRITESIZE+10, 50, 50);
	}
	
	public ArrowBlock(int x, int y, int points, String dir,  String name, Texture image) {
		super(x, y, points, name, image);
		this.dir = dir;
		icon = new Sprite(new Texture("blocks/arrowBlock"+dir+".png"));
		pointSprite = new Sprite(ClassicMode.images.specialPoints.get(points-1));
		pointSprite.setBounds(x*MainGame.SPRITESIZE, y*MainGame.SPRITESIZE, MainGame.SPRITESIZE, MainGame.SPRITESIZE);
		icon.setBounds(x*MainGame.SPRITESIZE+7,y*MainGame.SPRITESIZE+10, 50, 50);
	}
	
	public void update() {
	
		
	}


	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
		icon.draw(batch);
		pointSprite.draw(batch);
		
	}


	public void ability() {

		
	}


	public void discount() {
		if(points <=0) return;
		points-=1;
		if (points <= 0){
			pointSprite = new Sprite(ClassicMode.images.blocked);
		}
		else{
			pointSprite = new Sprite(ClassicMode.images.specialPoints.get(points-1));;
		}
		pointSprite.setBounds(pos_x*MainGame.SPRITESIZE, pos_y*MainGame.SPRITESIZE, MainGame.SPRITESIZE, MainGame.SPRITESIZE);
		
	}


	public void beforeAbility(Character chr) {
		chr.status = "arrow_"+dir;
		
	}

}
