package com.abc.pojo.vo;

import com.abc.pojo.Askbook;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AskbookVo extends Askbook {

    private String username;
}