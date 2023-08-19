package com.todolistbackend.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PhotoService {
    void setPhoto(MultipartFile file, Integer userid) throws IOException;

    byte[] getPhoto(@PathVariable Integer userid);
}
