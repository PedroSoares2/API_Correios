package correios.repository;

import org.springframework.data.repository.CrudRepository;

import correios.models.Address;

public interface AddressRepository extends CrudRepository<Address, String>{

}
