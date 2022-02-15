package com.example.demo.security;

import com.example.demo.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.Set;

@Component
public class JWTTokenProvider {

    // Bu classdan JWT yaeatishda, tokenning haqiqiy yoki haqiqqiy emasligi
    // tokenning nuddati tugamaganligini tekshirib berish uchun ishlatiladi

   // JWT token berilgan role va usernamelarni qo'shib base64 ga o'giradi

    @Value("${jwt.token.secret}")
    private  String secret;

    @Value("${jwt.token.validity}")
    private  long validMilliSecond;

    private  final UserDetailsService userDetailsService;

    public JWTTokenProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return  bCryptPasswordEncoder;
    }

    @PostConstruct
    public  void init(){
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }


    public String createToken(String username, Set<Role> roles){

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);

        Date now = new Date();  //token yasash uchunk/k
        Date validity = new Date(now.getTime() + validMilliSecond);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

    }

    boolean validateToken(String token){
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        if (claimsJws.getBody().getExpiration().before(new Date())){
            return  false;
        }
        return true;

    }

    public String resolveToken(HttpServletRequest request){   // requestda kelgan tokenni ajratib olish uchun
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken !=null && bearerToken.startsWith("Bearer")){
            return  bearerToken.substring(7);
        }
        return null;
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        UserDetails userDetails =this.userDetailsService.loadUserByUsername(getUser(token));
return  new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());

    }

    private String getUser(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

}
