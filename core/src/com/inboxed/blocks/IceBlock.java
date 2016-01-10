package com.inboxed.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inboxed.characters.Character;
import com.inboxed.main.MainGame;
import com.inboxed.screens.ClassicMode;

public class IceBlock extends Block{

	public Sprite icon;
	public IceBlock(int x, int y, int points, String name) {
		super(x, y, points, name);
		icon = new Sprite(new Texture("blocks/iceBlock.png"));
		pointSprite = new Sprite(ClassicMode.images.normalPoints.get(points-1));
		pointSprite.setBounds(x*MainGame.SPRITESIZE, y*MainGame.SPRITESIZE, MainGame.SPRITESIZE, MainGame.SPRITESIZE);
		sprite.setTexture(icon.getTexture());
		icon.setBounds(x*MainGame.SPRITESIZE+7,y*MainGame.SPRITESIZE+10, 50, 50);
		
		
	}

	public IceBlock(int x, int y, int points, String name, Texture image) {
		super(x, y, points, name, image);
		icon = new Sprite(new Texture("blocks/iceBlock.png"));
		pointSprite = new Sprite(ClassicMode.images.normalPoints.get(points-1));
		pointSprite.setBounds(x*MainGame.SPRITESIZE, y*MainGame.SPRITESIZE, MainGame.SPRITESIZE, MainGame.SPRITESIZE);
		sprite.setTexture(icon.getTexture());
		icon.setBounds(x*MainGame.SPRITESIZE+7,y*MainGame.SPRITESIZE+10, 50, 50);
		System.out.println("inside points"+points);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
		
		pointSprite.draw(batch);
		
	}

	@Override
	public void ability() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void discount() {
		if(points <=0) return;
		points-=1;
		if (points <= 0){
			pointSprite = new Sprite(ClassicMode.images.blocked);
			//sprite.setTexture(ClassicMode.images.getTexture("C0"));
		}
		else{
			pointSprite = new Sprite(ClassicMode.images.normalPoints.get(points-1));;
		}
		pointSprite.setBounds(pos_x*MainGame.SPRITESIZE, pos_y*MainGame.SPRITESIZE, MainGame.SPRITESIZE, MainGame.SPRITESIZE);
		
	}

	@Override
	public void beforeAbility(Character chr) {
		//if(chr instanceof Chilly) return;
		chr.status = "frozen"+chr.dir;
		System.out.println("FREEZIIIINNNNGGGG");
		
	}

}
