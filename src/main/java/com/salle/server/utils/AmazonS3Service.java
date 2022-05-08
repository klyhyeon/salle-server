package com.salle.server.utils;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.salle.server.config.AWSProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class AmazonS3Service {

    private AmazonS3 amazonS3;

    private final AWSProperties awsProperties;

    public AmazonS3Service(AWSProperties awsProperties) {
        this.awsProperties = awsProperties;
    }

    public String searchIcon() {
        String fileName = "static/img/searchicon.png";
        return amazonS3.getUrl(awsProperties.getS3().getBucket(), fileName).toString();
    }


    public String uploadImg(String bucket, String fileName, MultipartFile multiFile) throws IOException {
        File uploadFile = convert(multiFile);
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return "uploadS3 success";
    }

    public String uploadImgDirect(String bucket, String fileName, File file) throws IOException {
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, file).withCannedAcl(CannedAccessControlList.PublicRead));
        return "uploadS3 success";
    }


    public File convert(MultipartFile multiFile) throws IOException {
        File uploadFile = new File(multiFile.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(uploadFile);
        fos.write(multiFile.getBytes());
        fos.close();
        return uploadFile;
    }


    public void deleteFile(String bucket, String deleteFile) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, deleteFile));
    }

}
