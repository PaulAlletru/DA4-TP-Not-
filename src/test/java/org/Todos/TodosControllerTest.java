import static org.junit.jupiter.api.Assertions.*;
public class TodosControllerTest{


    @Autowired
    MockMvc mockMvc;




    @MockBean
    private TodosService TodosService;




    private List<Todos> Todos;




    private ObjectMapper mapper = new ObjectMapper();




    private User author = Mockito.mock(User.class);




    @BeforeEach
    void setup() {




        Todos = new ArrayList<>(){{
            add(new Todos(1L, "blah1", "some content1", author));
            add(new Todos(2L, "blah2", "some content2", author));
            add(new Todos(3L, "blah3", "some content3", author));
            add(new Todos(4L, "blah4", "some content4", author));
            add(new Todos(5L, "blah5", "some content5", author));
        }};
        Mockito.when(TodosService.getAll()).thenReturn(Todoss);
        Mockito.when(TodosService.getByCode(1L)).thenReturn(Todoss.get(0));
    }




    @Test
    void whenQueryingRoot_shouldReturn5TodosInJson() throws Exception {
        mockMvc.perform(get("/Todoss")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(content().contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andExpect(jsonPath("$", hasSize(5))
        ).andDo(print());
    }




    @Test
    void whenGetWithCode1_shouldReturnTodos1() throws Exception {
        mockMvc.perform(get("/Todoss/1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(content().contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andExpect(jsonPath("$", hasEntry("code", 1))
        ).andDo(print());
    }




    @Test
    void whenCreatingTodos_shouldGetLinkToResource() throws Exception {
        Todos Todos = new Todos(6L, "Todos", "it doesn't work", author);
        Mockito.when(TodosService.create(Mockito.any(Todos.class))).thenReturn(Todos);




        String toSend = mapper.writeValueAsString(Todos);




        mockMvc.perform(post("/Todoss")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toSend)
        ).andExpect(status().isCreated()
        ).andExpect(header().string("Location","/Todoss/"+Todos.getCode())
        ).andDo(print()).andReturn();
    }




    @Test
    void whenUpdateTodos_shouldBeNoContent_andPassCorrectTodosToService() throws Exception {
        Todos Todos = Todoss.get(0);
        Todos.setCode(7L);




        ArgumentCaptor<Todos> Todos_received = ArgumentCaptor.forClass(Todos.class);




        mockMvc.perform(put("/Todos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(Todos))
        ).andExpect(status().isNoContent()
        ).andDo(print()).andReturn();




        Mockito.verify(TodosService).update(Mockito.anyLong(), Todos_received.capture());
        assertEquals(Todos, Todos_received.getValue());
    }




    @Test
    void whenDelete_shouldCallServiceWithCorrectCode() throws Exception {
        Long code_toSend = 1L;




        mockMvc.perform(delete("/Todoss/"+code_toSend)
        ).andExpect(status().isNoContent()
        ).andDo(print()).andReturn();




        ArgumentCaptor<Long> code_received = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(TodosService).delete(code_received.capture());
        assertEquals(code_toSend, code_received.getValue());
    }




    @Test
    void whenDeleteNonExistingResource_shouldGet404() throws Exception {
        Mockito.doThrow(ResourceNotFoundException.class).when(TodosService).delete(Mockito.anyLong());




        mockMvc.perform(delete("/Todos/972")
        ).andExpect(status().isNotFound()
        ).andDo(print()).andReturn();






    }





