package com.rick.sys.GlobalException;

import com.rick.sys.VO.ResultVO;
import com.rick.sys.customException.CustomException;
import com.rick.sys.enums.ResultEnums;
import com.rick.sys.untils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ExceptionHandle {

    /***
     * 可能出现的未知异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultVO handle(Exception e) {
        log.error("【系统异常】{}", e);
        return ResultVOUtil.error(ResultEnums.FAILURE);
    }

    /***
     * 参数异常 -- ConstraintViolationException()
     * 用于处理类似http://localhost:8080/user/getUser?age=30&name=yoyo请求中age和name的校验引发的异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultVO urlParametersExceptionHandle(ConstraintViolationException e) {
        log.error("【请求参数异常】:{}", e);
        //收集所有错误信息
        List<String> errorMsg = e.getConstraintViolations()
                .stream().map(s -> s.getMessage()).collect(Collectors.toList());
        return ResultVOUtil.error(ResultEnums.PARAM_ERROR.getCode(), errorMsg.toString());
    }

    /***
     * 参数异常 --- MethodArgumentNotValidException和BindException
     * MethodArgumentNotValidException --- 用于处理请求参数为实体类时校验引发的异常 --- Content-Type为application/json
     * BindException --- 用于处理请求参数为实体类时校验引发的异常  --- Content-Type为application/x-www-form-urlencoded
     * @param e
     * @return
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultVO bodyExceptionHandle(Exception e) {
        log.error("【请求参数异常】:{}", e);
        BindingResult bindingResult = null;
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
            bindingResult = ex.getBindingResult();
        } else {
            BindException ex = (BindException) e;
            ex.getBindingResult();
        }
        if (bindingResult != null) {
            //收集所有错误信息
            List<String> errorMsg = bindingResult.getFieldErrors().stream()
                    .map(s -> s.getDefaultMessage()).collect(Collectors.toList());
            return ResultVOUtil.error(ResultEnums.PARAM_ERROR.getCode(), errorMsg.toString());
        }

        return ResultVOUtil.error(ResultEnums.PARAM_ERROR);
    }
    /***
     * 自定义异常 --- 自定义异常一般不要设置为ERROR级别,因为我们用自定义的异常主要是为了辅助我们处理业务逻辑,
     *          --- 它们其实并不能被真正当作异常来看待
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.IM_USED)
    @ExceptionHandler(value = CustomException.class)
    public ResultVO elegantExceptionHandle(CustomException e) {
        log.warn("【自定义异常】", e);
        return ResultVOUtil.error(e.getCode(), e.getMessage());
    }

}
