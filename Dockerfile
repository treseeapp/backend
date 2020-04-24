FROM tomcat
WORKDIR /usr/local/tomcat/webapps
COPY ./tartget/app.war ./ROOT.war
RUN ls -la
EXPOSE 8080
