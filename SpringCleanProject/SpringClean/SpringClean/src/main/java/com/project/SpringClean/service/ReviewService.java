package com.project.SpringClean.service;

import com.project.SpringClean.model.Reviews;
import com.project.SpringClean.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepo;

    public Reviews saveReview(Reviews review) {
        return reviewRepo.save(review);
    }

    public List<Reviews> getReviewsByCustomer(Long customerId) {
        return reviewRepo.findByCustomer_customerId(customerId);
    }

    public List<Reviews> getReviewsForCompany(Long companyId) {
        return reviewRepo.findByCompany_companyCleanerId(companyId);
    }

    public List<Reviews> getReviewsForCleaner(Long cleanerId) {
        return reviewRepo.findByCleaner_cleanerId(cleanerId);
    }

    public Double getCompanyAvgRating(Long companyId) {
        return reviewRepo.getAvgRatingByCompany(companyId);
    }

    public Long getCompanyReviewCount(Long companyId) {
        return reviewRepo.getReviewCountByCompany(companyId);
    }

    public Double getCleanerAvgRating(Long cleanerId) {
        return reviewRepo.getAvgRatingByCleaner(cleanerId);
    }

    public Long getCleanerReviewCount(Long cleanerId) {
        return reviewRepo.getReviewCountByCleaner(cleanerId);
    }

}

