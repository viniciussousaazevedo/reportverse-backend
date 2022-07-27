package com.es.reportverse.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Base64;

@Service
@AllArgsConstructor
public class MediaServiceImpl implements MediaService{
    @Override
    public String encodeMedia(byte[] data) throws Exception {
        return Base64.getEncoder().encodeToString(data);
    }
}
