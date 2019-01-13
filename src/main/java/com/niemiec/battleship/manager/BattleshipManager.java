package com.niemiec.battleship.manager;

import java.util.ArrayList;

import com.niemiec.battleship.game.Battleship;

public class BattleshipManager {
	private ArrayList<Battleship> battleships;
	private int index;

	public BattleshipManager() {
		this.battleships = new ArrayList<>();
		this.index = 0;
	}

	public Battleship getBattleship(int index) {
		for (int i = 0; i < battleships.size(); i++) {
			if (battleships.get(i).getIndex() == index)
				return battleships.get(i);
		}
		return null;
	}

	public int createBattleship() {
		index++;
		Battleship battleship = new Battleship(index);
		battleships.add(battleship);
		return index;
	}

	public void deleteBattleship(int index) {
		for (int i = 0; i < battleships.size(); i++) {
			if (battleships.get(i).getIndex() == index)
				battleships.remove(i);
		}
	}

	public void showStatistic() {
		System.out.println("*********BATTLESHIP STATISTIC*************");
		for (int i = 0; i < battleships.size(); i++) {
			Battleship b = battleships.get(i);
			System.out.println("Battleship index: " + b.getIndex() + ", player1: " + b.getNickFirstPlayer()
					+ ", player2: " + b.getNickSecondPlayer());
		}
		System.out.println("******************************************");
	}
}
