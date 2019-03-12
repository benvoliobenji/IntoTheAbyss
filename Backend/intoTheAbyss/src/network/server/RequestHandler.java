package network.server;

import java.util.Optional;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import app.db.LevelRepository;
import app.db.PlayerRepository;
import app.level.Level;
import app.player.Player;
import app.world.World;
import network.packets.ConnectionPacket;
import network.packets.MapPacket;
import network.packets.MapRequestPacket;
import network.packets.MoveFloorPacket;
import network.packets.PlayerLocationPacket;
import network.packets.PlayerPacket;

public class RequestHandler {
	private PlayerRepository playerRepository;
	private LevelRepository levelRepository;
	private Server server;
	private World world;

	public RequestHandler(PlayerRepository playerRepo, LevelRepository levelRepo, Server server, World gWorld) {
		playerRepository = playerRepo;
		levelRepository = levelRepo;
		this.server = server;
		world = gWorld;
	}

	public void handleRequests(Connection connection, Object object) {
		if (object instanceof ConnectionPacket) {
			handleConnectionRequest(connection, object);
		} else if (object instanceof MapRequestPacket) {
			handleMapRequest(connection, object);
		} else if (object instanceof PlayerPacket) {
			handlePlayerRequest(connection, (PlayerPacket) object);
		} else if (object instanceof PlayerLocationPacket) {
			handlePlayerLocationRequest(connection, object);
		} else if (object instanceof MoveFloorPacket) {
			handleMoveFloorPacket(connection, object);
		}
	}

	public void handleConnectionRequest(Connection connection, Object object) {
		ConnectionPacket request = (ConnectionPacket) object;
		Player p = playerRepository.getPlayerByPlayerID(request.getID());
		if (p != null)
			world.getLevel(p.getFloor()).addPlayer(p);
		System.out.println("User added to world :" + p.toString());
	}

	public void handlePlayerRequest(Connection connection, Object object) {
		String id = ((PlayerPacket) object).getID();
		if (playerRepository.findById(id) == null) {
			Player recivedPlayer = new Player((PlayerPacket) object);
			playerRepository.save(recivedPlayer);
		}
	}

	public void handleMapRequest(Connection connection, Object object) {
		int floor = ((MapRequestPacket) object).getFloorNum();
		Level requestedLevel = world.getLevel(floor);
		MapPacket map = new MapPacket(requestedLevel.getGrid());
		server.sendToUDP(connection.getID(), map);
	}

	public void handlePlayerLocationRequest(Connection connection, Object object) {
		PlayerLocationPacket packet = ((PlayerLocationPacket) object);
		Level level = world.getLevel(packet.getPlayerFloor());
		Player p = level.getPlayer(packet.getPlayerID());
		p.setPosX(Integer.valueOf(packet.getXPos()));
		p.setPosY(Integer.valueOf(packet.getYPos()));
		System.out.println(p.toString());
	}

	public void handleMoveFloorPacket(Connection connection, Object object) {
		MoveFloorPacket packet = ((MoveFloorPacket) object);
		System.out.println(world.getDepth());
		if (world.getDepth() == packet.getFloorNum()) {
			Optional<Level> newLevel = levelRepository.findById(Integer.valueOf(packet.getFloorNum()));
			world.addLevel(newLevel.get());
		}
		Player p = playerRepository.getPlayerByPlayerID(packet.getUserID());
		world.switchFloors(p, packet.getFloorNum() - 1, packet.getFloorNum());
		playerRepository.save(p);
		connection.sendUDP(new PlayerPacket(p));
		System.out.println(p.toString());
	}

}
