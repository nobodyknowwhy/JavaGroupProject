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

import java.util.List;


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

    @GetMapping("/rhinelabmain")
    public String toMain(){
        return "rhinelabmain";
    }


    @PostMapping("/rhineLog")
    public String toLog(@ModelAttribute("user") User user, Model model) {
        String email = user.getEmail();
        String password = user.getPassword();

        List<User> users = rhineLabMapper.checkUser(email, password);

        if (users.isEmpty()) {
            model.addAttribute("message", "Email or password is incorrect!");
            return "Rhinelab_resign";
        } else {
            model.addAttribute("message", "Welcome " + user.getName() + ", successfully logged in!");
            return "rhinelabmain";
        }
    }


    @RequestMapping(value = "/rhineRegister", method = {RequestMethod.POST})
    public String toRegister(User usr, Model model, String confirmPassword) {

        String email = usr.getEmail();
        String password = usr.getPassword();
//        System.out.println(email + " " + password);

        List<User> users = rhineLabMapper.checkUser(email, password);

        if (users.isEmpty()) {

            model.addAttribute("message", "Email or password is incorrect!");

            return "Rhinelab_resign";
        } else {

            model.addAttribute("message", "Welcome " + usr.getName() + ", successfully logged in!");

            return "Rhinelab_resign";
        }
    }








}


