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
	public Block special,touched;
	public Vector3 touchPos;
	public Array<Sprite> possibles;
	public int FRUNTUM = 40;
	public int mid = MainGame.SPRITESIZE/2;
	public Blocks(String name){
		blocks = new Array<Block>();
		background = new Array<Block>();
		Random rand = new Random();
		System.out.println("before loading file");
		/*int r = 0;
		int p = 0;
		for(int i = -6; i < 13; i++){
			for(int j = -3; j < 10; j++){
				if((i >= 0 && i <= 6) && (j >= 0 && j <= 6)) continue;
				r = rand.nextInt(3);
				if(i == -1 && j == 7) background.add(new BackgroundBlock(i,j,1,"forestBorder",ClassicMode.images.borderLU));
				else if(i == 7 && j == 7) background.add(new BackgroundBlock(i,j,1,"forestBorder",ClassicMode.images.borderRU));
				else if(i == -1 && j == -1) background.add(new BackgroundBlock(i,j,1,"forestBorder",ClassicMode.images.borderLD));
				else if(i == 7 && j == -1) background.add(new BackgroundBlock(i,j,1,"forestBorder",ClassicMode.images.borderRD));
				else if(i>= 0 && i <=6 && j == 7){
					if(r == 0) background.add(new BackgroundBlock(i,j,1,"forestBorder",ClassicMode.images.borderU));
					if(r == 1) background.add(new BackgroundBlock(i,j,1,"forestBorder",ClassicMode.images.borderU1));
					if(r == 2) background.add(new BackgroundBlock(i,j,1,"forestBorder",ClassicMode.images.borderU2));
				}
				else if(i>= 0 && i <=6 && j == -1){
					if (r == 0) background.add(new BackgroundBlock(i,j,1,"forestBorder",ClassicMode.images.borderD));
					if (r == 1) background.add(new BackgroundBlock(i,j,1,"forestBorder",ClassicMode.images.borderD1));
					if (r == 2) background.add(new BackgroundBlock(i,j,1,"forestBorder",ClassicMode.images.borderD2));
				}
				else if(j>= 0 && j <=6 && i == -1){
					if(r == 0) background.add(new BackgroundBlock(i,j,1,"forestBorder",ClassicMode.images.borderL));
					if(r == 1) background.add(new BackgroundBlock(i,j,1,"forestBorder",ClassicMode.images.borderL1));
					if(r == 2) background.add(new BackgroundBlock(i,j,1,"forestBorder",ClassicMode.images.borderL2));
				}
				else if(j>= 0 && j <=6 && i == 7){
					if(r == 0) background.add(new BackgroundBlock(i,j,1,"forestBorder",ClassicMode.images.borderR));
					if(r == 1) background.add(new BackgroundBlock(i,j,1,"forestBorder",ClassicMode.images.borderR1));
					if(r == 2) background.add(new BackgroundBlock(i,j,1,"forestBorder",ClassicMode.images.borderR2));
				}
				else{
					if(r == 0) background.add(new BackgroundBlock(i,j,1,"forestBorder",ClassicMode.images.borderC));
					if(r == 1) background.add(new BackgroundBlock(i,j,1,"forestBorder",ClassicMode.images.borderC1));
					if(r == 2) background.add(new BackgroundBlock(i,j,1,"forestBorder",ClassicMode.images.borderC2));
				}
			}
		}
		for(int i=0;i<7;i++){ //BATTLEFIELD
			for(int j=0;j<7;j++){
				if(i== 1 && j == 1){
					blocks.add(new ArrowBlock(i,j,rand.nextInt(6)+1,"U","forest",ClassicMode.images.center));
					continue;
				}
				else if(i== 1 && j == 5){
					blocks.add(new ArrowBlock(i,j,rand.nextInt(6)+1,"D","forest",ClassicMode.images.center));
					continue;
				}
				else if(i== 3 && j == 3){
					blocks.add(new LightningBlock(i,j,rand.nextInt(6)+1,"forest",ClassicMode.images.center));
					continue;
				}
				else if(i== 5 && j == 5){
					blocks.add(new ArrowBlock(i,j,rand.nextInt(6)+1,"L","forest",ClassicMode.images.center));
					continue;
				}
				else if(i== 5 && j == 1){
					blocks.add(new ArrowBlock(i,j,rand.nextInt(6)+1,"R","forest",ClassicMode.images.center));
					continue;
				}
				p = rand.nextInt(100)+1;
				if(p >= 26) blocks.add(new NormalBlock(i,j,rand.nextInt(6)+1,"forest",ClassicMode.images.center));
				else{
					r = rand.nextInt(2)+1;
					if(r==1) blocks.add(new NormalBlock(i,j,rand.nextInt(6)+1,"forest",ClassicMode.images.center1));
					if(r==2) blocks.add(new NormalBlock(i,j,rand.nextInt(6)+1,"forest",ClassicMode.images.center2));
				}

				
			}
		}*/
		moves = new Array<Block>();
		blocks.clear();
		loadFile(name);
		touchPos = new Vector3();
		possibles = new Array<Sprite>();
		System.out.println("SIZE"+blocks.size);
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
			if(subline[0].equals("B")){
				System.out.println(line);
				if(!subline[4].equals("MO")){
					x = Integer.parseInt(subline[1]);
					y = Integer.parseInt(subline[2]);
				}

				if(subline[4].equals("LI")){
					blocks.add(new LightningBlock(x,y,rand.nextInt(6)+1,subline[3],ClassicMode.images.center));
				}
				else if(subline[4].equals("TO")){
					blocks.add(new TornadoBlock(x,y,rand.nextInt(6)+1,subline[3],ClassicMode.images.center));
				}
				else if(subline[4].equals("ARU")){
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
					System.out.println(block.pos_x+","+block.pos_y+", pressed? "+block.pressed+" points: "+block.points);
				}
			}
		}
		
	}

}