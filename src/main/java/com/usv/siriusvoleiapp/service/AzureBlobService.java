package com.usv.siriusvoleiapp.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.azure.core.http.rest.PagedIterable;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.models.BlobItem;

@Component
public class AzureBlobService {

   @Autowired
   BlobServiceClient blobServiceClient;

   @Autowired
   BlobContainerClient blobContainerClient;

   public String upload(MultipartFile multipartFile) 
        throws IOException {

      // Todo UUID
      Random random = new Random();
      String numeImagine = random.nextInt() + multipartFile.getOriginalFilename();

      BlobClient blob = blobContainerClient
            .getBlobClient(numeImagine);
      blob.upload(multipartFile.getInputStream(),
            multipartFile.getSize(), true);
        
      return numeImagine;
   }

   public String uploadMultipleFile(List<MultipartFile> multipartFiles)
           throws IOException {
      StringBuilder imagini= new StringBuilder();

      // Todo UUID
      for (MultipartFile multipartFile: multipartFiles
           ) {
         Random random = new Random();
         String numeImagine = random.nextInt() +multipartFile.getOriginalFilename();

         BlobClient blob = blobContainerClient
                 .getBlobClient(numeImagine);
         blob.upload(multipartFile.getInputStream(),
                 multipartFile.getSize(), true);

         if(imagini.length()==0)
            imagini.append(numeImagine);
         else
            imagini.append(", ").append(numeImagine);
      }


      return imagini.toString();
   }

   public byte[] getFile(String fileName) {

      BlobClient blob = blobContainerClient.getBlobClient(fileName);
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      blob.download(outputStream);
      return outputStream.toByteArray();
   }

   public String getFileURL(String fileName){
      BlobClient blob = blobContainerClient.getBlobClient(fileName);
      return blob.getBlobUrl();
   }

   public List<String> listBlobs() {

      PagedIterable<BlobItem> items = blobContainerClient.listBlobs();
      List<String> names = new ArrayList<>();
      for (BlobItem item : items) {
         names.add(item.getName());
      }
      return names;

   }

   public Boolean deleteBlob(String blobName) {

      BlobClient blob = blobContainerClient.getBlobClient(blobName);
      blob.delete();
      return true;
   }

}