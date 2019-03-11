package app;

import java.util.concurrent.TimeUnit;

import com.esotericsoftware.kryonet.Client;

import app.world.World;
import network.packets.ConnectionPacket;
import network.packets.MapRequestPacket;
import network.packets.MoveFloorPacket;
import network.packets.PlayerLocationPacket;
import network.packets.PlayerPacket;
import network.server.NetworkHandler;

public class IntoTheAbyssTestUtil {
	public static int portTCP = 44444;
	public static int portUDP = 44445;

	public static void main(String[] args) throws Exception {
		World world = new World();
		world.addLevel();
		world.addLevel();
		
		NetworkHandler network = new NetworkHandler(world);
		network.registerPackets();
		network.setupListener();
		network.startNetwork();
		
		String userID = "cfdc4cd4-9324-416f-b4ee-42705bf406f3";
		
		Client client = network.getClient();
		ConnectionPacket packet = new ConnectionPacket();
		packet.setID(userID);
		client.sendTCP(packet);
		
		PlayerLocationPacket locUpdate = new PlayerLocationPacket();
		locUpdate.setPlayerID(userID);
		locUpdate.setPlayerPositionX(15);
		locUpdate.setPlayerPositionY(15);
		client.sendTCP(locUpdate);
		
		MoveFloorPacket changeFloor = new MoveFloorPacket();
		changeFloor.setUserID(userID);
		changeFloor.setFloor(2);
		client.sendTCP(changeFloor);
		
		
		
		TimeUnit.SECONDS.sleep(10);
	}
}