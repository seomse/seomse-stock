# seomse-stock
- Team 섬세한 사람들의 주식시장 자동 투자 프로젝트 
- [github.com/seomse](https://github.com/seomse)
# 개발환경
- open jdk 15
- erwin 파일은 7.3 기준으로 제작 되었습니다.
- 아직 초기버젼이라 seomse-trading 를 같이 업데이트 하고 있기때문에 프로젝트가 의존되어 있습니다 개발할때 seomse-trading 프로젝트도 경로가 맞아야 합니다.
- settings.gradle 파일을 참고해주세요.
- 개발환경 세팅이 잘 되지 않으면 단톡방 혹은 email로 문의주세요.


# 개요
- 분석을 위한 데이터 확보가 완료되어 본격적인 분석 프로젝트를 시작 합니다.
- 주식 시장 자동 매매를 위한 프로젝트 입니다.
- 다양한 분석 기법을 공유하고 발전시키기 위해 오픈화 합니다
- 데이터 구조는 doc/erd 디렉토리에 erwin 파일로 공유되어 있습니다
    - 데이터 공유를 원할경우 따로 연락 부탁 드립니다.
- 많은 분들이 이 프로젝트로 수익에 도움이 되었으면 좋겠습니다  


- 아래와 같이 3가지 프로젝트로 진행합니다
    - 분석 모듈 (analysis)
        - 증권 시장 분석
        - seomse-trading 프로젝트 분석기능 연동
    - 데이터 모듈 (data)
        - 관리중인 증시 데이터 업데이트 / 빠진데이터가 없는지 검증 / 백업
        - 2차 가공 데이터 생성 테마별 동향, WICS별 동향 등. 가치주별 동향 등.
        - 원천 데이터는 stock-crawling 프로젝트에서 관리 (비공개)(문의바람)
    - 거래 (trade)
        - 매매 전략
        - backtest
    - 키움증권 java api module (kiwoom)
        - kiwoom 자바 api
        

- 관련 프로젝트가 진행되면서 seomse-trading 도 연관해서 같이 많은 업데이트가 진행되고 있습니다.
    - https://github.com/seomse/seomse-trading
    - 이 프로젝트는 비트코인, 해외선물 과 같은 투자 시장에서 같이 사용 될 에정이라 분리하였습니다.
    
- 비공개 저장소 관련
    - 비공개 저장소에는 데이터를 축척하는 API와 크롤링, 수익에 큰 영향을 주는 분석기법과 매매전략, 팀에서 운영하는 자동매매 관련 부분이 있습니다.
    - 위 내용에 관련된 문의는 메일로 부탁 드립니다.
    - 아직 오픈소스화 하지 않은 많은 private module 이 존재합니다. 관련부분은 순차적으로 오픈화 될 예정입니다.
    

# 용어사전
https://docs.google.com/spreadsheets/d/1ECaUMUoZkb-jMGUS7-9FRQtiiP-Zfa4gvpLZ4WKiHXg/edit#gid=0

# gradle
### analysis
implementation 'com.seomse.stock:analysis:0.1.3'
- etc 
    - https://mvnrepository.com/artifact/com.seomse.stock/analysis/0.1.3

### data
implementation 'com.seomse.stock:data:0.1.0'
- etc 
    - https://mvnrepository.com/artifact/com.seomse.stock/data/0.1.0

### trade
implementation 'com.seomse.stock:trade:0.1.0'
- etc 
    - https://mvnrepository.com/artifact/com.seomse.stock/trade/0.1.0

# communication
### blog, homepage
- [www.seomse.com](https://www.seomse.com/)
- [github.com/seomse](https://github.com/seomse)
- [seomse.tistory.com](https://seomse.tistory.com/)
- [seomse.github.io](https://seomse.github.io/)
- seomse.com

### 카카오톡 오픈톡
 - https://open.kakao.com/o/g6vzOKqb

### 슬랙 slack
- https://seomse.slack.com/

### email
 - comseomse@gmail.com
 
 
# main developer
 - macle
    -  [github.com/macle86](https://github.com/macle86)