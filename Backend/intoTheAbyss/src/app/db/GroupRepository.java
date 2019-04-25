package app.db;

import org.springframework.data.repository.CrudRepository;
import app.group.Group;

/**
 * The Interface GroupRepository.
 */
public interface GroupRepository extends CrudRepository<Group, String>{

}
