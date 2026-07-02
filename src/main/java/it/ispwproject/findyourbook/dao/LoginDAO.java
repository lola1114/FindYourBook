package it.ispwproject.findyourbook.dao;

import it.ispwproject.findyourbook.exception.LoginException;
import it.ispwproject.findyourbook.model.Credentials;

public interface LoginDAO {
    Credentials execute(String username, String password) throws LoginException;
}