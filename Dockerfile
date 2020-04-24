FROM tomcat
WORKDIR /usr/local/tomcat/webapps
RUN ls -la ../conf
COPY app.war ./ROOT.war
RUN ls -la
EXPOSE 8080
