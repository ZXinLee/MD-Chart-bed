package sso.utli;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class WebUtils {
    /**
     * 将数据以json格式写到客户端
     *
     * @param response 响应对象,
     * @param dateMap
     * @throws IOException 底层源码:调用此方法的源码都是抛出IOException, ServletException;
     *                             所以此方法抛出的异常需要为其本身或者子类异常,不然调用着需要抛出更多的异常
     *                     <p>
     *                     void commence() throws IOException, ServletException;
     *                     void onAuthenticationFailure(HttpServletRequest var1,
     *                     HttpServletResponse var2,
     *                     AuthenticationException var3) throws IOException, ServletException;
     */
    public static void writeJsonToClient(HttpServletResponse response, Map<String, Object> dateMap) throws IOException {
        //1. 设置响应数据的编码
        response.setCharacterEncoding("utf-8");
        //2. 告诉浏览器响应数据的内容类型及编码
        response.setContentType("application/json;charset=utf-8");
        //3. 将数据转换为json
        String jsonStr = new ObjectMapper().writeValueAsString(dateMap);
        //4. 获取输出流对象将json数据写到客户端
        //4.1 获取输出流对象
        PrintWriter out = response.getWriter();
        //4.2 通过输出流向客户端写数据
        out.println(jsonStr);
        out.flush();
    }
}
