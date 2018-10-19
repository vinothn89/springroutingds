package com.company.springbootroutingds.dao;


import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class DataDAO extends JdbcDaoSupport{

    @Autowired
    public DataDAO(DataSource dataSource){
        this.setDataSource(dataSource);
    }

    public List<Map<String, Object>> queryPublishers(){
        String sql = "Select * from users";

        List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sql);
        return list;
    }

    public List<Map<String, Object>> queryAdvertisers(){
        String sql = "Select taskname from tasks";
        String  sql1 =" select \n" + 
        		" vr.feed_id,\n" + 
        		" vc.branch_no,\n" + 
        		" vc.acc_nb,\n" + 
        		" vc.acct_status\n" + 
        		" from volume_capture vc\n" + 
        		" left join volume_run_details vr\n" + 
        		" on vc.file_id = vr.file_id";

        List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sql1);
        
        return list;
    }
    
    public List<Map<String, Object>> queryAdvertisers(String sql){
       
        List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sql);
        
        return list;
    }


}
