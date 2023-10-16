LXC(Linux Containers)
- 단일 컴퓨팅 시스템에 설치된 리눅스 운영체제 상에서, 별도의 리눅스 시스템을 운영할 수 있는 리눅스 커널 기술.
- 리눅스 운영체제 레벨에서 영역과 자원할당을 분리하여, 마치 별도의 시스템처럼 사용할 수 있음.
- docker는 리눅스 커널에 LXC 기술을 사용해서
    - 리눅스 컨테이너를 만들고
    - 리눅스 컨테이너 상에 별도로 구성된 파일 시스템에 시스템 설정 및 응용 프로그램을 실행할 수 있도록 하는 기술.
    - 최근에는 별도 컨테이너 기술을 구현하여 사용.

### docker 구성요소

docker engine
- 도커는 서버/클라이언트 구조로 이루어짐
-서버는 데몬프로세스 형태로 작동함
- 도커 데몬 프로세스에 요청하기 위해, 프로세스간 통신 기법이 필요하며, 도커는 이를 위해 Reat API를 사용
- docker command는 일종의 클라이언트 형태
- docker command를 하면, 내부적으로 Rest API를 사용해서 도커 데몬 프로세스를 호출함.

docker image
- 도커 컨테이너를 생성하기 위한 명령들을 가진 템플릿
- 여러 이미지들을 레이어로 쌓아서, 원하는 형태의 이미지를 만드는 것이 일반적.
    - ex) ubuntu 이미지에, 아파치 웹서버 이미지를 얹고, 웹서버 이미지를 만듬

docker container
- 도커이미지가 리눅스 컨테이너 형태로 실행한 상태(instance)
- 도커 컨테이너는 분리된 공간이므로, 도커데몬 프로세스를 통해 접속할 수도 있고, 내부에 들어가서 코드수정, 재실행 등도 가능.

다운로드 받을 이미지 선택
- docker search ubuntu

도커 이미지 다운로드
- docker pull ubuntu

다운로드 받은 목록 보기
- docker images
- docker image ls

다운로드 받은 이미지 삭제하기
- docker rmi 이미지ID(또는 이미지 레포지토리 이름)
- docker image rm 이미지ID(또는 이미지 레포지토리 이름)

컨테이너 생성
- 각 이미지는 컨테이너로 만들어줘야 실행가능함
- 이미지와 컨테이너는 각각 관리해줘야함
- 컨테이너 생성시, 도커에서 이름이 자동 부여됨.
- docker create ubuntu

생성된 컨테이너 확인
- docker ps : 현재 싱행중인 컨테이너 확인.

컨테이너 실행
- docker start 컨테이너이름

docker run 옵션
- -i: 컨테이너 입력을 열어놓는 옵션(주로 -it로 -t 옵션과 함께사용)
- -t: 가상 터미널을 할당하는 옵션
- --name: 컨테이너 이름을 설정하는 옵션
- -d: 컨테이너를 백그라운드에서 실행하는 옵션
- --rm: 컨테이너 종료 시 컨테이너를 자동으로 삭제하는 옵션
- -p: 호스트와 컨테이너 포트를 연결하는 옵션
- -v: 호스트와 디렉토리를 연결하는 옵션


도커에서 특정 경로의 index.html 파일 아파치로 실행
- docker run -d -p 9999:80 -v /home/ubuntu/2021_DEV_HTML:/usr/local/apache2/htdocs --name apacheweb2 httpd 

도커가 사용하고 있는 저장매체 현황 확인
- docker system df

실행중인 컨테이너 사용 리소스 확인
- docker container stats

실행중인 컨테이너에 명령 실행하기
- docker exec 옵션 컨테이너ID 명령인자

실행중인 컨테이너에 연결하기
- docker attach 컨테이너이름

모든 컨테이너 삭제
- docker stop $(docker ps -a -q) #모든 컨테이너 중지
- docker rm $(docker ps -a -q) #모든 컨테이너 삭제
- docker container prune # 정지된 컨테이너 삭제
- docker image prune # 실행중인 컨테이너 image 외의 이미지 삭제
- docker system prune # 정지된 컨테이너, 실행중인 컨테이너 이미지 외의 이미지, 볼륨, 네트워크 삭제


Dockerfile이란?
- docker 이미지를 작성할 수 있는 기능
- dockerfile 문법으로 이미지 생성을 위한 스크립트 작성, 이를 기반으로 이미지 생성 가능
- 이미지 커스터마이징 및, 배포시 주로 사용.

dockerfile 명령어
- FROM:base 이미지 설정
- WORKDIR:	작업 디렉터리 설정
- RUN:	이미지 빌드 시 커맨드 실행
- ENTRYPOINT:	이미지 실행 시 항상 실행되야 하는 커맨드 설정
- CMD:	이미지 실행 시 디폴트 커맨드 or 파라미터 설정
- EXPOSE:	컨테이너가 리스닝할 포트 및 프로토콜 설정
- COPY/ADD:	이미지의 파일 시스템으로 파일 또는 디렉터리 복사
- ENV:	환경 변수 설정
- ARG:	빌드 시 넘어올 수 있는 인자 설정

docker history
- 이미지 히스토리 확인
- docker history 컨테이너명

docker cp
- 컨테이너에서 특정 파일을 호스트 PC로 가져오는 명령
- 특정 파일 확인을 위해, 활용

docker commit 
- 컨테이너 변경사항을 이미지 파일로 생성.
- docker commit -m "add vim" mywebserver myhistory

docker diff 
- 컨테이너가 실행되면서, 본래의 이미지와 비교해서 변경된 파일 목록 출력 
- A: 파일추가, D: 파일삭제, C: 파일수정

docker compose 
- 여러 컨테이너를 모아서 관리하기 위한 툴
- front, db, backend 서버를 각각 컨테이너로 작성하고, 연결하여 동작하기 때문에 docker compose와 같은 컨테이너 관리 툴이 필요
```yaml
# docker compose 파일 포맷 버전 지정
version: '3'

# 컨테이너 설정
services:

#컨테이너에서 사용하는 volume 설정으로 대체 가능(옵션)
volumes:

#컨테이너 간 네트워크 분리를 위한 추가 설정(옵션)
networks:


services:
    db:
        image: mysql
        restart: always #컨테이너가 다운되었을 경우, 항상 재시작하는 설정
        volumes: #docker run 옵션 중 -v 옵션과 동일한 역할, 여러 volume을 지정할 수 있기 때문에, 리스트처럼 작성
            - ./mysqldata:/var/lib/mysql
        environment: #Dockerfile의 ENV 옵션과 동일
            - MYSQL_ROOT_PASSWORD=admin
            - MYSQL_DATABASE=testdb
        env_file: #환경변수값이 들어있는 파일을 읽을 떄
            - ./mysql_env.txt
        ports: # docker run -p 옵션과 동일
            - "3306:3306"
```

docker compose 명령어
- docker compose up --build -d #docker compose 시작 --build 는 이미지 재빌드가 필요할때 옵션
- docker compose stop # docker compose 중지
- docker compose down #docekr compose 에서 사용하는 컨테이너 삭제 명령


Nginx 기본 사용법
```docker
docker run -dit -p 80:8080 --name myos ubuntu:20.04
docker exec -it myos /bin/bash

apt-get update
apt-get install nginx=1.10.0-0ubuntu1
apt-get install vim
```
