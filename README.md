# Req1. 프라이빗 이더리움 네트워크 구축

## 1-1 가상 머신 구성

- ### VirtualBox 설치

  > https://www.virtualbox.org

  <br/>

- ### Vagrant 설치

  > https://www.vagrantup.com

  <br/>

- ### 이더리움 네트워크 구축용 VM 2대 생성

  - VM name : eth0, eth1

  <br>

- ### 각각의 VM에 개발 환경 구성

  - Go Ethereum (geth) 다운

    ```
    sudo apt-get update
    sudo apt-get install software-properties-common
    sudo add-apt-repository -y ppa:ethereum/ethereum
    sudo apt-get install ethereum
    ```

    <br>

  - geth data 저장할 폴더 생성

    ```
    mkdir ~/dev/eth_localdata 
    ```

    <br/>


- ### genesis.json 생성

  - 실행

    ```
    cd ~/dev/eth_localdata 
    vi genesis.json
    ```

  - 결과

    ```
    {
    "config": {
    "chainId": 921,
    "homesteadBlock": 0,
    "eip150Block": 0,
    "eip155Block": 0,
    "eip158Block": 0
    },
    "coinbase"   : "0x0000000000000000000000000000000000000000",
    "difficulty" : "0x10",
    "extraData"  : "",
    "gasLimit"   : "9999999",
    "nonce"      : "0xdeadbeefdeadbeef",
    "mixhash"    : "0x0000000000000000000000000000000000000000000000000000000000000000",
    "parentHash" : "0x0000000000000000000000000000000000000000000000000000000000000000",
    "timestamp"  : "0x00",
    "alloc" : {}
    }
    ```

    - Tip : vi에서 `ESC` 를 눌러 명령모드로 전환한 다음, `gg=G` 입력하면 자동 줄 정렬

  <br>

- ### genesis.json block 생성

    ```
    geth --datadir ~/dev/eth_localdata init ~/dev/eth_localdata/genesis.json
    ```

    <br>

## 1-2 이더리움 eth0 노드 구성

- eth0

```
geth --port 30303 --networkid 921 --maxpeers 2 --datadir ~/dev/eth_localdata  --rpcport 8545 --rpcaddr 0.0.0.0 --rpc --mine console 2>> ~/dev/eth_localdata/geth.log
```

<br>

## 1-3 이더리움 eth1 노드 구성

- eth1

```
geth --port 30303 --networkid 921 --maxpeers 2 --datadir ~/dev/eth_localdata  --rpcport 8545 --rpcaddr localhost --mine console 2>> ~/dev/eth_localdata/geth.log
```

<br>

### Geth 실행 시 ERROR

＊ Fatal: Failed to start mining: etherbase missing: etherbase must be explicitly specified 에러 시

=> 이더리움 계정 생성 먼저 해야 됨

<br>



# Req2. 이더리움 계정 생성

## 2-1 계정 생성

- 이더리움 계정 생성하는 세가지 방법

  1. vm console 에서

     ```
     geth --datadir ~/dev/eth_localdata account new
     ```

  2. geth console 에서 

     ```
     personal.newAccount()
     PASSWORD 입력
     ```

     또는

     ```
     personal.newAccount("PASSWORD 입력");
     ```

     

  생성할 때마다 keystore에 key 하나씩 추가됨.

<br/>


## 2-2 코인베이스(Coinbase) 설정

- 코인베이스 확인 (default : eth.accounts[0] 이 코인베이스로 지정돼있음)

  ```
  eth.coinbase
  ```

- 코인베이스 변경

  ```
  miner.setEtherbase(eth.accounts[n])
  ```

<br/>

## 2-3 마이닝(Mining) 시작

- <b>시작</b> 

  - 실행

    ```
    miner.start(1)  
    ```

  - 결과

    ```
    null
    ```

    

- <b>종료</b>

  - 실행

    ```
    miner.stop()
    ```

  - 결과

    ```
    null
    ```

    

- <b>마이닝 진행여부 확인</b> 

  - 실행

    ```
    eth.mining
    ```

  - 결과

    ```
    true
    ```

    또는

    ```
    false
    ```

