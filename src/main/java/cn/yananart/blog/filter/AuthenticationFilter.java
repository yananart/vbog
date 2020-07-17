package cn.yananart.blog.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * 统一登录
 *
 * @author Yananart
 */
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public AuthenticationFilter() {
        super.setFilterProcessesUrl("/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
            ObjectMapper mapper = new ObjectMapper();
            UsernamePasswordAuthenticationToken authRequest;
            try (InputStream inputStream = request.getInputStream()) {
                Map<String, String> authenticationBean = mapper.readValue(inputStream, Map.class);
                authRequest = new UsernamePasswordAuthenticationToken(
                        authenticationBean.get(SPRING_SECURITY_FORM_USERNAME_KEY),
                        authenticationBean.get(SPRING_SECURITY_FORM_PASSWORD_KEY)
                );
            } catch (IOException e) {
                e.printStackTrace();
                authRequest = new UsernamePasswordAuthenticationToken("", "");
            }
            setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        } else {
            return super.attemptAuthentication(request, response);
        }
    }


}
