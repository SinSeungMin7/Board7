-- menu_id 별로 sample data 200 건씩 생성해준다 
INSERT INTO BOARD (
      IDX
    , MENU_ID
    , TITLE
    , CONTENT
    , WRITER
    , REGDATE
    , HIT
)
WITH
BASE AS (
    SELECT NVL(MAX(IDX), 0) AS START_IDX
    FROM BOARD
),
NUMS AS (
    SELECT LEVEL AS SEQ_NO
    FROM DUAL
    CONNECT BY LEVEL <= 200
),
DATA AS (
    SELECT
          M.MENU_ID
        , N.SEQ_NO
        , ROW_NUMBER() OVER (ORDER BY M.MENU_ID, N.SEQ_NO) AS RN
    FROM MENUS M
    CROSS JOIN NUMS N
)
SELECT
      B.START_IDX + D.RN AS IDX
    , D.MENU_ID
    , '[' || D.MENU_ID || '] 샘플 게시글 제목 ' || D.SEQ_NO AS TITLE
    , '[' || D.MENU_ID || '] 메뉴의 샘플 게시글 내용입니다. 번호: ' || D.SEQ_NO AS CONTENT
    , 'user' || LPAD(MOD(D.SEQ_NO, 1000), 3, '0') AS WRITER
    , SYSDATE - MOD(D.SEQ_NO, 30) AS REGDATE
    , MOD(D.SEQ_NO * 3, 1000) AS HIT
FROM DATA D
CROSS JOIN BASE B;

commit;

----
페이징 성능을 위한 인덱스 추천

CREATE INDEX IDX_BOARD_MENU_IDX
ON BOARD (MENU_ID, IDX DESC);

이 인덱스는 MENU_ID별 게시글 목록을 최신순으로 가져올 때 유리합니다.
----------------------------------
로그인 후 데이터 수정, 삭제를 위해 작성자 변경
 UPDATE  BOARD
  SET    WRITER = 'admin'
  WHERE  MENU_ID = 'MENU01'
   AND   WRITER  IN ('user194', 'user196', 'user197', 'user199');
commit;  
    


