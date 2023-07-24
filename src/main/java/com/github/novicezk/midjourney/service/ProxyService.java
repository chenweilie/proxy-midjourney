package com.github.novicezk.midjourney.service;

import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;

public interface ProxyService {


    ResponseEntity<byte[]> proxy(String reqPath) throws URISyntaxException;
}
