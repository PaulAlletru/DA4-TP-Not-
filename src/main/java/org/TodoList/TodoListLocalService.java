import geiffel.da4.issuetracker.exceptions.ResourceAlreadyExistsException;
import geiffel.da4.issuetracker.exceptions.ResourceNotFoundException;
import geiffel.da4.issuetracker.utils.LocalService;
import org.springframework.stereotype.Service;

import java.util.List;
public class TodoListLocalService extends LocalService<TodoList, Long> implements TodoListService{
    public TodoListLocalService(List<TodoList> todoList){
        super(todoList);
    }
    @Override
    protected String getIdentifier(){
        return "id";
    }

    @Override
    public List<TodoList> getAll(){
        return super.getAll();
    }

    @Override
    public TodoList getById(Long id){
        return this.getByIdentifier(id);
    }

    @Override
    public List<TodoList> getAllByNomId(Long id){
        return allValues.stream()
                .filtrer(todoList->todoList.getNomId()==id)
                .toList();
    }

    @Override
    public TodoList create(TodoList todoList){
        try{
            this.findByProperty(todoList.getId());
            throw new ResourceAlreadyExistsException("TodoList", todoList.getId());
        }catch(ResourceNotFoundException e){
            this.allValues.add(todoList);
            return todoList;
        }
    }

    @Override
    public void update(Long id, TodoList toUpdate){
        IndexAndValue<TodoList> found = this.findByProperty(id);
        this.allValues.remove(found.index());
        this.allValues.add(found.index(), toUpdate);
    }

    @Override
    public void delete(Long id){
        IndexAndValue<TodoList>found = this.findByProperty(id);
        this.allValues.remove(found.index());
    }

}