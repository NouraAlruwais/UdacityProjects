package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {


    private final FileMapper fileMapper;
    private final UserService userService;

    public FileService(FileMapper fileMapper, UserService userService) {
        this.fileMapper = fileMapper;
        this.userService = userService;
    }

    public boolean isFileNameAvailable(String fileName){
        return fileMapper.getFile(fileName)==null;
    }

    public File getFile(String fileName){
            return fileMapper.getFile(fileName);
    }

    public List<File> getAllFiles(int userId) {
        return fileMapper.getAllFiles(userId);
    }

    public void uploadFile(MultipartFile fileUpload, String username) throws IOException {
        int userId=userService.getUserid(username);

        fileMapper.insert(new File(null, fileUpload.getOriginalFilename(), fileUpload.getContentType(),fileUpload.getSize(),userId,fileUpload.getBytes()));
    }

    public void deleteFile(Integer fileId) {
        fileMapper.delete(fileId);
    }
}
