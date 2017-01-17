package com.backend.mvc.implementation;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.backend.mvc.dao.FileUploadDao;
import com.backend.mvc.model.UploadFile;

@Repository
@Transactional
public class FileUploadDaoImpl implements FileUploadDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void save(UploadFile uploadFile) {
		Session session=sessionFactory.openSession();
		session.save(uploadFile);
		session.flush();
		session.close();
	}

	@Override
	public UploadFile getFile(String username) {
		Session session=sessionFactory.openSession();
		Query query=session.createQuery("from UploadFile where username=?");
		query.setParameter(0, username);
        UploadFile uploadFile=(UploadFile)query.setMaxResults(1).uniqueResult();
		session.close();
		return uploadFile;
	}

}