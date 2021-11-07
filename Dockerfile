# 이미지 베이스
FROM openjdk:11-jdk

# 환경 변수 -> spring jar 이 생성되는 위치
ARG JAR_FILE=build/libs/*.jar

# jar 파일을 app.jar 이라는 이름으로 복사
# 실행할 jar 파일명 통일 위해서 ~ jar 파일명 매번 달라지면 실행 번거로움
COPY ${JAR_FILE} app.jar

# 이미지를 container 로 씌월 때 jar 파일이 실행되도록 (=서버가 구동되도록) 커맨드 설정
# shell 스크립트 직접 작성해서 선언하는 것도 가능
# ex) run.sh : #!/bin/sh exec java -jar /app.jar
# ENTRYPOINT["run.sh"]
ENTRYPOINT ["java","-jar","/app.jar","--spring.profiles.active=prod"]
