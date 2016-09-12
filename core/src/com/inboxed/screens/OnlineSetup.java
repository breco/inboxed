package com.inboxed.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.inboxed.inputs.MyGestures;
import com.inboxed.main.MainGame;
import com.inboxed.setuphud.CharacterSelect;

/**
 * Created by victor on 6/11/16.
 */
public class OnlineSetup implements Screen {
    public MainGame game;
    public static OrthographicCamera cam;
    public Vector3 vec;
    //
    public static CharacterSelect characterSelect;
    public OnlineSetup(MainGame game){

        this.game = game;
        cam = new OrthographicCamera();
        vec = new Vector3();
        cam.setToOrtho(false, MainGame.width, MainGame.height);
        //
        characterSelect = new CharacterSelect(1);
        characterSelect.showing = true;

    }
    public void input(){
        if (MyGestures.isTap()){
            vec.set(MyGestures.tapX,MyGestures.tapY,0);
            cam.unproject(vec);
            if(characterSelect.showing){
                characterSelect.input(vec);
                return;
            }
        }
    }
    public void update(){
        if(characterSelect.okPressed){

            //join room screen
            game.setScreen(new JoinRoom(game,characterSelect.playerNames.first()));
        }
    }
    public void draw(){
        if(characterSelect.showing) characterSelect.draw(game.batch);
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
