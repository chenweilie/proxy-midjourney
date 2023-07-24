package com.github.novicezk.midjourney.service;

import com.github.novicezk.midjourney.exception.InvalidSessionException;
import com.github.novicezk.midjourney.support.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class ProxyServiceImpl implements ProxyService{

    @Autowired
    private TaskStoreService taskStoreService;

    @Override
    public ResponseEntity<byte[]> proxyImage(String id) {

        Task task = taskStoreService.get(id);

        if (null == task){
            return ResponseEntity.notFound().build();
        }

        String url = task.getImageUrl();

        RestTemplate restTemplate = new RestTemplate();

        //有BUG，只能返回png格式的图片
        restTemplate.setInterceptors(Collections.singletonList((request, body, execution) -> {
            request.getHeaders().add("Accept", "image/png");
            return execution.execute(request, body);
        }));

        byte[] imageBytes = restTemplate.getForObject(url, byte[].class);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "image/png");

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<byte[]> proxy(String reqPath) {

        String url = "https://cdn.discordapp.com/" + reqPath;
        System.out.println(url);

        RestTemplate restTemplate = new RestTemplate();

        //有BUG，只能返回png格式的图片
        restTemplate.setInterceptors(Collections.singletonList((request, body, execution) -> {
            request.getHeaders().add("Accept", "image/png");
            return execution.execute(request, body);
        }));

        byte[] imageBytes = restTemplate.getForObject(url, byte[].class);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "image/png");

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

}
