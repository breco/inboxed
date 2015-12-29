package com.inboxed.helpers;

@SuppressWarnings("hiding")
public class Pair<Block,String>{
	public final Block block;
	public final String dir;
	public Pair(Block block, String dir){
		this.block = block;
		this.dir = dir;
	}
	public Block getBlock(){
		return block;
	}
	public String getString(){
		return dir;
	}
	
}