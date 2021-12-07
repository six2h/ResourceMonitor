package com.example.demo;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import oshi.SystemInfo;
import oshi.hardware.*;


import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import oshi.SystemInfo.*;
import oshi.software.os.OperatingSystem;
import oshi.software.os.windows.nt.CentralProcessor;
import oshi.software.os.windows.nt.GlobalMemory;

@RestController
public class TestController {
    @RequestMapping("/get")
    private String getOshi() throws IOException, InterruptedException {


        Map<String, Object> object = new HashMap<>();

        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();

        //processor Information
        Processor[] processors = hal.getProcessors();
        Object[] objects = Arrays.stream(processors).toArray();

        //Memory Information
        Memory ram = hal.getMemory();


        Runtime r = Runtime.getRuntime();

        Process p = r.exec("netstat -an -p tcp -o");
        //TODO
        //tried this command to execute on ubuntu ec2 instance, but this is not working. So we have to execute manually on the cmd and store the output of it into a file.
        // Then read that file in the resource monitor
        //Process p = r.exec("netstat -ntu|awk '{print $5}'|cut -d: -f1 -s|sort|uniq -c|sort -nk1 -r");

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(p.getInputStream())
        );
        System.out.println("reader response"+reader.toString());

        String line = reader.readLine();
        System.out.println("line"+line);

        int a = 1;
        while (line != null) {
            System.out.println(line);
            line = reader.readLine();
            a++;
        }


        object.put("TCP Connections", a);
        object.put("Available Memory", ram.getAvailable());
        object.put("Total Memory", ram.getTotal());
        object.put("Processor Info", objects[0]);


        JSONObject jo = new JSONObject(object);
        return jo.toString();


    }


}

