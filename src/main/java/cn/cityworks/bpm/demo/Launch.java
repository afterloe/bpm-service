package cn.cityworks.bpm.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * Created by afterloe on 10/11/2017.
 */
@SpringCloudApplication
@EnableFeignClients
public class Launch {

    public static void main(String[] args) {
        SpringApplication.run(Launch.class, args);
    }
}
