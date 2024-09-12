package com.bhartiTel.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.bhartiTel.entity.Plan;

public interface PlanRepository extends JpaRepository<Plan, Integer> {
	


}
