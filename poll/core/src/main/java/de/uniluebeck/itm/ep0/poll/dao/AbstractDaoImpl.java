package de.uniluebeck.itm.ep0.poll.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Abstract dao that provides implementations for the typical data
 * access operations.
 *
 * @param <T> type of the business objects on which the dao shall
 *            operate.
 */
public abstract class AbstractDaoImpl<T> implements Dao<T> {

    private Class<T> persistentClass;

    private EntityManager entityManager;

    public AbstractDaoImpl(final Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    @Override
    public void add(final T t) {
        entityManager.persist(t);
    }

    @Override
    public T update(final T t) {
        return entityManager.merge(t);
    }

    @Override
    public void remove(final T t) {
        entityManager.remove(t);
    }

    @Override
    public T findById(final Integer id) {
        return entityManager.find(this.persistentClass, id);
    }

    @Override
    public List<T> findAll() {
        return entityManager.createQuery(
                String.format("SELECT x FROM %s x", persistentClass.getName()), persistentClass).getResultList();
    }

    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setPersistentClass(final Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    /**
     * Used by Spring to inject the configured {@link EntityManager}.
     *
     * @param entityManager JPA {@link EntityManager} object
     */
    @PersistenceContext
    public void setEntityManager(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
