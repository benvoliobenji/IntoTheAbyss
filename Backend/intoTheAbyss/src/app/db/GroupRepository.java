package app.db;

import org.springframework.data.repository.CrudRepository;
import app.group.Group;

public interface GroupRepository extends CrudRepository<Group, String>{

}
