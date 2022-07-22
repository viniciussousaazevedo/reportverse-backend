package com.es.reportverse.service;

import java.io.FileInputStream;

public interface MediaService {

    String encodeMedia(FileInputStream mediaStream) throws Exception;

}
