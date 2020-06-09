package com.company;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.*;
import java.util.logging.LogManager;

public class Request {
    private String url;
    private String json;
    private String output;
    private String upload;
    private String header;
    private String method;
    private String data;
    private hello fieldList;

    public Request(hello hello) {
        this.fieldList = hello;
        fillToTheFields();
        HttpRequest.Builder request = HttpRequest.newBuilder().version(HttpClient.Version.HTTP_1_1).uri(URI.create(url)).timeout(Duration.ofSeconds(80)).
                headers().method();

    }

    public HttpRequest.Builder addMethod(HttpRequest.Builder request, String method) {
        switch (method) {
            case "GET":
                request = request.GET();
                break;
            case "HEAD":
                request = request.method("HEAD", HttpRequest.BodyPublishers.noBody());
                break;
            case "POST":
                request = request.POST(HttpRequest.BodyPublishers.noBody());
                break;
            case "PUT":
                //  request = request.PUT();
                break;
            case "DELETE":
                request = request.DELETE();
                break;
            case "PATCH":
                request = request.method("PATCH", HttpRequest.BodyPublishers.noBody());
                break;
        }
        return request;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getUpload() {
        return upload;
    }

    public void setUpload(String upload) {
        this.upload = upload;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void fillToTheFields() {
        List<String> all = fieldList.getAllThing();
        url = all.get(0);
        json = all.get(1);
        output = all.get(2);
        upload = all.get(3);
        header = all.get(4);
        method = all.get(5);
        data = all.get(6);
    }

    public HttpRequest.BodyPublisher ofMimeMultipartData(Map<String, String> ourData,  String bound) throws IOException {
        var byteArrays = new ArrayList<byte[]>();
        byte[] separator = ("--" + bound + "\r\nContent-Disposition: form-data; name=")
                .getBytes(StandardCharsets.UTF_8);
        for (Map.Entry<String, String> A : ourData.entrySet()) {
            byteArrays.add(separator);
            Path file = new File(A.getValue()).toPath();
            if (Files.exists(file)) {
              //  var path = file;
                String mimeType = Files.probeContentType(file);
                byteArrays.add(("\"" + A.getKey() + "\"; filename=\"" + file.getFileName() + "\"\r\nContent-Type: " + mimeType + "\r\n\r\n")
                        .getBytes(StandardCharsets.UTF_8));
                byteArrays.add(Files.readAllBytes(file));
                byteArrays.add("\r\n".getBytes(StandardCharsets.UTF_8));
            } else {
                byteArrays.add(("\"" + A.getKey() + "\"\r\n\r\n" + A.getValue() + "\r\n").getBytes(StandardCharsets.UTF_8));
            }
        }
        byteArrays.add(  (  "--"+ bound +"--"  ).getBytes( StandardCharsets.UTF_8 ) );
        return HttpRequest.BodyPublishers.ofByteArrays(byteArrays);
    }
}
