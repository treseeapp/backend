FROM tomcat
WORKDIR /usr/local/tomcat/
RUN mkdir uploads
COPY ./target/app.war ./webapps/ROOT.war
RUN ls -la
RUN rm /etc/localtime
RUN ln /usr/share/zoneinfo/Europe/Madrid /etc/localtime
EXPOSE 8080
