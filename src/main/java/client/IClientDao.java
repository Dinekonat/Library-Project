package client;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClientDao extends JpaRepository<Client, Integer> {
	
	public Client findClientByEmailIgnoreCase(String email);
	
	public List<Client> findClientsByLastNameIgnoreCase(String lastname);

}