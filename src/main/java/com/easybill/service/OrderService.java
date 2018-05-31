package com.easybill.service;

import java.util.List;

import com.easybill.exception.EntityNotFoundException;
import com.easybill.model.OrderInfo;
import com.easybill.pojo.OrderVO;

public interface OrderService {

	/**
	 * Service to add order.
	 * 
	 * @param userId - id of logged in user
	 * @param orderVO
	 * @throws EntityNotFoundException
	 */
	void addOrder(int userId, OrderVO orderVO) throws EntityNotFoundException;

	void editOrder(Integer userId, OrderVO orderVO) throws EntityNotFoundException;

	OrderInfo getOrderById(Integer id) throws EntityNotFoundException;

	OrderVO getOrderDetailsById(Integer orderId) throws EntityNotFoundException;

	List<OrderVO> getAllOrders();
}
