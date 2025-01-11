package com.review.reviewplatform.services.Interfaces;

import com.review.reviewplatform.models.Business;
import java.util.List;

public interface BusinessService {
    Business createBusiness(Business business);
    Business getBusinessById(Long id);
    List<Business> getAllBusinesses();
    List<Business> getBusinessesByCity(String city, String country);
    Business updateBusiness(Long id, Business business);
    void deleteBusiness(Long id);
    List<Business> searchBusinesses(String keyword);
    void verifyBusiness(Long id);
}