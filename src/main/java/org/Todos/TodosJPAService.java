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
public class TodosJPAService implements TodosService{
    @Autowired
    private TodosRepository TodosRepository;
    public List<Todos> getAll(){
        return TodosRepository.findAll();
    }
    @Override
    public Todos getById(Long id){
        Optional<Todos> Todos = TodosRepository.findById(id);
        if(Todos.isPresent()){
            return Todos.get();
        }else{
            throw new RessourceNotFoundException("Todos", id);
        }
    }


    @Override
    public Todos create(Todos Todos) throws ResourceAlreadyExistException{
        return null;
    }
    @Override
    public void update(Long id, Todos toUpdate1) throws ResourceNotFoundException{


    }
    @Override
    public void delete(Long id) throws ResourceNotFoundException{


    }
}

