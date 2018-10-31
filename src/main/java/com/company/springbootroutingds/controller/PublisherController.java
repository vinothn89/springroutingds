package com.company.springbootroutingds.controller;


import java.sql.SQLException;
import java.util.List;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.springbootroutingds.config.QueryInput;
import com.company.springbootroutingds.dao.DataDAO;

/*import com.company.springbootroutingds.dao.PublisherDAO;
import com.company.springbootroutingds.model.Publisher;*/

@Controller
@EnableAsync
public class PublisherController {
  
  @Autowired
  private DataDAO dataDAO;
  
  @RequestMapping(method = RequestMethod.GET, value="/database") 
  
  @ResponseBody
  public List<Map<String, Object>> getDatabase(@RequestParam String query) throws InterruptedException, ExecutionException, SQLException
  {
	
	  List<Map<String, Object>> advertisers = dataDAO.queryAdvertisersForConfig(query).get();
	  return advertisers;
  }
  
  @RequestMapping(method = RequestMethod.POST, value="/database/query")
  @ResponseBody
  public List<Map<String, Object>> executeDynamicQuery(@RequestParam String appl, @RequestParam String env, @RequestBody QueryInput query)
	  {
	  
	  if(env.equalsIgnoreCase("bam"))
	  {
		  List<Map<String, Object>> advertisers = dataDAO.queryAdvertisers(query.getSqlQuery());
		  return advertisers;
	  }
  
	  List<Map<String, Object>> publishers = dataDAO.queryPublishers();
	  return publishers;
}
  
}
