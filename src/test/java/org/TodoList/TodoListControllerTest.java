import static org.junit.jupiter.api.Assertions.*;
public class TodosControllerTest{

    @Autowired
    MockMvc mockMvc;


    @MockBean
    private TodoListService TodoListService;


    private List<TodoList> TodoLists;


    private ObjectMapper mapper = new ObjectMapper();


    private User author = Mockito.mock(User.class);


    @BeforeEach
    void setup() {


        TodoLists = new ArrayList<>(){{
            add(new TodoList(1L, "blah1", "some content1", author));
            add(new TodoList(2L, "blah2", "some content2", author));
            add(new TodoList(3L, "blah3", "some content3", author));
            add(new TodoList(4L, "blah4", "some content4", author));
            add(new TodoList(5L, "blah5", "some content5", author));
        }};
        Mockito.when(TodoListService.getAll()).thenReturn(TodoLists);
        Mockito.when(TodoListService.getByCode(1L)).thenReturn(TodoLists.get(0));
    }


    @Test
    void whenQueryingRoot_shouldReturn5TodoListsInJson() throws Exception {
        mockMvc.perform(get("/TodoLists")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(content().contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andExpect(jsonPath("$", hasSize(5))
        ).andDo(print());
    }


    @Test
    void whenGetWithCode1_shouldReturnTodoList1() throws Exception {
        mockMvc.perform(get("/TodoLists/1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(content().contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andExpect(jsonPath("$", hasEntry("code", 1))
        ).andDo(print());
    }


    @Test
    void whenCreatingTodoList_shouldGetLinkToResource() throws Exception {
        TodoList TodoList = new TodoList(6L, "TodoList", "it doesn't work", author);
        Mockito.when(TodoListService.create(Mockito.any(TodoList.class))).thenReturn(TodoList);


        String toSend = mapper.writeValueAsString(TodoList);


        mockMvc.perform(post("/TodoLists")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toSend)
        ).andExpect(status().isCreated()
        ).andExpect(header().string("Location","/TodoLists/"+TodoList.getCode())
        ).andDo(print()).andReturn();
    }


    @Test
    void whenUpdateTodoList_shouldBeNoContent_andPassCorrectTodoListToService() throws Exception {
        TodoList TodoList = TodoLists.get(0);
        TodoList.setCode(7L);


        ArgumentCaptor<TodoList> TodoList_received = ArgumentCaptor.forClass(TodoList.class);


        mockMvc.perform(put("/TodoLists/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(TodoList))
        ).andExpect(status().isNoContent()
        ).andDo(print()).andReturn();


        Mockito.verify(TodoListService).update(Mockito.anyLong(), TodoList_received.capture());
        assertEquals(TodoList, TodoList_received.getValue());
    }


    @Test
    void whenDelete_shouldCallServiceWithCorrectCode() throws Exception {
        Long code_toSend = 1L;


        mockMvc.perform(delete("/TodoLists/"+code_toSend)
        ).andExpect(status().isNoContent()
        ).andDo(print()).andReturn();


        ArgumentCaptor<Long> code_received = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(TodoListService).delete(code_received.capture());
        assertEquals(code_toSend, code_received.getValue());
    }


    @Test
    void whenDeleteNonExistingResource_shouldGet404() throws Exception {
        Mockito.doThrow(ResourceNotFoundException.class).when(TodoListService).delete(Mockito.anyLong());


        mockMvc.perform(delete("/TodoLists/972")
        ).andExpect(status().isNotFound()
        ).andDo(print()).andReturn();



    }


