package network.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import app.db.PlayerRepository;
import app.level.Level;
import app.player.Player;
import app.world.World;
import network.packets.ConnectionPacket;
import network.packets.MapPacket;
import network.packets.MapRequestPacket;
import network.packets.PlayerPacket;

public class RequestHandler {
	private PlayerRepository playerRepository;
	private Server server;
	private World world;

	public RequestHandler(PlayerRepository playerRepo, Server server, World gWorld) {
		playerRepository = playerRepo;
		this.server = server;
		world = gWorld;
	}

	public void handleRequests(Connection connection, Object object) {
		if (object instanceof ConnectionPacket) {

		} else if (object instanceof MapRequestPacket) {
			handleMapRequest(connection, object);

		} else if (object instanceof PlayerPacket) {
			handlePlayerRequest(connection, (PlayerPacket) object);
		}
	}

	public void handleConnectionRequest(Connection connection, Object object) {
		ConnectionPacket request = (ConnectionPacket) object;
	}

	public void handlePlayerRequest(Connection connection, Object object) {
		Integer ID = new Integer(((PlayerPacket) object).getID());
		if (playerRepository.findById(ID) == null) {
			Player recivedPlayer = new Player((PlayerPacket) object);
			System.out.println(recivedPlayer.toString());
			playerRepository.save(recivedPlayer);
		} else {
			// Do some processing if the player already exists.

		}
	}

	public void handleMapRequest(Connection connection, Object object) {
		int floor = ((MapRequestPacket) object).getFloorNum();
		Level requestedLevel = world.getLevel(floor);
		MapPacket map = new MapPacket(requestedLevel.getGrid());
		server.sendToUDP(connection.getID(), map);
	}
}
