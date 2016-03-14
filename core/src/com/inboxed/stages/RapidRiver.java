package com.inboxed.stages;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.inboxed.blocks.Block;
import com.inboxed.blocks.NormalBlock;
import com.inboxed.blocks.TeleportBlock;
import com.inboxed.characters.Character;
import com.inboxed.main.MainGame;
import com.inboxed.screens.ClassicMode;

import java.util.Random;

public class RapidRiver extends Stage{

    private Random rand;
    private int width,height;
    private Array<Block> deleted;
    private int blockedProbability, teleportProbability;
    public RapidRiver(String name) {
        super(name);
        rand = new Random();
        width = 8;
        height = 6;
        deleted = new Array<Block>();
        changeBlockedProbability(4,0); // CORREGIR !!!
        teleportProbability = 20;
    }
    public void changeBlockedProbability(int players, int extra){
        blockedProbability = (5 - players)*100/6 + extra;
        System.out.println("probabilidad de bloquear:"+ blockedProbability);
    }
    public void effect(){
        changeBlockedProbability(ClassicMode.round.playersPlaying, ClassicMode.round.roundNumber);
        System.out.println("doing effect");
        deleted.clear();
        Character chr = null;
        for(Block block : blocks.blocks){ //para tamaño de width x height
            chr = null;
            block.changePosition(block.pos_x+1,block.pos_y);
            if(block.pressed){
                for(Character chr2 : ClassicMode.round.players){
                    if(chr2.block.equals(block)){
                        chr = chr2;
                        chr2.pos_x++;
                        chr2.sprite.setPosition(block.pos_x* MainGame.SPRITESIZE,block.pos_y*MainGame.SPRITESIZE);
                        break;
                    }
                }
            }
            if(block.pos_x == width){
                if(chr != null){
                    if(!chr.lose){
                        chr.lose = true;
                        ClassicMode.round.playersPlaying--;

                    }
                    changeBlockedProbability(ClassicMode.round.playersPlaying,ClassicMode.round.roundNumber);
                }
                deleted.add(block);
                continue;
            }

        }
        for(Block delete : deleted){
            blocks.blocks.removeValue(delete,false);
        }
        int r;
        for(int i = 0; i < height; i++){
            r = rand.nextInt(100)+1;
            if(r <= teleportProbability){
                System.out.println("adding teleportBlock on left");
                blocks.blocks.add(new TeleportBlock(0,i,rand.nextInt(6)+1,"rapidRiver",ClassicMode.images.getTexture("C0")));
            }
            else if(r <= blockedProbability){
                blocks.blocks.add(new NormalBlock(0,i,0,"rapidRiver",ClassicMode.images.getTexture("C0")));
            }
            else blocks.blocks.add(new NormalBlock(0,i,rand.nextInt(6)+1,"rapidRiver",ClassicMode.images.getTexture("C0")));
        }
        r = rand.nextInt(100)+1;
        if(r <= teleportProbability){
            System.out.println("adding teleportBlock on random");
            r = rand.nextInt(blocks.blocks.size);
            while(blocks.blocks.get(r).pressed || blocks.blocks.get(r).points <= 0 || !(blocks.blocks.get(r) instanceof TeleportBlock)){
                r = rand.nextInt(blocks.blocks.size);
            }
            Block block = blocks.blocks.get(r);
            blocks.blocks.removeIndex(r);
            blocks.blocks.add(new TeleportBlock(block.pos_x,block.pos_y,block.points,"rapidRiver",block.sprite.getTexture()));


        }
    }
    public void update() {
        blocks.update();

    }

    public void draw(SpriteBatch batch) {

        blocks.draw(batch);

    }
    public void input(){
        blocks.input(ClassicMode.cam);
    }

}
