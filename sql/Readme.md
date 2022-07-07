# sql 튜닝 기초

## index
```sql
1. select empno, empname, job, sal from emp where empno = 7789;
2. select empno, empname from emp where empno = 7789;
```
서버 엔진 기준으로 조회 성능은 같음.
<br/>

오라클에서 Null 은 인덱스에 포함하지 않음.



### 인덱스란?
- 인덱스포 페이지에 저장


인덱스 아키텍쳐 이미지

#### B-tree index

#### nonclustered Index


#### clustered Index
- 데이터 페이지를 리프레벨에서 재정렬

#### Page split(nonclustered Index)
- 새로운 데이터 추가 시, 페이지 분할 후 데이터를 재정렬함.


#### Page split(clustered Index)
- 

<br>


 엔드게임 예매사례
 <br/>
 not in 및 조건절 서브쿼리로 인하여 옵티마이저가 풀스캔.

### Page 구조
- 레코드는 블럭 단위로 저장.
- 인덱스도 페이지 단위로 저장됨.
- Page headers, data rows, row offset
- 데이터 저장소 기본 단위
- 8KB 고정 크기(오라클은 디폴트로)
- 하나의 행은 여러 개의 페이지에 걸쳐 저장될 수 없고 단 하나의 페이지에만 저장 가능
- 헤더를 뺸 실제 데이터 행의 최대 행은 8060바이트


### Heap
- 테이블: 데이터 저장소 기본단위인 Data Page들의 집합
- heap: Data PAge 안의 대이터 행이 특정 순서가 없이 저장되고 이러한 데이터 페이지들이 순서에 상관없이 무작위 형태로 저장.
- 데이터 행이 저장되는 특정 순서가 없다.
- 연결된 리스트로 연결되어 있지 않다.