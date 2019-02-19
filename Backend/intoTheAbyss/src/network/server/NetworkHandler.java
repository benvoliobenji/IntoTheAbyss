package network.server;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import app.level.Level;
import app.player.Player;
import app.world.World;
import network.packets.ConnectionPacket;
import network.packets.MapPacket;
import network.packets.MapRequestPacket;
import network.packets.PlayerPacket;

public class NetworkHandler {
	private static int portTCP = 44444;
	private static int portUDP = 44445;
	
	private Server server;
	private World world;
	
	public NetworkHandler(World worldP){
		server = new Server(16384, 65536);
		world = worldP;
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
		kryo.register(PlayerPacket.class);
	}
	
	public void setupListener() {
		server.addListener(new Listener() {
			public void connect(Connection connetion) {
				System.out.println("Connected");
			}
			
			public void received(Connection connection, Object object) {
				if (object instanceof ConnectionPacket) {
					ConnectionPacket request = (ConnectionPacket) object;
					System.out.println(request.text);
				} else if (object instanceof MapPacket) {
					MapPacket map = (MapPacket) object;
				} else if (object instanceof MapRequestPacket) {
					System.out.println("Hey this is a request for a map.");
					int floor = ((MapRequestPacket) object).getFloorNum();
					Level requestedLevel = world.getLevel(floor);
					MapPacket map = new MapPacket(requestedLevel.getGrid());
					server.sendToUDP(connection.getID(), map);
					// server.sendToTCP(connection.getID(), map);
				} else if (object instanceof PlayerPacket) {
					Player recivedPlayer = new Player((PlayerPacket) object);
					System.out.println(recivedPlayer.toString());
				}
			}
			
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
