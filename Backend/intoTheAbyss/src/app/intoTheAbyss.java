package app;
<<<<<<< HEAD

=======
>>>>>>> 90e545b49ddfb035ee22b683f015ba3871df1f8f
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import level.Level;
import network.packets.ConnectionPacket;
import network.packets.MapPacket;
import network.packets.MapRequestPacket;
import network.packets.PlayerPacket;
import app.player.Player;
import world.World;

@SpringBootApplication
public class intoTheAbyss {
	public static int portTCP = 44444;
	public static int portUDP = 44445;
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(intoTheAbyss.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void startListener() {
		World world = new World();
		world.addLevel();
		world.addLevel();

		// Server server = new Server();
		Server server = new Server(16384, 65536);

		Kryo kryo = server.getKryo();
		kryo.register(ConnectionPacket.class);
		kryo.register(MapRequestPacket.class);
		kryo.register(MapPacket.class);
		kryo.register(utils.TileTypes.class);
		kryo.register(tiles.Tile.class);
		kryo.register(tiles.Wall.class);
		kryo.register(tiles.Floor.class);
		kryo.register(tiles.Tile[].class);
		kryo.register(tiles.Tile[][].class);
		kryo.register(PlayerPacket.class);

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
					//server.sendToTCP(connection.getID(), map);
				} else if (object instanceof PlayerPacket) {
					Player recivedPlayer = new Player((PlayerPacket) object);
					System.out.println(recivedPlayer.toString());
				}
			}
		});

		try {
			server.bind(portTCP, portUDP);
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
