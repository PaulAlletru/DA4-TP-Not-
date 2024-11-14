import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
@Entity
public class Todos{
    @Id
    private Long id;
    private String nom;
    @OneToMany
    private List<Todos> todos;


    public Todos(Long id, String nom){
        this.id = id;
        this.nom = nom;
        this.todos = new ArrayList<>();
    }


    public void addTodos(Todos todos){
        this.todos.add(todos);
    }
    public List<Todos> getTodos(){
        return todos;
    }
    public Long id(){
        return id;
    }
    public String nom(){
        return nom;
    }
    public void setId(Long id){
        this.id = id;
    }
    public void setNom(String nom){
        this.nom = nom;
    }
}

