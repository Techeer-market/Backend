

# Techeer Market
<img width="980" alt="techeermarkte" src="https://github.com/Techeer-market/.github/assets/95288297/7f17d217-85a3-4f24-a5a1-c77ee8b8d6a0">


<br>

## 🎀 Introduction

테커 내 중고거래를 위한 웹 플랫폼, 테커 마켓 👩🏻‍🌾
<br>
<br>

## 📚 Tech Stack

| Frontend | Backend | Database&Storage | DevOps | Test |
| --- | --- | --- | --- | --- |
| <img src="https://img.shields.io/badge/Vite-646CFF?style=for-the-badge&logo=Vite&logoColor=white"> <img src="https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=React&logoColor=white"> <img src="https://img.shields.io/badge/TypeScript-3178C6?style=for-the-badge&logo=TypeScript&logoColor=white"> <img src="https://img.shields.io/badge/styled--components-DB7093?style=for-the-badge&logo=styled-components&logoColor=white"> <img src="https://img.shields.io/badge/-React%20Query-FF4154?style=for-the-badge&logo=react%20query&logoColor=white"> ![React Router](https://img.shields.io/badge/React_Router-CA4245?style=for-the-badge&logo=react-router&logoColor=white) <br>![ESLint](https://img.shields.io/badge/ESLint-4B3263?style=for-the-badge&logo=eslint&logoColor=white) ![React Hook Form](https://img.shields.io/badge/React%20Hook%20Form-%23EC5990.svg?style=for-the-badge&logo=reacthookform&logoColor=white)<br><img src="https://img.shields.io/badge/Mock Service Worker-FF6A33?&style=for-the-badge"> ![Storybook](https://img.shields.io/badge/-Storybook-FF4785?style=for-the-badge&logo=storybook&logoColor=white) | <br><img src="https://img.shields.io/badge/SpringBoot-37814A?style=for-the-badge&logo=SpringBoot&logoColor=white"> <img src="https://img.shields.io/badge/springsecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white"> ![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)<br> | <img src="https://img.shields.io/badge/PostgreSQL-blue?style=for-the-badge&logo=PostgreSQL&logoColor=white"><br><img src="https://img.shields.io/badge/AWS-S3Bucket-yellow?style=for-the-badge&logo=Amazon"><img src="https://img.shields.io/badge/RDS-527FFF?style=for-the-badge&logo=AmazonRds&logoColor=white"> <br> | <img src="https://img.shields.io/badge/AWS-ElasticBeanstalk-orange?style=for-the-badge&logo=AWS-ElasticBeanstalk&logoColor=white"><br><img src="https://img.shields.io/badge/AWS-EC2-D1743D?style=for-the-badge&logo=AWS-EC2&logoColor=white"><br> ![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)<br> | <br><img src="https://img.shields.io/badge/junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white"> ![Jest](https://img.shields.io/badge/-jest-%23C21325?style=for-the-badge&logo=jest&logoColor=white)<br> |


<br>

## ⚙️ System Architecture

![시스템아키텍쳐](https://github.com/Techeer-market/.github/assets/95288297/4203200b-027d-465d-b71a-c6dc93f4f540)


<br>

## ⛺️ ERD

![ERD](https://github.com/Techeer-market/.github/assets/95288297/641c44d6-ce34-4297-95b8-e9fa28b2a089)



<br>

### 🔍 기능 구현 범위

- **회원**
    - 회원 가입 / 로그인
    - 마이페이지
        - 판매 / 구매 내역 리스트 확인
        - 좋아요 게시물 목록
        - 판매자 정보
- **중고 거래 시스템**
    - 중고거래 게시물 글쓰기
        - 판매할 물건 사진 업로드
        - 게시물 내용 (제목, 가격, 사진, 내용, 원하는 거래 위치)
    - 구매
        - 채팅 (판매자, 구매자 간 1:1 채팅)
        - 게시물 상태 (판매중→예약중→구매완료)
        - 상세 게시물 페이지
    - 상품 검색 (키워드)

<br>

## 💻 Installation Process

> <b>Docker repository clone </b>
> 

```
git clone --recursive <https://github.com/Techeer-market/Frontend.git>
git clone --recursive <https://github.com/Techeer-market/Backend.git>
```

<br>

> <b>Set .env in the backend folder </b>
> 

```
CLOUD_AWS_DB_HOST=
CLOUD_AWS_DB_DATABASE_NAME=
CLOUD_AWS_DB_USERNAME=
CLOUD_AWS_DB_PASSWORD=

CLOUD_AWS_S3_BUCKETNAME=
CLOUD_AWS_S3_REGION=       
CLOUD_AWS_ACCESS_KEY_ID=
CLOUD_AWS_SECRET_ACCESS_KEY=
```

<br>

## 📊 Flowchart

![flowchart](https://github.com/Techeer-market/.github/assets/95288297/1228566c-f532-40a8-9e26-1ed69d17caf1)


## 🔍 Features

- **회원가입/ 로그인**
    - JWT, SpringSecurity 를 이용한 회원가입/로그인 페이지
    - 로그아웃, 회원 탈퇴 구현
- **메인페이지**
    - InfiniteScroll을 이용한 게시물의 목록을 보여주는 페이지
    - AWS S3 람다를 이용해 썸네일 리사이징
- **전체 카테고리 페이지**
- **검색기능**
- **게시물 작성**
    - 대표이미지를 선택하여 지정할 수 있고, 갯수를 제한하여 총 4개 사진 게시 가능
    - 카카오맵API를 이용하여 지도상 거래 희망 장소를 선정 가능
- **게시물 수정하기**
    - 게시된 내용을 수정하여 게시 가능
- **게시물 상세보기**
    - 카카오맵API를 이용하여 지도상 거래 희망 장소 확인 가능
    - 좋아요 기능을 통해 게시물을 좋아요 목록에서 관리 가능
    - 구매자 와의 채팅 기능
- **채팅**
    - 구매자와 판매자 사이의 1:1 채팅 가능
    - STOMP의 pub/sub 메커니즘을 활용하여 텍스트 기반 채팅 구현
- **마이페이지**
    - 좋아요 목록, 판매내역, 구매내역 선택하여 확인
    - 계정/정보 관리에서 이메일, 비밀번호, 생년월일 등의 정보 수정 가능
- **판매내역/구매내역**
    - 사용자의 판매 내역과 구매내역을 관리 가능
    - 판매내역에서는 판매 중, 거래 완료 상태로 구분하여 관리 가능
  <br>

## ⛹🏼‍♀️ Postman

Frontend와 Backend 통신을 위한 API 문서화는 Postman과 노션을 사용했다

https://documenter.getpostman.com/view/27567744/2s9Ykn9N3U

https://documenter.getpostman.com/view/27567744/2s9Ykn9N3V

[API 명세서](https://www.notion.so/API-9e28dd92dc194e1c85f6e801877a2432?pvs=21) 

<br>
<br>

## Member 
| 이름 | 홍다연 | 김유라 | 조은주 | 하재민 | 권찬영 |
| --- |  --- | --- | --- | --- |  --- | 
| 역할  | Leader, Backend | Frontend | Frontend | Frontend |  Backend |
| 깃허브 | [Dayeon-Hong](https://github.com/Dayeon-Hong) | [yura0302](https://github.com/yura0302) | [dmswn1004](https://github.com/dmswn1004) | [penloo](https://github.com/penloo) | [fnzl54](https://github.com/fnzl54)

<br>
