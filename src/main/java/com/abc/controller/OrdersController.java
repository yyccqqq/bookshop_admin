package com.abc.controller;

import java.util.List;
import java.util.Map;

import com.abc.pojo.Orders;
import com.abc.pojo.vo.OrderVo;
import com.abc.pojo.vo.Result;
import com.abc.service.IOrdersService;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yan
 * @since 2020-04-19
 */
@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private IOrdersService ordersService;

    @GetMapping("/getData")
    public Map<String, List<String>> getData() {
        return ordersService.getData();
    }

    @GetMapping(value = "/search/{pageNum}/{rows}")
    public PageInfo<Orders> search(@PathVariable Integer pageNum, @PathVariable Integer rows) {
        return ordersService.search(pageNum, rows);
    }

    @GetMapping("/findOne/{id}")
    public OrderVo findOne(@PathVariable String id) {
        return ordersService.findOne(id);
    }

    @PostMapping("/delete")
    public Result delte(@RequestBody String[] ids) {
        try {
            ordersService.delete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            return new Result(false, "删除失败");
        }
    }

}
