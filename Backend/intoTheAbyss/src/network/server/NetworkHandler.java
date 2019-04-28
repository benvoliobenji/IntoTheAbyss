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
import network.packets.DisconnectPacket;
import network.packets.MapPacket;
import network.packets.MapRequestPacket;
import network.packets.PlayerPacket;

/**
 * The Class NetworkHandler.
 */
public class NetworkHandler {

	/** The player repository. */
	private PlayerRepository playerRepository;

	/** The port TCP. */
	private static int portTCP = 44444;

	/** The port UDP. */
	private static int portUDP = 44445;

	/** The server. */
	private Server server;

	/** The request handler. */
	private RequestHandler requestHandler;

	/**
	 * Instantiates a new network handler.
	 *
	 * @param worldP     the world P
	 * @param playerRepo the player repo
	 * @param levelRepo  the level repo
	 */
	public NetworkHandler(World worldP, PlayerRepository playerRepo, LevelRepository levelRepo) {
		server = new Server(16384, 65536);
		playerRepository = playerRepo;
		requestHandler = new RequestHandler(playerRepository, levelRepo, server, worldP);
	}

	/**
	 * Register packets for kryonet.
	 */
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
		kryo.register(DisconnectPacket.class);
	}

	/**
	 * Setup listener for kryonet.
	 */
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

	/**
	 * Start network.
	 */
	public void startNetwork() {
		try {
			server.bind(portTCP, portUDP);
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
