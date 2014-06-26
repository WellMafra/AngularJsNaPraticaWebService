package org.example.salesserver.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.EntityPath;

public abstract class AbstractRepository<E, ID> {
	
	@PersistenceContext(unitName = "sales-server-persistence-unit")
	private EntityManager entityManager;
	
	public void persist(E entity) {
		getEntityManager().persist(entity);
	}

	public E merge(E entity) {
		return getEntityManager().merge(entity);
	}

	public void remove(E entity) {
		getEntityManager().remove(entity);
	}

	public void detach(E entity) {
		getEntityManager().detach(entity);
	}

	public void refresh(E entity) {
		getEntityManager().refresh(entity);
	}

	public boolean contains(E entity) {
		return getEntityManager().contains(entity);
	}

	public void flush() {
		getEntityManager().flush();
	}

	public void clear() {
		getEntityManager().clear();
	}
	
	public E find(Class<E> clazz, Object id) {
		return getEntityManager().find(clazz, id);
	}
	
	protected E findById(Class<E> clazz, ID id) {
		return getEntityManager().find(clazz, id);
	}
	
	public List<E> listAll(EntityPath<E> domain, Integer offset, Integer limit) {
		JPQLQuery query  = new JPAQuery(getEntityManager());
		query.from(domain);
		
		if (offset != null && offset > 0) {
			query.offset(offset);
		}
		if (limit != null && limit > 0) {
			query.limit(limit);
		}
		
		return query.list(domain);
	}
	
	protected EntityManager getEntityManager() {
		return entityManager;
	}
	
}