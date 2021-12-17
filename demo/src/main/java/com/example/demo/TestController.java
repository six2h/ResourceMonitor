package com.example.demo;

import com.sun.management.OperatingSystemMXBean;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {
    @RequestMapping("/get")
    private String getOshi() throws IOException {

        OperatingSystemMXBean bean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        Map<String, Object> object = new HashMap<>();
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        CentralProcessor processor = hal.getProcessor();
        CentralProcessor.ProcessorIdentifier processorIdentifier = processor.getProcessorIdentifier();

        //processor Information
        System.out.println("Processor Vendor: " + processorIdentifier.getVendor());
        System.out.println("Processor Name: " + processorIdentifier.getName());
        System.out.println("Processor ID: " + processorIdentifier.getProcessorID());
        System.out.println("Identifier: " + processorIdentifier.getIdentifier());
        System.out.println("Microarchitecture: " + processorIdentifier.getMicroarchitecture());
        System.out.println("Frequency (Hz): " + processorIdentifier.getVendorFreq());
        System.out.println("Frequency (GHz): " + processorIdentifier.getVendorFreq() / 1000000000.0);
        System.out.println("Number of physical packages: " + processor.getPhysicalPackageCount());
        System.out.println("Number of physical CPUs: " + processor.getPhysicalProcessorCount());
        System.out.println("Number of logical CPUs: " + processor.getLogicalProcessorCount());


        //Operating System Information
        OperatingSystem operatingSystem = si.getOperatingSystem();

        System.out.println("Family: " + operatingSystem.getFamily());
        System.out.println("Manufacturer: " + operatingSystem.getManufacturer());
        System.out.println("Number of bits supported by the OS (32 or 64): " + operatingSystem.getBitness());



        //Memory Information
        GlobalMemory globalMemory = hal.getMemory();

        long usedMemory = globalMemory.getTotal() - globalMemory.getAvailable();

        System.out.println("Total memory: " + FormatUtil.formatBytes(globalMemory.getTotal()));
        System.out.println("Available memory: " + FormatUtil.formatBytes(globalMemory.getAvailable()));
        System.out.println("Used memory: " + FormatUtil.formatBytes(usedMemory));

        //File file = new File(
               //"C:\\Users\\indur\\Downloads\\demo\\demo\\src\\main\\java\\com\\example\\demo\\output.txt");
     File file = new File(
                "/home/ubuntu/output.txt");
        BufferedReader br
                = new BufferedReader(new FileReader(file));
        String st;
        String[] maxTcp = null;
       if ((st = br.readLine()) != null){
             maxTcp = st.split(" ");
            //System.out.println("aaa"+maxTcp[6]+"bb"+maxTcp[7]);

        }
        //Performance

        Performance performance = new Performance(bean.getProcessCpuLoad(), bean.getSystemLoadAverage(), processor.getLogicalProcessorCount(),processor.getPhysicalProcessorCount(),
                processor.getProcessorCpuLoadTicks(),usedMemory, globalMemory.getTotal(), globalMemory.getAvailable(),"300",maxTcp[7]);

        JSONObject performanceObj = new JSONObject(performance);
        object.put("Performance",performanceObj);


        //Agility
        Sensors sen = hal.getSensors();
        Agility agility = new Agility(sen.getCpuTemperature(),sen.getCpuVoltage(),sen.getFanSpeeds());
        JSONObject agilityObj = new JSONObject(agility);
        object.put("Agility",agilityObj);

        //Security
        List<NetworkIF> nif = si.getHardware().getNetworkIFs();
        OperatingSystem os1 = si.getOperatingSystem();
        Security security = new Security(nif.size(),os1.getProcessCount());
        JSONObject securityObj = new JSONObject(security);
        object.put("Security",securityObj);



        JSONObject jo = new JSONObject(object);
        return jo.toString();


    }



}

