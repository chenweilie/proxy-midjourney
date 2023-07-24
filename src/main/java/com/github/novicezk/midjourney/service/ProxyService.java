package com.github.novicezk.midjourney.service;

import org.springframework.http.ResponseEntity;

public interface ProxyService {
    ResponseEntity<byte[]> proxyImage(String id);

    ResponseEntity<byte[]> proxy(String reqPath);
}
