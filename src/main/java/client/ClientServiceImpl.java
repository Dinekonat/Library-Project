package client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("clientService")
@Transactional
public class ClientServiceImpl implements IClientService {

    @Autowired
    private IClientDao clientDao;

    @Override
    public Client saveClient(Client client) {
        return clientDao.save(client);
    }

    @Override
    public Client uptdateClient(Client client) {
        return clientDao.save(client);
    }

    @Override
    public void deleteClient(Integer clientId) {
        clientDao.deleteById(clientId);
    }

    @Override
    public boolean checkIfIdExists(Integer id) {
        return clientDao.existsById(id);
    }

    @Override
    public Client findClientByEmail(String email) {
        return clientDao.findClientByEmailIgnoreCase(email);
    }

    @Override
    public List<Client> findClientsByLastname(String lastname) {
        return clientDao.findClientsByLastNameIgnoreCase(lastname);
    }

    @Override
    public Client findClientById(Integer clientId) {
        return clientDao.findById(clientId).orElse(null);
    }

    @Override
    public Page<Client> getPaginatedClientsList(int begin, int end) {
        Pageable page = PageRequest.of(begin, end);
        return clientDao.findAll(page);
    }
}