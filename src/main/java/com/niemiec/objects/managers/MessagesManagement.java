package com.niemiec.objects.managers;

import com.niemiec.battleship.manager.BattleshipGamesManager;
import com.niemiec.chat.messages.CheckNickMessage;
import com.niemiec.chat.messages.ExitMessage;
import com.niemiec.chat.messages.GroupMessage;
import com.niemiec.chat.messages.PrivateMessage;
import com.niemiec.chat.messages.ReadyToWorkMessage;
import com.niemiec.objects.ClientThread;

public class MessagesManagement {
	private ClientThread clientThread;
	private ClientThreadManager clientThreadManager;
//	private BattleshipGamesManager battleshipGamesManager;

	public MessagesManagement(ClientThread clientThread, ClientThreadManager clientThreadManager,
			BattleshipGamesManager battleshipGamesManager) {
		this.clientThread = clientThread;
		this.clientThreadManager = clientThreadManager;
//		this.battleshipGamesManager = battleshipGamesManager;
	}

	public boolean recieveTheObject(Object object) {
		if (object instanceof GroupMessage) {
			sendGroupMessage((GroupMessage) object);
		} else if (object instanceof PrivateMessage) {
			sendPrivateMessage((PrivateMessage) object);
		} else if (object instanceof CheckNickMessage) {
			checkNick((CheckNickMessage) object);
		} else if (object instanceof ReadyToWorkMessage) {
			updateUsersList();
		} else if (object instanceof ExitMessage) {
			deleteClientThread((ExitMessage) object);
			return false;
		}
		return true;
	}

	private void updateUsersList() {
		clientThreadManager.sendAllClientThreadNickList();
	}

	private void deleteClientThread(ExitMessage exitMessage) {
		clientThreadManager.removeClientThread(exitMessage.getNick());
	}

	private void sendPrivateMessage(PrivateMessage privateMessage) {
		clientThreadManager.sendTheObject(privateMessage.getRecipientNick(), privateMessage);
	}

	private void sendGroupMessage(GroupMessage groupMessage) {
		clientThreadManager.sendTheObjectAll(groupMessage);
	}

	private void checkNick(CheckNickMessage checkNickMessage) {
		if (clientThreadManager.add(checkNickMessage.getNick(), clientThread)) {
			checkNickMessage.nickNotExist();
		}
		sendTheObject(checkNickMessage);
	}

	private void sendTheObject(Object object) {
		clientThread.sendTheObject(object);
	}

}
