package com.company;

import javafx.scene.control.skin.CellSkinBase;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.*;
import java.util.logging.LogManager;

public class Request {
    private String url;
    private String messegeBody;
    private String output;
    private String header;
    private String method;
    private hello fieldList;
    HttpRequest.Builder request;
    HttpClient client;

    public Request(hello hello) {
        this.fieldList = hello;
        fillToTheFields();

    }
    public void fillToTheFields() {
        List<String> all = fieldList.getAllThing();
        System.out.println(all.get(0));
        System.out.println(all.get(1));
        System.out.println(all.get(2));
        System.out.println(all.get(3));
        url = all.get(0);
        output = all.get(1);
        header = all.get(2);
        method = all.get(3);
        messegeBody = all.get(4);
    }

    public HttpRequest.BodyPublisher ofMimeMultipartData(Map<String, String> ourData, String bound) throws IOException {
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
        byteArrays.add(("--" + bound + "--").getBytes(StandardCharsets.UTF_8));
        return HttpRequest.BodyPublishers.ofByteArrays(byteArrays);
    }

    public HttpRequest.BodyPublisher getBodyPublisher() {
        String firstWord = messegeBody.split("=")[0];
        if (firstWord.equals("JSON"))
            return HttpRequest.BodyPublishers.ofString(messegeBody.replaceAll("JSON=", ""));
        else {
            try {
                return ofMimeMultipartData(addToMap(messegeBody), "17101379");
            } catch (IOException e) {
                return HttpRequest.BodyPublishers.noBody();
            }
        }
    }

    public Map<String, String> addToMap(String form) {
        Map headersMap;
        String[] formArr = form.split(";|:");
        headersMap = new HashMap();
        for (int i = 0; i < formArr.length; i++) {
            headersMap.put(formArr[i], formArr[++i]);
        }
        return headersMap;
    }

    public HttpRequest.Builder addHeader(HttpRequest.Builder request) {
        String firstWord = messegeBody.split("=")[0];
        if (firstWord.equals("JSON"))
            request.headers("Content-Type", "application/json");
        else
            request.headers("Content-Type", "multipart/form-data;boundary=" + "546464557");
        return request;
    }

    public String[] convertHeadersToArray(String header) {
        String[] headerArr = header.split(";|:");
        return headerArr;
    }

    public HttpRequest getRequest() {
        request = HttpRequest.newBuilder().version(HttpClient.Version.HTTP_1_1)
                .uri(URI.create(url)).timeout(Duration.ofSeconds(10));
        try {
            request = addMethod(request, method, getBodyPublisher());
            request = addHeader(request);
        } catch (IOException e) {
            request = request.method(method, HttpRequest.BodyPublishers.noBody());
        }
        return request.build();
    }



    public HttpRequest.Builder addMethod(HttpRequest.Builder request, String method, HttpRequest.BodyPublisher bodyPublisher) throws IOException {
        switch (method) {
            case "GET":
                request = request.GET();
                break;
            case "HEAD":
                request = request.method("HEAD", bodyPublisher);
                break;
            case "POST":
                request = request.POST(bodyPublisher);
                break;
            case "PUT":
                request = request.PUT(bodyPublisher);
                break;
            case "DELETE":
                request = request.DELETE();
                break;
            case "PATCH":
                request = request.method("PATCH", bodyPublisher);
                break;
        }
        return request;
    }
    public void getRespons(HttpRequest req) {
        client = HttpClient.newBuilder().followRedirects(
                !fieldList.getOurArgs().contains("-f") ? HttpClient.Redirect.NEVER :
                        HttpClient.Redirect.ALWAYS).connectTimeout(Duration.ofSeconds(25))
                .version(HttpClient.Version.HTTP_1_1).build();
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
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
}
