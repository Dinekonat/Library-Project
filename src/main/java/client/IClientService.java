package client;

import java.util.List;

import org.springframework.data.domain.Page;



public interface IClientService {
	
	public Client saveClient(Client client);
	
	public Client uptdateClient(Client client);
	
	public void deleteClient(Integer clientId);
	
	public boolean checkIfIdExists(Integer id);
	
	public Client findClientByEmail(String email);
	
	public List<Client> findClientsByLastname(String lastname);
	
	public Client findClientById(Integer clientId);
	
	public Page<Client> getPaginatedClientsList(int begin, int end);


}
