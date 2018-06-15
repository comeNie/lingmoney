package com.mrbt.lingmoney.mobile.controller.info;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mrbt.lingmoney.service.info.InfoInterfaceService;
import com.mrbt.lingmoney.service.users.UsersService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
  *
  *@author yiban
  *@date 2018年1月12日 下午10:07:10
  *@version 1.0
 **/
@Controller
@RequestMapping("/infoInterface")
public class InfoInterfaceController {
    @Autowired
    private InfoInterfaceService infoInterfaceService;
    @Autowired
    private UsersService usersService;

    @RequestMapping(value = "/recommend")
    public String recommend() {
        return "recommend";
    }

    /**
     * 发送短信验证码
     * 
     * @param telephone
     * @param response
     * @throws IOException 
     * @throws Exception
     */
    @RequestMapping(value = "/sendSMS")
    public void sendSMS(String telephone, HttpServletResponse response) throws IOException {
        JSONObject jsonObject = new JSONObject();
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        try {
            jsonObject = infoInterfaceService.getValidCode(telephone);
        } catch (Exception e) {
            e.printStackTrace();

            jsonObject.put("code", ResultInfo.SERVER_ERROR.getCode());
            jsonObject.put("msg", "系统错误");
        }

        jsonObject.write(response.getWriter());
    }

    /**
     * 用户注册
     * 
     * @author YJQ 2017年4月12日
     * @param request request
     * @param telephone 
     *            手机号
     * @param password
     *            密码
     * @param invitationCode
     *            推荐码
     * @param verificationCode
     *            验证码
     * @param channel channel
     * @return pageInfo
     * @throws IOException 
     */
    @RequestMapping("/register")
    public void regist(HttpServletRequest request, HttpServletResponse response, String telephone, String password,
            String invitationCode, String verificationCode, Integer channel) throws IOException {
        PageInfo pageInfo = null;
        JSONObject jsonObject = new JSONObject();
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        try {
            pageInfo = usersService.regist(telephone, password, invitationCode, verificationCode, request.getSession()
                    .getServletContext().getRealPath(""), channel);
        } catch (Exception e) {
            pageInfo = new PageInfo();
            pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
            pageInfo.setMsg("注册失败");
            e.printStackTrace();
        }
        jsonObject.put("code", pageInfo.getCode());
        jsonObject.put("msg", pageInfo.getMsg());
        jsonObject.write(response.getWriter());
    }
}
