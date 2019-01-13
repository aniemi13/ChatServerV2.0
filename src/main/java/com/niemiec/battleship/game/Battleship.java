package com.niemiec.battleship.game;

import java.util.HashMap;

import com.niemiec.battleship.game.logic.ShotShip;
import com.niemiec.battleship.game.objects.Player;
import com.niemiec.battleship.manager.BattleshipGame;

public class Battleship {
	private final int FIRST_PLAYER = 0;
	private final int SECOND_PLAYER = 1;
	
	private int index;
	private HashMap<String, Player> players;
	private String nicks[]; // dzielenie bez reszty do usytalania kolejki
	
	private ShotShip shotShip;

	public Battleship(int index) {
		this.index = index;
		this.nicks = new String[2];
		this.players = new HashMap<>();
		this.shotShip = new ShotShip();
	}
	
	public BattleshipGame receiveStartTheGame(BattleshipGame battleshipGame) {
		if (shotShip.shot(battleshipGame.getCoordinates())) {
			battleshipGame.setGameStatus(BattleshipGame.END_GAME);
		}
		battleshipGame.setNickWhoseTourn(nicks[shotShip.getTurn()]);
		return battleshipGame;
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
		return nicks[FIRST_PLAYER];
	}
	
	public String getNickSecondPlayer() {
		return nicks[SECOND_PLAYER];
	}

	public boolean checkIfStart() {
		if (players.size() == 2) {
			shotShip.addPlayers(players.get(nicks[0]), players.get(nicks[1]));
			return true;
		}
		return false;
	}

	public BattleshipGame generateBattleshipGameForStart(BattleshipGame battleshipGame) {
		battleshipGame.setInvitingPlayerNick(null);
		battleshipGame.setInvitingPlayer(null);
		battleshipGame.setOpponentPlayer(null);
		battleshipGame.setGameStatus(BattleshipGame.START_THE_GAME);
		battleshipGame.setNickWhoseTourn(nicks[shotShip.getTurn()]);
		
		return battleshipGame;
	}

	public Object sendFirstPlayer(BattleshipGame b) {
		b.setPlayer(shotShip.getPlayer(FIRST_PLAYER));
		b.setOpponentPlayerNick(nicks[SECOND_PLAYER]);
		return b;
	}

	public Object sendSecondPlaayer(BattleshipGame b) {
		b.setPlayer(shotShip.getPlayer(SECOND_PLAYER));
		b.setOpponentPlayerNick(nicks[FIRST_PLAYER]);
		return b;
	}
}
