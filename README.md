# Guranticket : 개런티켓
> - Guarantee + Ticket
> - 블록체인의 투명성을 활용한 공정한 티켓거래 플랫폼

## 목차
- [개요](#개요)
- [기능](#기능)
- [향후 전망](#향후-전망)
- [기술 스택](#기술-스택)
- [기술 설명](#기술-설명)
	- [ERD](#erd)
	- [SEQD](#seqd)
	- [REST API](#rest-api)
- [테스트 방법](#테스트-방법)

## 개요
![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/e6bacf4f-15b9-454e-a90b-da93d5d92ab4/Untitled.png](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/e6bacf4f-15b9-454e-a90b-da93d5d92ab4/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20200918%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20200918T013914Z&X-Amz-Expires=86400&X-Amz-Signature=051195c30ba204af026852a13417c6067c692956783cfb0cdd89712fd1cb8840&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22)
> 1. 발권 업계 및 이벤트 관리 분야에서 암표 거래로 인해 생긴 소비자 피해액은 수십억 달러<br>
> 2. 2016년 한해동안 공연 티켓을 대량으로 사들여 이를 다시 프리미엄가를 붙여 일반인들에게 되팔아 생기는 차익이 자그마치 50억 달러<br>
> 3. 전문가들에 따르면 인기 많은 공연/이벤트 일수록 암표상들의 주 타겟이 되며 전체 티켓의 4분의 1에 달하는 티켓이 정가보다 700% 비싼 가격으로 암시장에서 거래<br>

**=> 실제로 공연을 보기 위해 티켓을 구매하는 소비자들에게 불공정한 시장이 조성**


## 기능
|번호|기능|기대효과|
|:--:|----|--------|
|1|구매 수량 제한|공연당 살 수 있는 갯수의 제한을 둬서 사재기 방지|
|2|티켓 양도가 제한|블록에 기록된 최초 구매 가격을 통해 적정가에 가격에 양도할 수 있도록 함|
|3|공연사와 계약 보장|블록에 기록된 거래내역을 통해 계약 신뢰성 보장|
|4|구매자-사용자 불일치 시 입장불가|암거래를 통한 구매 행위를 막음|




## 향후 전망
|WEEK|TO DO|
|:--:|-----|
|**1**|**블록체인 개인 프로젝트**|
|**2**|**SPA 기본 틀 설계 & 개발환경 셋팅**|
|**3**|**API 설계 & TABLE 생성, 서버 구축**|
|4|웹 서비스 개발, 배포 자동화|
|5|블록체인 적용|
|6|디버깅 및 UCC 제작|

## 기술 스택
|PART|TECH|
|:--:|----|
|FRONTEND|`Vue.js` `Vuetify`|
|BACKEND|`Spring Boot` `MyBatis` `Lombok`|
|BLOCKCHAIN|`Go-Ethereum` `web3.js` `web3j` |
|DATABASE|`MySQL`|
|SERVER|`AWS EC2 Ubuntu 18.04` `Nginx` `Spring Boot Tomcat 8.0` `SSL`|
|CO-WORK|`GitLab` `Slack` `Notion` `Jira` `Google Docs` `ERD Cloud` `Dirgrams`|




## 기술 설명

### ERD
![ERD](./산출물/ER%20다이어그램/ERD.png)

### SEQD

> #### 회원

![SEQD](./산출물/시퀀스%20다이어그램/회원가입.jpg)
![SEQD](./산출물/시퀀스%20다이어그램/로그인.jpg)
![SEQD](./산출물/시퀀스%20다이어그램/비밀번호찾기.jpg)
<br>
> #### 공연

![SEQD](./산출물/시퀀스%20다이어그램/공연등록.jpg)
![SEQD](./산출물/시퀀스%20다이어그램/공연등록요청.jpg)
![SEQD](./산출물/시퀀스%20다이어그램/공연정보조회.jpg)
<br>
> #### 티켓

![SEQD](./산출물/시퀀스%20다이어그램/티켓구매.jpg)
![SEQD](./산출물/시퀀스%20다이어그램/티켓예매.jpg)
![SEQD](./산출물/시퀀스%20다이어그램/티켓판매.jpg)

### REST API
![REST_API](./산출물/REST%20API/REST%20API.jpg)

## 테스트 방법
> - URL : https://j3b101.p.ssafy.io/
> - TEST ID : test3
> - TEST PW : 3
> <br>*<i>현재 `로그인` `이더리움 블록 현황` `마이페이지` `이더리움 충전` 가능</i>