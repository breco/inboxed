package com.inboxed.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.inboxed.inputs.MyGestures;
import com.inboxed.main.MainGame;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by victor on 6/11/16.
 */

public class JoinRoom implements Screen {
    public MainGame game;
    public static OrthographicCamera cam;
    public Vector3 vec;
    public BitmapFont font;
    //
    private Socket socket;
    HashMap<String,String[]> players;
    private boolean startGame = false;
    public String name,stage = "snowland";
    private String id;
    //
    private boolean finalWait = false;
    private int waitingTimer = 0;
    private int MAXWAITINGTIMER = 500;
    public JoinRoom(MainGame game, String name){
        this.name = name;
        this.game = game;
        cam = new OrthographicCamera();
        vec = new Vector3();
        cam.setToOrtho(false, MainGame.width, MainGame.height);
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(2);
        connectSocket();
        configSocketEvents();
        players = new HashMap<String, String[]>();
    }
    public void connectSocket(){
        try{
            socket = IO.socket("http://localhost:1313");
            socket.connect();
            System.out.println("connecting...");
            JSONObject data = new JSONObject();
            try{
                data.put("name",name);
                socket.emit("addName", data);
            }
            catch(JSONException e){
                Gdx.app.log("Socket.IO", "Error sending update");
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
    public void input(){
        if (MyGestures.isTap()){
            vec.set(MyGestures.tapX, MyGestures.tapY, 0);
            cam.unproject(vec);
        }
    }
    public void update(){
        if(finalWait){
            waitingTimer++;
            if(waitingTimer == MAXWAITINGTIMER/2) socket.emit("getPlayersShuffled");
            if(waitingTimer >= MAXWAITINGTIMER){
                waitingTimer = 0;
                finalWait = false;
                startGame = true;
            }
        }
        if(startGame){
            Array<String> names = new Array<String>();
            Iterator it = players.entrySet().iterator();
            int i = 0;
            while (it.hasNext()) {
                Map.Entry e = (Map.Entry)it.next();
                names.add(((String[]) e.getValue())[1]);
                i++;
            }
            game.setScreen(new OnlineMode(id,game,socket,players,stage));
        }
    }
    public void draw(){
        Iterator it = players.entrySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry)it.next();
            font.draw(game.batch,e.getKey() + " " + ((String[]) e.getValue())[1],100,100*(i+1));
            i++;
        }
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
    public void configSocketEvents(){
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Gdx.app.log("SocketIO", "Connected");

            }
        }).on("socketID", new Emitter.Listener() {
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    String pid = data.getString("id");
                    id = pid;
                    String number = data.getString("number");

                    Gdx.app.log("SocketIO", "My ID: " + pid + "    " + number);
                    String[] info = {number, name, "me"};
                    players.put(pid, info);
                } catch (JSONException e) {
                    Gdx.app.log("SocketIO", "Error getting ID");
                }
            }
        }).on("playerDisconnected", new Emitter.Listener(){
            public void call(Object... args){
                JSONObject data = (JSONObject) args[0];
                try {
                    String id = data.getString("id");
                    players.remove(id);
                } catch (JSONException e) {
                    Gdx.app.log("SocketIO", "Error getting New Player ID");
                }
            }
        }).on("getPlayers", new Emitter.Listener(){
            public void call(Object... args){
                JSONArray objects = (JSONArray) args[0];
                try{
                    for(int i = 0; i < objects.length(); i++){
                        String[] info = {objects.getJSONObject(i).getString("number"), objects.getJSONObject(i).getString("name"), "other"};
                        players.put(objects.getJSONObject(i).getString("id"), info);
                    }
                }catch(JSONException e){

                }
            }
        }).on("newPlayer", new Emitter.Listener(){
            public void call(Object... args){
                JSONObject data = (JSONObject) args[0];
                try{
                    System.out.println("new player!");
                    String[] info = {data.getString("number"), data.getString("name"),"other"};
                    players.put(data.getString("id"), info);
                }
                catch(JSONException e){
                    Gdx.app.log("SocketIO", "Error getting New Player ID");
                }
            }
        }).on("enoughPlayers", new Emitter.Listener(){
            public void call(Object... args){
                finalWait = true;
            }
        }).on("setPlayersShuffled", new Emitter.Listener() {
            public void call(Object... args) {
                JSONArray objects = (JSONArray) args[0];
                try {
                    players.clear();
                    for (int i = 0; i < objects.length(); i++) {
                        String[] info = {objects.getJSONObject(i).getString("number"), objects.getJSONObject(i).getString("name")};
                        players.put(objects.getJSONObject(i).getString("id"), info);
                    }
                } catch (JSONException e) {
                    Gdx.app.log("SocketIO", "Error getting all players shuffled");
                }
            }
        })
        ;
    }
}
