### @Configuration


스프링 부트?
- 스프링 프레임워크를 잘 쓰게 도와주는 도구의 모음
- 서블릿 컨테이너와 관련된 모든 번거로운 작업을 감춰줌
- 스프링과 각종 기술의 주요 인스스럭쳐 빈을 자동구성을 이용해서 자동으로 등록해줌.
- 외부 설정, 커스텀 빈 등록을 통해서 유연하게 확장 가능.


JDBC 구성
- 커넥션 설정 기본은 HikariCP


공부해야 할 자동구성정보
- PersistenceExceptionTranslationAutoConfiguration
- DataSourceAutoConfiguration
- DataSourceTransactionManagerAutoConfiguration
- JdbcTemplateAutoConfiguration
- TransactionAutoConfiguration