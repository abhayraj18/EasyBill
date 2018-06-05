package com.easybill.service;

import java.util.List;

import com.easybill.exception.BillAmountNotPaidException;
import com.easybill.exception.EntityExistsException;
import com.easybill.exception.EntityNotFoundException;
import com.easybill.exception.OrderAlreadyApprovedException;
import com.easybill.exception.ValidationException;
import com.easybill.model.OrderInfo;
import com.easybill.pojo.EditOrderVO;
import com.easybill.pojo.OrderVO;

public interface OrderService {

	/**
	 * Service to add order.
	 * 
	 * @param userId - id of logged in user
	 * @param orderVO
	 * @throws EntityNotFoundException
	 * @throws ValidationException 
	 */
	void addOrder(int userId, OrderVO orderVO) throws EntityNotFoundException, ValidationException;

	/**
	 * Service to edit an order.
	 * 
	 * @param userId
	 * @param editOrderVO
	 * @throws EntityNotFoundException
	 * @throws OrderAlreadyApprovedException 
	 * @throws ValidationException 
	 * @throws EntityExistsException 
	 */
	void editOrder(Integer userId, EditOrderVO editOrderVO)
			throws EntityNotFoundException, OrderAlreadyApprovedException, ValidationException, EntityExistsException;

	/**
	 * Service to get OrderInfo by id.
	 * 
	 * @param id
	 * @return
	 * @throws EntityNotFoundException
	 */
	OrderInfo getOrderById(Integer id) throws EntityNotFoundException;

	/**
	 * Service to get order details by id.
	 * @param orderId
	 * @return
	 * @throws EntityNotFoundException
	 */
	OrderVO getOrderDetailsById(Integer orderId) throws EntityNotFoundException;

	/**
	 * Service to get all orders.
	 * 
	 * @return
	 */
	List<OrderVO> getAllOrders();

	/**
	 * Service to delete an order by id.
	 * 
	 * @param orderId
	 * @throws EntityNotFoundException
	 * @throws BillAmountNotPaidException 
	 */
	void deleteOrderById(Integer orderId) throws EntityNotFoundException, BillAmountNotPaidException;

	void approveOrder(Integer orderId)
			throws EntityNotFoundException, ValidationException, OrderAlreadyApprovedException, EntityExistsException;
}
