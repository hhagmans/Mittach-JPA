package com.innoq.sample;

import java.io.FileOutputStream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.DatabaseSequenceFilter;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.FilteredDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.filter.ITableFilter;
import org.dbunit.dataset.xml.FlatXmlDataSet;


public class DatabaseExport {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJPAProject");
		EntityManager em = emf.createEntityManager();
		try{
		em.getTransaction().begin();

		java.sql.Connection conn = em.unwrap(java.sql.Connection.class);
		IDatabaseConnection connection = new DatabaseConnection(conn);
		ITableFilter filter = new DatabaseSequenceFilter(connection);
		IDataSet fullDataSet = new FilteredDataSet(filter, connection.createDataSet());
        FlatXmlDataSet.write(fullDataSet, new FileOutputStream("full.xml"));
        em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		finally {
			em.close();
		}
	}

}
