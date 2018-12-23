package com.emep.changzhi.analyse.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wz
 * @version V1.0
 * @Description: TODO
 * @date 2018/11/23 19:20
 */
@Data
public class ResultBean <T> implements Serializable {

    private static final long serialVersionUID = -783585909126800171L;

    public static final int NO_LOGIN = -1;

    public static final int SUCCESS = 1;

    public static final int FAIL = 0;

    public static final int NO_PERMISSION = 2;

    private String msg = "success";

    private int code = SUCCESS;

    private T content;

    public ResultBean() {
        super();
    }

    public ResultBean(T data) {
        super();
        this.content = data;
    }

    public ResultBean(Throwable e) {
        super();
        this.msg = e.toString();
        this.code = FAIL;
    }


    /**
     * 成功使用
     * @param data
     * @return
     */
    public ResultBean<T> ok(T data){
        ResultBean resultBean = new ResultBean();
        resultBean.setMsg(msg);
        resultBean.setCode(SUCCESS);
        resultBean.setContent(data);

        return resultBean;
    }

    /**
     * 失败使用
     * @param data
     * @return
     */
    public ResultBean<T> error(T data){
        ResultBean resultBean = new ResultBean();
        resultBean.setMsg("fail");
        resultBean.setCode(FAIL);
        resultBean.setContent(data);
        return  resultBean;
    }

    public ResultBean Lack(String args){
        ResultBean resultBean = new ResultBean();
        resultBean.setMsg("参数："+args+"为必填参数");
        resultBean.setCode(FAIL);
        return  resultBean;
    }


    public ResultBean nulls(String args){
        ResultBean resultBean = new ResultBean();
        resultBean.setMsg("参数："+args+"为空");
        resultBean.setCode(FAIL);
        return  resultBean;
    }


    /**
     * 删除
     * @param bool
     * @return
     */
    public ResultBean<T> bool(Boolean bool){
        ResultBean resultBean = new ResultBean();
        if (bool){
            resultBean.setMsg(msg);
            resultBean.setCode(SUCCESS);
        }else {
            resultBean.setMsg("fail");
            resultBean.setCode(FAIL);
        }
        resultBean.setContent(bool);
        return  resultBean;
    }


    public ResultBean<T> boolAdd(T bool){
        if (bool != null ){
            return ok(bool);
        }else {
            return error(bool);
        }
    }

}
