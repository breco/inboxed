package com.inboxed.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Array;
import com.inboxed.characters.Character;
import com.inboxed.helpers.ClassicHud;
import com.inboxed.helpers.ImageController;
import com.inboxed.helpers.RoundController;
import com.inboxed.inputs.MyGestures;
import com.inboxed.main.MainGame;
import com.inboxed.stages.AncientRuins;
import com.inboxed.stages.BigCity;
import com.inboxed.stages.Clouds;
import com.inboxed.stages.ExplosiveFactory;
import com.inboxed.stages.Forest;
import com.inboxed.stages.IceRink;
import com.inboxed.stages.RapidRiver;
import com.inboxed.stages.Snowland;
import com.inboxed.stages.Stage;


public class ClassicMode implements Screen{

	public MainGame game;
	public static OrthographicCamera cam;
	public static OrthographicCamera cam2;
	//TEST
	//public static Blocks blocks;
	public static RoundController round;
	public ClassicHud hud;
	public static ImageController images;
	public static Stage stage;
	//CAMERA PAN LIMITS FOR A 11x11
	//public int PANLIMITL = -2, PANLIMITR = 650;
	//public int PANLIMITU = 650, PANLIMITD = 30;
	public int PANLIMITL = -2, PANLIMITR = 850; // cloud stage
	public int PANLIMITU = 900, PANLIMITD = 30; // cloud stage
	public float x,y;

	public Stage getStage(String name){
		if(name.equals("forest")) return new Forest(name,cam);
		else if(name.equals("snowland")) return new Snowland(name,cam);
        else if(name.equals("clouds")) return new Clouds(name,cam);
        else if(name.equals("iceRink")) return new IceRink(name,cam);
        else if(name.equals("bigCity")) return new BigCity(name,cam);
        else if(name.equals("rapidRiver")) return new RapidRiver(name,cam);
        else if(name.equals("ancientRuins")) return new AncientRuins(name,cam);
        else if(name.equals("explosiveFactory")) return new ExplosiveFactory(name,cam);
        else return null;
	}
	public ClassicMode(final MainGame game,Array<String> playerNames, String stageName) {
        this.game = game;
        
        
        cam = new OrthographicCamera();
        cam.setToOrtho(false, MainGame.width, MainGame.height);
        cam2 = new OrthographicCamera();
        cam2.setToOrtho(false, MainGame.width, MainGame.height);
        
        images = new ImageController(stageName);

        stage = getStage(stageName);
		round = new RoundController(stage,cam);
        stage.setBounds(this);

        int i = 0;
		playerNames.shuffle();
        for(String name : playerNames){
        	round.addPlayer(name,i,stage.blocks.size);
        	i++;
        }
        round.playersPlaying = playerNames.size;
        hud = new ClassicHud(round.players);
        round.addHUD(hud);
        round.setTurn(false);
        hud.setCam(cam);
        cam.zoom -= 0.3; //0.5

    }
	
	public void input(){
		if (MyGestures.isPan()){
			x = -MyGestures.panX;
			y = MyGestures.panY;
			if(PANLIMITL > cam.position.x + x|| cam.position.x + x > PANLIMITR)
				x = 0;
			if( PANLIMITD > cam.position.y + y || cam.position.y + y > PANLIMITU){
				y = 0;
			}
			cam.translate(x,y);

    	
	    }
	    if(MyGestures.isZoom()){
	    	if(MyGestures.zoomOut) cam.zoom += 0.005;
	    	else cam.zoom -= 0.005;
	    }
	    
	}
	

	@Override
	public void render(float delta) {
		//System.out.println(Gdx.graphics.getFramesPerSecond());
		// 1)Clear the screen
        Gdx.gl.glClearColor(255,255,255,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
     // 2)Input handling
        if(!hud.movingCam && !(hud.current.moving && hud.current.dir.equals("TO"))){
        	input();
        	stage.input(cam);
        	if(stage.blocks.special == null){
        		round.input(cam2);
        	}
        }
        
        // 3)Update system
        	
        // 3.1)---> Update Cam
        cam.update();
        cam2.update();
        game.batch.setProjectionMatrix(cam.combined);
        //game.batch.setProjectionMatrix(cam2.combined);
        
        // 4)Draw
        game.batch.begin();
        stage.draw(game.batch);
        round.draw(game.batch);
        game.batch.end();
        
        // 4.2) Draw HUD
        game.hudBatch.begin();
        round.drawHUD(game.hudBatch);
        hud.draw(game.hudBatch);
        game.hudBatch.end();
        
        // 3.2)---> Update Game
        stage.update();
        if(stage.blocks.special == null){
        	round.update();
            hud.update(cam);
        }
        if(round.playersPlaying <= 1){
			for(Character chr : round.players){
				if(!chr.lose) game.setScreen(new WinnerScreen(game,chr.name));
			}
		}
     
	}

	@Override
	public void resize(int width, int height) {

		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	
}
