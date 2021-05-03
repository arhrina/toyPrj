package me.study.bootstart;

/**
 * ServletWebServerFactoryAutoConfiguration 서블릿 웹 서버 생성
 *      TomcatServletWebServerFactoryCustomizer 서버 커스터마이징
 * DispatcherServletAutoConfiguration 서블릿 만들고 등록
 */
public class TomcatExampleApplication { // 내장 servlet tomcat
//    public static void main(String[] args) throws LifecycleException {
//        Tomcat tomcat = new Tomcat(); // Spring boot starter에 tomcat dependency가 있다. embed tomcat을 라이브러리에서 확인 가능
//        tomcat.setPort(8080);
//
//        Context context = tomcat.addContext("/", "/");
//
//        HttpServlet servlet = new HttpServlet() {
//            @Override
//            protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//                PrintWriter writer = resp.getWriter();
//                writer.println("<html><head><title>");
//                writer.println("TOMCAT");
//                writer.println("</title></head>");
//                writer.println("<body><h1>Hello TOMCAT</h1></body>");
//                writer.println("</html>");
//            }
//        };
//
//        String servletName = "helloServlet";
//        tomcat.addServlet("/", servletName, servlet);
//        context.addServletMappingDecoded("/hello", servletName);
//
//        tomcat.getConnector();
//        tomcat.start();
//        tomcat.getServer().await();
//    }
}
