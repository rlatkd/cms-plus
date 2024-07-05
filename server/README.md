## 시작하기

1. 레포지토리 클론
```
   git clone [repo-url]
```
2. DB - MYSQL 시작 및 초기 데이터 설정
```
docker-compose up -d
```
3. 동작 테스트
```
    http://localhost:8080/api/vendor
```
```
[
    {
        "id": 1,
        "username": "vendor1",
        "name": "홍길동",
        "email": "vendor1@example.com",
        "phone": "010-1111-1111",
        "homePhone": null,
        "department": "영업부"
    },
    {
        "id": 2,
        "username": "vendor2",
        "name": "김영희",
        "email": "vendor2@example.com",
        "phone": "010-2222-2222",
        "homePhone": null,
        "department": "마케팅부"
    },
    ...
]
```

### [컨벤션 노션 페이지](https://standing-ball-696.notion.site/1cd7eae545044b77861da6ee05391e29?pvs=74)
