# sql 튜닝 기초

## index
```sql
1. select empno, empname, job, sal from emp where empno = 7789;
2. select empno, empname from emp where empno = 7789;
```
서버 엔진 기준으로 조회 성능은 같음.
<br/>

오라클에서 Null 은 인덱스에 포함하지 않음.(단일컬럼 기준)



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



quiz
```sql
/*부서는 100건, 사원은 10만건
어느 테이블을 먼저 읽는게 유리할까
*/
select A.*, B.* from 부서 A, 사원 B where B.부서코드 = A.부서코드
```
부서 먼저일 경우
1. 부서 1번에서 사원 테이블 풀스캔
2. 2번에서 사원테이블 풀스캔... 부서 100건 * 사원 10만건 모두 풀스캔

사원테이블 먼저일 경우
1. 사원 1번에서 부서 테이블 인덱스
2. 사원 2번 부터 테이블 인덱스 ... 사원 10만건 * 부서 1건
   
---

## JOIN 에서의 index 전략.

### NL JOIN (Nested Loop Join)
- 이중 for문의 형태.
- 대량의 데이터를 조인할 떄 비효율적
- 대용량이더라도 부분범위 처리 상황에서 빠른 속도를 낼 수 있음.
- 소량의 데이터처리할 때 효율적
- Prefetch, Buffer Planning 효과 이용 시 엑세스 비용 획기적 감소
- 가능한 Nested Loop 방식으로 처리하고, 비효율적일 때 Hash 조인과 Merge 조인 고려.
- random access 위주의 i/o 방식이르모 인덱스 구성 전략이 중요하다.
#### NL 조인 수행과정 분석
```sql
select /*+ orded use_nl(e) */
    e.empno, e.name, d.dname, e.job, e.sal
from dept d, emp e
where e.deptno = d.deptno ----1
and d.loc = 'SEOUL'       ----2
and d.gb = '2'            ----3
and e.sal > =1500         ----4 
order by sal desc;

/*
인덱스 상황
pk_dept: dept.deptno
dept_loc_idx: dept.loc
pk_emp: emp.empno
emp_deptno_idx: emp.dept
*/
```
위의 쿼리를 자바스크립트로 짠다면?
```js
for(let i=0; i< emp.length; i++){
    for(let j=0; j< dept.length; j++){
        if(dept.loc === 'SEOUL')
        if(dept.gb === '2')
        if(emp.deptno === dept.deptno)
        if(emp.sal >= 1500)
    }
}

```
1. dept 테이블을 메인으로 잡고있기 때문에 dept 테이블의 조건절 먼저 수행
2. nested loop 힌트로 emp 테이블에 강제로 명령하였으므로 emp 테이블의 인덱스 수행.


### Sort Merge Join
1. 소트단계 : 양쪽 집합을 조인 컬럼 기준으로 정렬
2. 머지단계 : 정렬된 양쪽 집합을 서로 머지

  

### Hash Join

#### 해시조인 사용기준
- 한쪽 테이블이 Hash Area에 담길 정도로 충분히 작아야 함.
- Build INput 해시 키 컬럼에 중복 값이 거의 없어야 함.
- 조인 컬럼에 적당한 인덱스가 없어 NL 조인이 비효율적일 떄
- 조인 컬럼에 인덱스가 있더라도 NL 조인으로 수행할 경우 Random 액세스 부하가 심할 때
- 소트머지 조인 하기에는 두 테이블이 너무 커 부하가 심할 때
- 수행빈도가 낮고 쿼리수행시간이 오래 걸리는 대용량 테이블을 조회할 때(ex. 하루에 한번 조회 수행하는 대용량 테이블)

#### 쿼리 작성 전략
- 논리적으로 가장 효율적인 경로로 sql을 작성.
  -  옵티마이저가 추천하는 인덱싱에 맞게 쿼리 작성.
  -  hint를 남발하는 것은 좋지 않다 


