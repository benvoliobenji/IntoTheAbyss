package network.server;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import app.db.LevelRepository;
import app.db.PlayerRepository;
import app.world.World;
import network.actions.Action;
import network.actions.ActionTypes;
import network.packets.ConnectionPacket;
import network.packets.MapPacket;
import network.packets.MapRequestPacket;
import network.packets.PlayerPacket;

public class NetworkHandler {
	private PlayerRepository playerRepository;

	private static int portTCP = 44444;
	private static int portUDP = 44445;

	private Server server;
	private RequestHandler requestHandler;

	public NetworkHandler(World worldP, PlayerRepository playerRepo, LevelRepository levelRepo) {
		server = new Server(16384, 65536);
		playerRepository = playerRepo;
		requestHandler = new RequestHandler(playerRepository, levelRepo, server, worldP);
	}

	public void registerPackets() {
		Kryo kryo = server.getKryo();
		kryo.register(ConnectionPacket.class);
		kryo.register(MapRequestPacket.class);
		kryo.register(MapPacket.class);
		kryo.register(app.utils.TileTypes.class);
		kryo.register(app.tiles.Tile.class);
		kryo.register(app.tiles.Wall.class);
		kryo.register(app.tiles.Floor.class);
		kryo.register(app.tiles.Tile[].class);
		kryo.register(app.tiles.Tile[][].class);
		kryo.register(app.tiles.Stair.class);
		kryo.register(PlayerPacket.class);
		kryo.register(Action.class);
		kryo.register(ActionTypes.class);
	}

	public void setupListener() {
		server.addListener(new Listener() {
			@SuppressWarnings("unused")
			public void connect(Connection connection) {
				System.out.println("Connected");
			}

			public void received(Connection connection, Object object) {
				requestHandler.handleRequests(connection, object);
			}

			@SuppressWarnings("unused")
			public void disconnect(Connection connetion) {
				System.out.println("disconnected");
			}
		});
	}

	public void startNetwork() {
		try {
			server.bind(portTCP, portUDP);
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
