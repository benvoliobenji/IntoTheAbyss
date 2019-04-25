package network.server;

import java.util.Optional;

import com.esotericsoftware.jsonbeans.Json;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import app.db.LevelRepository;
import app.db.PlayerRepository;
import app.entity.player.Player;
import app.level.Level;
import app.world.World;
import network.actions.Action;
import network.actions.ActionTypes;
import network.actions.Attack;
import network.actions.Move;
import network.packets.ConnectionPacket;
import network.packets.MapPacket;
import network.packets.MapRequestPacket;
import network.packets.PlayerPacket;

/**
 * The Class RequestHandler handles all request types. As of now the supported
 * request types are ConnectionPacket, and ActionPacket.
 */

/*
 * TODO Document this with good comments explaining the processing. TODO Fix the
 * to all method calls such that they don't send to the sender TODO Fix storage
 * of connections such that we are able to keep them by floor this may be part
 * of a larger fix. TODO Check for consistent data across DB, server, and
 * client.
 */
public class RequestHandler {

	/** The player repository. */
	private PlayerRepository playerRepository;

	/** The level repository. */
	private LevelRepository levelRepository;

	/** The Kryonet server. */
	private Server server;

	/** The world. */
	private World world;

	/**
	 * Instantiates a new request handler.
	 *
	 * @param playerRepo the player repository
	 * @param levelRepo  the level repository
	 * @param server     the server and instance of Kryonet server
	 * @param gWorld     the g world instance used to track state
	 */
	public RequestHandler(PlayerRepository playerRepo, LevelRepository levelRepo, Server server, World gWorld) {
		playerRepository = playerRepo;
		levelRepository = levelRepo;
		this.server = server;
		world = gWorld;
	}

	/**
	 * Handle requests by filtering to appropriate handle method.
	 *
	 * @param connection the connection
	 * @param object     the object
	 */
	public void handleRequests(Connection connection, Object object) {
		if (object instanceof ConnectionPacket) {
			handleConnectionRequest(connection, object);
		} else if (object instanceof MapRequestPacket) {
			handleMapRequest(connection, object);
		} else if (object instanceof PlayerPacket) {
			handlePlayerRequest(connection, (PlayerPacket) object);
		} else if (object instanceof Action)
			handleActionPacket(connection, object);
	}

	/**
	 * Handles ActionPacket types. Does so by sorting the type based on the
	 * ActionType enum.
	 *
	 * @param connection the connection
	 * @param object     the object
	 */
	public void handleActionPacket(Connection connection, Object object) {
		// Action action = ((ActionPacket) object).getAction();
		Action action = (Action) object;
		Json json = new Json();
		switch (action.getActionType()) {
		case MOVE:
			handleMoveAction(action, json);
			server.sendToAllExceptUDP(connection.getID(), action);
			break;
		case ATTACK:
			handleAttackAction(action, json);
			server.sendToAllExceptUDP(connection.getID(), action);
			break;
		case JOIN:
			break;
		case REQUEST:
			break;
		case KICK:
			break;
		default:
			System.out.println("Unrecognized Action type found.");
		}

	}

	/**
	 * Handles connection request by adding entity(currently only player). To the
	 * world and sends a add packet to all clients.
	 *
	 * @param connection the connection from Kryonet
	 * @param object     the object that is a instance of ConnectionRequest
	 */
	public void handleConnectionRequest(Connection connection, Object object) {
		ConnectionPacket request = (ConnectionPacket) object;
		Player p = playerRepository.getPlayerByPlayerID(request.getID());
		if (p != null) {
			world.getLevel(p.getFloor()).addPlayer(p);
			Action action = new Action();
			action.setActionType(ActionTypes.ADD);
			action.setFloor(p.getFloor());
			action.setPerformerID(p.getID());
			action.setPayload(new Json().toJson(p, Player.class));
			server.sendToAllExceptTCP(connection.getID(), action);
			System.out.println("User added to world :" + p.toString());
		}
	}

	/**
	 * Handles move action by taking an Action with a Move type payload.
	 *
	 * @param action the action
	 * @param json   the json
	 */
	public void handleMoveAction(Action action, Json json) {
		Move move = json.fromJson(Move.class, action.getPayload());
		Player moved = (Player) world.getLevel(action.getFloor()).getPlayer(action.getPerformerID());
		if (moved != null) {
			// If the move action was on the same floor they obviously didn't switch floors.
			if (move.getFloorMovedTo() == action.getFloor()) {
				moved.setPosX(move.getLocation().x);
				moved.setPosY(move.getLocation().y);
				world.getLevel(action.getFloor()).replacePlayer(moved.getID(), moved);
				playerRepository.save(moved);
			} else {
				// Get floor from DB, this should be there as clients request prior to sending
				// the move action
				if (world.getLevel(move.getFloorMovedTo()) == null) {
					Optional<Level> newLevel = levelRepository.findById(Integer.valueOf(move.getFloorMovedTo()));
					world.addLevel(newLevel.get());
				}

				Player p = playerRepository.getPlayerByPlayerID(action.getPerformerID());
				world.switchFloors(p, action.getFloor(), move.getFloorMovedTo());
				p = (Player) world.getLevel(move.getFloorMovedTo()).getPlayer(action.getPerformerID());
				playerRepository.save(p);
				System.out.println(p.toString());
			}
		}
	}

	/**
	 * This updates the DB entry for the Entity(as of now it only works for player)
	 * listed by ID in the payload.
	 *
	 * @param action the action the instance of an Action with a Attack Payload.
	 * @param json   the json
	 */
	// TODO convert to use entities allowing for monsters.
	private void handleAttackAction(Action action, Json json) {
		Attack atk = json.fromJson(Attack.class, action.getPayload());
		Player attacked = (Player) world.getLevel(action.getFloor()).getPlayer(action.getPerformerID());
		attacked.setHealth(attacked.getHealth() - atk.getDmg());
		attacked = playerRepository.save(attacked);
	}

	/**
	 * Handle player request actions used to create a player without using the api.
	 * Used exclusively for testing.
	 *
	 * @param connection the connection
	 * @param object     the object
	 */
	public void handlePlayerRequest(Connection connection, Object object) {
		String id = ((PlayerPacket) object).getID();
		if (playerRepository.findById(id) == null) {
			Player recivedPlayer = new Player((PlayerPacket) object);
			playerRepository.save(recivedPlayer);
		}
	}

	/**
	 * This method handles map requests. This is going to lose support soon, and is
	 * really only here for testing
	 *
	 * @param connection the connection received from Kryonet
	 * @param object     the object that is an instance of MapRequestPacket
	 */
	public void handleMapRequest(Connection connection, Object object) {
		int floor = ((MapRequestPacket) object).getFloorNum();
		Level requestedLevel = (Level) world.getLevel(floor);
		MapPacket map = new MapPacket(requestedLevel.getGrid());
		server.sendToTCP(connection.getID(), map);
	}

}
