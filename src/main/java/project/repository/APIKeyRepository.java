package project.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.domain.APIKey;
import project.domain.Board;

import java.util.List;

@Repository
@Transactional
public class APIKeyRepository {

    @PersistenceContext
    private EntityManager em;

    public void saveAPIKey(APIKey apiKey){
        em.persist(apiKey);
    }

    //게시물 리스트
    public List<APIKey> findAll(){
        return em.createQuery("select a from APIKey a", APIKey.class)
                .getResultList();
    }

}
