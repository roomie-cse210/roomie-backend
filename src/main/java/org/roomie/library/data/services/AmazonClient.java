package org.roomie.library.data.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;


import javax.annotation.PostConstruct;

// TODO need to cite https://medium.com/oril/uploading-files-to-aws-s3-bucket-using-spring-boot-483fcb6f8646

@Service
public class AmazonClient {

    @Value("${amazon.aws.region}")
    private String amazonAWSRegion;

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
    this.s3client = AmazonS3ClientBuilder.standard().withEndpointConfiguration(new AmazonS3ClientBuilder.EndpointConfiguration (endpointUrl, amazonAWSRegion))
                        .withCredentials(new AWSStaticCredentialsProvider(new
                        BasicAWSCredentials(this.accessKey, this.secretKey)))
                        .build();

    }

    private byte[] convertImage(String file) throws IOException {
        String base64String = file.split(",")[1];
        byte[] convFile = Base64.getDecoder().decode(base64String);
        return convFile;
    }
    
    private void uploadFileTos3bucket(String fileName, String file) throws IOException {
        byte[] convFile = convertImage(file);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(convFile);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(convFile.length);
        
        s3client.putObject(bucketName, fileName, inputStream, metadata);
    }

    public String uploadFile(String base64Image, String fileName) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        String fileNameAppendix = fileName + "-" + timeStamp + ".JPG";
        String fileURL = "";
        try {
            uploadFileTos3bucket(fileNameAppendix, base64Image);
            fileURL = "https://" + bucketName + ".s3.us-west-2.amazonaws.com/" + fileNameAppendix;
        } catch (Exception e) {
           e.printStackTrace();
        }
        return fileURL;
    }

    public void deleteFile(String fileURL) {
        String[] parts = fileURL.split("/");
        String keyName = parts[parts.length-1]; 
        s3client.deleteObject(new DeleteObjectRequest(bucketName, keyName));
    }
}
