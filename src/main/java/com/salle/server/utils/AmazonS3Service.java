package com.salle.server.utils;

import com.amazonaws.services.s3.AbstractAmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.salle.server.config.AWSProperties;
import com.salle.server.domain.enumeration.ErrorCode;
import com.salle.server.error.SlErrorException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AmazonS3Service {

    private AbstractAmazonS3 amazonS3;
    private final AWSProperties awsProperties;

    public AmazonS3Service(AWSProperties awsProperties) {
        this.awsProperties = awsProperties;
    }

    public String searchIcon() {
        String fileName = "static/img/searchicon.png";
        return amazonS3.getUrl(awsProperties.getS3().getBucket(), fileName).toString();
    }


    public List<String> convertToFileAndUpload(List<MultipartFile> multipartFiles) {
        String bucketName = awsProperties.getS3().getBucket();
        String endPointUrl = awsProperties.getS3().getEndpointUrl();
        String urlPrefix = endPointUrl + "/" + bucketName + "/";
        List<String> fileUrls = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            File uploadFile = convertMultiPartToFile(multipartFile);
            upholdFileToS3(bucketName, uploadFile);
            String uploadedFileUrl = urlPrefix + uploadFile.getName();
            fileUrls.add(uploadedFileUrl);
        }
        return fileUrls;
    }

    private void upholdFileToS3(String bucketName, File uploadFile) {
        amazonS3.putObject(new PutObjectRequest(
                bucketName,
                uploadFile.getName(),
                uploadFile
        )
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public String convertToFileAndUpload(String bucket, String fileName, MultipartFile multiFile) throws IOException {
        File uploadFile = convertMultiPartToFile(multiFile);
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return "uploadS3 success";
    }

    public String uploadImgDirect(String bucket, String fileName, File file) throws IOException {
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, file).withCannedAcl(CannedAccessControlList.PublicRead));
        return "uploadS3 success";
    }

    public File convertMultiPartToFile(MultipartFile multiFile) {
        String multipartFileName = multiFile.getOriginalFilename();
        File uploadFile = new File(multipartFileName);
        try (FileOutputStream fos = new FileOutputStream(uploadFile)) {
            fos.write(multiFile.getBytes());
        } catch (IOException e) {
            throw new SlErrorException(ErrorCode.FILE_UPLOAD_ERROR);
        }
        return uploadFile;
    }


    public void deleteFile(String bucket, String deleteFile) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, deleteFile));
    }

}
