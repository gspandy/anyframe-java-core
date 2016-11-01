CREATE TABLE IDS(
	TABLE_NAME VARCHAR2(16) NOT NULL PRIMARY KEY,
	NEXT_ID DECIMAL(30) NOT NULL);	
CREATE TABLE MY_IDS(
	MY_KEY VARCHAR2(16) NOT NULL PRIMARY KEY,
	MY_ID DECIMAL(30) NOT NULL);		
CREATE SEQUENCE IDGEN_SEQ START WITH 1 INCREMENT BY 1;

INSERT INTO IDS VALUES('IDGEN_MOVIE',1);
INSERT INTO MY_IDS VALUES('test', 1);

commit;

