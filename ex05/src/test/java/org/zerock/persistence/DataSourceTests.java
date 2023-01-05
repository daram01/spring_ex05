package org.zerock.persistence;

import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class DataSourceTests {
	@Setter(onMethod_ = { @Autowired })
	private DataSource dataSource;
	// 스프링 컨테이너에서 주입받음.
	
	@Setter(onMethod_ = { @Autowired })
	private SqlSessionFactory sqlSessionFactory;
	//		Bean으로 등록했던 클래스 타입으로, 객체 정보를 받아온다. 
	
	@Test
	public void testConnection() {
		try(Connection con = dataSource.getConnection()){
			log.info(con);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testMyBatis() {
		try(SqlSession session = sqlSessionFactory.openSession();
			Connection con = session.getConnection();){
			
			log.info(session);
			log.info(con);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
