package com.jvmops.gumtree.report;

import com.jvmops.gumtree.city.City;
import com.jvmops.gumtree.city.CityService;
import com.jvmops.gumtree.city.Subscription;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Profile("web")
@Controller
@RequestMapping("/")
@AllArgsConstructor
public class ReportController {
    private ReportService reportService;
    private CityService cityService;

    @GetMapping
    public String notifications(Model model) {
        model.addAttribute("emailMapping", Subscription.builder().build());
        return "notifications";
    }

    @PostMapping
    public String sentReport(
            @Valid Subscription cityEmail
    ) {
        City city = cityService.getByName(cityEmail.getCity());
        reportService.initialEmail(
                city, cityEmail.getEmail());
        return "redirect:/?emailSent=true";
    }
}
