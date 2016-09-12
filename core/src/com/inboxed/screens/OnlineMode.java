package com.inboxed.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Array;
import com.inboxed.blocks.Block;
import com.inboxed.characters.Character;
import com.inboxed.characters.Chilly;
import com.inboxed.characters.Rocky;
import com.inboxed.characters.Simirror;
import com.inboxed.characters.Tac;
import com.inboxed.characters.Wheelie;
import com.inboxed.helpers.ClassicHud;
import com.inboxed.helpers.ImageController;
import com.inboxed.helpers.OnlineTurnController;
import com.inboxed.helpers.Pair;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by victor on 6/5/16.
 */
public class OnlineMode implements Screen {

    public MainGame game;
    public static OrthographicCamera cam;
    public static OrthographicCamera cam2;
    //public static Blocks blocks;
    public static RoundController round;
    public ClassicHud hud;
    public static Stage stage;
    //CAMERA PAN LIMITS FOR A 11x11
    //public int PANLIMITL = -2, PANLIMITR = 650;
    //public int PANLIMITU = 650, PANLIMITD = 30;
    public int PANLIMITL = -2, PANLIMITR = 850; // cloud stage
    public int PANLIMITU = 900, PANLIMITD = 30; // cloud stage
    public float x,y;
    private Socket socket;
    //online
    public HashMap<String, String[]> playerData;
    public boolean myTurn = false;
    public String id;
    // ROUND TEST VARIABLES
    public Array<com.inboxed.characters.Character> players;
    public OnlineTurnController turn;
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
    public OnlineMode(String id, final MainGame game, Socket socket, HashMap<String, String[]> players, String stageName) {
        this.id = id;
        this.game = game;
        this.socket = socket;
        configSocketEvents();
        cam = new OrthographicCamera();
        cam.setToOrtho(false, MainGame.width, MainGame.height);
        cam2 = new OrthographicCamera();
        cam2.setToOrtho(false, MainGame.width, MainGame.height);

        ClassicMode.images = new ImageController(stageName); //ARREGLAR EN ALGUN MOMENTO DE LA VIDA

        stage = getStage(stageName);
        round = new RoundController(stage,cam);
        stage.setBounds(this);
        socket.emit("getStageNumbers");



        // CREATE PLAYERS FROM PLAYERDATA
        playerData = players;
        this.players = new Array<Character>();
        Iterator it = players.entrySet().iterator();
        int i = 0;
        int x,y;
        Character chr;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            String name = ((String[]) pair.getValue())[1];
            String idd = (String) pair.getKey();
            x = stage.blocks.positions.get(i*2);
            y = stage.blocks.positions.get(i*2+1);
            System.out.println("NAME: "+ name + "with id: "+idd);
            if(name.equals("rocky")){
                chr = new Rocky(x,y,"rocky",stage,round);
            }
            else if(name.equals("simirror")){
                chr = new Simirror(x,y,"simirror",stage,round);
            }
            else if(name.equals("chilly")){
                chr = new Chilly(x,y,"chilly",stage,round);
            }
            else if(name.equals("tac")){
                chr = new Tac(x,y,"tac",stage,round);
            }
            else if(name.equals("wheelie")){
                chr = new Wheelie(x,y,"wheelie",stage,round);
            }
            else{
                chr = new Rocky(x,y,name,stage,round);
            }
            chr.setID(idd);
            this.players.add(chr);
            i++;
        }

