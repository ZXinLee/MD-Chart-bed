package com.cy.config.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RedirectAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private String redirectUrl;

    public RedirectAuthenticationSuccessHandler(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    /**
     * 登录成功以后,会执行此方法
     */
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            Authentication authentication) throws IOException, ServletException {
        httpServletResponse.sendRedirect(redirectUrl);
    }
}
