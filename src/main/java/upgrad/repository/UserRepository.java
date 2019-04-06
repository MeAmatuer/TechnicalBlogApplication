package upgrad.repository;

import org.springframework.stereotype.Repository;
import upgrad.model.User;

import javax.persistence.*;

@Repository
public class UserRepository {

    @PersistenceUnit(name = "techblog")
    private EntityManagerFactory emf;

    public void registerUser(User user){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(user);
            transaction.commit();

        }catch (Exception e){
            transaction.rollback();
        }
    }

    public User checkCredentials(String username, String password){
        EntityManager em = emf.createEntityManager();

        TypedQuery<User> query = em.createQuery("SELECT u FROM User u where u.username = :username and u.password = :password", User.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        try {
            return query.getSingleResult();

        }catch (NoResultException e){
            return null;
        }

    }


}
