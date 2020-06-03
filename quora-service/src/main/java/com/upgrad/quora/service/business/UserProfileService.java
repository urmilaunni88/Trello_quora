package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

  @Autowired
  private UserDao userDao;

  @Autowired
  private AuthorizationService authorizationService;

  public UserEntity getUserByUuid(final String uuid, final String accessToken)
      throws AuthorizationFailedException, UserNotFoundException {
    UserAuthTokenEntity userAuthTokenEntity = authorizationService
        .isValidActiveAuthToken(accessToken);
    final UserEntity userDetails = userDao.getUserById(uuid);
    return userDetails;
  }

}