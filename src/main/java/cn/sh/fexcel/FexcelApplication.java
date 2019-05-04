package cn.sh.fexcel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)
public class FexcelApplication {

    public static void main(String[] args) {
        SpringApplication.run(FexcelApplication.class, args);
    }

}
