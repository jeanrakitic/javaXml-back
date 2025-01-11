package com.review.reviewplatform.services.Implementations;

import com.review.reviewplatform.models.Business;
import com.review.reviewplatform.repositories.BusinessRepository;
import com.review.reviewplatform.services.Interfaces.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    private BusinessRepository businessRepository;

    @Override
    public Business createBusiness(Business business) {
        return businessRepository.save(business);
    }

    @Override
    public Business getBusinessById(Long id) {
        return businessRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Business not found"));
    }

    @Override
    public List<Business> getAllBusinesses() {
        return businessRepository.findAll();
    }

    @Override
    public List<Business> getBusinessesByCity(String city, String country) {
        return businessRepository.findByCityAndCountry(city, country);
    }

    @Override
    public Business updateBusiness(Long id, Business businessDetails) {
        Business business = getBusinessById(id);
        business.setName(businessDetails.getName());
        business.setDescription(businessDetails.getDescription());
        business.setAddress(businessDetails.getAddress());
        business.setCity(businessDetails.getCity());
        business.setCountry(businessDetails.getCountry());
        business.setPhone(businessDetails.getPhone());
        business.setWebsite(businessDetails.getWebsite());
        business.setBusinessImageUrl(businessDetails.getBusinessImageUrl());
        return businessRepository.save(business);
    }

    @Override
    public void deleteBusiness(Long id) {
        Business business = getBusinessById(id);
        businessRepository.delete(business);
    }

    @Override
    public List<Business> searchBusinesses(String keyword) {
        return businessRepository.findByNameContainingIgnoreCase(keyword);
    }

    @Override
    public void verifyBusiness(Long id) {
        Business business = getBusinessById(id);
        business.setVerified(true);
        businessRepository.save(business);
    }
}