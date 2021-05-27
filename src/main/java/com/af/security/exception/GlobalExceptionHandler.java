package com.af.security.exception;

import com.af.security.enums.ErrorCode;
import com.af.security.modal.ResponseVO;
import com.af.security.util.ResponseUtils;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 全局异常处理类
 * @author AF
 * @date 2021/5/14 11:40
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class, UserUnableException.class, UserPasswordNotMatchException.class, TokenExpirationException.class})
    public ResponseVO<Object> userLoginException(BaseException e) {
        return ResponseUtils.error(e.getErrorCode(), e.getData());
    }

    /**
     * 接收过滤器异常
     * @param request
     * @return
     */
    @RequestMapping("/filter/error")
    public ResponseVO<Object> filterException(HttpServletRequest request) {
        BaseException exception = (BaseException) request.getAttribute("exception");
        return ResponseUtils.error(exception.getErrorCode(), exception.getData());
    }


    @ExceptionHandler({AccessDeniedException.class})
    public ResponseVO<Object> accessDeniedException(AccessDeniedException e) {
        return ResponseUtils.error(ErrorCode.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseVO<Object> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errorMsg = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return ResponseUtils.error(ErrorCode.ARGUMENT_INVALID, errorMsg);
    }

    @ExceptionHandler({CustomException.class})
    public ResponseVO<Object> customException(CustomException e) {
        return ResponseUtils.error(e.getErrorCode(), e.getData());
    }

}
