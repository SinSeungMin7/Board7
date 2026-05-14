Microsoft Windows [Version 10.0.19045.6466]
(c) Microsoft Corporation. All rights reserved.

C:\Users\GG>sqlplus /nolog

SQL*Plus: Release 21.0.0.0.0 - Production on 수 4월 22 16:08:54 2026
Version 21.3.0.0.0

Copyright (c) 1982, 2021, Oracle.  All rights reserved.

SQL> conn /as sysdba
연결되었습니다.

SQL> alter session set "_ORACLE_SCRIPT"=true;
세션이 변경되었습니다.

SQL> create user spring identified by 1234;
사용자가 생성되었습니다.

SQL> grant connect, resource to spring;
권한이 부여되었습니다.

SQL> alter user spring default tablespace
  2  users quota unlimited on users;
사용자가 변경되었습니다.

SQL> conn spring/1234
연결되었습니다.

SQL> select * from tab;

선택된 레코드가 없습니다.

SQL>

---------------------------------------
-- 메뉴 목록
CREATE TABLE  MENUS (
    MENU_ID     VARCHAR2(6)    PRIMARY KEY
  , MENU_NAME   VARCHAR2(100)
  , MENU_SEQ    NUMBER(5)
);

INSERT INTO  MENUS VALUES ('MENU01','JAVA', 1);
COMMIT;

---------------------------------------------------
-- 회원 정보
CREATE TABLE   TUSER (
      USERID     VARCHAR2(12)   PRIMARY  KEY
    , PASSWD     VARCHAR2(12)   NOT  NULL
    , USERNAME   VARCHAR2(100)  NOT  NULL
    , EMAIL      VARCHAR2(320)
    , UPOINT     NUMBER(9)      DEFAULT   0
    , REGDATE    DATE           DEFAULT   SYSDATE
);

INSERT INTO  TUSER VALUES (
	 'admin','1234', '관리자', 'admin@green.com', 1000, sysdate);
INSERT INTO  TUSER VALUES (
	 'sky1','1234', '고객1', 'sky1@green.com', 1000, sysdate);	 
COMMIT;

--------------------------------------------
-- 멀티 게시판 정보

CREATE  TABLE   BOARD (
     IDX         NUMBER(8, 0)    PRIMARY KEY
   , MENU_ID     VARCHAR2(6)
      REFERENCES   MENUS ( MENU_ID )
   , TITLE       VARCHAR2(300)   NOT  NULL
   , CONTENT     VARCHAR2(4000) 
   , WRITER      VARCHAR2(12)
   , REGDATE     DATE            DEFAULT  SYSDATE
   , HIT         NUMBER(9, 0)    DEFAULT  0
);

INSERT INTO board (
    idx,
    menu_id,
    title,
    content,
    writer
) VALUES (  
    (SELECT NVL(MAX(IDX),0)+1 FROM BOARD),
    'MENU02',
    'JSP Hello',
    'JSP 게시판에 오신것을 환영합니다',
    'jsp' 
);
commit;

SELECT
    idx,
    menu_id,
    title,
    writer,
    TO_CHAR(regdate,'YYYY-MM-DD') REGDATE,
    hit
FROM
    board
WHERE
    MENU_ID = 'MENU01'
ORDER BY
    IDX DESC;










