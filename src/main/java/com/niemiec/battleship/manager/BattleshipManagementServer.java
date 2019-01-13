package com.niemiec.battleship.manager;

import com.niemiec.battleship.game.Battleship;
import com.niemiec.objects.managers.ClientThreadManager;

public class BattleshipManagementServer {
	private ClientThreadManager clientThreadManager;
	private BattleshipManager battleshipManager;

	public BattleshipManagementServer(ClientThreadManager clientThreadManager) {
		this.clientThreadManager = clientThreadManager;
		this.battleshipManager = new BattleshipManager();
	}

	public void receiveBattleshipGame(BattleshipGame battleshipGame) {
		switch (battleshipGame.getGameStatus()) {
		case BattleshipGame.GAME_PROPOSAL:
			receiveGameProposal(battleshipGame);
			break;
		case BattleshipGame.ACCEPTING_THE_GAME:
			receiveAcceptingTheGame(battleshipGame);
			break;
		case BattleshipGame.REJECTION_GAME_PROPOSAL:
			receiveRejectionGameProposal(battleshipGame);
			break;
		case BattleshipGame.SHIPS_ADDED:
			receiveShipsAdded(battleshipGame);
			break;
		case BattleshipGame.START_THE_GAME:
			receiveStartTheGame(battleshipGame);
			break;
		}
	}

	private void receiveStartTheGame(BattleshipGame battleshipGame) {
		Battleship battleship = battleshipManager.getBattleship(battleshipGame.getGameIndex());
		BattleshipGame b = battleship.receiveStartTheGame(battleshipGame);
		clientThreadManager.sendTheObject(battleship.getNickFirstPlayer(), battleship.sendFirstPlayer(b));
		clientThreadManager.sendTheObject(battleship.getNickSecondPlayer(), battleship.sendSecondPlaayer(b));
	}

	private void receiveShipsAdded(BattleshipGame battleshipGame) {
		
		Battleship battleship = battleshipManager.getBattleship(battleshipGame.getGameIndex());
		battleship.addPlayer(battleshipGame.getInvitingPlayer());
		if (battleship.checkIfStart()) {
			BattleshipGame b = battleship.generateBattleshipGameForStart(battleshipGame);
			clientThreadManager.sendTheObject(battleship.getNickFirstPlayer(), battleship.sendFirstPlayer(b));
			clientThreadManager.sendTheObject(battleship.getNickSecondPlayer(), battleship.sendSecondPlaayer(b));
		}
	}

	private void receiveRejectionGameProposal(BattleshipGame battleshipGame) {
		battleshipGame.createBattleshipGameForOpponent();
		clientThreadManager.sendTheObject(battleshipGame.getInvitingPlayerNick(), battleshipGame);
	}

	private void receiveAcceptingTheGame(BattleshipGame battleshipGame) {
		//tworzymy logikę gry
		//nadajemy grze indeks
		//wysyłany informację do graczy, że mogą dodawać statki START_THE_GAME
		battleshipGame = createNewBattleship(battleshipGame);
		battleshipGame.setGameStatus(BattleshipGame.ADD_SHIPS);
		
		clientThreadManager.sendTheObject(battleshipGame.getInvitingPlayerNick(), battleshipGame);
		battleshipGame.createBattleshipGameForOpponent();
		clientThreadManager.sendTheObject(battleshipGame.getInvitingPlayerNick(), battleshipGame);
	}

	private BattleshipGame createNewBattleship(BattleshipGame battleshipGame) {
		int index = battleshipManager.createBattleship();
		battleshipGame.setGameIndex(index);
		return battleshipGame;
	}

	private void receiveGameProposal(BattleshipGame battleshipGame) {
		battleshipGame.createBattleshipGameForOpponent();
		clientThreadManager.sendTheObject(battleshipGame.getInvitingPlayerNick(), battleshipGame);
	}

}
