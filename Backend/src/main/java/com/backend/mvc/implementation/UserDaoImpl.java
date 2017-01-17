package com.backend.mvc.implementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.backend.mvc.dao.UserDao;
import com.backend.mvc.model.User;

@Repository
public class UserDaoImpl implements UserDao{
	Logger logger=LoggerFactory.getLogger(this.getClass());
	@Autowired
private SessionFactory sessionFactory;
	
	@Override
	public User authenticate(User user) {
		logger.debug("USERDAOIMPL :: AUTHENTICATE");
		Session session=sessionFactory.openSession();
		Query query=session.createQuery("from User where username=?  and password=?");
		//select * from User where username='smith' and password='123'
		query.setString(0, user.getUsername());
		query.setString(1, user.getPassword());
		User validUser=(User)query.uniqueResult();
		session.flush();
		session.close();
		if(validUser!=null)
		logger.debug("VALID USER IS  " + validUser.getUsername() + " " + validUser.getRole() + " " + validUser.isOnline());;
		if(validUser==null)
			logger.debug("Valid USER is null");
		return validUser;
		
	}
	@Override
	public void updateUser(User user) {
		logger.debug("USERDAOIMPL::UPDATE");
		logger.debug("ISONLINE VALUE IS [BEFORE UPDATE]" + user.isOnline());
		Session session=sessionFactory.openSession();
		User existingUser=(User)session.get(User.class, user.getId());
		//update online status as true
		existingUser.setOnline(user.isOnline()); 
		
		session.update(existingUser);
		session.flush();
		session.close();
		logger.debug("ISONLINE VALUE IS [AFTER UPDATE] " + existingUser.isOnline());
	}
	
	@Override
	public User registerUser(User user) {
		Session session=sessionFactory.openSession();
		session.save(user);
		session.flush();
		session.close();
		logger.debug("User id in Dao " + user.getId());
		return user;
	
	}
	
	@Override
	public List<User> getAllUsers(User user) {
	Session session=sessionFactory.openSession();
	//SQL QUERY
	SQLQuery query=session.createSQLQuery("select * from useinfo where username in (select username from useinfo where username!=? minus(select to_id from friend_detail where from_id=? and status!='D' union select from_id from friend_detail where to_id=? and status!='D'))");
	query.setString(0, user.getUsername());
	query.setString(1, user.getUsername());
	query.setString(2, user.getUsername());
	query.addEntity(User.class);
	List<User> users=query.list();// list of users to whom 'smith' can send a friend request
	System.out.println(users);
	session.close();
	return users;
	}
}