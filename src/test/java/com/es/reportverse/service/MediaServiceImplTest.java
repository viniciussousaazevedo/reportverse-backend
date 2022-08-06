package com.es.reportverse.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {MediaServiceImpl.class})
@ExtendWith(SpringExtension.class)
class MediaServiceImplTest {
    @Autowired
    private MediaServiceImpl mediaServiceImpl;

    /**
     * Method under test: {@link MediaServiceImpl#encodeMedia(byte[])}
     */
    @Test
    void testEncodeMedia() throws Exception {
        assertEquals("QUFBQUFBQUE=", mediaServiceImpl.encodeMedia("AAAAAAAA".getBytes("UTF-8")));
    }
}

