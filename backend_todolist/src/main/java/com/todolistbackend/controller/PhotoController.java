package com.todolistbackend.controller;

import com.todolistbackend.service.PhotoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@Slf4j
public class PhotoController {
    @Autowired
    private PhotoService photoService;

    @PostMapping("/setphoto")
    public void setPhoto(MultipartFile file, Integer userid) throws IOException {
        photoService.setPhoto(file, userid);
    }

    @GetMapping("/getphoto/{userid}")
    public byte[] getPhoto(@PathVariable Integer userid) {
        return photoService.getPhoto(userid);
    }
}