<br/>

## 2-3 마이닝(Mining) 결과 확인

- **잔액 확인**

  - accounts[0] 계좌의 잔액 확인

  ```
  eth.getBalance(eth.accounts[0])
  ```

<br/>

- **생성된 블록 수 조회**

  ```
  eth.blockNumber
  ```

<br/>

- 블록의 상세 정보 조회

  - 0 부터 eth.blockNumber 까지 block이 존재

  ```
  eth.getBlock(0)
  ```

<br/><br/>

# Req3. 이더리움 트랜잭션 생성

## 3-1 트랜잭션(Transaction) 생성

#### 계정 간 이더(ETH) 전송 트랜잭션 생성

-----

<br/>

> ##### accounts[0] -> accounts[1] 송금 시나리오

- 송금을 진행할 계좌 **Unlock** 먼저 해줘야 함. Unlock안하고 진행 시 에러 발생<br/>

1. **accounts[0] 계정 Unlock** 

   - default는 300초간 Unlock 유지됨

   ```
   personal.unlockAccount(eth.accounts[0]) 
   ```

   ​	또는

   - geth 종료 시까지 Unlock 유지

   ```
   personal.unlockAccount(eth.accounts[0], "pass0", 0) 
   ```

   <br/>

2. **송금**

- accounts[0] 에서 accounts[1] 로 10 ethereum 송금
- 실행

```
eth.sendTransaction({from:eth.accounts[0], to:eth.accounts[1], value:web3.toWei(10, "ether")}) 
```

- 결과

```
transacion Hash 값
```

<br/>

=> But, <u>**송금 바로 적용안됨!!**</u>

<br/>

#### 트랜잭션 상태 조회

------

- 송금 후 return된 **transacion ID**를 통해 transaction을 조회

- 실행

  ```
  eth.getTransaction("transaction ID")
  ```

- 결과

  ```
  {
      blockHash: "0x0000000000000000000000000000000000000000000000000000000000000000",
      blockNumber: null,
      from: "0xfa501cae369107a5010d1842b0ada628ab5b9f33",
      gas: 90000,
      gasPrice: 18000000000,
      hash: "0xa85bfc2f487d8e2775795cae3dcf1f379feafe9e937378a1d9aba6b376a5e397",
      input: "0x",
      nonce: 0,
      r: "0xd3d9411517b7e3be7c56b7c56e01dbb7f209620223c680d7c0e4dea308807f91",
      s: "0x6e1abeb08674e0e4b6daa18fa7904e62874cf8bc5e48c3c66fc093f02e4e56f5", 
      to: "0xe3914bd95eb83a70b71255f80df8115fb9c41501",
      transactionIndex: 0,
      v: "0x1b",
      value: 10000000000000000000
  }
  ```

  -  **blockNumber**가 **null** 인 것을 확인할 수 있다 => 트랜잭션(송금)이 <u>완료되지 않은 상태</u>

<br/>

- 미처리 트랜잭션 확인

  ```
  eth.pendingTransacionts
  ```

  => 방금 생성된 트랜잭션이 포함돼있는 것을 확인할 수 있음

<br/><br/>

## 3-2 트랜잭션(Transaction) 결과 확인

#### 마이닝 재시작(일정 시간 수행)

-----

- 마이닝을 다시 시작

  ```
  miner.start(1)
  ```

<br/>

#### 트랜잭션 상태 조회

-----

- 미처리된 트랜잭션을 조회

- 실행

  ```
  eth.pendingTransacionts
  ```

- 결과

  ```
  []
  ```

  - 모든 트랜잭션이 처리된 것을 확인

<br/>

#### 마이닝에 따른 트랜잭션 처리가 완료되면 마이닝 중단

-----

- 마이닝 종료

  ```
  miner.stop()
  ```

  



# Req4. 스마트 컨트랙트 배포

## 4-1 eth0 노드 확인

#### eth0 VM에 대한 포트 포워딩 확인

-----

- 

<br/>

#### eth0 의 keystore를 json 파일로 저장

-----

- eth0에 접속해서 keystore 폴더로 진입

  ```
  vi ~/dev/eth_localdata/keystore
  ```

