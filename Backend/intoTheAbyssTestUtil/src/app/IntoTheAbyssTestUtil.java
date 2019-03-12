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
		
		String userID = "734b164a-c4c5-48af-bb98-db9194f0fafa";
		
		Client client = network.getClient();
		MapRequestPacket packet = new MapRequestPacket();
		packet.floorNum = 0;
		client.sendTCP(packet);
		
		TimeUnit.SECONDS.sleep(10);
		
		
		/*Client client = network.getClient();
		ConnectionPacket packet = new ConnectionPacket();
		packet.setID(userID);
		client.sendTCP(packet);
		
		PlayerLocationPacket locUpdate = new PlayerLocationPacket();
		locUpdate.setPlayerID(userID);
		locUpdate.setPlayerPositionX(15);
		locUpdate.setPlayerPositionY(15);
		locUpdate.setPlayerFloor(3);
		client.sendTCP(locUpdate);
		
		MoveFloorPacket changeFloor = new MoveFloorPacket();
		changeFloor.setUserID(userID);
		changeFloor.setFloor(4);
		client.sendTCP(changeFloor);
		
		TimeUnit.SECONDS.sleep(3);
		
		MoveFloorPacket changeFloor = new MoveFloorPacket();
		changeFloor.setUserID(userID);
		changeFloor.setFloor(1);
		client.sendTCP(changeFloor);
		
		TimeUnit.SECONDS.sleep(3);
		
		MoveFloorPacket changeFloor2 = new MoveFloorPacket();
		changeFloor2.setUserID(userID);
		changeFloor2.setFloor(2);
		client.sendTCP(changeFloor2);
		
		TimeUnit.SECONDS.sleep(3);
		
		MoveFloorPacket changeFloor3 = new MoveFloorPacket();
		changeFloor3.setUserID(userID);
		changeFloor3.setFloor(3);
		client.sendTCP(changeFloor3);
		
		TimeUnit.SECONDS.sleep(3);*/
		
		
	}
}