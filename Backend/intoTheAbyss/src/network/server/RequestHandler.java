package network.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.esotericsoftware.jsonbeans.Json;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import app.db.LevelRepository;
import app.db.PlayerRepository;
import app.entity.GameEntity;
import app.entity.player.Player;
import app.group.Group;
import app.level.Level;
import app.world.World;
import network.actions.Action;
import network.actions.ActionTypes;
import network.actions.Attack;
import network.actions.Move;
import network.packets.ConnectionPacket;
import network.packets.DisconnectPacket;
import network.packets.MapPacket;
import network.packets.MapRequestPacket;
import network.packets.PlayerPacket;

/**
 * The Class RequestHandler handles all request types. As of now the supported
 * request types are ConnectionPacket, and ActionPacket.
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
		Json json = new Json();
		if (object instanceof ConnectionPacket) {
			handleConnectionRequest(connection, object, json);
		} else if (object instanceof DisconnectPacket) {
			handleDisconnectRequest(connection, object);
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
			handleMoveAction(action, json, connection);
			server.sendToAllExceptTCP(connection.getID(), action);
			break;
		case ATTACK:
			System.out.println(action.getPayload());
			handleAttackAction(action, json);
			server.sendToAllExceptTCP(connection.getID(), action);
			break;
		case REQUEST:
			handleRequestAction(action, json, connection);
			break;
		case KICK:
			handleKickAction(action, json, connection);
			break;
		case DEATH:
			handleDeathAction(action, json);
			server.sendToAllExceptTCP(connection.getID(), action);
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
	public void handleConnectionRequest(Connection connection, Object object, Json json) {
		ConnectionPacket request = ((ConnectionPacket) object);
		Player p = playerRepository.findById(request.getID()).get();
		if (p != null) {
			world.getLevel(p.getFloor()).addEntity(p);
			Action action = new Action();
			action.setActionType(ActionTypes.ADD);
			action.setFloor(p.getFloor());
			action.setPerformerID(p.getID());
			action.setPayload(new Json().toJson(p, Player.class));
			server.sendToAllExceptTCP(connection.getID(), action);

			Collection<GameEntity> entity = ((Level) world.getLevel(p.getFloor())).getAllEntities().values();
			for (GameEntity e : entity) {
				Action action1 = new Action();
				action1.setActionType(ActionTypes.ADD);
				action1.setFloor(p.getFloor());
				action1.setPerformerID(e.getID());
				action1.setPayload(new Json().toJson((Player) e, Player.class));
				server.sendToTCP(connection.getID(), action1);
			}
			System.out.println("User added to world :" + p.toString());
		}
		world.getLevel(p.getFloor()).addEntity(p);
	}

	/**
	 * Handles disconnect request by adding entity(currently only player). To the
	 * world and sends a add packet to all clients.
	 *
	 * @param connection the connection from Kryonet
	 * @param object     the object that is a instance of ConnectionRequest
	 */
	public void handleDisconnectRequest(Connection connection, Object object) {
		DisconnectPacket request = (DisconnectPacket) object;
		Player p = (Player) world.getLevel(request.getFloor()).getEntity(request.getID());
		if (p.getGroup() != null) {
			if (p.getGroup().getLeader() == p.getID()) {
				ArrayList<String> members = (ArrayList<String>) p.getGroup().getPlayers();
				for (int i = 0; i < members.size(); i++) {
					Action action = new Action();
					action.setActionType(ActionTypes.KICK);
					action.setFloor(request.getFloor());
					action.setPerformerID(request.getID());
					action.setPayload(members.get(i));
					server.sendToAllExceptTCP(connection.getID(), action);
				}
			}
		}

		Action action = new Action();
		action.setActionType(ActionTypes.REMOVE);
		action.setFloor(request.getFloor());
		action.setPerformerID(request.getID());
		action.setPayload(request.getID());
		server.sendToAllExceptTCP(connection.getID(), action);
		world.getLevel(request.getFloor()).removeEntity(p.getID());
		playerRepository.save(p);
		System.out.println("User removed from world: " + p.toString());

	}

	/**
	 * Handles move action by taking an Action with a Move type payload.
	 *
	 * @param action the action
	 * @param json   the json
	 */
	public void handleMoveAction(Action action, Json json, Connection connection) {
		Move move = json.fromJson(Move.class, action.getPayload());
		Player moved = (Player) world.getLevel(action.getFloor()).getEntity(action.getPerformerID());
		if (moved != null) {
			// If the move action was on the same floor they obviously didn't switch floors.
			if (move.getFloorMovedTo() == action.getFloor()) {
				moved.setPosX(move.getLocation().x);
				moved.setPosY(move.getLocation().y);
				world.getLevel(action.getFloor()).replaceEntity(moved.getID(), moved);
				playerRepository.save(moved);
			} else {
				// Get floor from DB, this should be there as clients request prior to sending
				// the move action
				System.out.print(action.getPayload());
				if (world.getLevel(move.getFloorMovedTo()) == null) {
					Optional<Level> newLevel = levelRepository.findById(Integer.valueOf(move.getFloorMovedTo()));
					world.addLevel(newLevel.get());
				}

				Action action1 = new Action();
				action1.setActionType(ActionTypes.ADD);
				action1.setFloor(move.getFloorMovedTo());
				action1.setPerformerID(moved.getID());
				action1.setPayload(new Json().toJson(moved, Player.class));
				server.sendToAllExceptTCP(connection.getID(), action);

				Collection<GameEntity> entity = ((Level) world.getLevel(move.getFloorMovedTo())).getAllEntities()
						.values();
				for (GameEntity e : entity) {
					Action action2 = new Action();
					action2.setActionType(ActionTypes.ADD);
					action2.setFloor(move.getFloorMovedTo());
					action2.setPerformerID(e.getID());
					action2.setPayload(new Json().toJson((Player) e, Player.class));
					server.sendToTCP(connection.getID(), action2);
				}

				Player p = playerRepository.findById(action.getPerformerID()).get();
				world.switchFloors(p, action.getFloor(), move.getFloorMovedTo());
				p = (Player) world.getLevel(move.getFloorMovedTo()).getEntity(action.getPerformerID());
				playerRepository.save(p);
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
	private void handleAttackAction(Action action, Json json) {
		Attack atk = json.fromJson(Attack.class, action.getPayload());
		Player attacked = (Player) world.getLevel(action.getFloor()).getEntity(action.getPerformerID());
		attacked.setHealth(attacked.getHealth() - atk.getDmg());
		attacked = playerRepository.save(attacked);
		System.out.println("Look at that swing.");
	}

	/*
	 * Upon recieving updates the group for all members
	 */
	private void handleRequestAction(Action action, Json json, Connection connection) {
		Player leader = (Player) world.getLevel(action.getFloor()).getEntity(action.getPerformerID());
		if (leader.getGroup() == null) {
			leader.setGroup(new Group());
			leader.getGroup().addPlayer(action.getPayload());
			leader.getGroup().addPlayer(leader.getID());
		} else {
			ArrayList<String> members = (ArrayList<String>) leader.getGroup().getPlayers();
			for (int i = 0; i < members.size(); i++) {
				Player member = (Player) world.getLevel(action.getFloor()).getEntity(members.get(i));
				if (member.getID() != action.getPayload()) {
					member.getGroup().addPlayer(action.getPayload());
					world.getLevel(action.getFloor()).replaceEntity(member.getID(), member);
				}
			}

		}

		Player joinee = (Player) world.getLevel(action.getFloor()).getEntity(action.getPayload());
		joinee.setGroup(leader.getGroup());
		world.getLevel(action.getFloor()).replaceEntity(joinee.getID(), joinee);

		Action joinAction = new Action();
		joinAction.setActionType(ActionTypes.JOIN);
		joinAction.setFloor(joinee.getFloor());
		joinAction.setPerformerID(leader.getID());
		joinAction.setPayload(joinee.getID());
		server.sendToAllTCP(joinAction);
	}

	// Kick action is just the ID of the person to be kicked if it's from a admin.
	private void handleKickAction(Action action, Json json, Connection connection) {
		if (((Player) world.getLevel(action.getFloor()).getEntity(action.getPerformerID())).getIsAdmin()) {
			Action updateClient = action;
			updateClient.setActionType(ActionTypes.REMOVE);
			updateClient.setPerformerID(action.getPayload());
			playerRepository.deleteById(action.getPayload());
			world.getLevel(action.getFloor()).removeEntity(action.getPayload());
			server.sendToAllExceptUDP(connection.getID(), updateClient);
		} else {
			Player leader = (Player) world.getLevel(action.getFloor()).getEntity(action.getPerformerID());
			List<String> members = leader.getGroup().getPlayers();
			for (int i = 0; i < members.size(); i++) {
				Player member = (Player) world.getLevel(action.getFloor()).getEntity(members.get(i));
				member.removePlayerFromGroup(action.getPayload());
				world.getLevel(action.getFloor()).replaceEntity(member.getID(), member);
			}
			server.sendToAllExceptUDP(connection.getID(), action);
		}
	}

	private void handleDeathAction(Action action, Json json) {
		world.getLevel(action.getFloor()).removeEntity(action.getPerformerID());
		playerRepository.deleteById(action.getPerformerID());
	}
	// --------------------------------------------

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
