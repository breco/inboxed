package com.inboxed.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.inboxed.inputs.MyGestures;
import com.inboxed.main.MainGame;
import com.inboxed.setuphud.CharacterSelect;
import com.inboxed.setuphud.PlayerNumber;
import com.inboxed.setuphud.StageSelect;

public class ClassicSetup implements Screen {

	public MainGame game;
	public static OrthographicCamera cam;
	public Sprite forest,snowland;
	public Vector3 vec;
	public String stage;
	//
	public PlayerNumber playerNumber;
	public static CharacterSelect characterSelect;
	public static StageSelect stageSelect;
	public ClassicSetup(MainGame game){

		this.game = game;
		cam = new OrthographicCamera();
		vec = new Vector3();
        cam.setToOrtho(false, MainGame.width, MainGame.height);
        forest = new Sprite(new Texture(Gdx.files.internal("blocks/forest/blocked.png")));
        snowland = new Sprite(new Texture(Gdx.files.internal("blocks/snowland/blocked.png")));
        forest.setBounds(100, 200, MainGame.SPRITESIZE, MainGame.SPRITESIZE);
        snowland.setBounds(100, 100, MainGame.SPRITESIZE, MainGame.SPRITESIZE);
        stage = "";
        //
        playerNumber = new PlayerNumber();
        characterSelect = new CharacterSelect(4);
        stageSelect = new StageSelect();

	}
	public void input(){
		if (MyGestures.isTap()){
			vec.set(MyGestures.tapX,MyGestures.tapY,0);
			cam.unproject(vec);
			if(playerNumber.showing){
				playerNumber.input(vec);
				return;
			}
			if(characterSelect.showing){
				characterSelect.input(vec);
				return;
			}
			if(stageSelect.showing){
				stageSelect.input(vec);
				return;
			}
			
	    }
	}
	public void update(){
		if(!stageSelect.current.equals("") && !stageSelect.showing){
			game.setScreen(new ClassicMode(game,characterSelect.playerNames,stageSelect.current));
		}
	}
	public void draw(){
		if(playerNumber.showing) playerNumber.draw(game.batch);
		if(characterSelect.showing) characterSelect.draw(game.batch);
		if(stageSelect.showing) stageSelect.draw(game.batch);

	}

	@Override
	public void render(float delta) {
		//System.out.println(Gdx.graphics.getFramesPerSecond());
				// 1)Clear the screen
		        Gdx.gl.glClearColor(255,255,255,0);
		        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		        
		        // 2)Input handling
		        input();
		        
		        // 3)Update system
		        	
		        // 3.1)---> Update Cam
		        cam.update();
		        game.batch.setProjectionMatrix(cam.combined);
		        
		        // 4)Draw
		        game.batch.begin();
		        draw();
		        game.batch.end();
		        
		        
		        // 3.2)---> Update Game
		        
		        update();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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
		
	}
	public void show() {

		
	}
}
