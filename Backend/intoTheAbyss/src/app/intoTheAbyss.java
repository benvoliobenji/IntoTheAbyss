package app;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import app.db.LevelRepository;
import app.db.PlayerRepository;
import app.level.Level;
import app.world.World;
import network.server.NetworkHandler;

/*
 * The Class intoTheAbyss stores the main method. 
 */
@SpringBootApplication
public class intoTheAbyss {

	/** The player repository. */
	@Autowired
	private PlayerRepository playerRepository;

	/** The level repository. */
	@Autowired
	private LevelRepository levelRepository;

	/** The entity manager. */
	@PersistenceContext
	EntityManager entityManager;

	/** The port TCP. */
	public static int portTCP = 44444;

	/** The port UDP. */
	public static int portUDP = 44445;

	/**
	 * The main method.
	 *
	 * @param args the arguments passed upon launch
	 * @throws Exception this exception is managed by spring.
	 */
	public static void main(String[] args) throws Exception {
		SpringApplication.run(intoTheAbyss.class, args);
	}

	/**
	 * Start listener for the websockets using the NetworkHandler class.
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void startListener() {
		World world = new World();
		levelRepository.save((Level) world.getLevel(0));
		levelRepository.save((Level) world.getLevel(1));

		NetworkHandler network = new NetworkHandler(world, playerRepository, levelRepository);
		network.registerPackets();
		network.setupListener();
		network.startNetwork();
	}
}
