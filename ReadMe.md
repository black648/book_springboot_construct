1. 어노테이션 : 어노테이션은 최대한 클래스에 가깝게 두자..
    * Entity : 테이블과 링크될 클래스, 기본값으로 클래스의 카멜케이스 이름을 언더스코어 네이밍(_)으로 테이블 이름을 매칭한다.
    * Id : 해당 테이블의 PK 필드
    * GeneratedValue : PK 생성 규칙, IDENTIFY 옵션을 추가해여 auth_increment가 됨.
    * Column 생략가능하나 필드 옵션을 명확하게 설정하고 싶을 때
    * NoArgsConstructor : 기본 생성자 자동 추가, public Posts() {}
    * Builder : 클래스 빌더 패턴 클래스 생성
    * After : JUnit 단위 테스트 끝날 떄 수행됨. 보통은 배포 전 전체테스트를 수행할 때 테스트간 데이터 침범을 막기 위해 사용.
    * @Transactional(readOnly = true) : 트랜잭션 범위는 유지하되, 조회 기능만 남겨두어 조회 속도가 개선된다. 등록, 수정, 삭제 기능이 전혀없는 서비스 메소드에서 사용을 추천
    * @Target(ElementType.PARAMETER) : 메소드의 파라미터로 선언된 객에서만 사용가능 ElementType. 으로 단위 지정
    * @interface : 어노테이션 클래스로 지정

2. Entity 클래스
    * @Entity클래스를 가깝게 두고, 롬복 어느테이션을 그 위에 두었다. 이렇게 되면 코틀린과 같은 새 언어로 전환할 때 롬복이 필요 없을 경우 쉽게 삭제 할 수 있다..
    * Entity 클래스에서는 절대!! Setter 메소드 생성하지 않는다..
    * Entity 클래스와 Entity Repository는 함꼐 위치하는게 좋다.
      * 프로젝트 규모가 커져 도메인별로 프로젝트가 분리된다면 이때 Entity 클래스와 Reposiroty는 함께 움직여야 하므로 도메인 패키지에서 함꼐 관리하는게 좋다.

3. 스프링 계층(Layer) 분류
   * Web Layer : Controller, 필터, 인터셉터, 컨트롤러 어드바이스, JSP, Freemarker등 뷰 템플릿 영역 등 외부 요청과 응답에 대한 영역
   * Service Layer : @Service영역, @Transactional이 사용되는 영역
   * Repository Layer : DB 저장소 접근하는 영역
   * Dtos : 계층간 데이터 교환을 위한 객체
   * Domain Model : @Entity와 같이 개발대상을 모든 사람이 동일한 관점에서 이해하고 공유할 수 있도록 단순화 시킨 영역

4. CRUD
   * update 기능에서 쿼리날리는 부분이 없이도 update가 가능한 이유
     * JPA 영속성 컨텍스트 때문.. 엔티티를 영구 저장하는 환경. JPA의 엔티티 매니저가 활성화된 상태로 트랜잭션 안에서 데이터베이스 데이터를 가져오면 데이터는 영속성 컨텍스트가 유지된 상태.
     * 이 상태에서 해당 데이터의 값을 변경하면 트랜잭션이 끝나는 시점에 해당 테이블에 변경분을 반영한다.. 
     * 즉 Entity 값만 변경하면 별도로 update 쿼리를 날릴 필요가 없다.. 이 것을 더티 체킹이라 함..

5. 세션관리
   1) WAS 세션 사용
      * 일반적으로 ㅂ려다른 설정을 하지 않을 때 기본적으로 선택되는 방식
      * WAS에 세션이 저장되면 2대 이상의 WAS가 구동되는 환경에서는 톰캣들 간의 세션 공유를 위한 추가 설정이 필요하다.
   2) DB 세션저장소로 이용
      * 여러 WAS간의 공용 세션을 사용하기엔 가장 쉬운 방법
      * 로그인 요청마다 DB IO가 발생하여 성능상 이슈가 발생할 수 있음
      * 보통 로그인 요청이 많이 없는 백오피스, 사내 시스템 용도에서 사용
      * org.springframework.session:spring-session-jdbc
        * schema-h2.sql 혹은 mysql 부분의 테이블처럼 자동생성 혹은 생성
          * 로그인 시 생성한 SPRING_SESSION, SPRING_SESSION_ATTRIBUTES에 로우가 등록 됨.
   3) Redis, Memcached와 같은 메모리 DB 사용
      * B2C 서비스에서 많이 사용
      * 실제 서비스로 사용하기 위해 Embedded Redis와 같은 방식이 아닌 외부 메모리 서버가 필요함.
