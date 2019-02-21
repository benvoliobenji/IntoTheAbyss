package network.server;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import app.level.Level;
import app.player.Player;
import app.tiles.Tile;
import app.world.World;
import network.packets.*;


public class NetworkHandler {
	private static int portTCP = 44444;
	private static int portUDP = 44445;

	private Client client;
	private World world;

	public NetworkHandler(World worldP) {
		client = new Client(16384, 65536);
		world = worldP;
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
		kryo.register(PlayerPacket.class);
	}

	public void setupListener() {
		client.addListener(new Listener() {
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
							System.out.print(grid[i][j].getType());
						}
						System.out.print("\n");
					}
				} else if (object instanceof MapRequestPacket) {

				} else if (object instanceof PlayerPacket) {

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
