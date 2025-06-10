package com.smartbalaram.planning.repository;

import com.smartbalaram.planning.model.PlanResponse;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlanRepository extends MongoRepository<PlanResponse, String> {
}
