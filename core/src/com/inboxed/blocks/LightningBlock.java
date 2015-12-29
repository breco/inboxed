package com.inboxed.blocks;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.inboxed.main.MainGame;
import com.inboxed.screens.ClassicMode;
import com.inboxed.characters.Character;
public class LightningBlock extends Block{
	
	public Sprite icon;
	public Array<Sprite> possibleSprites;
	public Texture target;
	public Character chr;
	public LightningBlock(int x, int y, int points, String name, Texture image) {
		super(x, y, points, name,image);
		icon = new Sprite(new Texture("blocks/lightningBlock.png"));
		pointSprite = new Sprite(ClassicMode.images.specialPoints.get(points-1));
		target = new Texture(Gdx.files.internal("blocks/targetBlock.png"));
		pointSprite.setBounds(x*MainGame.SPRITESIZE, y*MainGame.SPRITESIZE, MainGame.SPRITESIZE, MainGame.SPRITESIZE);
		icon.setBounds(x*MainGame.SPRITESIZE+7,y*MainGame.SPRITESIZE+10, 50, 50);
		possibleSprites = new Array<Sprite>();
		
		
	}
	public LightningBlock(int x, int y, int points, String name) {
		super(x, y, points, name);
		icon = new Sprite(new Texture("blocks/lightningBlock.png"));
		pointSprite = new Sprite(new Texture(Gdx.files.internal("points/specialPoint-"+points+".png")));
		target = new Texture(Gdx.files.internal("blocks/targetBlock.png"));
		pointSprite.setBounds(x*MainGame.SPRITESIZE, y*MainGame.SPRITESIZE, MainGame.SPRITESIZE, MainGame.SPRITESIZE);
		icon.setBounds(x*MainGame.SPRITESIZE+7,y*MainGame.SPRITESIZE+10, 50, 50);
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

	public Block getBlock(int x, int y){
		for(Block block : ClassicMode.stage.blocks.blocks){
			if(block.pos_x == x && block.pos_y == y){
				if(!block.pressed && block.points > 0){
					return block;
				}
			}
		}
		return null;
	}
	public void startAbility(){
		Sprite sprite;
		for(Block block : ClassicMode.stage.blocks.blocks){
			if(!block.pressed && block.points > 0 && (block.pos_x == pos_x || block.pos_y == pos_y)){
				sprite = new Sprite(target);
				sprite.setBounds(block.pos_x*MainGame.SPRITESIZE,block.pos_y*MainGame.SPRITESIZE,MainGame.SPRITESIZE,MainGame.SPRITESIZE);
				//sprite.setColor(Color.MAROON);
				possibleSprites.add(sprite);
			}
		}
	}
	public void ability() {
		Block block = ClassicMode.stage.blocks.touched;
		if(block ==null) return;
		//System.out.println(block.pos_x+","+block.pos_y);
		if(block.pressed || block.points <= 0) return;
		if(block.pos_x == pos_x || block.pos_y == pos_y){
			block.points = 0;
			block.pointSprite = new Sprite(ClassicMode.images.blocked);
			block.pointSprite.setBounds(block.pos_x*MainGame.SPRITESIZE, block.pos_y*MainGame.SPRITESIZE, MainGame.SPRITESIZE, MainGame.SPRITESIZE);
			finished = true;
		}		
	}

	public void discount() {
		if(points <=0) return; 
		points-=1;
		if (points <= 0){
			ClassicMode.stage.blocks.special = this;
			pointSprite = new Sprite(ClassicMode.images.blocked);
			startAbility();
			ClassicMode.stage.blocks.possibles = possibleSprites;
		}
		else{
			pointSprite = new Sprite(ClassicMode.images.specialPoints.get(points-1));;
			chr = null;
		}
		pointSprite.setBounds(pos_x*MainGame.SPRITESIZE, pos_y*MainGame.SPRITESIZE, MainGame.SPRITESIZE, MainGame.SPRITESIZE);
		
	}
	public void beforeAbility(Character chr){
		
	}
}
