package com.easybill.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easybill.exception.EntityNotFoundException;
import com.easybill.model.User;
import com.easybill.pojo.ItemVO;
import com.easybill.repository.ItemRepository;
import com.easybill.service.ItemService;
import com.easybill.service.UserService;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private UserService userService;;
	
	@Override
	public void addItem(int userId, ItemVO itemVO) throws EntityNotFoundException {
		User user = userService.getUserById(userId);
		
	}

}
