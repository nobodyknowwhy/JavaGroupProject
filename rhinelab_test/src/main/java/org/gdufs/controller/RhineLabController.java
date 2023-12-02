package org.gdufs.controller;

import org.gdufs.entity.*;
import org.gdufs.general.FileUploadUtil;
import org.gdufs.general.PurchaseInfo;
import org.gdufs.mapper.RhineLabMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

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
    public String joinAction(Application application, Model model) throws Exception {
        List<Employee> employees = rhineLabMapper.isAlreadyEmployee(application.getEmail());
        if(employees.isEmpty()){
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
        }else {
            model.addAttribute("messageJoinError", "you have already a member of RHINE LAB");
            return "Rhinelab_join";
        }


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

    @RequestMapping(value = "/shenQing")
    public String toShenQing() {
        return "/人员管理/员工/shenqing";
    }

    @RequestMapping(value = "/employeeLogin")
    public String toEmployeeLogin(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("userEmailCookie")) {
                String userEmail = cookie.getValue();
                if(checkAdmin(userEmail)){
                    return "/人员管理/index";
                }else if(checkEmployee(userEmail)){
                    return "/人员管理/index";
                }else {
                    return "permissionDenied";
                }
            }
        }
        return "permissionDenied";
    }


    @RequestMapping(value = "/employeeLoginTo")
    public String toEmployeeLoginTo(int id,String password) {
        Employee employee = rhineLabMapper.checkEmployeeByEmployee(id, password);
        if(employee.getLevel().equals("1")){
            return "/人员管理/员工/index";
        }else if(employee.getLevel().equals("2")){
            return "/人员管理/管理/index";
        }else {
            return "permissionDenied";
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
            return "launchProject";
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

    @RequestMapping("/purchase_select")
    public String toPurchaseSelect(HttpServletRequest request){
        if (checkUserNameInCookie(request)) {
            return "product_all";
        } else {
            return "Rhinelab_resign";
        }
    }

    @RequestMapping ("/purchaseChoice")
    public String handleButtonChoicePurchase(@RequestParam("button") String buttonValue, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (buttonValue.equals("productPurchase")) {
            return "订购";
        } else if (buttonValue.equals("purchaseDetail")) {
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("userEmailCookie")) {
                        String userEmail = cookie.getValue();
                        if (checkAdmin(userEmail)) {
                            return "redirect:purchase_allTemp";
                        } else {
                            return "permissionDenied";
                        }
                    }
                }
            }
        } else {
            System.out.println("阿弥诺斯");
            return "product_all";
        }
        return "rhinelabmain";

    }


    @RequestMapping("/purchase_allTemp")
    public String toPurchaseAll(Model model) {
        List<Purchase> purchases = rhineLabMapper.getPurchaseAll();
        List<PurchaseInfo> purchaseInfos = new ArrayList<>();

        for (Purchase purchase : purchases) {
            long productNum = purchase.getProductNum();
            List<Product> products = rhineLabMapper.getProductOne(productNum);
            for (Product product : products) {
                PurchaseInfo purchaseInfo = new PurchaseInfo();
                purchaseInfo.setPurchase(purchase);
                purchaseInfo.setProductName(product.getName());
                purchaseInfos.add(purchaseInfo);
            }
        }

        model.addAttribute("purchaseInfos", purchaseInfos);
        return "purchase_acc";
    }

    @RequestMapping("/projectStatusAction")
    public String toProjectStatusAction(@RequestParam(value = "accomplish", required = false) String buttonAccomplishValue,
                                        @RequestParam(value = "unreviewed", required = false) String buttonUnreviewedValue,
                                        Model model) {
        if (buttonUnreviewedValue != null && !buttonUnreviewedValue.isEmpty()) {
            model.addAttribute("projectNum", buttonUnreviewedValue);
            return "accept";
        } else if (buttonAccomplishValue != null && !buttonAccomplishValue.isEmpty()) {
            rhineLabMapper.deleteProject(Integer.parseInt(buttonAccomplishValue));
            return "redirect:project_accTemp";
        } else {
            return "error";
        }
    }

    @RequestMapping("/purchaseStatusAction")
    public String toPurchaseStatusAction(@RequestParam(value = "accomplish", required = false) String buttonAccomplishValue,
                                        Model model) {

        if (buttonAccomplishValue != null && !buttonAccomplishValue.isEmpty()) {
            rhineLabMapper.deletePurchase(Integer.parseInt(buttonAccomplishValue));
            return "redirect:purchase_allTemp";
        } else {
            return "error";
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
    public String accept_con(Model model,@RequestParam(value = "projectNum", required = false) String PNum,@RequestParam("department") List<String> SelDepart) {
        String url = "jdbc:mysql://localhost:3306/rhinelabtest?serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "12345678";
        System.out.println(PNum);
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            String sql = "UPDATE project SET status='进行中' WHERE projectNum = " + PNum + ";";
            int rowsAffected = statement.executeUpdate(sql);
            int count = SelDepart.size();
            for (int i = 0; i < count; i++) {
                String SecNum = SelDepart.get(i);
                String sql2 = "SELECT COUNT(*) FROM receive WHERE projectNum = ? AND sectionNum = ?";
                PreparedStatement checkStatement = connection.prepareStatement(sql2);
                checkStatement.setString(1, PNum);
                checkStatement.setString(2, SecNum);
                ResultSet resultSet = checkStatement.executeQuery();
                resultSet.next();
                int rowCount = resultSet.getInt(1);
                if (rowCount == 0) {
                    String insertSql = "INSERT INTO receive (projectNum, sectionNum) VALUES (?, ?)";
                    PreparedStatement insertStatement = connection.prepareStatement(insertSql);
                    insertStatement.setString(1, PNum);
                    insertStatement.setString(2, SecNum);
                    insertStatement.executeUpdate();
                    insertStatement.close();
                }
                checkStatement.close();
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("value", "链接数据库失败");
            System.out.println("连接失败");
        }

        return "project_select";
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

    @RequestMapping("/checkEmployee")
    public Boolean checkEmployee(String email){
        List<Employee> employees = rhineLabMapper.checkEmployee(email);
        if(!employees.isEmpty()){
            return true;
        }else {
            return false;
        }
    }

    @RequestMapping("/launchProject")
    public String launchProject(Model model){
        return "launchProject";
    }

    @PostMapping("/launch")
    public String launch(@ModelAttribute("project") Project project, Model model, HttpServletResponse response) {
        rhineLabMapper.launch(project);
        return "rhinelabmain";
    }
}


