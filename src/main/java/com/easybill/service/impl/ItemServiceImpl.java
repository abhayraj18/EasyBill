package com.easybill.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easybill.exception.EntityExistsException;
import com.easybill.exception.EntityNotFoundException;
import com.easybill.model.Item;
import com.easybill.model.User;
import com.easybill.pojo.ItemVO;
import com.easybill.repository.ItemRepository;
import com.easybill.service.ItemService;
import com.easybill.service.UserService;
import com.easybill.specifications.ItemSpecification;
import com.easybill.util.CommonUtil;
import com.easybill.util.DateUtil;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private ModelMapper modelMapper;
	
	@Override
	public void addItem(int userId, ItemVO itemVO) throws EntityNotFoundException, EntityExistsException {
		doesItemExistWithName(itemVO.getName());
		Item item = new Item();
		setValues(item, itemVO, userId);
		itemRepository.save(item);
	}
	
	@Override
	public void editItem(int userId, ItemVO itemVO) throws EntityNotFoundException, EntityExistsException {
		Item item = getItemById(itemVO.getId());
		if (!StringUtils.equalsIgnoreCase(item.getName(), itemVO.getName())) {
			doesItemExistWithName(itemVO.getName());
		}
		setValues(item, itemVO, userId);
		itemRepository.save(item);
	}

	/**
	 * Set field values
	 * 
	 * @param item
	 * @param itemVO
	 * @param userId
	 * @throws EntityNotFoundException
	 */
	private void setValues(Item item, ItemVO itemVO, int userId) throws EntityNotFoundException {
		User user = userService.getUserById(userId);
		item.setName(itemVO.getName());
		item.setBaseUnit(CommonUtil.getUnit(itemVO.getBaseUnit()));
		item.setLargeUnit(CommonUtil.getUnit(itemVO.getLargeUnit()));
		item.setUnitConversionValue(itemVO.getUnitConversionValue());
		setUnitPrice(item, itemVO.getBaseUnitPrice(), itemVO.getLargeUnitPrice(), itemVO.getUnitConversionValue());
		// If adding, id will be null
		if (Objects.isNull(itemVO.getId())) {
			item.setAddedBy(user);
			item.setAddedAt(DateUtil.getCurrentTime());
		} else {
			item.setModifiedBy(user);
			item.setModifiedAt(DateUtil.getCurrentTime());
		}
	}
	
	/**
	 * Set unit price based on baseUnitPrice and largeUnitPrice
	 * 
	 * @param item
	 * @param baseUnitPrice
	 * @param largeUnitPrice
	 * @param unitConversionValue
	 */
	private void setUnitPrice(Item item, Float baseUnitPrice, Float largeUnitPrice, Integer unitConversionValue) {
		/*
		 * If baseUnitPrice is null or <= 0, set baseUnitPrice based on largeUnitPrice and unitConversionValue.
		 * Else, set largeUnitPrice based on baseUnitPrice and unitConversionValue.
		 */
		if (Objects.isNull(baseUnitPrice) || baseUnitPrice <= 0) {
			item.setLargeUnitPrice(largeUnitPrice);
			item.setBaseUnitPrice(largeUnitPrice / unitConversionValue);
		} else {
			item.setBaseUnitPrice(baseUnitPrice);
			item.setLargeUnitPrice(baseUnitPrice * unitConversionValue);
		}
	}

	@Override
	public ItemVO getItemDetailsById(Integer itemId) throws EntityNotFoundException {
		Item item = getItemById(itemId);
		return mapToItemVO(item);
	}

	/**
	 * Map item to itemVO
	 * 
	 * @param item
	 * @return itemVO
	 */
	private ItemVO mapToItemVO(Item item) {
		ItemVO itemVO = modelMapper.map(item, ItemVO.class);
		itemVO.setBaseUnit(item.getBaseUnit().name());
		itemVO.setLargeUnit(item.getLargeUnit().name());
		return itemVO;
	}

	@Override
	public Boolean doesItemExistWithName(String name) throws EntityExistsException {
		if (itemRepository.count(ItemSpecification.hasName(name).and(ItemSpecification.isActive())) > 0) {
			throw new EntityExistsException("Item already exists with name: " + name);
		}
		return Boolean.FALSE;
	}

	@Override
	public List<ItemVO> getAllItems() {
		return itemRepository.findAll(ItemSpecification.isActive()).stream().map(this::mapToItemVO).collect(Collectors.toList());
	}

	@Override
	public Item getItemById(Integer itemId) throws EntityNotFoundException {
		return itemRepository.findById(itemId)
				.orElseThrow(() -> new EntityNotFoundException("Item could not be found with id: " + itemId));
	}

}
