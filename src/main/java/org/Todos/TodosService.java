import java.util.List;


public interface TodosService {
    List<Todos> getAll();
    Todos getById(Long id);
    List<Todos> getAllNomId(Long id);


    Todos create(Todos Todos);
    void update(Long id, Todos toUpdate);


    void delete(Long id);
}

