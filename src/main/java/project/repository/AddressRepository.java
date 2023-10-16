package project.repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.domain.Address;
import project.domain.Board;

@Repository
@Transactional
public class AddressRepository {
    @PersistenceContext
    private EntityManager em;

    //주소 저장
    public void saveAddress(Address address){
        em.persist(address);
    }


}
