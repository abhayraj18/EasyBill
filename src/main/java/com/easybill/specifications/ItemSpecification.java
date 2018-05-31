package com.easybill.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.easybill.model.Item;

@SuppressWarnings("serial")
public class ItemSpecification {

	private ItemSpecification() {}
	
	public static Specification<Item> isActive() {
		return new Specification<Item>() {
			@Override
			public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("archive"), false);
			}
		};
	}
	
	public static Specification<Item> hasName(String name) {
		return new Specification<Item>() {
			@Override
			public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("name"), name);
			}
		};
	}
	
}
