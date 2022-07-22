package com.es.reportverse.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.util.Base64;

@Service
@AllArgsConstructor
public class MediaServiceImpl implements MediaService{
    @Override
    public String encodeMedia(FileInputStream mediaStream) throws Exception {
        byte[] data = mediaStream.readAllBytes();
        String mediaString = Base64.getEncoder().encodeToString(data);
        mediaStream.close();

        return mediaString;
    }
}
