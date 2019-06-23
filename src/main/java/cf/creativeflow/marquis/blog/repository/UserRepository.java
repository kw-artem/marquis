package cf.creativeflow.marquis.blog.repository;

import cf.creativeflow.marquis.blog.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
