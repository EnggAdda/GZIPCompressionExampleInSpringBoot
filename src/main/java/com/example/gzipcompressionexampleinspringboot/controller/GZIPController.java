package com.example.gzipcompressionexampleinspringboot.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

@RestController
@RequestMapping("/gzip")
public class GZIPController {


    @PostMapping("/withoutCompression")
    public ResponseEntity<String> withoutCompression(@RequestBody String body) {


        return ResponseEntity
                .status(HttpStatus.OK)
                .body(body);
    }


    @PostMapping("/withCompression")
    public ResponseEntity<byte[]> withCompression(@RequestBody String body) {

        byte[] response =   compress(body.getBytes());

        HttpHeaders httpHeaders  = new HttpHeaders();
        httpHeaders.set(httpHeaders.CONTENT_ENCODING , "gzip");

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(httpHeaders)
                .body(response);
    }

    private byte[] compress(byte [] data) {

        ByteArrayOutputStream  byteArrayOutputStream     = new ByteArrayOutputStream();
        try {
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gzipOutputStream.write(data);
            gzipOutputStream.finish();
            return byteArrayOutputStream.toByteArray();
        }


        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
