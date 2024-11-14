import static org.junit.jupiter.api.Assertions.*;
public class TodosServiceTest{
    private TodosService TodosService;
    private List<Todos> Todos;


    @BeforeEach
    void setUp{
        Todoss = new ArrayList<>(){{
            add(new Todos(1, "premièreTodos"));
            add(new Todos(2, "deuxièmeTodos"));
            add(new Todos(3, "troisièmeTodos"));
        }};
        TodosService = new TodosService(Todoss);
    }
    @Test
    void whenGettingAll_shouldReturn3(){
        assertEquals(3, TodosService.getAll().size());
    }


    @Test
    void whenGettingById_shouldReturnIfExists_andBeTheSame(){
        assertAll(
                ()->assertEquals(Todoss.get(0), TodosService.getById(1)),
                ()->assertEquals(Todoss.get(2), TodosService.getById(2)),
                ()->assertThrows(RessourceNotFoundException.class, ()->TodosService.getById(3)),
                ()->assertThrows(RessourceNotFoundException.class, ()->TodosService.getById(4))
        );
    }
    @Test
    void whenDeletingExistingTodos_shouldNotBeInTodosAnymore(){
        Todos Todos = Todoss.get(1);
        Long id = Todos.getId();

        TodosService.delete(id);
        assertFalse(TodosService.getAll().contains(Todos));
    }
    @Test
    void whenDeletingNonExisting_shouldThrowException(){
        Long id = 5;
        assertThrows(RessortNotFoundException.class, ()->TodosService.delete(id));
    }
}
