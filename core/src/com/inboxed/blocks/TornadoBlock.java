package com.inboxed.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inboxed.characters.Character;
import com.inboxed.main.MainGame;
import com.inboxed.screens.ClassicMode;
import com.inboxed.stages.Stage;

public class TornadoBlock extends Block{
	public Sprite icon;
	public TornadoBlock(Stage stage, int x, int y, int points, String name, Texture image){
		super(stage, x,y,points,name,image);
		icon = new Sprite(new Texture("blocks/tornadoBlock.png"));
		pointSprite = new Sprite(ClassicMode.images.specialPoints.get(points-1));
		pointSprite.setBounds(x*MainGame.SPRITESIZE, y*MainGame.SPRITESIZE, MainGame.SPRITESIZE, MainGame.SPRITESIZE);
		icon.setBounds(x*MainGame.SPRITESIZE+7,y*MainGame.SPRITESIZE+10, 50, 50);
	}
	public TornadoBlock(int x, int y, int points, String name) {
		super(x, y, points, name);
		icon = new Sprite(new Texture("blocks/tornadoBlock.png"));
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
	public void beforeAbility(){
		
	}
	public void startAbility(){
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
		chr.status = "tornado";
	}

}
