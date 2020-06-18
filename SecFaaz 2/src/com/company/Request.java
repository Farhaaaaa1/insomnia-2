package com.company;


import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.*;

/**
 * in this class we get sth and send
 * them to the net via http ...
 */
public class Request {
    private String url;
    private String messegeBody;
    private String output;
    private String header;
    private String method;
    private RequestModel fieldList;
    HttpRequest.Builder request;
    HttpClient client;

    public Request(RequestModel RequestModel) {
        this.fieldList = RequestModel;
        fillToTheFields();
        initClient();
    }

    /**
     * here we fill what we want to
     * sth in this class to send them
     */
    public void fillToTheFields() {
        List<String> all = fieldList.getAllThing();
        url = all.get(0);
        output = all.get(1);
        header = all.get(2);
        method = all.get(3);
        if (method == null)
            method = "GET";
        messegeBody = all.get(4);
    }

    /**
     * we create our body publisher
     *
     * @param ourData messege body
     * @param bound   onr bound
     * @return body Publisher
     * @throws IOException
     */
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

    /**
     * here we get our body publisher
     * and get its json or not (form data and ... )
     *
     * @return the messege body
     */
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

    /**
     * here we create hash map it's about form data and in better
     * way it's messege body
     */
    public Map<String, String> addToMap(String form) {
        Map headersMap;
        String[] formArr = form.split("=|&");
        headersMap = new HashMap();
        for (int i = 0; i < formArr.length - 1; i++) {
            int k = i + 1;
            headersMap.put(formArr[i], formArr[k]);
        }
        return headersMap;
    }

    /**
     * in this method we add the messege body
     *
     * @param request
     * @return
     */
    public HttpRequest.Builder addHeader(HttpRequest.Builder request) {
        String firstWord = messegeBody.split("=")[0];
        if (firstWord.equals("JSON"))
            request.headers("Content-Type", "application/json");
        else
            request.headers("Content-Type", "multipart/form-data;boundary=" + "546464557");
        return request;
    }

    /**
     * here in this method we get our request
     *
     * @return request
     */
    public HttpRequest getRequest() {
        request = HttpRequest.newBuilder().version(HttpClient.Version.HTTP_1_1)
                .uri(URI.create(url)).timeout(Duration.ofSeconds(10));
        if (header != null) {
            System.out.println(header);
            System.out.println(header);
            request = request.headers(header.split("[:;]"));
        }
        try {
            request = addMethod(request, method, getBodyPublisher());
            request = addHeader(request);
        } catch (IOException e) {
            request = request.method(method, HttpRequest.BodyPublishers.noBody());
        }
        return request.build();
    }

    /**
     * if we use -f argoman we have to redirect our request always
     * here we choos we want to redirect or not
     */
    public void initClient() {
        client = HttpClient.newBuilder().followRedirects(
                !fieldList.getOurArgs().contains("-f") ? HttpClient.Redirect.NEVER :
                        HttpClient.Redirect.ALWAYS).connectTimeout(Duration.ofSeconds(25))
                .version(HttpClient.Version.HTTP_1_1).build();
    }

    /**
     * may be here is our
     * most important method that we have in this class our may be in this code
     * here we send our request (up our request)
     */
    public void sendRequest() {
        HttpResponse<byte[]> response = null;
        try {
            long T = System.currentTimeMillis();
            response = client.send(getRequest(), HttpResponse.BodyHandlers.ofByteArray());
            long T1 = System.currentTimeMillis();
            String time = String.valueOf(T1 - T);
            System.out.println("time: " + time + " ms\n");
            float transfer = response.body().length / 1024f;
            String transferByte = String.valueOf(transfer) + " KB\n";
            System.out.println(transferByte);
            int statusCode = response.statusCode();
            System.out.println("status code: " + statusCode + "\n");
            if (fieldList.getOurArgs().contains("-H")) {
                HttpHeaders header = response.headers();
                System.out.println(header.toString());
            }
            byte[] body = response.body();
            System.out.println(new String(body, Charset.defaultCharset()));
            if (fieldList.getOurArgs().contains("-O")) {
                System.out.println("halle soltan");
                FileWork.saveResBody(body,output);
            }
            //System.out.println("time : " + time + " transferres byte : " + transferByte + " body : " + body + " headers : " + header);
        } catch (Exception e) {
            System.out.println("don't use https and ... ");
           // e.printStackTrace();
        }
    }

    /**
     * here we create request refer to the method's name
     *
     * @param request       our request that we want to add our request to it
     * @param method        name of our request
     * @param bodyPublisher may be means our messege body
     * @return our request
     */
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

}
