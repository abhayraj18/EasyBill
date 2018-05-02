package com.easybill.controller;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.easybill.exception.EntityNotFoundException;
import com.easybill.pojo.DistributorVO;
import com.easybill.schema.Distributor;
import com.easybill.schema.metadata.EnumConstant.Status;
import com.easybill.service.DistributorService;
import com.easybill.util.Constants;
import com.easybill.util.DateUtil;
import com.easybill.util.ResponseUtil;
import com.easybill.validation.PojoValidator;

@RestController
@RequestMapping(value = "/distributor")
public class DistributorController {

	@Autowired
	private DistributorService distributorService;

	@Autowired
	private PojoValidator validator;

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> addDistributor(@RequestBody @Validated DistributorVO distributorVO, Errors result)
			throws Exception {
		validator.validate(distributorVO, result);
		if (result.hasErrors()) {
			return ResponseUtil.buildResponseEntity(result, Constants.FAIL);
		}
		if (StringUtils.isBlank(distributorVO.getPhoneNumber())) {
			return ResponseUtil.buildResponseEntity("Contact is mandatory", Constants.FAIL);
		}

		Distributor distributor = new Distributor();
		distributor.setName(distributorVO.getName());
		distributor.setPhoneNumber(distributorVO.getPhoneNumber());
		distributor.setAddress(distributorVO.getAddress());
		distributor.setStatus(Status.ACTIVE);
		distributor.setCreatedOn(DateUtil.getCurrentTime());
		distributor = distributorService.saveDistributor(distributor);

		distributorVO.setId(distributor.getId());
		return ResponseUtil.buildResponseEntity(ResponseUtil.convertPOJOToString(distributorVO), Constants.SUCCESS);
	}

	@RequestMapping(value = "/get/{distributorId}", method = RequestMethod.GET)
	public ResponseEntity<Object> getDistributor(@PathVariable("distributorId") Integer distributorId)
			throws EntityNotFoundException {
		if (Objects.isNull(distributorId)) {
			return ResponseUtil.buildResponseEntity("Please send the distributor Id", Constants.FAIL);
		}

		DistributorVO distributorVO = distributorService.getDistributor(distributorId);
		if (Objects.isNull(distributorVO)) {
			throw new EntityNotFoundException("Distributor could not be found with id: " + distributorId);
		}

		return ResponseUtil.buildResponseEntity(ResponseUtil.convertPOJOToString(distributorVO), Constants.SUCCESS);
	}

}
