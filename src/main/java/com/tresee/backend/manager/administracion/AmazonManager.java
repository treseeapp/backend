package com.tresee.backend.manager.administracion;

import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

@Service
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
    @Value("${upload.directory}")
    private String path;


    @PostConstruct
    private void initializeAmazon() {
        BasicAWSCredentials creds = new BasicAWSCredentials(accessKey, secretKey);
        this.s3client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(creds)).withRegion(String.valueOf(Region.EU_London)).build();
    }

    public String uploadFile(MultipartFile multipartFile) {

        String fileName = "";
        File file = null;
        try {
            file = convertMultiPartToFile(multipartFile);
            fileName = generateFileName(multipartFile);
            uploadFileTos3bucket(fileName, file);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
            fileName = null; // SI NO SE HA PODIDO SUBIR LA FOTO, EN DDBB SE GUARDA UN NULL
            file.delete();
        }
        return fileName;
    }

    private String generateFileName(MultipartFile multiPart) {

        /*
         * Cogemos la fecha actual para crear el nombre del archivo
         * antes de subirlo a Amazon
         * */

        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {

        /*
         * Volvemos a juntar las partes del archivo ya que desde el
         * cliente nos llega en partes
         * */
        File convFile = new File(path+file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        this.s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.Private));
    }

    public String getFile(String fileName) {
        String urlPreSigned = this.generatePresignedUrl(fileName);
        return urlPreSigned;
    }

     /*
     * Generamos una URL temporal de un tiempo establecido del nombre
     * del archivo recibido que se encuentra en el bucket de Amazon
     * */
    private String generatePresignedUrl(String fileName) {

        try {

            Date expiration = new Date();
            long expTimeMillis = expiration.getTime();
            expTimeMillis += 1000 * 50;
            expiration.setTime(expTimeMillis);

            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(bucketName, fileName)
                            .withMethod(HttpMethod.GET)
                            .withExpiration(expiration);
            URL url = s3client.generatePresignedUrl(generatePresignedUrlRequest);

            return url.toString();

        } catch (SdkClientException e) {
            return null;
        }
    }

    public boolean deletePicture(String nombre) {
        try {
            this.s3client.deleteObject(this.bucketName, nombre);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
