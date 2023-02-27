package org.roomie.library.data.config;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

// import com.amazonaws.AmazonServiceException;
// import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;

// import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
// import com.amazonaws.services.s3.model.ListObjectsRequest;
// import com.amazonaws.services.s3.model.ObjectListing;
// import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.model.Bucket;


import java.util.List;


@Configuration
@EnableDynamoDBRepositories(basePackages = "org.roomie.library.data.repositories")
public class S3Config {
    @Value("${amazon.dynamodb.endpoint}")
    private String amazonDynamoDBEndpoint;

    @Value("${amazon.aws.accesskey}")
    private String amazonAWSAccessKey;

    @Value("${amazon.aws.secretkey}")
    private String amazonAWSSecretKey;

    @Value("${amazon.aws.region}")
    private static String amazonAWSRegion;

    @Value("$(amazon.aws.s3.bukect)")
    private static String amazonS3Bukect;

    // @Bean
    public AmazonS3 amazonS3() {
        // Config the s3 client
        AmazonS3 amazonS3Client = AmazonS3ClientBuilder.standard()
                .withRegion(amazonAWSRegion)
                .withCredentials(new AWSStaticCredentialsProvider( new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey)))
                .build();

        // Testing for connectivity
        List<Bucket> buckets = amazonS3Client.listBuckets();
        System.out.println("Your {S3} buckets are:");
        for (Bucket b : buckets) {
            System.out.println("* " + b.getName());
        }

        return amazonS3Client;
    };
}
