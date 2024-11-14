import static org.junit.jupiter.api.Assertions.*;
public class TodoListServiceTest{
    private TodoListService todoListService;
    private List<TodoList> todoLists;

    @BeforeEach
    void setUp{
        todoLists = new ArrayList<>(){{
            add(new TodoList(1, "premièreTodoList"));
            add(new TodoList(2, "deuxièmeTodoList"));
            add(new TodoList(3, "troisièmeTodoList"));
        }};
        todoListService = new TodoListService(todoLists);
    }
    @Test
    void whenGettingAll_shouldReturn3(){
        assertEquals(3, TodoListService.getAll().size());
    }

    @Test
    void whenGettingById_shouldReturnIfExists_andBeTheSame(){
        assertAll(
                ()->assertEquals(todoLists.get(0), todoListService.getById(1)),
                ()->assertEquals(todoLists.get(2), todoListService.getById(2)),
                ()->assertThrows(RessourceNotFoundException.class, ()->todoListService.getById(3)),
                ()->assertThrows(RessourceNotFoundException.class, ()->todoListService.getById(4))
        );
    }
    @Test
    void whenDeletingExistingTodoList_shouldNotBeInTodoListAnymore(){
        TodoList todoList = todoLists.get(1);
        Long id = todoList.getId();

        todoListService.delete(id);
        assertFalse(todoListService.getAll().contains(todoList));
    }
    @Test
    void whenDeletingNonExisting_shouldThrowException(){
        Long id = 5;
        assertThrows(RessortNotFoundException.class, ()->todoListService.delete(id));
    }
}