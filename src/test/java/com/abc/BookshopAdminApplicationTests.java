package com.abc;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
class BookshopAdminApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("12344444444444");
    }

    @Test
    public void test(){
        Date date = DateUtil.date();
        String dateStr = DateUtil.format(date, "MM-dd");
        System.out.println(dateStr);
        System.out.println("=========");
        Double totalPrice = 68.9;
        System.out.println(Convert.toDouble(totalPrice));
    }

}
