package cn.sh.fexcel.model;

import cn.sh.fexcel.Util.commons;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


/**
 * BaseResponse
 *
 * @author PeterChen
 * @summary BaseResponse
 * @Copyright (c) 2019, PeterChen.
 * @Description BaseResponse
 * @since 2019-04-22 21:10
 */
@Getter
@Setter
public class BaseResponse<T> {

    private String Message;

    private Date datetime;

    private T data;

    private boolean isSuccess;

    private BaseResponse() {

    }

    public static class ResponseFacory {
        public static BaseResponse success(Object list) {
            BaseResponse response = new BaseResponse();
            response.setMessage(commons.SUCCESS_MSG);
            response.setSuccess(true);
            response.setData(list);
            response.setDatetime(new Date());
            return response;
        }

        public static BaseResponse fail(String message) {
            if (message == null) {
                message = commons.TAB;
            }
            BaseResponse response = new BaseResponse();
            response.setSuccess(false);
            response.setMessage(commons.FAIL_MSG + message);
            response.setData(null);
            response.setDatetime(new Date());
            return response;
        }
    }
}
