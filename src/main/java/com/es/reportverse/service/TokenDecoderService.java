package com.es.reportverse.service;

import com.es.reportverse.model.AppUser;

import javax.servlet.http.HttpServletRequest;

public interface TokenDecoderService {

    AppUser decodeAppUserToken(HttpServletRequest request);
}
