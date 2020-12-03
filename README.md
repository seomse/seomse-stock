# seomse-stock
- Team 섬세한 사람들의 주식시장 자동 투자 프로젝트
- [github.com/seomse](https://github.com/seomse)
# 개발환경
- open jdk 15
- erwin 파일은 7.3 기준으로 제작 되었습니다.

# 개요
- 분석을 위한 데이터 확보가 완료되어 본격적인 분석 프로젝트를 시작 합니다.
- 주식 시장 자동 매매를 위한 프로젝트 입니다.
- 다양한 분석 기법을 공유하고 발전시키기 위해 오픈화 합니다
- 데이터 구조는 doc/erd 디렉토리에 erwin 파일로 공유되어 있습니다
    - 데이터 공유를 원할경우 따로 연락 부탁 드립니다.
- 많은 분들이 이 프로젝트로 수익에 도움이 되었으면 좋겠습니다  


- 아래와 같이 2가지 프로젝트로 진행합니다
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
       

- 관련 프로젝트가 진행되면서 seomse-trading 도 연관해서 같이 많은 업데이트가 진행되고 있습니다.
    - https://github.com/seomse/seomse-trading
    
- 과거에 만들어논 부분을 오픈소스화 하고, 혹은 새로운 방식을 신규로 개발하고 있습니다.
    - 아직 오픈소스화 하지 않은 많은 pirvate module 이 존재합니다

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