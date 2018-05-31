package com.easybill.service;

import java.util.List;

import com.easybill.exception.EntityExistsException;
import com.easybill.exception.EntityNotFoundException;
import com.easybill.model.Item;
import com.easybill.pojo.ItemVO;

public interface ItemService {

	/**
	 * Service to add item.
	 * 
	 * @param userId - id of logged in user
	 * @param itemVO
	 * @throws EntityNotFoundException
	 * @throws EntityExistsException 
	 */
	void addItem(int userId, ItemVO itemVO) throws EntityNotFoundException, EntityExistsException;

	/**
	 * Service to edit item.
	 * 
	 * @param userId - id of logged in user
	 * @param itemVO
	 * @throws EntityNotFoundException
	 * @throws EntityExistsException 
	 */
	void editItem(int userId, ItemVO itemVO) throws EntityNotFoundException, EntityExistsException;

	/**
	 * Service to get item details by id.
	 * 
	 * @param itemId
	 * @return itemVO
	 * @throws EntityNotFoundException
	 */
	ItemVO getItemDetailsById(Integer itemId) throws EntityNotFoundException;

	/**
	 * Service to check if an item already exists with a name.
	 * 
	 * @param name
	 * @return false if item does not exist with given name
	 * @throws EntityExistsException
	 */
	Boolean doesItemExistWithName(String name) throws EntityExistsException;

	/**
	 * Service to get list of all items
	 * 
	 * @return list containing item details
	 */
	List<ItemVO> getAllItems();

	/**
	 * Service to get item using id.
	 * 
	 * @param id
	 * @return item
	 * @throws EntityNotFoundException
	 */
	Item getItemById(Integer id) throws EntityNotFoundException;

}
