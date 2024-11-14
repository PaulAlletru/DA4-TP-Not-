import java.util.List;

public interface TodoListService {
    List<TodoList> getAll();
    TodoList getById(Long id);
    List<TodoList> getAllNomId(Long id);

    TodoList create(TodoList todoList);
    void update(Long id, TodoList toUpdate);

    void delete(Long id);
}