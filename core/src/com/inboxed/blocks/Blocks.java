package com.inboxed.blocks;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.inboxed.inputs.MyGestures;
import com.inboxed.main.MainGame;
import com.inboxed.screens.ClassicMode;

import java.util.Random;


public class Blocks {
	public Array<Block> blocks;
	public Array<Block> moves;
	public Array<Block> background;
	public Block special,touched,longTouched;
	public Vector3 touchPos;
	public Array<Sprite> possibles;
	public int FRUNTUM = 40;
	public int mid = MainGame.SPRITESIZE/2;
	public String size;
	public Blocks(String name){
		blocks = new Array<Block>();
		background = new Array<Block>();
		Random rand = new Random();


		moves = new Array<Block>();
		blocks.clear();
		loadFile(name);
		touchPos = new Vector3();
		possibles = new Array<Sprite>();

	}
	public void loadFile(String name){
		System.out.println("NAME ->"+name);
		FileHandle file = Gdx.files.internal("archivos/"+name+".txt");
		if(!file.exists()) System.out.println("no existe");
		Random rand = new Random();
		String text = file.readString();
		int x=0,y=0;
		System.out.println("lineas");
		System.out.println(text.split("\n").length);
		for(String line : text.split("\n")){
			String[] subline = line.split(",");
            if(subline[0].equals("SE")){
                String[] position;
                size = subline[1];
                for(int i = 2;i < subline.length;i++){
					position = subline[i].split("-");
                    ClassicMode.round.positions.add(Integer.parseInt(position[0]));
                    ClassicMode.round.positions.add(Integer.parseInt(position[1]));
				}
            }
			else if(subline[0].equals("B")){
				System.out.println(line);
				if(!subline[4].equals("MO")){
					x = Integer.parseInt(subline[1]);
					y = Integer.parseInt(subline[2]);
				}

				if(subline[4].equals("LI")){ //lightning block
					blocks.add(new LightningBlock(x,y,rand.nextInt(6)+1,subline[3],ClassicMode.images.center));
				}
				else if(subline[4].equals("MLI")){ //Mega lightning block
					blocks.add(new MegaLightningBlock(x,y,rand.nextInt(6)+1,subline[3],ClassicMode.images.center));
				}
				else if(subline[4].equals("TO")){ //Tornado Block
					blocks.add(new TornadoBlock(x,y,rand.nextInt(6)+1,subline[3],ClassicMode.images.center));
				}
				else if(subline[4].equals("ARU")){ //
					blocks.add(new ArrowBlock(x,y,rand.nextInt(6)+1,"U","forest",ClassicMode.images.center));
				}
				else if(subline[4].equals("ARD")){
					blocks.add(new ArrowBlock(x,y,rand.nextInt(6)+1,"D","forest",ClassicMode.images.center));
				}
				else if(subline[4].equals("ARL")){
					blocks.add(new ArrowBlock(x,y,rand.nextInt(6)+1,"L","forest",ClassicMode.images.center));
				}
				else if(subline[4].equals("ARR")){
					blocks.add(new ArrowBlock(x,y,rand.nextInt(6)+1,"R","forest",ClassicMode.images.center));
				}
				else if(subline[4].equals("IC")){
					blocks.add(new IceBlock(x,y,rand.nextInt(6)+1,"forest",ClassicMode.images.center));
				}
				else if(subline[4].equals("TE")){
					blocks.add(new TeleportBlock(x,y,rand.nextInt(6)+1,"forest",ClassicMode.images.center));
				}
				else if(subline[4].equals("MO")){
					blocks.add(new MovingBlock(subline[1],rand.nextInt(6)+1,"forest",ClassicMode.images.center5));
				}
				else{
					blocks.add(new NormalBlock(x,y,rand.nextInt(6)+1,subline[3],ClassicMode.images.getTexture(subline[4])));
				}

			}
			else if(subline[0].equals("BG")){
				x = Integer.parseInt(subline[1]);
				y = Integer.parseInt(subline[2]);
				background.add(new BackgroundBlock(x,y,1,subline[3],ClassicMode.images.getTexture(subline[4])));
			}
		}
	}
	public void draw(SpriteBatch batch){
		for(Block block : background){
			if(ClassicMode.cam.frustum.sphereInFrustum(block.sprite.getX()+mid, block.sprite.getY()+mid, 0,FRUNTUM))
				block.draw(batch);
		}
		for (Block block : blocks){
			if(ClassicMode.cam.frustum.sphereInFrustum(block.sprite.getX()+mid, block.sprite.getY()+mid, 0,FRUNTUM))
				block.draw(batch);
		}
		for(Sprite sprite : possibles){
			sprite.draw(batch);
		}

	}
	public void update(){
		if(special != null){
			special.ability();
			if(special.finished){
				special = null;
				possibles.clear();
			}
		}
	}
	public void input(OrthographicCamera cam){
		if(MyGestures.isTap()){
			touchPos.set(MyGestures.tapX,MyGestures.tapY,0);
			cam.unproject(touchPos);
			for(Block block : blocks){
				if(block.touched(touchPos)){
					touched = block;
					//System.out.println(block.pos_x+","+block.pos_y+", pressed? "+block.pressed+" points: "+block.points);
					break;
				}
			}
		}
		else if(MyGestures.isLongPress()){
			touchPos.set(MyGestures.longPX,MyGestures.longPY,0);
			cam.unproject(touchPos);
			for(Block block : blocks){
				if(block.touched(touchPos)){
					longTouched = block;
					//System.out.println(block.pos_x+","+block.pos_y+", pressed? "+block.pressed+" points: "+block.points);
					break;
				}
			}
		}
		
	}

}