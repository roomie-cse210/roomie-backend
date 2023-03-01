package org.roomie.library.data.services;

// import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

// import java.util.List;

import javax.annotation.PostConstruct;

// TODO need to cite https://medium.com/oril/uploading-files-to-aws-s3-bucket-using-spring-boot-483fcb6f8646

@Service
public class AmazonClient {

    @Value("${amazon.s3.endpoint}")
    private String endpointUrl;

    @Value("${amazon.aws.accesskey}")
    private String accessKey;

    @Value("${amazon.aws.secretkey}")
    private String secretKey;

    @Value("${amazon.s3.bucket}")
	private String bucketName;

    private AmazonS3 s3client;

    @PostConstruct
    private void initializeAmazon() {
    // Config the s3 client
    this.s3client = AmazonS3ClientBuilder.standard()
                        .withCredentials(new AWSStaticCredentialsProvider(new
                        BasicAWSCredentials(this.accessKey, this.secretKey)))
                        .build();

    }

    // private byte[] convertImage(String file) throws IOException {
    //     String base64String = file.split(",")[1];
    //     byte[] convFile = Base64.getDecoder().decode(base64String);
    //     return convFile;
    // }
    
    private void uploadFileTos3bucket(String fileName, String file) {
        s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public String uploadFile(String base64Image, String fileName) {

        String fileUrl = "";
        try {
            // byte[] file = convertImage(base64Image);
            fileUrl = endpointUrl + "/"  + fileName + ".JPG";
            uploadFileTos3bucket(fileName, base64Image);
        } catch (Exception e) {
           e.printStackTrace();
        }
        return fileUrl;
    }
}
