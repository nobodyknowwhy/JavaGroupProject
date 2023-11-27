package org.gdufs.controller;

import org.gdufs.entity.*;
import org.gdufs.general.FileUploadUtil;
import org.gdufs.mapper.RhineLabMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Controller
@RequestMapping
public class RhineLabController {
    @Autowired
    RhineLabMapper rhineLabMapper;

    @RequestMapping(value = "/RHINE LAB", method = {RequestMethod.POST, RequestMethod.GET})
    public String listUser(Model model, @RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "10") int size) throws Exception {


        return "RHINE LAB";
    }

    @RequestMapping(value = "/rhinelabmain")
    public String toMain(HttpServletRequest request) {
        if (checkUserNameInCookie(request)) {
            return "rhinelabmain";
        } else {
            return "Rhinelab_resign";
        }
    }

    @RequestMapping("/what_is_rhine")
    public String toWIR() {
        return "what_is_rhine";
    }

    @RequestMapping("/why_rhine")
    public String toWR() {
        return "why_rhine";
    }

    @RequestMapping(value = "/Rhinelab_join")
    public String toJoin() {
        return "Rhinelab_join";
    }

    @RequestMapping(value = "/Rhinelab_join_action", method = {RequestMethod.POST})
    public String joinAction(Application application) throws Exception {
        FileUploadUtil.saveFileToForm(application.getApplicationForms());
        FileUploadUtil.saveFileToPhoto(application.getPhotos());
        application.setApplicationForm(application.getApplicationForms().getName());
        application.setPhoto(application.getPhotos().getName());
        if (application.getOthers() == null) {
            rhineLabMapper.applicationSave(application);
        } else {
            rhineLabMapper.applicationSaveO(application);
        }

        return "joinafter";
    }


    @RequestMapping("/Rhinelab_resign")
    public String toResign() {
        return "Rhinelab_resign";
    }


    @PostMapping("/rhineLog")
    public String toLog(@ModelAttribute("user") User user, Model model, HttpServletResponse response) {
        String email = user.getEmail();
        String password = user.getPassword();

        List<User> users = rhineLabMapper.checkUser(email, password);

        if (users.isEmpty()) {
            model.addAttribute("messageLogError", "Email or password is incorrect!");
            return "Rhinelab_resign";
        } else {
            String userNameStore = user.getName();
            Cookie userNameCookie = new Cookie("userNameOfRhineLab", userNameStore);
            Cookie userEmailCookie = new Cookie("userEmailCookie", user.getEmail());
            userNameCookie.setMaxAge(365 * 24 * 60 * 60);
            response.addCookie(userNameCookie);
            response.addCookie(userEmailCookie);
            return "redirect:rhinelabmain";
        }
    }

    @RequestMapping(value = "/rhineRegister", method = {RequestMethod.POST})
    public String toRegister(@ModelAttribute("user") User user, Model model, String confirmPassword, HttpServletResponse response) {
        String password = user.getPassword();
        if (!Objects.equals(confirmPassword, password)) {
            model.addAttribute("messageRegisterError", "Register error: the password and the confirmed password is not equalled!");
            return "Rhinelab_resign";
        } else {

            List<User> userEmail = rhineLabMapper.checkEmail(user.getEmail());
            List<User> userName = rhineLabMapper.checkUserName(user.getName());
            if (userEmail.isEmpty()) {
                if (userName.isEmpty()) {
                    rhineLabMapper.userSave(user);
                    String userNameStore = user.getName();
                    Cookie userNameCookie = new Cookie("userNameOfRhineLab", userNameStore);
                    Cookie userEmailCookie = new Cookie("userEmailCookie", user.getEmail());
                    userNameCookie.setMaxAge(365 * 24 * 60 * 60);
                    response.addCookie(userNameCookie);
                    response.addCookie(userEmailCookie);
                    return "redirect:rhinelabmain";
                } else {
                    model.addAttribute("messageRegisterError", "Register error: the user name have been used!");
                    return "Rhinelab_resign";
                }


            } else {
                model.addAttribute("messageRegisterError", "Register error: the email have been used!");
                return "Rhinelab_resign";
            }
        }

    }

    @RequestMapping(value = "/new_1")
    public String toNewOne(Model model, HttpServletRequest request) {
        if (checkUserNameInCookie(request)) {
            return "new_1";
        } else {
            return "Rhinelab_resign";
        }

    }

    @GetMapping("/checkUserNameInCookie")
    public Boolean checkUserNameInCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userNameOfRhineLab")) {
                    return true;
                }
            }
        }

        return false;
    }


    @RequestMapping("/project_select")
    public String toProjectSelect(HttpServletRequest request){
        if (checkUserNameInCookie(request)) {
            return "project_select";
        } else {
            return "Rhinelab_resign";
        }

    }

    @PostMapping("/projectChoice")
    public String handleButtonChoice(@RequestParam("button") String buttonValue, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (buttonValue.equals("projectLaunch")) {
            return "project_select";
        } else if (buttonValue.equals("projectAcceptance")) {
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("userEmailCookie")) {
                        String userEmail = cookie.getValue();
                        if (checkAdmin(userEmail)) {
                            return "redirect:project_accTemp";
                        } else {
                            return "permissionDenied";
                        }
                    }
                }
            }
        } else {
            System.out.println("what are you fucking clicking?");
            return "product";
        }
        return "rhinelabmain";

    }

    @RequestMapping("/project_accTemp")
    public String toProjectAcc(Model model) {
        List<Project> projects = rhineLabMapper.getProjectAll();
        model.addAttribute("projects", projects);
        return "project_acc";
    }

    @RequestMapping("/projectStatusAction")
    public String toProjectStatusAction(@RequestParam("button") String buttonValue, Model model) {
        System.out.println(buttonValue);
        if(buttonValue.equals("unreviewed")){
            System.out.println(buttonValue);
            return "accept";
        }else {
            System.out.println(buttonValue);
            rhineLabMapper.deleteProject(Integer.parseInt(buttonValue));

            return "redirect:project_accTemp";
        }


    }



    @RequestMapping("/query_management")
    public String toQueryManagement() {
        return "query_management";
    }


    @PostMapping("/query")
    public String getQuery(@RequestParam("type") String type, @RequestParam("phone") String phone, Model model) {
        QueryResult queryResult = new QueryResult();
        if (type.equals("order")) {
            List<Purchase> resultList = rhineLabMapper.getPurchase(phone);
            queryResult.setQueryType("order");
            queryResult.setResult(resultList);
        } else if (type.equals("project")) {
            List<Project> resultList = rhineLabMapper.getProject(phone);
            queryResult.setQueryType("project");
            queryResult.setResult(resultList);
        }
        model.addAttribute("queryResult", queryResult);

        return "query_result";
    }

    @RequestMapping("/product")
    public String toproduct() {
        return "product";
    }
    @RequestMapping("/showProValue")
    public String showValue(Model model) {
        String url = "jdbc:mysql://localhost:3306/rhinelabtest?serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "12345678";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            for(int i=0;i<43;i++){
                String sql = "SELECT * FROM product WHERE productNum = "+i+";";
                ResultSet resultSet = statement.executeQuery(sql);
                resultSet.next();
                String value = resultSet.getString("quantity");
                String cost = resultSet.getString("unitPrice");
                resultSet.close();
                String values="value"+i;
                String costs="cost"+i;
                model.addAttribute(values, value);
                model.addAttribute(costs, cost);
            }
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("value", "链接数据库失败");
            System.out.println("连接失败");
        }

        return "product";
    }
    @RequestMapping("/accept_con")
    public String accept_con(Model model) {
        String url = "jdbc:mysql://localhost:3306/rhinelabtest?serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "12345678";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
                String sql = "SELECT * FROM product WHERE productNum =0;";
                ResultSet resultSet = statement.executeQuery(sql);
                resultSet.next();
                String value = resultSet.getString("quantity");
                String cost = resultSet.getString("unitPrice");
                resultSet.close();
                String values="value";
                String costs="cost";
                model.addAttribute(values, value);
                model.addAttribute(costs, cost);
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("value", "链接数据库失败");
            System.out.println("连接失败");
        }

        return "product";
    }



    @RequestMapping("/checkAdmin")
    public Boolean checkAdmin(String email){
        List<Employee> employees = rhineLabMapper.checkAdmin(email);
        if(!employees.isEmpty()){
            return true;
        }else {
            return false;
        }

    }



}


