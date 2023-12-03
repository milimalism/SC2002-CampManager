package sc2002.campmanager.camp;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for CRUD operations
 * @param <T> type of entity 
 */
public interface Repository<T> {
    T save(T entity) throws IOException;
    Optional<T> findByID(int id) throws IOException;
    List<T> findAll() throws IOException;
    Optional<T> delete(T entity);
    void update(T entity, T newEntity);
    void saveToCsv();
}
