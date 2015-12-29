package com.inboxed.characters;



import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.inboxed.blocks.Block;
import com.inboxed.blocks.IceBlock;
import com.inboxed.helpers.Pair;
import com.inboxed.main.MainGame;
import com.inboxed.screens.ClassicMode;

public abstract class Character {
	public int pos_x,pos_y;
	public int moves;
	
	public String name;
	public Texture image;
	public Sprite sprite;
	public Block block;
	public Texture movesImage;
	public Texture movesBg;
	//movement
	public Array<Pair<Block, String>> possibleMoves;
	public String dir = " ";
	public int MOV = 4;
	public int movTimer = 0;
	public int MAXMOV = MainGame.SPRITESIZE/MOV;
	public boolean moving = false;
	//
	public boolean lose;
	//ability
	public boolean doingAbility = false;
	public int abilityCost;
	public int energyPoints;
	//status
	public String status;
	public Character(int x, int y, String name){
		pos_x = x;
		pos_y = y;
		moves = 0;
		energyPoints = 0;
		movesImage = new Texture(Gdx.files.internal("numbers/"+moves+".png"));
		movesBg = new Texture(Gdx.files.internal("hud/moveBg.png"));
		image = new Texture(Gdx.files.internal("characters/"+name+".png"));
		sprite = new Sprite(image);
		sprite.setBounds(x*MainGame.SPRITESIZE, y*MainGame.SPRITESIZE, MainGame.SPRITESIZE, MainGame.SPRITESIZE);
		block = getBlock();
		block.pressed = true;
		possibleMoves = new Array<Pair<Block,String>>();
		this.name = name;
		lose = false;
		status = "";
	}
	public void update(){
		//if(status.equals("tornado")) System.out.println("TORNADO");
		if(moving) move();
	}
	public abstract void draw(SpriteBatch batch);
	public abstract void ability();
	public abstract void startAbility();
	public abstract boolean canAbility();
	public Block getBlock(){
		for(Block block : ClassicMode.stage.blocks.blocks){
			if(block.pos_x == pos_x && block.pos_y == pos_y){
				return block;
			}
		}
		return null;
	}
	
	//MOVEMENT
	public void changeMoves(int moves){
		this.moves += moves;
		movesImage = new Texture(Gdx.files.internal("numbers/"+moves+".png"));
	}
	public Array<Pair<Block, String>> getPossibleMoves(){
		possibleMoves.clear();
		if(status.equals("tornado")){ // TORNADO STATUS
			possibleMoves.add(new Pair<Block,String>(block,"S"));
			return possibleMoves;
		}
		else if(status.contains("arrow") || status.contains("frozen")){ //ARROW AND FROZEN STATUS
			for(Block block : ClassicMode.stage.blocks.blocks){
				if(block.points > 0 && !block.pressed){
					if(status.contains("L") && block.pos_x == pos_x - 1 && block.pos_y == pos_y){ //LEFT
						possibleMoves.add(new Pair<Block,String>(block,"L"));
					}
					else if(status.contains("R") && block.pos_x == pos_x + 1 && block.pos_y == pos_y){ //RIGHT
						possibleMoves.add(new Pair<Block,String>(block,"R"));
					}
					else if(status.contains("U") && block.pos_y == pos_y + 1 && block.pos_x == pos_x){ //UP
						possibleMoves.add(new Pair<Block,String>(block,"U"));
					}
					else if(status.contains("D") && block.pos_y == pos_y - 1 && block.pos_x == pos_x){ //DOWN
						possibleMoves.add(new Pair<Block,String>(block,"D"));
					}
				}
			}
			if(possibleMoves.size == 0 && status.contains("frozen")){
				status = "";
			}
			return possibleMoves;		
		}
		for(Block block : ClassicMode.stage.blocks.blocks){
			if(block.points > 0 && !block.pressed){
				if(block.pos_x == pos_x - 1 && block.pos_y == pos_y){ //LEFT
					possibleMoves.add(new Pair<Block,String>(block,"L"));
				}
				else if(block.pos_x == pos_x + 1 && block.pos_y == pos_y){ //RIGHT
					possibleMoves.add(new Pair<Block,String>(block,"R"));
				}
				else if(block.pos_y == pos_y + 1 && block.pos_x == pos_x){ //UP
					possibleMoves.add(new Pair<Block,String>(block,"U"));
				}
				else if(block.pos_y == pos_y - 1 && block.pos_x == pos_x){ //DOWN
					possibleMoves.add(new Pair<Block,String>(block,"D"));
				}
			}
		}
		return possibleMoves;
	}
	public boolean useStatus(){
		if(status.equals("tornado")){
			Random rand = new Random();
			Block temp;
			int r = rand.nextInt(ClassicMode.stage.blocks.blocks.size);
			temp = ClassicMode.stage.blocks.blocks.get(r);
			while(temp.pressed || temp.points <= 0){
				r = rand.nextInt(ClassicMode.stage.blocks.blocks.size);
				temp = ClassicMode.stage.blocks.blocks.get(r);
				System.out.println("while:"+r);
			}
			
			temp = block;
			System.out.println("NO WHILE "+r);
			block = ClassicMode.stage.blocks.blocks.get(r);
			block.pressed = true;
			temp.pressed = false;
			
			moves--;
			movesImage = new Texture(Gdx.files.internal("numbers/"+moves+".png"));
			energyPoints++;
			status = "";
			block.beforeAbility(this);
			dir = " ";
			pos_x = block.pos_x;
			pos_y = block.pos_y;
			sprite.setPosition(pos_x*MainGame.SPRITESIZE,pos_y*MainGame.SPRITESIZE);
			temp.discount();
			return true;
		}
		return false;
	}
	public void changeDir(String dir){
		if(moves <= 0) return;
		if(useStatus()) return;
		System.out.println("this.dir="+this.dir+"_dir received="+dir+".");
		if (this.dir != dir && !dir.equals(" ")){
			this.dir = dir;
			moving = true;
			System.out.println("alosolo");
			if(status.contains("arrow"))status = "";
			return;
		}
		moving = false;
		//dirr = dir;
	}
	public void move(){
		movTimer++;
		if (dir.equals("R")){
			sprite.setPosition(sprite.getX()+MOV,sprite.getY());
		}
		else if(dir.equals("L")){
			sprite.setPosition(sprite.getX()-MOV,sprite.getY());
		}
		else if(dir.equals("U")){
			sprite.setPosition(sprite.getX(),sprite.getY()+MOV);
		}
		else if(dir.equals("D")){
			sprite.setPosition(sprite.getX(),sprite.getY()-MOV);
		}
		if(movTimer >= MAXMOV){
			movTimer = 0;
			moving = false;
			pos_x = (int) (sprite.getX()/MainGame.SPRITESIZE);
			pos_y = (int) (sprite.getY()/MainGame.SPRITESIZE);
			Block temp;
			temp = block;
			block = getBlock();
			block.pressed = true;
			
			block.beforeAbility(this);
			temp.discount();
			temp.pressed = false;
			dir = " ";
			moves--;
			if(moves == 0 && status.contains("frozen") && !(block instanceof IceBlock)) status = ""; 
			movesImage = new Texture(Gdx.files.internal("numbers/"+moves+".png"));
			energyPoints++;
		}
		//animation();
	}
	
}
