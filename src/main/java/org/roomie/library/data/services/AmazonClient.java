package org.roomie.library.data.services;

// import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

// import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
// import com.amazonaws.AmazonServiceException;
// import com.amazonaws.SdkClientException;
// import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;

// import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
// import com.amazonaws.services.s3.AmazonS3Client;
// import com.amazonaws.services.s3.AmazonS3ClientBuilder;
// import com.amazonaws.services.s3.model.ListObjectsRequest;
// import com.amazonaws.services.s3.model.ObjectListing;
// import com.amazonaws.services.s3.model.S3ObjectSummary;
// import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

// import java.util.List;

import javax.annotation.PostConstruct;

@Service
public class AmazonClient {

    @Value("${amazon.s3.endpoint}")
    private String endpointUrl;

    @Value("${amazon.aws.accesskey}")
    private String accessKey;

    @Value("${amazon.aws.secretkey}")
    private String secretKey;

    @Value("$(amazon.s3.bucket)")
    private static String bucketName;

    private AmazonS3 s3client;

    @PostConstruct
    private void initializeAmazon() {

    // Config the s3 client
    this.s3client = AmazonS3ClientBuilder.standard()
                        .withCredentials(new AWSStaticCredentialsProvider(new
                        BasicAWSCredentials(this.accessKey, this.secretKey)))
                        .build();

    // // Testing for connectivity
    // List<Bucket> buckets = amazonS3Client.listBuckets();
    // System.out.println("Your {S3} buckets are:");
    // for (Bucket b : buckets) {
    // System.out.println("* " + b.getName());
    // }
    }
}
