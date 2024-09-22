package mk.ukim.finki.diansvinarii.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

import mk.ukim.finki.diansvinarii.model.User;
import mk.ukim.finki.diansvinarii.service.JWTService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JWTServiceImpl implements JWTService {
    public String generateToken(UserDetails userDetails){
        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
        claims.put("firstName", ((User) userDetails).getFirstName());
        claims.put("id", ((User) userDetails).getId());

        System.out.println("FIRST NAME:" + ((User) userDetails));
        System.out.println("no cast:" + (userDetails));

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .setClaims(claims)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();


    }
    public String extractUserName(String token){
        return extraClaim(token,Claims::getSubject);
    }
    private <T> T extraClaim(String token, Function<Claims,T> claimResolvers){
        final Claims claims=extractAllClaims(token);
        return claimResolvers.apply(claims);

    }
    private Key getSignKey(){
        byte [] key= Decoders.BASE64.decode("413F4428472B4B625065536856605970337336763979244226452948404D6351");
        return Keys.hmacShaKeyFor(key);

    }
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();

    }
    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username=extractUserName(token);
        return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }

    @Override
    public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder().setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+604800000))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    public boolean isTokenExpired(String token){
        return extraClaim(token,Claims::getExpiration).before(new Date());
    }
}
