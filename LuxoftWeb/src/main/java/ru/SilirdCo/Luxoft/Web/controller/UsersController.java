package ru.SilirdCo.Luxoft.Web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.SilirdCo.Luxoft.Web.Model.User;
import ru.SilirdCo.Luxoft.Web.service.IService;
import ru.SilirdCo.Luxoft.Web.service.UserService;

@Controller
@RequestMapping("/users")
public class UsersController {
    private IService<User> service = new UserService();

    @RequestMapping(value = "/getUsers", method = RequestMethod.GET)
    public String getUsers(Model model)
    {
        model.addAttribute("users", service.getAll());
        return "User!sListDisplay";
    }
}
