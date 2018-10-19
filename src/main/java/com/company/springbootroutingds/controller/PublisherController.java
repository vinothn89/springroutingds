package com.company.springbootroutingds.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
public class PublisherController {
  
  @Autowired
  private DataDAO dataDAO;
  
  @RequestMapping(method = RequestMethod.GET, value="/database") 
  
  @ResponseBody
  public List<Map<String, Object>> getDatabase(@RequestParam String appl,		  
          @RequestParam String env)
  
  {
	  System.out.println(appl+env);
	  if(env.equalsIgnoreCase("bam"))
	  {
		  List<Map<String, Object>> advertisers = dataDAO.queryAdvertisers();
		  return advertisers;
	  }
	  List<Map<String, Object>> publishers = dataDAO.queryPublishers();
	  return publishers;
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
