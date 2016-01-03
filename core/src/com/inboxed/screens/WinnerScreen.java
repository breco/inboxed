package com.inboxed.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.inboxed.inputs.MyGestures;
import com.inboxed.main.MainGame;



/**
 * Created by victor on 1/2/16.
 */
public class WinnerScreen implements Screen {

    public MainGame game;
    public static OrthographicCamera cam;
    public Vector3 vec;
    public Sprite ok, winner;
    public BitmapFont font;
    public String winnerName;
    public WinnerScreen(MainGame game, String winner){
        winnerName = winner;
        ok = new Sprite(new Texture(Gdx.files.internal("setupHud/ok.png")));
        ok.setBounds(MainGame.width/2, 10, MainGame.SPRITESIZE, MainGame.SPRITESIZE);
        this.winner = new Sprite(new Texture(Gdx.files.internal("characters/"+winner+".png")));
        this.winner.setBounds(MainGame.width/2,MainGame.height/2,MainGame.SPRITESIZE,MainGame.SPRITESIZE);
        font = new BitmapFont();
        font.getData().setScale(2);
        font.setColor(Color.BLACK);
        this.game = game;
        cam = new OrthographicCamera();
        vec = new Vector3();
        cam.setToOrtho(false, MainGame.width, MainGame.height);


    }
    public void input(){
        if (MyGestures.isTap()){
            vec.set(MyGestures.tapX,MyGestures.tapY,0);
            cam.unproject(vec);
            if(ok.getBoundingRectangle().contains(vec.x,vec.y)) game.setScreen(new ClassicSetup(game));

        }
    }
    public void update(){

    }
    public void draw(){
        ok.draw(game.batch);
        winner.draw(game.batch);
        font.draw(game.batch,winnerName+" wins!",MainGame.width/2 - 100, MainGame.height - 30);
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
