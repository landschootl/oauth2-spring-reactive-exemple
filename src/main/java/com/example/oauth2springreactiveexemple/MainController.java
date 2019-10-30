package com.example.oauth2springreactiveexemple;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
public class MainController {

    @GetMapping("/me")
    public Mono<String> me(@AuthenticationPrincipal Mono<OAuth2User> oauth2UserMono) {
        if (oauth2UserMono == null) {
            throw new UnauthorizeException();
        } else {
            return oauth2UserMono
                    .map(OAuth2User::getName)
                    .map(name -> String.format("Hi, %s", name));
        }
    }

    @GetMapping("/authenticate")
    public ResponseEntity authenticate() {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("http://localhost:4200"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @GetMapping("/about")
    public String getAboutPage() {
        return "WebFlux OAuth example";
    }

    @ExceptionHandler(UnauthorizeException.class)
    protected ResponseEntity handleUnauthorize(UnauthorizeException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e);
    }
}
