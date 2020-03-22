package com.tresee.backend.manager;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Region;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

public class AmazonManager {

    private AmazonS3 s3client;

    @Value("${amazon.endpointUrl}")
    private String endpointUrl;
    @Value("${amazon.bucketName}")
    private String bucketName;
    @Value("${amazon.accessKey}")
    private String accessKey;
    @Value("${amazon.secretKey}")
    private String secretKey;

    @PostConstruct
    private void initializeAmazon() {
        BasicAWSCredentials creds = new BasicAWSCredentials(accessKey, secretKey);
        this.s3client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(creds)).withRegion(String.valueOf(Region.EU_London)).build();
    }
}
