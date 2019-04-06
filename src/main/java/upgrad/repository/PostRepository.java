package upgrad.repository;

import org.springframework.stereotype.Repository;
import upgrad.model.Post;
import upgrad.model.User;

import javax.persistence.*;
import javax.transaction.Transaction;
import java.util.List;

@Repository
public class PostRepository {

    @PersistenceUnit(unitName = "techblog")
    private EntityManagerFactory emf;

    public List<Post> getAllPosts(){
        EntityManager em = emf.createEntityManager();

        TypedQuery<Post> query = em.createQuery("SELECT p from Post p", Post.class);
        List<Post> resultList = query.getResultList();

        return resultList;
    }

    public Post getLatestPost(){
        EntityManager em = emf.createEntityManager();
        return em.find(Post.class,3);
    }

    public Post createPost(Post newPost){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(newPost);
            transaction.commit();

        }catch (Exception e){
            transaction.rollback();
        }
        return newPost;
    }

    public Post getPost(Integer postId) {
        EntityManager em = emf.createEntityManager();
        Post post = em.find(Post.class, postId);
        return post;
    }

    public void updatePost(Post post) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.merge(post);
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Rolling back");
            transaction.rollback();
        }
        System.out.println("Updated Post:" + post);
    }

    public void deletePost(Integer postId){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.remove(em.find(Post.class,postId));
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Rolling back");
            transaction.rollback();
        }
    }
    
}
