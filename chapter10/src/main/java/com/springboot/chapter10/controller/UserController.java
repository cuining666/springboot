package com.springboot.chapter10.controller;

import com.springboot.chapter10.enumeration.SexEnum;
import com.springboot.chapter10.pojo.User;
import com.springboot.chapter10.service.UserService;
import com.springboot.chapter10.vo.ResultVo;
import com.springboot.chapter10.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/restful")
    public String index() {
        return "restful";
    }

    private UserVo changeToVo(User user) {
        UserVo userVo = new UserVo();
        userVo.setId(user.getId());
        userVo.setUserName(user.getUserName());
        userVo.setSexCode(user.getSex().getId());
        userVo.setSexName(user.getSex().getName());
        userVo.setNote(user.getNote());
        return userVo;
    }

    private User changeToPo(UserVo userVo) {
        User user = new User();
        user.setId(userVo.getId());
        user.setUserName(userVo.getUserName());
        user.setSex(SexEnum.getEnumById(userVo.getSexCode()));
        user.setNote(userVo.getNote());
        return user;
    }

    private List<UserVo> changeToVos(List<User> users) {
        List<UserVo> userVoList = new ArrayList<>();
        for (User user : users) {
            UserVo userVo = changeToVo(user);
            userVoList.add(userVo);
        }
        return userVoList;
    }

    @PostMapping("/user")
    @ResponseBody
    public User insertUser(@RequestBody UserVo userVo) {
        User user = changeToPo(userVo);
        return userService.insertUser(user);
    }

    @GetMapping("/user/{id}")
    @ResponseBody
    public UserVo getUser(@PathVariable("id") Long id) {
        User user = userService.getUser(id);
        return changeToVo(user);
    }

    @GetMapping("/user/{userName}/{note}/{start}/{limit}")
    @ResponseBody
    public List<UserVo> findUsers(@PathVariable("userName") String userName, @PathVariable("note") String note, @PathVariable("start") int start, @PathVariable("limit") int limit) {
        List<User> userList = userService.findUsers(userName, note, start, limit);
        return changeToVos(userList);
    }

    @PutMapping("/user/{id}")
    @ResponseBody
    public User updateUser(@PathVariable("id") Long id, @RequestBody UserVo userVo) {
        User user = changeToPo(userVo);
        user.setId(id);
        userService.updateUser(user);
        return user;
    }

    @PatchMapping("/user/{id}/{userName}")
    @ResponseBody
    public ResultVo changeUserName(@PathVariable("id") Long id, @PathVariable("userName") String userName) {
        int result = userService.updateUserName(id, userName);
        ResultVo resultVo = new ResultVo(result > 0, result > 0 ? "????????????" : "??????????????????" + id + "?????????");
        return resultVo;
    }

    @DeleteMapping("/user/{id}")
    @ResponseBody
    public ResultVo deleteUser(@PathVariable("id") Long id) {
        int result = userService.deleteUser(id);
        ResultVo resultVo = new ResultVo(result > 0, result > 0 ? "????????????" : "?????????" + id + "?????????");
        return resultVo;
    }

    @GetMapping("/user/name")
    public String changeUserNamePage() {
        return "changeUserName";
    }

    /**
     * ??????????????????changeUserName2??????
     * ?????????Springboot????????????application.properties??????spring.mvc.hiddenmethod.filter.enabled??????????????????true??????????????????false
     * @param id
     * @param userName
     * @return
     */
    @PatchMapping("/user/name")
    @ResponseBody
    public ResultVo changeUserName2(Long id, String userName) {
        int reuslt = userService.updateUserName(id, userName);
        ResultVo resultVo = new ResultVo(reuslt > 0, reuslt > 0 ? "????????????" : "??????????????????" + id + "?????????");
        return resultVo;
    }

}