        //round.playersPlaying = playerNames.size;
        hud = new ClassicHud(this.players);
        turn = new OnlineTurnController(this.players.get(0),round, cam, socket);
        //round.addHUD(hud);
        //round.setTurn(false);
        //hud.setCam(cam);
        cam.zoom -= 0.3; //0.5
        socket.emit("getFirstTurn");
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
        if(myTurn) turn.input(cam2);

    }


    @Override
    public void render(float delta) {
        //System.out.println(Gdx.graphics.getFramesPerSecond());
        // 1)Clear the screen
        Gdx.gl.glClearColor(255,255,255,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // 2)Input handling
        /*if(!hud.movingCam && !(hud.current.moving && hud.current.dir.equals("TO"))){
            input();
            stage.input(cam);
            if(stage.blocks.special == null){
                round.input(cam2);
            }
        }*/
        input();
        // 3)Update system

        // 3.1)---> Update Cam
        cam.update();
        cam2.update();
        game.batch.setProjectionMatrix(cam.combined);
        //game.batch.setProjectionMatrix(cam2.combined);

        // 4)Draw
        game.batch.begin();
        stage.draw(game.batch);
        for(Character chr : players){
            chr.draw(game.batch);
        }
        //round.draw(game.batch);
        turn.draw(game.batch);
        game.batch.end();

        // 4.2) Draw HUD
        game.hudBatch.begin();
        //round.drawHUD(game.hudBatch);

        hud.draw(game.hudBatch);
        turn.drawHUD(game.hudBatch);
        game.hudBatch.end();

        // 3.2)---> Update Game
        stage.update();
        for(Character chr : players){
            chr.update();
        }
        turn.update();
        if(turn.finish) myTurn = false;
        hud.update(cam);
        /*if(stage.blocks.special == null){
            //round.update();
            //hud.update(cam);
        }
        if(round.playersPlaying <= 1){
            for(com.inboxed.characters.Character chr : round.players){
                if(!chr.lose) game.setScreen(new WinnerScreen(game,chr.name));
            }
        }*/

    }

    public void configSocketEvents() {
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Gdx.app.log("SocketIO", "Connected");

            }
        }).on("playerDisconnected", new Emitter.Listener() {
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    String name = data.getString("name");
                    System.out.println("PLAYER DISCONNECTED: " + name);
                    //players.remove(id);
                } catch (JSONException e) {
                    Gdx.app.log("SocketIO", "Error getting New Player ID");
                }
            }
        }).on("updateDice", new Emitter.Listener(){
            public void call(Object... args){
                JSONObject data = (JSONObject) args[0];
                try{
                    int value = Integer.parseInt(data.getString("number"));
                    System.out.println("DICE NUMBER OBTAINED: " + value);
                    turn.dice.setNumber(value);
                }
                catch(JSONException e){

                }
            }
        }).on("startTurn", new Emitter.Listener(){
            public void call(Object... args){
                JSONObject data = (JSONObject) args[0];
                try{
                    Character chr = getCharacter(data.getString("id"));
                    System.out.println("with id: "+data.getString("id"));
                    System.out.println("Obtained character:" + chr.name);
                    if(data.getString("id").equals(id)){
                        myTurn = true;
                        System.out.println("ITS MY TURN");
                    }
                    if(chr != null) turn = new OnlineTurnController(chr,round, cam, socket);
                }
                catch(JSONException e){

                }
            }
        }).on("setStageNumbers", new Emitter.Listener(){
            public void call(Object... args){
                JSONArray objects = (JSONArray) args[0];
                try{
                    System.out.println("get stage numbers");
                    for(int i = 0; i < objects.length(); i++){
                        int t = objects.getInt(i);
                        System.out.println(t);
                        stage.blocks.blocks.get(i).restore(t);
                    }
                }catch(JSONException e){

                }
            }
        }).on("moveCharacter", new Emitter.Listener(){
            public void call(Object... args){
                JSONObject data = (JSONObject) args[0];
                try{
                    System.out.println("moving character");
                    Character chr = getCharacter(data.getString("id"));
                    Pair<Block,String> pair = new Pair<Block, String>(stage.blocks.getBlock(data.getInt("blockX"),data.getInt("blockY")),data.getString("dir"));
                    chr.changeDir(pair);

                }catch(JSONException e){
                    Gdx.app.log("SocketIO", "Error getting character movement");
                }
            }
        })
        ;
    }

    public Character getCharacter(String id){
        for(Character chr : players){
            if(id.equals(chr.id)) return chr;
        }
        return null;
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
