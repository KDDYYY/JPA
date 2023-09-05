package project.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.domain.Image;

@Repository
@Transactional
public class FileRepository {
    @PersistenceContext
    private EntityManager em;

    public void saveFile(Image image){
        em.persist(image);
    }


}
