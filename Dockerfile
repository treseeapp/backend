FROM tomcat
WORKDIR /usr/local/tomcat/webapps
COPY app.war ./ROOT.war
RUN ls -la
EXPOSE 8080
