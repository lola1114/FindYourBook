package it.ispwproject.findyourbook.dao;

import it.ispwproject.findyourbook.exception.DAOException;
import it.ispwproject.findyourbook.model.User;

public interface UserDAO {
    User findByUsername(String username) throws DAOException;
}