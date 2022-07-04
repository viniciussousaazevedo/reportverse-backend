package com.es.reportverse.service;

import com.es.reportverse.model.AppUser;

import javax.servlet.http.HttpServletRequest;

public interface TokenManagerService {

    AppUser decodeAppUserToken(HttpServletRequest request);

    String simulateToken(String username, HttpServletRequest request);
}
