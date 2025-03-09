package com.exh.jobseeker.service;

import com.exh.jobseeker.model.entity.JobApplication;
import com.exh.jobseeker.model.entity.JobSeeker;
import com.exh.jobseeker.model.entity.User;
import com.exh.jobseeker.model.enums.JobApplicationStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendJobApplicationStatusUpdateEmail(JobApplication jobApplication) {
        JobSeeker jobSeeker = jobApplication.getJobSeeker();
        User user = jobSeeker.getUser();
        String jobTitle = jobApplication.getJobOpening().getTitle();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(user.getEmail());
        message.setSubject("Update on your job application for " + jobTitle);

        String statusMessage = getStatusUpdateMessage(jobApplication.getStatus(), jobTitle);

        if (jobApplication.getStatus().equals(JobApplicationStatus.REJECTED) &&
                jobApplication.getJobApplicationReview() != null) {
            statusMessage += "\n\nFeedback: " + jobApplication.getJobApplicationReview().getComment();
            statusMessage += "\nRating: " + jobApplication.getJobApplicationReview().getRating() + "/5";
        }

        message.setText(statusMessage);

        mailSender.send(message);
    }

    private String getStatusUpdateMessage(JobApplicationStatus status, String jobTitle) {
        return switch (status) {
            case APPLIED -> "Your application for '" + jobTitle + "' has been received and is pending review.";
            case UNDER_REVIEW -> "Great news! Your application for '" + jobTitle + "' is now under review.";
            case REJECTED ->
                    "Thank you for your interest in '" + jobTitle + "'. Unfortunately, your application was not selected at this time.";
            case INVITED_FOR_INTERVIEW ->
                    "Exciting news! Your application for the '" + jobTitle + "' position has progressed to the interview stage. Our hiring team will reach out shortly to schedule your interview.";
            case HIRED ->
                    "Congratulations! You have been selected for the '" + jobTitle + "' position. We will contact you soon with onboarding details.";
            default ->
                    "There has been an update to your application for '" + jobTitle + "'. The current status is: " + status.name();
        };
    }
}