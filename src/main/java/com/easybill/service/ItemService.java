package com.easybill.service;

import com.easybill.exception.EntityNotFoundException;
import com.easybill.pojo.ItemVO;

public interface ItemService {

	void addItem(int userId, ItemVO itemVO) throws EntityNotFoundException;

}
