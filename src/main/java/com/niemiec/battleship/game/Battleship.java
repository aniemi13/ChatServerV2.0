package com.niemiec.battleship.game;

import java.util.HashMap;

import com.niemiec.battleship.game.logic.ShotShip;
import com.niemiec.battleship.game.objects.Player;
import com.niemiec.battleship.game.objects.PlayerImpl;
import com.niemiec.battleship.manager.BattleshipGame;

public class Battleship {
	private int index;
	private boolean automaticallySpacingOfShips;
	private boolean theGameWasStarted;
	private HashMap<String, Player> players;
	private String nicks[]; // dzielenie bez reszty do usytalania kolejki
	private PlayerImpl realPlayer;
	private PlayerImpl virtualPlayer;
//	private AddShips addShips;
	private ShotShip shotShip;
	private int tourn;

	public Battleship(int index) {
		this.index = index;
		this.nicks = new String[2];
		this.tourn = 0;
		this.players = new HashMap<>();
	}
	
	
	
	
	
	public int getIndex() {
		return index;
	}





	public void addPlayer(Player player) {
		if (players.size() <= 2) {
			nicks[players.size()] = player.getNick();
			players.put(player.getNick(), player);
		}
	}
	
	public String getNickFirstPlayer() {
		return nicks[0];
	}
	
	public String getNickSecondPlayer() {
		return nicks[1];
	}

	public boolean checkIfStart() {
		return players.size() == 2;
	}

	public BattleshipGame generateBattleshipGameForStart(BattleshipGame battleshipGame) {
		battleshipGame.setInvitingPlayerNick(null);
		battleshipGame.setInvitingPlayer(null);
		
		battleshipGame.setOpponentPlayer(null);
		
		battleshipGame.setGameStatus(BattleshipGame.START_THE_GAME);
		
		battleshipGame.setNickWhoseTourn(nicks[tourn]);
		
		return battleshipGame;
	}

	public Object sendFirstPlayer(BattleshipGame b) {
		b.setPlayer(players.get(nicks[0]));
		return b;
	}

	public Object sendSecondPlaayer(BattleshipGame b) {
		b.setPlayer(players.get(nicks[1]));
		return b;
	}
}
