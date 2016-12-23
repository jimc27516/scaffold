FROM campbell/grails:latest
COPY . /app
RUN grails dependency-report
EXPOSE 8080
ENTRYPOINT ["grails"]
CMD ["run"]