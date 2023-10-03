package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;
    private final UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping()
    public String homeView(@ModelAttribute("credential") Credential credential, @ModelAttribute("note") Note note,  Model model, Authentication authentication) {
        int userId=this.userService.getUserid(authentication.getName());
        model.addAttribute("getFile",this.fileService.getAllFiles(userId));

        return "home";

    }

    @PostMapping("/fileUpload")
    public String uploadFile(@ModelAttribute("credential") Credential credential,@ModelAttribute("note") Note note,@RequestParam("fileUpload")MultipartFile fileUpload, Model model,Authentication authentication) throws IOException {
        String uploadFailure=null;

        if(!fileService.isFileNameAvailable(fileUpload.getOriginalFilename())){
            uploadFailure= "The file name already exists.";
        }

        if (uploadFailure==null){
            if (fileUpload.isEmpty()){
                model.addAttribute("changesFail",true);
            } else {
            fileService.uploadFile(fileUpload,authentication.getName());
            model.addAttribute("changesSuccess",true);
            }
        } else {
            model.addAttribute("failure",uploadFailure);
        }


        int userId=this.userService.getUserid(authentication.getName());
        model.addAttribute("getFile",this.fileService.getAllFiles(userId));
        return "result";
    }

    @GetMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable(name = "fileId") Integer fileId,Model model){
        this.fileService.deleteFile(fileId);
        model.addAttribute("changesSuccess",true);

        return "result";
    }

    @GetMapping("/download/{fileName}")
    @ResponseBody
    public void downloadFile(@PathVariable(name = "fileName") String fileName, HttpServletResponse httpServletResponse)throws IOException{
        File file=fileService.getFile(fileName);
        httpServletResponse.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename = "+file.getFileName();
        httpServletResponse.setHeader(headerKey, headerValue);
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        outputStream.write(file.getFileData());
        outputStream.close();
    }
}
