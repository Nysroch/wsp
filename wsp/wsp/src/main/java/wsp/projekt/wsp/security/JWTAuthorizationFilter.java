package wsp.projekt.wsp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import wsp.projekt.wsp.constants.SecurityConstants;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {


    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }


    // Here we substitute the default method to implement our own, with our validations and also call the authenticaiton method
    // based in the type os authentication
    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(SecurityConstants.HEADER_STRING);

        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication); // setting the auth based on our method
        chain.doFilter(req, res);
    }


    // Reads the JWT from the Authorization header, and then uses JWT to validate the token
    // At the request we received our token (after login) and in every request we might put this
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.HEADER_STRING);
        if (token != null) {
            // Here it is parsing the token and verifying
            String user = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes())) //Here we decode the JWT received and 
                    .build()
                    .verify(token.replace(SecurityConstants.TOKEN_PREFIX, "")) // now we need to replace the "BEARER" and verify
                    // the token alone by verify()
                    .getSubject(); // and finally, here is the result

            if (user != null) {
                // If this is positive, we return an Object with the resullt
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }

            return null;
        }

        return null;
    }
}