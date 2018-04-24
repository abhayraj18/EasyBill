package com.easybill.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.easybill.config.controller.CustomRestControllerAnnotation;
import com.easybill.pojo.DistributorVO;
import com.easybill.schema.Distributor;
import com.easybill.service.DistributorService;
import com.easybill.util.Constants;
import com.easybill.util.ResponseUtil;

@CustomRestControllerAnnotation
@RequestMapping(value = "/distributor")
public class DistributorController {
	
	@Autowired
	private DistributorService distributorService;
	
	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addDistributor(@RequestBody DistributorVO distributorVO){
		ResponseEntity<String> entity = null;
		if(distributorVO == null){
			entity = new ResponseEntity<String>("Please send the details", Constants.FAIL);
			return entity;
		}
		
		if(StringUtils.isBlank(distributorVO.getName())){
			entity = new ResponseEntity<String>("Name is mandatory", Constants.FAIL);
			return entity;
		}
		
		if(StringUtils.isBlank(distributorVO.getPhoneNumber())){
			entity = new ResponseEntity<String>("Contact is mandatory", Constants.FAIL);
			return entity;
		}
		
		Distributor distributor = new Distributor();
		distributor.setName(distributorVO.getName());
		distributor.setPhoneNumber(distributorVO.getPhoneNumber());
		distributor.setAddress(distributorVO.getAddress());
		distributor = distributorService.saveDistributor(distributor);
		
		distributorVO.setId(distributor.getId());
		entity = new ResponseEntity<String>(ResponseUtil.convertPOJOToString(distributorVO), Constants.SUCCESS);
		return entity;
	}
	
	@RequestMapping(value = "/get/{distributorId}", method = RequestMethod.GET)
	public ResponseEntity<String> getDistributor(@PathVariable("distributorId") Integer distributorId){
		ResponseEntity<String> entity = null;
		if(distributorId == null){
			entity = new ResponseEntity<String>("Please send the distributor Id", Constants.FAIL);
			return entity;
		}
		
		DistributorVO distributorVO = distributorService.getDistributor(distributorId);
		if(distributorVO == null){
			entity = new ResponseEntity<String>("Distributor not found with Id: "+distributorId, Constants.FAIL);
			return entity;
		}
		
		entity = new ResponseEntity<String>(ResponseUtil.convertPOJOToString(distributorVO), Constants.SUCCESS);
		return entity;
	}
	
}
