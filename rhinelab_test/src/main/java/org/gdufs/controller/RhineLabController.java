package org.gdufs.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.gdufs.entity.User;
import org.gdufs.general.FileUploadUtil;
import org.gdufs.entity.Application;
import org.gdufs.entity.Student;
import org.gdufs.mapper.RhineLabMapper;
import org.gdufs.mapper.StudentMapper;
import org.gdufs.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Objects;


@Controller
@RequestMapping
public class RhineLabController {
    @Autowired
    RhineLabMapper rhineLabMapper;

    @RequestMapping(value = "/RHINE LAB", method = { RequestMethod.POST,	RequestMethod.GET })
    public String listUser(Model model, @RequestParam(value = "start", defaultValue = "0") int start,
                           @RequestParam(value = "size", defaultValue = "10") int size) throws Exception {


        return "RHINE LAB";
    }

    @RequestMapping(value = "/rhinelabmain")
    public String toMain(HttpServletRequest request){
        if(checkUserNameInCookie(request)){
            return "rhinelabmain";
        }else {
            return "Rhinelab_resign";
        }
    }

    @RequestMapping("/what_is_rhine")
    public String toWIR(){
        return "what_is_rhine";
    }

    @RequestMapping("/why_rhine")
    public String toWR(){
        return "why_rhine";
    }

    @RequestMapping(value = "/Rhinelab_join")
    public String toJoin(){
        return "Rhinelab_join";
    }

    @RequestMapping(value = "/Rhinelab_join_action",method = {RequestMethod.POST})
    public String joinAction(Application application) throws Exception{
        FileUploadUtil.saveFileToForm(application.getApplicationForms());
        FileUploadUtil.saveFileToPhoto(application.getPhotos());
        application.setApplicationForm(application.getApplicationForms().getName());
        application.setPhoto(application.getPhotos().getName());
        if(application.getOthers()==null){
            rhineLabMapper.applicationSave(application);
        }else {
            rhineLabMapper.applicationSaveO(application);
        }

        return "joinafter";
    }


    @RequestMapping("/Rhinelab_resign")
    public String toResign(){
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
            userNameCookie.setMaxAge(365 * 24 * 60 * 60);
            response.addCookie(userNameCookie);

            return "redirect:rhinelabmain";
        }
    }

    @RequestMapping(value = "/rhineRegister", method = {RequestMethod.POST})
    public String toRegister(@ModelAttribute("user") User user, Model model, String confirmPassword, HttpServletResponse response) {
        String password = user.getPassword();
        if(!Objects.equals(confirmPassword, password)){
            model.addAttribute("messageRegisterError", "Register error: the password and the confirmed password is not equalled!");
            return "Rhinelab_resign";
        }else {

            List<User> userEmail = rhineLabMapper.checkEmail(user.getEmail());
            List<User> userName = rhineLabMapper.checkUserName(user.getName());
            if(userEmail.isEmpty()){
                if(userName.isEmpty()){
                    rhineLabMapper.userSave(user);
                    String userNameStore = user.getName();
                    Cookie userNameCookie = new Cookie("userNameOfRhineLab", userNameStore);
                    userNameCookie.setMaxAge(365 * 24 * 60 * 60);
                    response.addCookie(userNameCookie);
                    return "redirect:rhinelabmain";
                }else {
                    model.addAttribute("messageRegisterError", "Register error: the user name have been used!");
                    return "Rhinelab_resign";
                }


            }else {
                model.addAttribute("messageRegisterError", "Register error: the email have been used!");
                return "Rhinelab_resign";
            }
        }

    }

    @RequestMapping(value = "/new_1")
    public String toNewOne(Model model, HttpServletRequest request){
        if(checkUserNameInCookie(request)){
            return "new_1";
        }else {
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




}


