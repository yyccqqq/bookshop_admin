package com.abc.controller;

import com.abc.pojo.Askbook;
import com.abc.pojo.vo.AskbookVo;
import com.abc.pojo.vo.Result;
import com.abc.service.IAskbookService;
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
 *  前端控制器
 * </p>
 *
 * @author yan
 * @since 2020-04-19
 */
@RestController
@RequestMapping("/askbook")
public class AskbookController {

    @Autowired
    private IAskbookService askbookService;

    @PostMapping("/search/{pageNum}/{rows}")
    public PageInfo<Askbook> search(@PathVariable Integer pageNum,@PathVariable Integer rows,@RequestBody Askbook askbook){
        return askbookService.search(pageNum,rows,askbook);
    }

    @GetMapping("/findOne/{id}")
    public AskbookVo findOne(@PathVariable Integer id){
        return askbookService.findOne(id);
    }

    @PostMapping("/delete")
    public Result delete(@RequestBody Integer[] ids){
        try {
            askbookService.delete(ids);
            return new Result(true,"删除成功");
        } catch (Exception e) {
            return new Result(false,"删除失败");
        }
    }
}
