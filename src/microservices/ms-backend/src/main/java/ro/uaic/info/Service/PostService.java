package ro.uaic.info.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import ro.uaic.info.entity.Post;
import ro.uaic.info.repository.PostRepository;
import ro.uaic.info.repository.Repository;

import java.sql.SQLException;

public class PostService extends ServiceAbstract{

    private final PostRepository repository = new PostRepository();

    @Override
    public String update(int id, String body) throws SQLException, JsonProcessingException {
        Post post = objectMapper.readValue(body, Post.class);
        Post dbEntity = repository.getById(id);
        if(post.getUser_id() != 0) dbEntity.setUser_id(post.getUser_id());
        if(post.getTitle() != null) dbEntity.setTitle(post.getTitle());
        if(post.getContent() != null) dbEntity.setContent(post.getContent());
        if(post.getComments() != null) dbEntity.setComments(post.getComments());
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(repository.update(dbEntity));
    }

    @Override
    public String add(String body) throws SQLException, JsonProcessingException {
        Post post = objectMapper.readValue(body, Post.class);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(repository.add(post));
    }

    @Override
    public Repository getRepository() {
        return repository;
    }
}
