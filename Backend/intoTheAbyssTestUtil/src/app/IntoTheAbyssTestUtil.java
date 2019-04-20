package app;

import java.awt.Point;
import java.util.concurrent.TimeUnit;

import com.esotericsoftware.jsonbeans.Json;
import com.esotericsoftware.kryonet.Client;

import app.world.World;
import network.actions.Action;
import network.actions.ActionTypes;
import network.actions.Move;
import network.packets.ConnectionPacket;
import network.packets.MapRequestPacket;
import network.server.NetworkHandler;

public class IntoTheAbyssTestUtil {
	public static int portTCP = 44444;
	public static int portUDP = 44445;

	public static void main(String[] args) throws Exception {
		World world = new World();

		NetworkHandler network = new NetworkHandler(world);
		network.registerPackets();
		network.setupListener();
		network.startNetwork();

		String userID = "2b70bde5-32ff-4fa8-a8f4-4f1e7e9be367";
		Json json = new Json();

		// Connection
		Client client = network.getClient();
		ConnectionPacket packet = new ConnectionPacket();
		packet.setID(userID);
		client.sendTCP(packet);

		long startTime = System.currentTimeMillis();
		System.out.println(startTime);

		Action action = new Action(userID, ActionTypes.MOVE, 0);
		Move move = new Move();
		move.setFloorMovedTo(1);
		move.setLocation(new Point(2, 2));
		action.setPayload(json.toJson(move, Move.class));

		client.sendTCP(action);

		MapRequestPacket p = new MapRequestPacket(1);
		client.sendTCP(p);

		TimeUnit.SECONDS.sleep(100);

	}
}