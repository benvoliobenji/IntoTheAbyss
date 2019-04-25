package network.server;

import java.io.IOException;

import com.esotericsoftware.jsonbeans.Json;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import app.player.Player;
import app.tiles.Tile;
import app.utils.TileTypes;
import app.world.World;
import network.actions.Action;
import network.actions.ActionTypes;
import network.actions.Move;
import network.packets.ConnectionPacket;
import network.packets.MapPacket;
import network.packets.MapRequestPacket;
import network.packets.PlayerPacket;

public class NetworkHandler {
	private static int portTCP = 44444;
	private static int portUDP = 44445;

	private Client client;

	public NetworkHandler(World worldP) {
		client = new Client(16384, 65536);
	}

	public void registerPackets() {
		Kryo kryo = client.getKryo();
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
		client.addListener(new Listener() {
			@SuppressWarnings("unused")
			public void connect(Connection connetion) {
				System.out.println("Connected");
			}

			public void received(Connection connection, Object object) {
				if (object instanceof ConnectionPacket) {

				} else if (object instanceof MapPacket) {
					System.out.println("Map packet recieved Successfully");
					Tile[][] grid = ((MapPacket) object).getGrid();

					for (int i = 0; i < grid.length; i++) {
						for (int j = 0; j < grid[0].length; j++) {
							if (grid[i][j].getType() == TileTypes.WALL) {
								System.out.print("8");
							} else if (grid[i][j].getType() == TileTypes.FLOOR) {
								System.out.print("-");
							} else if (grid[i][j].getType() == TileTypes.STAIR) { System.out.print("#"); }
						}
						System.out.print("\n");
					}
				} else if (object instanceof MapRequestPacket) {

				} else if (object instanceof PlayerPacket) {
					Player p = new Player((PlayerPacket) object);
					System.out.println(p.toString());
				} else if (object instanceof Action) {
					Action action = (Action) object;
					Json json = new Json();
					Move move = json.fromJson(Move.class, action.getPayload());
					System.out.print("Action recieved: ");
					System.out.print("Floor " + move.getFloorMovedTo() + ", ");
					System.out.println(action.getPayload());
					long endTime = System.currentTimeMillis();
					System.out.println(endTime);
				}
			}
		});
	}

	public void startNetwork() {
		client.start();
		try {
			client.connect(50000, "127.0.0.1", portTCP, portUDP);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Client getClient() {
		return client;
	}
}
