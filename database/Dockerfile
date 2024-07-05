FROM mysql:5.7

#MySQL 환경 변수 설정
ENV MYSQL_ROOT_PASSWORD=1004
ENV MYSQL_DATABASE=cmsplusmain
ENV MYSQL_USER=user
ENV MYSQL_PASSWORD=1004

CMD ["mysqld", "--character-set-server=utf8mb4", "--collation-server=utf8mb4_unicode_ci", "--skip-character-set-client-handshake"]

#SQL 복사
COPY init.sql /docker-entrypoint-initdb.d/
