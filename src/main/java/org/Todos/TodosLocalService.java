import geiffel.da4.issuetracker.exceptions.ResourceAlreadyExistsException;
import geiffel.da4.issuetracker.exceptions.ResourceNotFoundException;
import geiffel.da4.issuetracker.utils.LocalService;
import org.springframework.stereotype.Service;


import java.util.List;
public class TodosLocalService extends LocalService<Todos, Long> implements TodosService{
    public TodosLocalService(List<Todos> Todos){
        super(Todos);
    }
    @Override
    protected String getIdentifier(){
        return "id";
    }


    @Override
    public List<Todos> getAll(){
        return super.getAll();
    }


    @Override
    public Todos getById(Long id){
        return this.getByIdentifier(id);
    }


    @Override
    public List<Todos> getAllByNomId(Long id){
        return allValues.stream()
                .filtrer(Todos->Todos.getNomId()==id)
                .toList();
    }


    @Override
    public Todos create(Todos Todos){
        try{
            this.findByProperty(Todos.getId());
            throw new ResourceAlreadyExistsException("Todos", Todos.getId());
        }catch(ResourceNotFoundException e){
            this.allValues.add(Todos);
            return Todos;
        }
    }


    @Override
    public void update(Long id, Todos toUpdate){
        IndexAndValue<Todos> found = this.findByProperty(id);
        this.allValues.remove(found.index());
        this.allValues.add(found.index(), toUpdate);
    }


    @Override
    public void delete(Long id){
        IndexAndValue<Todos>found = this.findByProperty(id);
        this.allValues.remove(found.index());
    }


}

