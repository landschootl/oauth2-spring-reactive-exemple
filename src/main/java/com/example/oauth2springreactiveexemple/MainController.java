package com.example.oauth2springreactiveexemple;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class MainController {

    @GetMapping("/authorize")
    public Mono<String> index(@AuthenticationPrincipal Mono<OAuth2User> oauth2User) {
        return oauth2User
                .map(OAuth2User::getName)
                .map(name -> String.format("Hi, %s", name));
    }

    @GetMapping("/about")
    public String getAboutPage() {
        return "WebFlux OAuth example";
    }
}
