package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

  @Autowired
  private UserDao userDao;


  public UserAuthTokenEntity isValidActiveAuthToken(final String authorization)
      throws AuthorizationFailedException {
    try {
      return userDao.isValidActiveAuthToken(authorization);
    } catch (AuthorizationFailedException e) {
      throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
    }
  }

}