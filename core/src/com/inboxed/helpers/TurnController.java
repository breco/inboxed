package com.inboxed.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.inboxed.blocks.Block;
import com.inboxed.buttons.AbilityButton;
import com.inboxed.buttons.DiceButton;
import com.inboxed.buttons.EndButton;
import com.inboxed.buttons.SurrenderButton;
import com.inboxed.characters.Character;
import com.inboxed.inputs.MyGestures;
import com.inboxed.main.MainGame;
import com.inboxed.screens.ClassicMode;

public class TurnController {
	
	public Character current;
	public Array<Pair<Block,String>> possibleMoves;
	public Array<Sprite> possibleSprites;
	public Texture texture;
	
	public boolean finish;
	//HUD
	public DiceButton dice;
	public AbilityButton abilityButton;
	public EndButton endButton;
	public SurrenderButton surrenderButton;
	//input
	Vector3 vec;
	public TurnController(Character chara){
		current = chara;
		possibleMoves = current.getPossibleMoves();
		possibleSprites = new Array<Sprite>();
		texture = new Texture(Gdx.files.internal("blocks/targetMove.png"));
		setSprites();
		dice = new DiceButton("dice");
		abilityButton = new AbilityButton("power");
		endButton = new EndButton("end");
		surrenderButton = new SurrenderButton();
		vec = new Vector3();
				
	}
	public void setSprites(){
		possibleSprites.clear();
		Sprite sprite;
		for(Pair<Block,String> pair : possibleMoves){
			sprite = new Sprite(texture);
			sprite.setBounds(pair.getBlock().pos_x*MainGame.SPRITESIZE, pair.getBlock().pos_y*MainGame.SPRITESIZE,MainGame.SPRITESIZE,MainGame.SPRITESIZE);
			possibleSprites.add(sprite);
		}
	}
	public void input(){
		if(current.moving) return;
		if(current.doingAbility) return;
		if (MyGestures.isTap()){
			vec.set(MyGestures.tapX,MyGestures.tapY,0);

			ClassicMode.cam2.unproject(vec);
			if(dice.touched(vec)){ // ROLL THE DICE
				if(dice.rolled) return;
				dice.roll();
				current.changeMoves(dice.getValue());
				return;
			}
			else if(abilityButton.touched(vec)){ // USE ABILITY
				if(abilityButton.used || !current.canAbility()) return;
				abilityButton.used = true;
				current.startAbility();
				return;
			}
			else if(endButton.touched(vec)){ // END TURN
				if(current.moves != 0 || !dice.rolled) return;
				finish = true;
				return;
			}
			else if(surrenderButton.touched(vec)){
				current.lose = true;
				ClassicMode.round.playersPlaying--;
				finish = true;
				return;
			}
			vec.set(MyGestures.tapX,MyGestures.tapY,0);
			ClassicMode.cam.unproject(vec);
			for(Pair<Block,String> pair : possibleMoves){
				if(pair.getBlock().touched(vec)){
					current.changeDir(pair);
				}
			}
		}
	}
	public void update(){
		if(current.doingAbility){
			current.ability();
			return;
		}
		if(current.dir.equals(" ")){
			possibleMoves = current.getPossibleMoves();
			if(current.moving) return;
			if(possibleMoves.size == 0 && (abilityButton.used || !current.canAbility()) && current.moves != 0){ // END TURN AND LOSE
				current.lose = true;
				ClassicMode.round.playersPlaying--;
				finish = true;
				return;
			}
			else if(possibleMoves.size == 0 && current.status.contains("arrow")
					&& (abilityButton.used || !current.canAbility()) && current.moves != 0){
				current.lose = true;
				ClassicMode.round.playersPlaying--;

				finish = true;
			}
			setSprites();
		}
		if(current.moving) possibleSprites.clear();
		dice.update();
		if(dice.rolled && current.moves == 0 && (abilityButton.used || !current.canAbility() && !current.moving)){
			finish = true; // END TURN
		}
	}
	public void draw(SpriteBatch batch){
		if(current.moves == 0) return;
		if(current.doingAbility) return;
		for(Sprite sprite : possibleSprites){
			sprite.draw(batch);
		}
	}
	public void drawHUD(SpriteBatch batch){
		dice.draw(batch);
		if(current.canAbility() && !abilityButton.used) abilityButton.draw(batch);
		if(current.moves == 0 && dice.rolled && current.canAbility()) endButton.draw(batch);
		batch.draw(current.movesBg, MainGame.width/2-10,MainGame.height*4/5-10);
		batch.draw(current.movesImage, MainGame.width/2,MainGame.height*4/5);
		surrenderButton.draw(batch);
	}
}
