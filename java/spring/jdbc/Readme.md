
##### Db 락
조회시 락 획득
- select for update
- 세션 1 조회 시점에 락을 가져가기 때문에 다른세션에서 해당 데이터를 변경할 수 없다. 
- 트랜잭션 커밋 시 락 반납.
  
  