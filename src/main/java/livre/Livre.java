package livre;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import categorie.Categorie;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import pret.Pret;

@Entity
@Table(name = "BOOK")
public class Livre {
	private Integer id;
    private String title;
    private String isbn;
    private LocalDate releaseDate;
    private LocalDate registerDate;
    private Integer totalExamplaries;
    private String author;
    private Categorie category;
    
    Set<Pret> loans = new HashSet<Pret>();
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BOOK_ID")
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
    	this.id=id;
    }
    
    
    //title
    @Column(name = "TITLE", nullable = false)
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
    	this.title=title;
    }
    
    
    //isbn
    @Column(name = "ISBN", nullable = false, unique = true)
    public String getIsbn() {
        return isbn;
    }
    
    public void setIsbn(String isbn) {
    	this.isbn=isbn;
    }
    
    
    //releaseDate
    @Column(name = "RELEASE_DATE", nullable = false)
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
    
    
    //registerDate
    @Column(name = "REGISTER_DATE", nullable = false)
    public LocalDate getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDate registerDate) {
        this.registerDate = registerDate;
    }
    
    
    //totalExamplaries
    @Column(name = "TOTAL_EXAMPLARIES")
    public Integer getTotalExamplaries() {
        return totalExamplaries;
    }

    public void setTotalExamplaries(Integer totalExamplaries) {
        this.totalExamplaries = totalExamplaries;
    }
    
    
    //author
    @Column(name = "AUTHOR")
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    
    
    //Liason entre la classe livre et categorie
    //Une categorie contient 0 ou plusieurs livre
    @ManyToOne(optional = false)
    @JoinColumn(name = "CAT_CODE", referencedColumnName = "CODE")
    public Categorie getCategory() {
        return category;
    }

    public void setCategory(Categorie category) {
        this.category = category; 
        
    }
    
    
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.book", cascade = CascadeType.ALL)
    public Set<Pret> getLoans() {
        return loans;
    }

    public void setLoans(Set<Pret> loans) {
        this.loans = loans;
    }
}
