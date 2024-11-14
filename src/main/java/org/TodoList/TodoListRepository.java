import org.springframework.data.repository.ListCrudRepository;

public interface TodoListRepository extends ListCrudRepository<TodoList, Long{
}