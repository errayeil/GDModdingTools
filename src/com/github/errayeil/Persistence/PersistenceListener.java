package com.github.errayeil.Persistence;

/**
 * @author Errayeil
 * @version 0.1
 * @since 0.1
 */
public interface PersistenceListener {

	void keyAdded(PersistenceEvent event);

	void keyRemoved(PersistenceEvent event);

	void nodeCleared(PersistenceEvent event);

	void nodeExported(PersistenceEvent event);
}
