package com.niemiec.battleship.manager;

import java.util.ArrayList;

import com.niemiec.battleship.game.Battleship;

public class BattleshipManager {
	private ArrayList<Battleship> battleships;
	
	public BattleshipManager() {
		this.battleships = new ArrayList<>();
	}
	
	public Battleship getBattleship(int index) {
		return battleships.get(index - 1);
	}
	
	public int createBattleship() {
		int index = battleships.size() + 1;
		Battleship battleship = new Battleship(index);
		battleships.add(battleship);
		return index;
	}
}
