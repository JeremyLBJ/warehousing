package com.rick.sys.controller;

import com.rick.sys.VO.ResultVO;
import com.rick.sys.VO.UserVO;
import com.rick.sys.customException.CustomException;
import com.rick.sys.entity.SysUser;
import com.rick.sys.enums.ResultEnums;
import com.rick.sys.untils.ResultVOUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/user")
@Validated //校验URL中的请求参数时，该注解需要放在这里
public class UserInfoController {

    /***
     * ConstraintViolationException异常统一处理测试
     * @param name
     * @param age
     * @return
     */
    @GetMapping("/getUser")
    public ResultVO<SysUser> getUser(@NotNull(message = "name不能为空") String name,
                                     @Min(value = 18, message = "未满18岁")
                                      @NotNull(message = "age不能为空") Integer age) {
        SysUser userInfo = new SysUser();
        userInfo.setAge(age);
        userInfo.setName(name);
        return ResultVOUtil.success(userInfo);
    }

    /***
     * MethodArgumentNotValidException异常统一处理测试
     * Content-Type为application/json  ---> 实体类前必须要加一个@RequestBody注解才能获得到前端传来的参数
     * @param userInfo
     * @return
     */
    @PostMapping("/saveUser")
    public ResultVO<SysUser> saveUser(@RequestBody @Validated SysUser userInfo) {
        userInfo.setId(11);
        return ResultVOUtil.success(userInfo);
    }

    /***
     * BindException异常统一处理测试
     * Content-Type为application/x-www-form-urlencoded ---> 即表单请求，此时不能用@RequestBody注解
     * @param userInfo
     * @return
     */
    @PostMapping("/saveUserInfo")
    public ResultVO<SysUser> saveUserInfo(@RequestBody @Validated SysUser userInfo) {
        userInfo.setId(11);
        return ResultVOUtil.success(userInfo);
    }

    /***
     * ElegantException --- 自定义异常、未知异常和URL---统一处理测试
     * @param id
     * @return
     */
    @GetMapping("/getUserById/{id}")
    public ResultVO<SysUser> getUser(@PathVariable @Max(value = 100, message = "id应小于100") Integer id) {
        SysUser userInfo = new SysUser();
        //自定义异常
        if (id < 0) {
            throw new CustomException(ResultEnums.PARAM_ERROR);
        }
        //未知异常
        else if (id == 0) {
            throw new RuntimeException("xxoo");
        }
        userInfo.setId(id);
        userInfo.setAge(18);
        userInfo.setName("yoyo");
        return ResultVOUtil.success(userInfo);
    }
}
