package com.company.springbootroutingds.dao;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.company.springbootroutingds.controller.LookupRestClient;

@Repository
@Transactional
@ComponentScan(basePackages = "com.company.springbootroutingds")
public class DataDAO extends JdbcDaoSupport{
	
	private static final Logger logger = LoggerFactory.getLogger(DataDAO.class);
	
	@Resource(name="strategic")
	DataSource strategicDS;
	
	@Resource(name="legacy")
	DataSource legacyDS;
	
    @Autowired
    public DataDAO(DataSource dataSource){
        this.setDataSource(dataSource);
    }

    public List<Map<String, Object>> queryPublishers(){
        String sql = "Select * from users";

        List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sql);
        return list;
    }
    
    @Async("asyncExecutor")
    public CompletableFuture<List<Map<String, Object>>> queryAdvertisersForConfig(String query) throws SQLException{
    	
    	logger.info("Looking up : Aysnc thread");
    	
        String fetchLocation = " Select location from config where Name = '"+query+"'";
        
        String config = this.getJdbcTemplate().queryForObject(fetchLocation, String.class);
        
        if(null!=config)
        {
	        if(config.equalsIgnoreCase("legacy"))
	        {
	        	this.setDataSource(legacyDS);
	       		return CompletableFuture.completedFuture(this.getJdbcTemplate().queryForList(LookupRestClient.callLookupService(query)));
	        }
	        else if(config.equalsIgnoreCase("strategic"))
	        {
	        	this.setDataSource(strategicDS);
	       		return CompletableFuture.completedFuture(this.getJdbcTemplate().queryForList(LookupRestClient.callLookupService(query)));
	        }
        }
        
        return CompletableFuture.completedFuture(null);
    }

    public List<Map<String, Object>> queryAdvertisers(String sql1){
    	List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sql1);
    	return list;
    }
    
}


