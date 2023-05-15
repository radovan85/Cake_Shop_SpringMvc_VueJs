package com.radovan.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.radovan.spring.entity.ShippingAddressEntity;

@Repository
public interface ShippingAddressRepository extends JpaRepository<ShippingAddressEntity, Integer>{

	@Query(value = "select * from order_items where order_id = :orderId",nativeQuery = true)
	List<ShippingAddressEntity> findAllByOrderId(@Param("orderId") Integer orderId);
}
