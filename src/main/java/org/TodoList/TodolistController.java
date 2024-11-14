import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("todoList")
public class TodolistController{
    private TodoListService todoListService;
    @Autowired
    public TodolistController(TodoListService todoListService){
        this.TodoListService = todoListService;
    }

    @GetMapping("")
    public List<TodoList> getAll(){
        return todoListService.getAll();
    }
    @GetMapping("{id}")
    public TodoList getById(@PathVariable Long id){
        return todoListService.getById(id);
    }
    @PostMapping("")
    public ResponseEntity create(@RequestBody TodoList todoList){
        TodoList created = todoList.create(todoList);
        return ResponseEntity.created(URI.create("/todoList"+created.getId())).build();
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody TodoList todoList){
        todoListService.update(id, todoList);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id){
        todoListService.delete(id);
        return ResponseEntity.noContent().build();
    }
}