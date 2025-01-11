package com.review.reviewplatform.controllers;

import com.review.reviewplatform.models.Business;
import com.review.reviewplatform.models.User;
import com.review.reviewplatform.services.Interfaces.BusinessService;
import com.review.reviewplatform.services.Interfaces.UserService;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/businesses",
        produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
@CrossOrigin(origins = "*")
public class BusinessController {

    @Autowired
    private BusinessService businessService;
    private UserService userService;


    @PostMapping
    public ResponseEntity<Business> createBusiness(@RequestBody Business business) {
        User owner = userService.getUserById(business.getOwner().getId());

        // Ensure only business owners can create businesses
        if (!owner.isBusinessOwner()) {
            return ResponseEntity.status(403).body(null); // Forbidden
        }

        Business createdBusiness = businessService.createBusiness(business);
        return ResponseEntity.ok(createdBusiness);
    }



    @GetMapping("/{id}")
    public ResponseEntity<Business> getBusinessById(@PathVariable Long id) {
        return ResponseEntity.ok(businessService.getBusinessById(id));
    }

    @GetMapping
    public ResponseEntity<List<Business>> getAllBusinesses() {
        return ResponseEntity.ok(businessService.getAllBusinesses());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Business>> searchBusinesses(@RequestParam String keyword) {
        return ResponseEntity.ok(businessService.searchBusinesses(keyword));
    }

    @GetMapping("/location")
    public ResponseEntity<List<Business>> getBusinessesByLocation(
            @RequestParam String city,
            @RequestParam String country) {
        return ResponseEntity.ok(businessService.getBusinessesByCity(city, country));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Business> updateBusiness(
            @PathVariable Long id,
            @RequestBody Business business) {
        return ResponseEntity.ok(businessService.updateBusiness(id, business));
    }

    @PutMapping("/{id}/verify")
    public ResponseEntity<?> verifyBusiness(@PathVariable Long id) {
        businessService.verifyBusiness(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBusiness(@PathVariable Long id) {
        try {
            businessService.deleteBusiness(id);
            return ResponseEntity.ok("<message>Business deleted successfully.</message>");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404)
                    .body("<error>Business not found with ID: " + id + "</error>");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("<error>Something went wrong. Please try again later.</error>");
        }
    }
    @GetMapping("/{id}/images")
    public ResponseEntity<Set<String>> getBusinessImages(@PathVariable Long id) {
        Business business = businessService.getBusinessById(id);
        return ResponseEntity.ok(business.getImageUrls());
    }

}