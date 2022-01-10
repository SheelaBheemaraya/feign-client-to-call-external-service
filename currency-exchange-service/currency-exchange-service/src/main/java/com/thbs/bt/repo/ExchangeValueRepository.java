package com.thbs.bt.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thbs.bt.bean.ExchangeValue;

public interface ExchangeValueRepository extends JpaRepository<ExchangeValue, Long> {
	ExchangeValue findByFromAndTo(String from, String to);

}
