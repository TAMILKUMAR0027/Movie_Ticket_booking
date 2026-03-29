FROM jetty:9.4-jdk11

COPY target/TicketBookingJavaProject.war /var/lib/jetty/webapps/ROOT.war

EXPOSE 8080

CMD ["java", "-jar", "/usr/local/jetty/start.jar"]
