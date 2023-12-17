package org.gdufs.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.attoparser.dom.Document;
import org.gdufs.entity.*;
import org.gdufs.general.FileUploadUtil;
import org.gdufs.general.PurchaseInfo;
import org.gdufs.mapper.RhineLabMapper;
import org.gdufs.service.SaveDataRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.gdufs.service.EmployeeService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
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
@CrossOrigin
@RequestMapping
public class RhineLabController {
    @Autowired
    RhineLabMapper rhineLabMapper;

    @Autowired
    EmployeeService employeeService;

    public class AlertMessage {
        private String message;

        public AlertMessage(String message) {
            this.message = message;
        }

    }
    @RequestMapping(value = "/RHINE LAB", method = {RequestMethod.POST, RequestMethod.GET})
    public String listUser(Model model, @RequestParam(value = "start", defaultValue = "0")
    int start, @RequestParam(value = "size", defaultValue = "10") int size) throws Exception {


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
            application.setApplicationForm(application.getApplicationForms().getOriginalFilename());
            application.setPhoto(application.getPhotos().getOriginalFilename());
            if (application.getOthers().isEmpty()) {
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

    //管理修改数据
    @PostMapping("/modifyUpdate")
    public String tomodifyUpdate(Model model, Employee employee){
        rhineLabMapper.updateEmployee(employee);
        List<Employee> employees = rhineLabMapper.findAllMember();
        model.addAttribute("employeeinfos", employees);
        return "/人员管理/管理/modify";
    }

    //管理人员查询员工
    @RequestMapping(value = "/control2")
    public String tocontrol2(Model model,@RequestParam(value = "name") String name) {
        List<Employee> employees = rhineLabMapper.findEmployeeByName(name);
        model.addAttribute("employeeinfos", employees);
        return "/人员管理/管理/control";
    }

    @PostMapping ("applyUpdate")
    public String toapplyUpdate(Model model,Apply apply,
                                @RequestParam(value = "applyNum") int applyNum,
                                @RequestParam(value = "identity") String identity){
        Employee employee = new Employee();
        employee.setEmployeeNum(apply.getEmployeeNum());
        employee.setSectionNum(Long.parseLong(apply.getNewSection()));
        employee.setSalary(apply.getNewSalary());
        if (identity.equals("同意")){
            rhineLabMapper.applyUpdateAgree(applyNum);
            rhineLabMapper.employeeUpdateByApply(employee);
        } else {
            rhineLabMapper.applyUpdateDisgust(applyNum);
        }
        List<Apply> applys = rhineLabMapper.findAllapply();
        model.addAttribute("applyinfos", applys);
        return "/人员管理/管理/application";
    }

    //管理人员进入申请修改页面
    @RequestMapping(value = "/application")
    public String toapplication(Model model) {
        List<Apply> apply = rhineLabMapper.findAllapply();
        model.addAttribute("applyinfos", apply);
        return "/人员管理/管理/application";
    }

    //管理人员进行修改
    @PostMapping(value = "/productionUpdate")
    public String toproductionUpdate(Model model, Product product) {
        rhineLabMapper.updateProduct(product);
        List<Product> products = rhineLabMapper.findAllProduct();
        model.addAttribute("productinfos", products);
        return "/人员管理/管理/production";
    }

    //管理人员进入产品修改页面
    @RequestMapping(value = "/production")
    public String toproduction(Model model) {
        List<Product> product = rhineLabMapper.findAllProduct();
        model.addAttribute("productinfos", product);
        return "/人员管理/管理/production";
    }

    //管理人员查看签到记录
    @RequestMapping(value = "/showsignin")
    public String toshowsignin(Model model) {
        List<employeeSignin> signins = rhineLabMapper.findAllSignin();
        model.addAttribute("signinsinfos", signins);
        return "/人员管理/管理/signIn";
    }

    //管理人员查询专门签到记录
    @RequestMapping(value = "/showsigninByName")
    public String toshowsigninByName(Model model,@RequestParam(value = "name") String name) {
        List<employeeSignin> signins = rhineLabMapper.findAllSigninByName(name);
        model.addAttribute("signinsinfos", signins);
        return "/人员管理/管理/signIn";
    }

    //签到保存到表里
    @PostMapping("/saveData")
    public ResponseEntity<String> saveData(@RequestBody SaveDataRequest request) {
        // 在这里，你可以访问从JavaScript发送过来的数据
        String signInTime = request.getSignInTime();
        String signOutTime = request.getSignOutTime();
        int employeeNum = request.getEmployeeNum();
        double workingHours = request.getWorkingHours();
        Signin signin = new Signin();
        signin.setSigninTime(signInTime);
        signin.setCheckoutTime(signOutTime);
        signin.setEmployeeNum(employeeNum);
        signin.setWorkHour(workingHours);
        rhineLabMapper.Tosignin(signin);
        return ResponseEntity.ok("数据已成功接收");
    }

    //管理人员查询产品
    @RequestMapping(value = "/production2")
    public String toproduction2(Model model,@RequestParam(value = "name") String name) {
        List<Product> product = rhineLabMapper.findproductByName(name);
        model.addAttribute("productinfos", product);
        return "/人员管理/管理/showproduction";
    }
    //管理人员查看所有商品
    @RequestMapping(value = "/showproduction")
    public String toshowproduction(Model model) {
        List<Product> product = rhineLabMapper.findAllProduct();
        model.addAttribute("productinfos", product);
        return "/人员管理/管理/showproduction";
    }

    //管理人员进入修改页面
    @RequestMapping(value = "/modify")
    public String tomodify(Model model) {
        List<Employee> employees = rhineLabMapper.findAllMember();
        model.addAttribute("employeeinfos", employees);
        return "/人员管理/管理/modify";
    }
    //管理人员进入申请页面
    @RequestMapping(value = "/control")
    public String tocontrol(Model model) {
        List<Employee> employees = rhineLabMapper.findAllMember();
        model.addAttribute("employeeinfos", employees);
        return "/人员管理/管理/control";
    }



    //员工进入申请页面
    @RequestMapping(value = "/shenQing1")
    public String toShenQing1() {
        return "/人员管理/员工/shenqing";
    }

    //管理员进入申请页面
    @RequestMapping(value = "/shenQing2")
    public String toShenQing2() {
        return "/人员管理/管理/shenqing";
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
                    return "wrongLin";
                }
            }
        }
        return "wrongLin";
    }

    //员工管理登录判断
    @RequestMapping(value = "/employeeLoginTo", method = {RequestMethod.POST})
    public String toEmployeeLoginTo(Model model,@RequestParam(value = "idEmployee", required = false) String idName,
                                    @RequestParam(value = "password", required = false) String password,
                                    @RequestParam(value = "identity", required = false) String identity) {
        long id = Integer.parseInt(idName);
        Employee employee = rhineLabMapper.checkEmployeeByEmployee(id, password);
        if (employee.getLevel().equals(identity)){
            if(employee.getLevel().equals("1")){
                model.addAttribute("employee", employee);
                return "/人员管理/员工/index";
            }else if(employee.getLevel().equals("2")){
                model.addAttribute("employee", employee);
                return "/人员管理/管理/index";
            }else {
                return "wrongLin";
            }
        }else {
            return "wrongLin";
        }
    }

    //员工申请
    @RequestMapping(value = "/submitEmployee", method = {RequestMethod.POST})
    public String toSubmitEmployee(Model model,
                      @RequestParam(value = "employee_id", required = false) String employeeNum,
                      @RequestParam(value = "name", required = false) String name,
                      @RequestParam(value = "old_department", required = false) String origionSection,
                      @RequestParam(value = "new_department", required = false) String newSection,
                      @RequestParam(value = "old_salary", required = false) String origionSalary,
                      @RequestParam(value = "new_salary", required = false) String newSalary,
                      @RequestParam(value = "reason", required = false) String applyReason
    ) {
        try{
            long id = Integer.parseInt(employeeNum);
            double os = Double.parseDouble(origionSalary);
            double ns = Double.parseDouble(newSalary);
            Apply apply = new Apply();
            apply.setEmployeeNum(id);
            apply.setName(name);
            apply.setOrigionSection(origionSection);
            apply.setNewSection(newSection);
            apply.setOrigionSalary(os);
            apply.setNewSalary(ns);
            apply.setApplyReason(applyReason);
            rhineLabMapper.applySave(apply);
            long check = rhineLabMapper.checkapply(id,applyReason);
            if (check==id)
                return "/人员管理/员工/alert";
        }catch (Exception e){
            return "/人员管理/员工/wrong";
        }

        return "/人员管理/员工/alert";
    }

    //主任申请
    @RequestMapping(value = "/submitEmployeeManner", method = {RequestMethod.POST})
    public String toSubmitEmployeeManner(Model model,
                                   @RequestParam(value = "employee_id", required = false) String employeeNum,
                                   @RequestParam(value = "name", required = false) String name,
                                   @RequestParam(value = "old_department", required = false) String origionSection,
                                   @RequestParam(value = "new_department", required = false) String newSection,
                                   @RequestParam(value = "old_salary", required = false) String origionSalary,
                                   @RequestParam(value = "new_salary", required = false) String newSalary,
                                   @RequestParam(value = "reason", required = false) String applyReason
    ) {
        try{
            long id = Integer.parseInt(employeeNum);
            double os = Double.parseDouble(origionSalary);
            double ns = Double.parseDouble(newSalary);
            Apply apply = new Apply();
            apply.setEmployeeNum(id);
            apply.setName(name);
            apply.setOrigionSection(origionSection);
            apply.setNewSection(newSection);
            apply.setOrigionSalary(os);
            apply.setNewSalary(ns);
            apply.setApplyReason(applyReason);
            rhineLabMapper.applySave(apply);
            long check = rhineLabMapper.checkapply(id,applyReason);
            if (check==id)
                return "/人员管理/管理/alert";
        }catch (Exception e){
            return "/人员管理/管理/wrong";
        }

        return "/人员管理/管理/alert";

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

    @RequestMapping("/joinAll_allTemp")
    public String toJoinAll(Model model) {
        List<Application> applications = rhineLabMapper.getApplicationAll();


        model.addAttribute("applicationInfo", applications);
        return "joinAll";
    }

    @RequestMapping("/projectStatusAction")
    public String toProjectStatusAction(@RequestParam(value = "accomplish", required = false) String buttonAccomplishValue,
                                        @RequestParam(value = "unreviewed", required = false) String buttonUnreviewedValue,

                                        Model model) {
        System.out.println(buttonAccomplishValue);
        System.out.println(buttonUnreviewedValue);
        if (buttonUnreviewedValue != null && !buttonUnreviewedValue.isEmpty()) {
            model.addAttribute("projectNum", buttonUnreviewedValue);
//            rhineLabMapper.changProjectOngoing(Integer.parseInt(buttonAccomplishValue));



            return "accept";
        } else if (buttonAccomplishValue != null && !buttonAccomplishValue.isEmpty()) {
//            rhineLabMapper.deleteProject(Integer.parseInt(buttonAccomplishValue));
            rhineLabMapper.changProjectAccomplished(Integer.parseInt(buttonAccomplishValue));

            return "redirect:project_accTemp";
        } else {
            return "error";
        }
    }

    @RequestMapping("/agreementStatusAction")
    public String toAgreementStatusAction(@RequestParam(value = "agree", required = false) String buttonAgreeValue,
                                        @RequestParam(value = "disagree", required = false) String buttonDisagreeValue,
                                        Model model) {
        if (buttonDisagreeValue != null && buttonAgreeValue.isEmpty()) {

            rhineLabMapper.deleteApplication(Integer.parseInt(buttonAgreeValue));


            return "redirect:toJoinAll";
        } else if (buttonAgreeValue != null && buttonDisagreeValue.isEmpty()) {

            Application application = rhineLabMapper.fromApplicationFindEmail(Integer.parseInt(buttonAgreeValue));
            String email = application.getEmail();




            rhineLabMapper.deleteApplication(Integer.parseInt(buttonAgreeValue));

            return "redirect:toJoinAll";
        } else {
            return "error";
        }
    }

    @RequestMapping("/purchaseStatusAction")
    public String toPurchaseStatusAction(@RequestParam(value = "accomplish", required = false) String buttonAccomplishValue,
                                        Model model) {

        if (buttonAccomplishValue != null) {
//            rhineLabMapper.deletePurchase(Integer.parseInt(buttonAccomplishValue));
            rhineLabMapper.changPurchaseAccomplished(Integer.parseInt(buttonAccomplishValue));

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
    public String getQuery(
            @RequestParam(name = "type", required = false) String type,
            @RequestParam(name = "phone", required = false) String phone,
            @RequestParam(name = "projectNum", required = false) String projectNum,
            @RequestParam(name = "deleteNum", required = false) String deleteNum,
            Model model) {
        if (projectNum == null && deleteNum == null){
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
        if (deleteNum != null) {
            String url = "jdbc:mysql://localhost:3306/rhinelab?serverTimezone=Asia/Shanghai";
            String username = "root";
            String password = "12345678";
            try {
                Connection connection = DriverManager.getConnection(url, username, password);
                Statement statement = connection.createStatement();
                String sql = "DELETE FROM project WHERE projectNum = " + deleteNum + ";";
                System.out.println(sql);
                statement.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
                model.addAttribute("value", "链接数据库失败");
                System.out.println("连接失败");
            } finally {
                return "redirect:query_management";
            }

        }
        return "rhinelabmain";
    }
    @RequestMapping("/product")
    public String toproduct() {
        return "product";
    }
    @RequestMapping("/showProValue")
    public String showValue(Model model) {
        String url = "jdbc:mysql://localhost:3306/rhinelab?serverTimezone=Asia/Shanghai";
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
        String url = "jdbc:mysql://localhost:3306/rhinelab?serverTimezone=Asia/Shanghai";
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
    @RequestMapping("/personal")
    public String toperson(HttpServletRequest request,Model model) {
        Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("userEmailCookie")) {
                        String userEmail = cookie.getValue();
                        System.out.println(userEmail);
                        if (Objects.equals(checkEmail(userEmail), "1")||Objects.equals(checkEmail(userEmail), "2")) {
                            String url = "jdbc:mysql://localhost:3306/rhinelab?serverTimezone=Asia/Shanghai";
                            String username = "root";
                            String password = "12345678";
                            try {
                                Connection connection = DriverManager.getConnection(url, username, password);
                                Statement statement = connection.createStatement();
                                    String sql = "SELECT * FROM employee WHERE email = \""+userEmail+"\";";
                                    ResultSet resultSet = statement.executeQuery(sql);
                                    resultSet.next();
                                    String employeeNum = resultSet.getString("employeeNum");
                                    String name = resultSet.getString("name");
                                    String gender = resultSet.getString("gender");
                                    String photo = resultSet.getString("photo");
                                    String nation = resultSet.getString("nation");
                                    String birthday = resultSet.getString("birthday");
                                    String politicalStatus = resultSet.getString("politicalStatus");
                                    String degree = resultSet.getString("degree");
                                    String marriage = resultSet.getString("marriage");
                                    String birthplace = resultSet.getString("birthplace");
                                    String IDNum = resultSet.getString("IDNum");
                                    String phone = resultSet.getString("phone");
                                    String email = resultSet.getString("email");
                                    String entryTime = resultSet.getString("entryTime");
                                    String level = resultSet.getString("level");
                                    String salary = resultSet.getString("salary");
                                    String sectionNum = resultSet.getString("sectionNum");
                                    String status = resultSet.getString("status");
                                    String passwords = resultSet.getString("password");
                                    String identity = resultSet.getString("identity");

                                    sql = "SELECT name,sectionPeople FROM section WHERE sectionNum = \""+sectionNum+"\";";
                                    resultSet = statement.executeQuery(sql);
                                    resultSet.next();
                                    String section = resultSet.getString("name");
                                    String sectionP = resultSet.getString("sectionPeople");
                                    sql = "SELECT * FROM project WHERE phone = \""+phone+"\";";
                                    resultSet = statement.executeQuery(sql);
                                    resultSet.next();
                                    String Proname;
                                    String Prostatus;
                                    try{
                                        Proname =resultSet.getString("name");
                                    }catch (Exception e){
                                        Proname = "暂无项目";
                                    }
                                    try{
                                        Prostatus =resultSet.getString("status");
                                    }catch (Exception e){
                                        Prostatus = "...";
                                    }

                                    resultSet.next();
                                    resultSet.close();

                                    model.addAttribute("employeeNum", employeeNum);
                                    model.addAttribute("name", name);

                                    model.addAttribute("Proname", Proname);
                                    model.addAttribute("gender", gender);
                                    model.addAttribute("photo", photo);
                                    model.addAttribute("nation", nation);
                                    model.addAttribute("birthday", birthday);
                                    model.addAttribute("politicalStatus", politicalStatus);
                                    model.addAttribute("marriage", marriage);
                                    model.addAttribute("birthplace", birthplace);
                                    model.addAttribute("IDNum", IDNum);
                                    model.addAttribute("phone", phone);
                                    model.addAttribute("email", email);
                                    model.addAttribute("entryTime", entryTime);
                                    System.out.println(level);
                                    if(Objects.equals(level, "2")){level="Director";}
                                    else if(Objects.equals(level, "1")){level="Staff";}
                                    else if(Objects.equals(level, "3")){level="Higest";}
                                    else{level="Normal";}
                                    model.addAttribute("level", level);
                                    model.addAttribute("salary", salary);
                                    model.addAttribute("sectionNum", sectionNum);
                                    model.addAttribute("section", section);
                                    model.addAttribute("sectionP", sectionP);
                                    model.addAttribute("status", status);
                                    if(Prostatus==null)Proname="...";
                                    model.addAttribute("Prostatus", Prostatus);
                                    model.addAttribute("password", passwords);
                                    model.addAttribute("identity", identity);
                                    model.addAttribute("degree", degree);
                                statement.close();
                                connection.close();
                                System.out.println("已经执行完毕1");
                            } catch (SQLException e) {
                                e.printStackTrace();
                                model.addAttribute("value", "链接数据库失败");
                                System.out.println("连接失败");
                            }
                        } else {
                            String url = "jdbc:mysql://localhost:3306/rhinelab?serverTimezone=Asia/Shanghai";
                            String username = "root";
                            String password = "12345678";
                            try {
                                Connection connection = DriverManager.getConnection(url, username, password);
                                Statement statement = connection.createStatement();
                                String sql = "SELECT * FROM user WHERE email = \"" + userEmail + "\";";
                                ResultSet resultSet = statement.executeQuery(sql);
                                resultSet.next();
                                String email= resultSet.getString("email");
                                String gender= resultSet.getString("gender");
                                String name= resultSet.getString("name");
                                String phone= resultSet.getString("phone");
                                sql = "SELECT * FROM project WHERE phone = \""+phone+"\";";
                                resultSet = statement.executeQuery(sql);
                                resultSet.next();
                                String Proname;
                                String Prostatus;
                                try{
                                    Proname =resultSet.getString("name");
                                }catch (Exception e){
                                    Proname = "暂无项目";
                                }
                                try{
                                    Prostatus =resultSet.getString("status");
                                }catch (Exception e){
                                    Prostatus = "...";
                                }
                                model.addAttribute("gender", gender);
                                model.addAttribute("name", name);
                                model.addAttribute("email", email);
                                model.addAttribute("Prostatus", Prostatus);
                                model.addAttribute("Proname", Proname);
                            }catch(Exception e){
                                System.out.println(e);
                            }
                            System.out.println("已经执行完毕2");
                            return "PersonalCentre2";
                        }
                    }
                }
            }
            else{
                System.out.println("已经执行完毕3");
                return "permissionDenied";
            }

        System.out.println("已经执行完毕4");
        return "PersonalCentre";
    }
    @RequestMapping("/checkEmail")
    public String checkEmail(String email){
        List<Employee> employees1 = rhineLabMapper.checkEmployee(email);
        List<Employee> employees2 = rhineLabMapper.checkAdmin(email);
        List<User> users = rhineLabMapper.checkEmail(email);
        if(!employees1.isEmpty()){
            return "1";//是员工
        }else if(!employees2.isEmpty()){
            return "2";//是主任
        }
        else if(!users.isEmpty()){
            return "3";//是用户
        }
        else{
            return "0";//啥都不是
        }

    }
    @RequestMapping("/Modify")
    public String Modify(HttpServletRequest request,Model model) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userEmailCookie")) {
                    String userEmail = cookie.getValue();
                    if (Objects.equals(checkEmail(userEmail), "1")||Objects.equals(checkEmail(userEmail), "2")){
                        String url = "jdbc:mysql://localhost:3306/rhinelab?serverTimezone=Asia/Shanghai";
                        String username = "root";
                        String password = "12345678";
                        try{
                            Connection connection = DriverManager.getConnection(url, username, password);
                            Statement statement = connection.createStatement();
                            String sql = "SELECT * FROM employee WHERE email = \""+userEmail+"\";";
                            ResultSet resultSet = statement.executeQuery(sql);
                            resultSet.next();
                            String name = resultSet.getString("name");
                            model.addAttribute("name", name);
                            resultSet.close();
                            statement.close();
                            connection.close();
                            return "PersonalCentreForm";
                        }catch (Exception e){

                        }
                    }
                    else{
                        String url = "jdbc:mysql://localhost:3306/rhinelab?serverTimezone=Asia/Shanghai";
                        String username = "root";
                        String password = "12345678";
                        try{
                            Connection connection = DriverManager.getConnection(url, username, password);
                            Statement statement = connection.createStatement();
                            String sql = "SELECT * FROM user WHERE email = \""+userEmail+"\";";
                            ResultSet resultSet = statement.executeQuery(sql);
                            resultSet.next();
                            String name = resultSet.getString("name");
                            model.addAttribute("name", name);
                            resultSet.close();
                            statement.close();
                            connection.close();
                            return "PersonalCentreForm";
                    }catch(Exception e){

                        }
                    }


                }
            }

        }
        return "PersonalCentreForm";
    }
    @PostMapping("/Modify2")
    public String Modify2(@ModelAttribute("user") User user,HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userEmailCookie")) {
                    String userEmail = cookie.getValue();
                    String password2=user.getPassword();
                    String url = "jdbc:mysql://localhost:3306/rhinelab?serverTimezone=Asia/Shanghai";
                    String username = "root";
                    String password = "12345678";
                    System.out.println(password2);
                    try{
                        Connection connection = DriverManager.getConnection(url, username, password);
                        Statement statement = connection.createStatement();
                        String sql = "UPDATE user SET password="+password2+" WHERE email = \""+userEmail+"\";";
                        int resultSet = statement.executeUpdate(sql);
                        statement.close();
                        connection.close();
                        //System.out.println("已执行完5");
                        return "redirect:personal";
                    }catch(Exception e){
                        System.out.println(e);
                    }
                }
            }
        }
//        System.out.println("已执行完6");
        return "redirect:personal";
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