<br/>

- 각각의 키를 vi로 열어 내용을 복사한 뒤, 로컬 PC의 메모장에 각각 붙여넣고 .json 형태로 개별 저장



## 4-2 Metamask 설정

#### Metamask 설치

-----

- Chrome 웹 스토어에서 `메타마스크`  검색 후, 설치
  - *<u>DRM이 켜져있으면</u>, 설치·동작이 원활히 되지 않는 것 같다....*
- 계정 생성

<br/>

#### Metamask 의 Custom RPC 옵션 설정

-----

1. 우측 상단의 `이더리움 메인넷`을 클릭하여 네트워크 탭을 연다.

2. `사용자 정의 RPC` 를 선택하여 Custom RPC 옵션 설정

   1) 네트워크 이름  : eht0 (아무거나 해도 상관없음)

   2) 새로운 RPC URL : http://localhost:8545

3. `저장` 하기

<br/>

#### Metamask에서 계정 Import

-----

1. 우측 상단의 원형의 Profile 이미지를 클릭
2. `계정 가져오기` 탭을 클릭
3. '형식 선택' 을 `JSON 파일` 로 변경한 뒤, **Req4-1** 에서 저장한 keystore json 파일을 가져오기
4. 가져온 key에 해당하는 비밀번호 입력 후, `가져오기` 클릭

<br/>

#### Metamaske에서 계정 및 잔액 정보 확인

-----

1. 우측 상단의 `：` 을 클릭한 뒤,  `계정 상세보기` 

2. 가져온 계정의 hash값과, geth에서 해당 계정을 `eth.accounts[N]` 으로 조회했을 때 나온 hash 값 비교

3. Metamask에서 조회된 ETH 값과,

   geth에서 해당 계정의 잔고를 `web3.fromWei(eth.getBalance(eth.accounts[0]), "ether")` 으로 조회한 값 비교

<br/><br/>

## 4-3  스마트 컨트랙트 배포(Remix)

#### Remix 접속

-----

- http://remix.ethereum.org/ 으로 접속

<br/>

#### Deploy & Run Transaction 으로 이동

-----

- 좌측 사이드바의 세번째 탭인 `DEPLOY & RUN TRANSACTIONS` 으로 이동

<br/>

#### Environment 를 로컬 이더리움 네트워크와 연동

-----

1. ' ENVIRONMENT ' 부분을 `Web3 Provider` 로 변경
2. ' Web3 Provider Endpoint '를 `http://127.0.0.1:8545` 로 적용 후, `OK`
   - ' ENVIRONMENT ' 의 하단부에 작은 글씨로 `Custom (Network ID) network` 가 떴다면 성공
   - ' ACCOUNT ' 에 geth에서 생성한 계좌가 가져와졌다면 성공

<br/>

#### 기본 예제 중 1개를 선택하여 코드 내용 확인

-----

1. 제공된 스켈레톤 코드 실행하여 웹 열기
2. 상단 바에서 `Explorer` 클릭
3. `LATEST BLOCK` 과 `TRANSACIONTS` 확인

<br/><br/>

## 4-4 블록 정보 조회

#### 스켈레톤 프로젝트의 이더리움 네트워크 정보를 맞게 수정

-----

- `LATEST BLOCK` 과 `TRANSACIONTS` 에 아무 정보도 뜨지 않는다면, frontend 스켈레톤 코드의 index.js 수정

<br/>

#### 스켈레톤 프로젝트 구동

-----

- 제공된 스켈레톤 코드 실행하여 웹 열기

<br/>

#### 각 블록 및 트랜잭션 정보 확인

-----

- 블록 정보 확인

  1. `LATEST BLOCK` 의 Block Number를 눌러, Block 상세 조회
  2. geth에서 `eth.getBlock(Block Number)` 을 실행한 뒤, 비교

  <br/>

- 트랜잭션 정보 확인
  1.  `TRANSACIONTS`  Transaction ID를 눌러, Transaction 상세 조회
  2. geth에서 `eth.getTransaction("Transacion ID)` 을 실행한 뒤, 비교

<br/><br/>

# =================== END ====================