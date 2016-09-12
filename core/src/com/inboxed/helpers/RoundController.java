package com.inboxed.helpers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.inboxed.blocks.Blocks;
import com.inboxed.characters.Character;
import com.inboxed.characters.Chilly;
import com.inboxed.characters.Rocky;
import com.inboxed.characters.Simirror;
import com.inboxed.characters.Tac;
import com.inboxed.characters.Wheelie;
import com.inboxed.inputs.MyGestures;
import com.inboxed.stages.Stage;


public class RoundController {
	public int roundNumber;
	public Array<Character> players;
	public TurnController turn;
	public int turnNumber;
	public int playersPlaying;
	public ClassicHud hud;
    private boolean drawTouched;
    private int dt_timer,MAX_DT_TIMER;
	public Array<Integer> positions;
	private Blocks blocks;
    public OrthographicCamera cam;
    public Stage stage;
	public RoundController(Stage stage, OrthographicCamera cam){
        this.stage = stage;
        this.cam = cam;
		this.blocks = stage.blocks;
		players = new Array<Character>();
        positions = blocks.positions;
		roundNumber = 1;
		turnNumber =1;
        drawTouched = false;
        dt_timer = 0;
        MAX_DT_TIMER = 40;
	}
	public void update(){
        if(drawTouched) dt_timer++;
        if(drawTouched && dt_timer < MAX_DT_TIMER) return;
        else{
            dt_timer = 0;
            drawTouched = false;
        }


		turn.update();
		if(turn.finish) nextTurn();
		for(Character player : players){
			player.update();		
		}

	}
	public void addHUD(ClassicHud hud){
		this.hud = hud;
	}
	public void addPlayer(String name, int number,String size){
        int x = positions.get(number*2);
        int y = positions.get(number*2+1);
		if(name.equals("rocky")){
			players.add(new Rocky(x,y,"rocky",stage,this));
		}
		else if(name.equals("simirror")){
			players.add(new Simirror(x,y,"simirror",stage,this));
		}
		else if(name.equals("chilly")){
			players.add(new Chilly(x,y,"chilly",stage,this));
		}
        else if(name.equals("tac")){
            players.add(new Tac(x,y,"tac",stage,this));
        }
		else if(name.equals("wheelie")){
			players.add(new Wheelie(x,y,"wheelie",stage,this));
		}
		else{
            players.add(new Rocky(x,y,name,stage,this));
		}
	}
	public void nextTurn(){
		turnNumber++;
		if(turnNumber > players.size){
			turnNumber =1;
			roundNumber++;
            System.out.println("ROUND "+roundNumber);
			stage.effect();
		}
		setTurn(true);
		
	}
	public void setTurn(boolean changeHud){
		turn = new TurnController(players.get(turnNumber-1), this, cam);
		while(turn.current.lose){
			turnNumber++;
			if(turnNumber > players.size){
				turnNumber = 1;
				roundNumber++;
				System.out.println("ROUND "+roundNumber);
				stage.effect();
			}
			turn = new TurnController(players.get(turnNumber-1), this, cam);
		}
		if (changeHud)
			hud.startMoveCam();
	}
	public void input(OrthographicCamera cam2){
		turn.input(cam2);

        if(MyGestures.isLongPress()){
            drawTouched = true;
        }
        else {

            if (dt_timer >= MAX_DT_TIMER){
                dt_timer = 0;
                drawTouched = false;
            }
        }
	}
	public void draw(SpriteBatch batch){
		turn.draw(batch);
		for(Character player : players){
			player.draw(batch);
		}

        if(drawTouched && stage.blocks.longTouched.pos_x == turn.current.block.pos_x && stage.blocks.longTouched.pos_y == turn.current.block.pos_y){

            turn.current.block.draw(batch);
        }

	}
	public void drawHUD(SpriteBatch batch){
		turn.drawHUD(batch);
	}
}
