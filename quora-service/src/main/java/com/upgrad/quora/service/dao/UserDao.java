package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;

import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Service
public class UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    public UserEntity createUser(UserEntity userEntity){
        entityManager.persist(userEntity);
        return userEntity;
    }

    public UserEntity getUserByEmail(final String email) {
        try {
            return entityManager.createNamedQuery("userByEmail", UserEntity.class)
                    .setParameter("email", email).getSingleResult();
        } catch (NoResultException exc) {
            return null;
        }

    }

    public UserEntity getUserByUsername(final String username) {
        try {
            return entityManager.createNamedQuery("userByUsername", UserEntity.class)
                    .setParameter("username", username).getSingleResult();
        } catch (NoResultException exc) {
            return null;
        }

    }
    
  //Get the user details by user UUID
    //Returns UserEntity
    public UserEntity getUserById(final String uuid) throws UserNotFoundException {
      try {
        return entityManager.createNamedQuery("userByUuid", UserEntity.class)
            .setParameter("uuid", uuid).getSingleResult();
      } catch (NoResultException exc) {
        throw new UserNotFoundException("USR-001", "User with entered uuid does not exist");
      }

    }
    
    public UserAuthTokenEntity isValidActiveAuthToken(final String accessToken)
    	      throws AuthorizationFailedException {
    	    try {
    	      UserAuthTokenEntity userAuthTokenEntity = entityManager
    	          .createNamedQuery("userByAccessToken", UserAuthTokenEntity.class)
    	          .setParameter("accessToken", accessToken).getSingleResult();
    	      final ZonedDateTime now = ZonedDateTime.now();
    	      if (userAuthTokenEntity.getLogoutAt() == null) {
    	        return userAuthTokenEntity;
    	      } else {
    	        //Exception message for CommonController
    	        throw new AuthorizationFailedException("ATHR-002",
    	            "User is signed out.Sign in first to get user details");
    	      }
    	    } catch (NoResultException exception) {
    	      throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
    	    }
    	  }

}
