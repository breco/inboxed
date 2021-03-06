package com.inboxed.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class ImageController {
	//default
	public Texture defaultt;
	//BLOCKS
	public Texture center, center1, center2,blocked; // Board blocks
	public Texture center3, center4,center5;
	public Texture borderC, borderC1, borderC2; // Center BG blocks
	public Texture borderC3, borderC4, borderC5;
	public Texture borderD, borderD1, borderD2; // Bottom BG blocks
	public Texture borderL, borderL1, borderL2; // Left BG blocks
	public Texture borderR, borderR1, borderR2; // Right BG blocks
	public Texture borderU, borderU1, borderU2; // Top BG blocks
	public Texture borderLD, borderLU, borderRD, borderRU; // Corner BG blocks
	//POINTS
	public Array<Texture> normalPoints, specialPoints;
	//DICES
	public Array<Texture> dices;
	//NUMBERS
	public Array<Texture> numbers;
	//HUD
	public Texture targetMove;
	public Texture wrong,moveBG,power,end,surrender;
	public ImageController(String name){
		System.out.println("wea");
		defaultt = new Texture(Gdx.files.internal("blocks/default.png"));
		if (Gdx.files.internal("blocks/"+name+"/0.png").exists()) center =   new Texture(Gdx.files.internal("blocks/"+name+"/0.png"));
		if (Gdx.files.internal("blocks/"+name+"/1.png").exists()) center1 =  new Texture(Gdx.files.internal("blocks/"+name+"/1.png"));
		if (Gdx.files.internal("blocks/"+name+"/2.png").exists()) center2 =  new Texture(Gdx.files.internal("blocks/"+name+"/2.png"));
		if (Gdx.files.internal("blocks/"+name+"/3.png").exists()) center3 =  new Texture(Gdx.files.internal("blocks/"+name+"/3.png"));
		if (Gdx.files.internal("blocks/"+name+"/4.png").exists()) center4 =  new Texture(Gdx.files.internal("blocks/"+name+"/4.png"));
		if (Gdx.files.internal("blocks/"+name+"/5.png").exists()) center5 =  new Texture(Gdx.files.internal("blocks/"+name+"/5.png"));
		if (Gdx.files.internal("blocks/"+name+"/blocked.png").exists()) blocked =  new Texture(Gdx.files.internal("blocks/"+name+"/blocked.png"));
		if (Gdx.files.internal("blocks/"+name+"/borderC0.png").exists()) borderC =  new Texture(Gdx.files.internal("blocks/"+name+"/borderC0.png"));
		if (Gdx.files.internal("blocks/"+name+"/borderC1.png").exists()) borderC1 = new Texture(Gdx.files.internal("blocks/"+name+"/borderC1.png"));
		if (Gdx.files.internal("blocks/"+name+"/borderC2.png").exists()) borderC2 = new Texture(Gdx.files.internal("blocks/"+name+"/borderC2.png"));
		if (Gdx.files.internal("blocks/"+name+"/borderC3.png").exists()) borderC3 = new Texture(Gdx.files.internal("blocks/"+name+"/borderC3.png"));
		if (Gdx.files.internal("blocks/"+name+"/borderC4.png").exists()) borderC4 = new Texture(Gdx.files.internal("blocks/"+name+"/borderC4.png"));
		if (Gdx.files.internal("blocks/"+name+"/borderC5.png").exists()) borderC5 = new Texture(Gdx.files.internal("blocks/"+name+"/borderC5.png"));
		if (Gdx.files.internal("blocks/"+name+"/borderD0.png").exists()) borderD =  new Texture(Gdx.files.internal("blocks/"+name+"/borderD0.png"));
		if (Gdx.files.internal("blocks/"+name+"/borderD1.png").exists()) borderD1 = new Texture(Gdx.files.internal("blocks/"+name+"/borderD1.png"));
		if (Gdx.files.internal("blocks/"+name+"/borderD1.png").exists()) borderD2 = new Texture(Gdx.files.internal("blocks/"+name+"/borderD2.png"));
		if (Gdx.files.internal("blocks/"+name+"/borderL0.png").exists()) borderL =  new Texture(Gdx.files.internal("blocks/"+name+"/borderL0.png"));
		if (Gdx.files.internal("blocks/"+name+"/borderL1.png").exists()) borderL1 = new Texture(Gdx.files.internal("blocks/"+name+ "/borderL1.png"));
		if (Gdx.files.internal("blocks/"+name+"/borderL2.png").exists()) borderL2 = new Texture(Gdx.files.internal("blocks/"+name+ "/borderL2.png"));
		if (Gdx.files.internal("blocks/"+name+"/borderR0.png").exists()) borderR =  new Texture(Gdx.files.internal("blocks/"+name+"/borderR0.png"));
		if (Gdx.files.internal("blocks/"+name+"/borderR1.png").exists()) borderR1 = new Texture(Gdx.files.internal("blocks/"+name+"/borderR1.png"));
		if (Gdx.files.internal("blocks/"+name+"/borderR2.png").exists()) borderR2 = new Texture(Gdx.files.internal("blocks/"+name+"/borderR2.png"));
		if (Gdx.files.internal("blocks/"+name+"/borderU0.png").exists()) borderU =  new Texture(Gdx.files.internal("blocks/"+name+"/borderU0.png"));
		if (Gdx.files.internal("blocks/"+name+"/borderU1.png").exists()) borderU1 = new Texture(Gdx.files.internal("blocks/"+name+"/borderU1.png"));
		if (Gdx.files.internal("blocks/"+name+"/borderU2.png").exists()) borderU2 = new Texture(Gdx.files.internal("blocks/"+name+"/borderU2.png"));
		if (Gdx.files.internal("blocks/"+name+"/borderLD.png").exists()) borderLD = new Texture(Gdx.files.internal("blocks/"+name+"/borderLD.png"));
		if (Gdx.files.internal("blocks/"+name+"/borderLU.png").exists()) borderLU = new Texture(Gdx.files.internal("blocks/"+name+"/borderLU.png"));
		if (Gdx.files.internal("blocks/"+name+"/borderRD.png").exists()) borderRD = new Texture(Gdx.files.internal("blocks/"+name+"/borderRD.png"));
		if (Gdx.files.internal("blocks/"+name+"/borderRU.png").exists()) borderRU = new Texture(Gdx.files.internal("blocks/"+name+"/borderRU.png"));
		normalPoints = new Array<Texture>();
		specialPoints = new Array<Texture>();
		dices = new Array<Texture>();
		numbers = new Array<Texture>();
		for(int i = 1; i<=6;i++){
			normalPoints.add(new Texture(Gdx.files.internal("points/point-"+i+".png")));
			specialPoints.add(new Texture(Gdx.files.internal("points/specialPoint-"+i+".png")));
			dices.add(new Texture(Gdx.files.internal("dices/dice-"+i+".png")));
		}
		for(int i = 0; i<=9;i++){
			numbers.add(new Texture(Gdx.files.internal("numbers/"+i+".png")));
		}
		targetMove = new Texture(Gdx.files.internal("blocks/targetMove.png"));
		wrong = new Texture(Gdx.files.internal("hud/wrong.png"));
		moveBG = new Texture(Gdx.files.internal("hud/moveBg.png"));
		power = new Texture(Gdx.files.internal("hud/power.png"));
		end = new Texture(Gdx.files.internal("hud/end.png"));
		surrender = new Texture(Gdx.files.internal(("hud/surrender.png")));
	}
	public Texture getTexture(String code){
		if(code.equals("DEFAULT")) return defaultt;
		else if(code.equals("C0")) return center;
		else if(code.equals("C1")) return center1;
		else if(code.equals("C2")) return center2;
		else if(code.equals("C3")) return center3;
		else if(code.equals("C4")) return center4;
		else if(code.equals("C5")) return center5;
		else if(code.equals("BC0")) return borderC;
		else if(code.equals("BC1")) return borderC1;
		else if(code.equals("BC2")) return borderC2;
		else if(code.equals("BC3")) return borderC3;
		else if(code.equals("BC4")) return borderC4;
		else if(code.equals("BC5")) return borderC5;
		else if(code.equals("BD0")) return borderD;
		else if(code.equals("BD1")) return borderD1;
		else if(code.equals("BD2")) return borderD2;
		else if(code.equals("BL0")) return borderL;
		else if(code.equals("BL1")) return borderL1;
		else if(code.equals("BL2")) return borderL2;
		else if(code.equals("BLD")) return borderLD;
		else if(code.equals("BLU")) return borderLU;
		else if(code.equals("BR0")) return borderR;
		else if(code.equals("BR1")) return borderR1;
		else if(code.equals("BR2")) return borderR2;
		else if(code.equals("BRU")) return borderRU;
		else if(code.equals("BRD")) return borderRD;
		else if(code.equals("BU0")) return borderU;
		else if(code.equals("BU1")) return borderU1;
		else if(code.equals("BU2")) return borderU2;
		else return null;
	}
}
