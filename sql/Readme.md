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
- 해시조인은 이퀄 조건에서만 사용 가능

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




```sql
    select * from 
        (
            select 
                rownum as no,
                등록일자,
                번호,
                제목,
                회원명,
                게시판유형명,
                질문유형명,
                아이콘,
                (
                    select count(*) from 댓글
                    where 게시판번호 = A.게시판번호
                ) as 댓글개수, /** 서브쿼리는 조인으로 변경해야 함. */
            from  
                게시판 A,
                회원 B,
                게시판유형 C,
                질문유형 D
            where
                A.게시판유형 =:type
                and B.회원번호 = A.작성자번호
                and C.게시판유형 = A.게시판유형
                and D.질문유형 = A.질문유형
            order by A.등록일자 desc, A.질문유형, A.번호 
        )
        where rownum <= (:page*10)
    )
    where no >= (:page-1)*10 +1

    /**
        1. 먼저 회원, 게시판유형, 질문유형을 where rownum 있는 쪽으로 뺌.
        2. 그 이후 게시판 테이블 인덱스 생성(type, 등록일자, 질문유형, 번호)
     */


```
모범답안
```sql
    select 
        A.등록일자,
        A.번호,
        A.제목,
        A.회원명,
        A.게시판유형명,
        A.질문유형명,
        A.아이콘,
        (
            select count(*) from 댓글
            where 게시판번호 = A.게시판번호
        ) as 댓글개수
     from 
        (
            select 
                A.*,
                A.ROWNUM as NO
            from  
                게시판
            where
                게시판유형 =:type
                and 작성자번호 is not null
                and 게시판유형 is not null
                and 질문유형 is not null
            order by 등록일자 desc, 질문유형
        ) A
        where rownum <= (:page*10)
    )   A,
        회원 B,
        게시판유형 C,
        질문유형 D
    where 
        no >= (:page-1)*10 +1
        and B.회원번호 = A.작성자번호
                    and C.게시판유형 = A.게시판유형
                    and D.질문유형 = A.질문유형
    order by A.등록일자 desc, A.질문유형, A.번호 
```
<br/>

------
### sql 처리과정
  1. sql 파싱
  2. optimazation
     1. Query Transformer
     2. Estimator
     3. Plan Generator
  3. row-source generation
  4. sql 엔진 실행



#### 바인드 변수


#### 바인드 변수의 부작용(바인드변수 피킹)
```sql
select * from 아파트매물 where 도시 = ${CITY}
```
서울시나 경기도의 경우 테이블 풀스캔 하는게 낫지만, 그 이외의 도시일 땐 인덱스를 만드는게 좋을때 바인드 변수의 부작용이 생긴다.
  
#### 해결법
- 입력 값에 따라 sql 분리(UNION ALL)
- 적응적 커서 공유
- 예외적으로 Literal 상수값 사용
  - 조건절 값의 종류가 소수
  - 실행횟수가 작고 부등호나 BETWEEN 과 같은 범위 검색 조건 시


#### 선택적 검색 조건에 사용할 수 있는 기법 비교
- not null 컬럼일때는 nvl(isnull), decode 를 사용하는 것이 편하다.
- nvl 또는 decode 를 여러 컬럼에 대해 사용했을 때는, 그 중 변별력이 가자 좋은 컬럼 기준으로 한번만 분기가 일어나므로, 복잡한 옵션 조건을 처리하기 어렵다.
- null 값을 허용하고 인덱스 엑세스 조건으로 의미있는 컬럼이라면 union all 을 사용하여 명시적으로 분기해야 한다.
- 인덱스 엑세스 조건으로 참여하지 않는 경우, 즉 인덱스 필터 또는 테이블 필터 조건으로만 사용되는 컬럼이라면 어떤 방식을 사용해도 무방하다.
   

  