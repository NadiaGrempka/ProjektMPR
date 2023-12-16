package com.example.MPR_LAB03.exceptions;


import com.example.MPR_LAB03.Cat;
import com.example.MPR_LAB03.MyRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebController {

    private final MyRestService restService;

    @Autowired
    public WebController(MyRestService restService) {
        this.restService = restService;
    }

    @GetMapping(value = "/welcome")
    public String welcome(){
        return "welcome";
    }

    @GetMapping(value = "/viewAll")
        public String viewAll(Model model){
            model.addAttribute("cats", restService.getAllCats());
            return "index";
        }

    @GetMapping(value = "/addCat")
    public String addView(Model model){
        model.addAttribute("cat", new Cat("",0));
        return "addCat";
    }

    @RequestMapping(value = "/addCat", method = RequestMethod.POST)
    public String addCat(@ModelAttribute Cat cat, Model model){
        if (cat.getAge() == 0 || cat.getAge()<= 0){
            model.addAttribute("message", "Podano zły wiek");
        } else if (cat.getName().equals("") || cat.getName().contains(" ")) {
            model.addAttribute("message", "Podane niepoprawne imię");
        }else {
            restService.addNewCat(cat);
        }
        return "redirect:/viewAll";
    }

    @GetMapping(value = "/editCat/{id}")
    public String editView(Model model, @PathVariable("id") long id){
        model.addAttribute("cat", restService.getCatId(id).getId());
        return "editCat";
    }

    @RequestMapping(value = "/editCat/{id}", method = RequestMethod.POST)
    public String editCat(@ModelAttribute Cat cat, Model model, @PathVariable("id") long id){
        if (cat.getAge() == 0 || cat.getAge()<=0){
            model.addAttribute("message", "Podano zły wiek");
        } else if (cat.getName().equals("") || cat.getName().contains(" ")) {
            model.addAttribute("message", "Podano niepoprawne imię");
        }else {
            restService.updateCat(cat);
        }
        return "redirect:/viewAll";
    }

    @GetMapping(value = "/deleteCat/{id}")
    public String deleteView(Model model, @PathVariable("id") long id){
        model.addAttribute("cat", restService.getCatId(id).getId());
        return "deleteCat";
    }

    @RequestMapping(value = "/deleteCat/{id}")
    public String deleteCat(@ModelAttribute Cat cat, Model model, @PathVariable("id") long id){
        restService.deleteById(cat.getId());
        return "redirect:/viewAll";
    }


}
