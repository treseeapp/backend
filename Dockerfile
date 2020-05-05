FROM tomcat
WORKDIR /usr/local/tomcat/
RUN mkdir uploads
RUN sudo cp /usr/share/zoneinfo/Europe/Madrid /etc/localtime
COPY ./target/app.war ./webapps/ROOT.war
RUN ls -la
EXPOSE 8080
