import geiffel.da4.issuetracker.exceptions.ResourceAlreadyExistsException;
import geiffel.da4.issuetracker.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@Primary
@Qualifier("jpa")
public class TodoListJPAService implements TodoListService{
    @Autowired
    private TodoListRepository todoListRepository;
    public List<TodoList> getAll(){
        return todoListRepository.findAll();
    }
    @Override
    public TodoList getById(Long id){
        Optional<TodoList> todoList = todoListRepository.findById(id);
        if(todoList.isPresent()){
            return todoList.get();
        }else{
            throw new RessourceNotFoundException("TodoList", id);
        }
    }

    @Override
    public TodoList create(TodoList todoList) throws ResourceAlreadyExistException{
        return null;
    }
    @Override
    public void update(Long id, TodoList toUpdate1) throws ResourceNotFoundException{

    }
    @Override
    public void delete(Long id) throws ResourceNotFoundException{

    }
}